package csp.ela.develop.smarthealthcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReceptionNotification extends Activity
{
	
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title, txt_labnotifyhome_title;
	
	ListView listView1;
	List<HashMap<String, String>> aList;
	
	private Context context;
	private Handler handler = new Handler();
	
	private ProgressDialog simpleWaitDialog;
	private String TAG ="Vik";
	
	String did,pid,ff,rs;
	
	SimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reception_notification);
		
		context=this;
		
		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_labnotifyhome_title = (TextView) findViewById(R.id.txt_labnotifyhome_title);
		
		txt_home_title.setTypeface(tf);
		txt_labnotifyhome_title.setTypeface(tf);
		
		listView1 = (ListView) findViewById(R.id.listView1);
		
		aList = new ArrayList<HashMap<String, String>>();
		
		AsyncCallWS_list task = new AsyncCallWS_list();
        task.execute();
        
        listView1.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				final HashMap<String, String> hm = (HashMap<String, String>) listView1.getItemAtPosition(arg2);
				Log.i(TAG,hm.get("doc"));
				
				LayoutInflater li = LayoutInflater.from(ReceptionNotification.this);
				final View layoutFromInflater = li.inflate(R.layout.medicinebill_option, (ViewGroup) findViewById(R.id.linearlayout_medicinebill_option));
				final TextView txt_did = (TextView) layoutFromInflater.findViewById(R.id.txt_did);
				final TextView txt_pid = (TextView) layoutFromInflater.findViewById(R.id.txt_pid);
				final TextView txt_pres = (TextView) layoutFromInflater.findViewById(R.id.txt_pres);
				final TextView txt_total = (TextView) layoutFromInflater.findViewById(R.id.txt_total);
				
				txt_did.setText(hm.get("doc"));
				txt_pid.setText(hm.get("pat"));
				txt_pres.setText(hm.get("ff"));
				txt_total.setText(hm.get("rs"));
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ReceptionNotification.this)
				.setTitle("Payment").setView(layoutFromInflater)
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						dialog.dismiss();
					}
				})
				.setPositiveButton("Paid", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						did=txt_did.getText().toString();
						pid=txt_pid.getText().toString();
						ff= hm.get("ff");
						rs= hm.get("rs");
						
						AsyncCallWS task = new AsyncCallWS();
				        task.execute();
						
						dialog.dismiss();
					}
				});
				final AlertDialog alertDialog = builder.create();
				alertDialog.show();
			}
		});
	}
	
	private class AsyncCallWS_list extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            call_list();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (simpleWaitDialog != null)
			{
            	simpleWaitDialog.dismiss();
			}
            // Keys used in Hashmap
    		String[] from = { "doc", "pat", "ff" };

    		// Ids of views in listview_layout
    		int[] to = { R.id.doc, R.id.pat, R.id.test };

    		// Instantiating an adapter to store each items
    		// R.layout.listview_layout defines the layout of each item
    		adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_lab, from, to);
    		adapter.notifyDataSetChanged();

    		// Getting a reference to listview of main.xml layout file
    		ListView listView = (ListView) findViewById(R.id.listView1);

    		// Setting the adapter to the listView
    		listView.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            simpleWaitDialog = ProgressDialog.show(ReceptionNotification.this,"Wait", "Connecting...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }
	
	public void call_list()
	{
		try
		{
			/** Called when the activity is first created. */
			String SOAP_ACTION = "http://tempuri.org/getRecNotifyService";
			String METHOD_NAME = "getRecNotifyService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject res = (SoapObject) envelope.getResponse();
			for (int i = 0; i < res.getPropertyCount(); i++)
			{
				SoapObject res_in = (SoapObject) res.getProperty(i);
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("doc", res_in.getProperty("did").toString());
				hm.put("pat", res_in.getProperty("pid").toString());
				hm.put("ff", res_in.getProperty("ff").toString());
				hm.put("rs", res_in.getProperty("rs").toString());
				
				aList.add(hm);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            call();
            aList.clear();
            call_list();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (simpleWaitDialog != null)
			{
            	simpleWaitDialog.dismiss();
			}
            
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            simpleWaitDialog = ProgressDialog.show(ReceptionNotification.this,"Wait", "Connecting...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }
	
	public void call()
	{
		try
		{
			String SOAP_ACTION = "http://tempuri.org/updateRecNotifyService";
			String METHOD_NAME = "updateRecNotifyService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("did", did);
			request.addProperty("pid", pid);
			request.addProperty("fees_for", ff);
			request.addProperty("rs", rs);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			
			Object result = (Object) envelope.getResponse();
			if (result.toString().equals("Success"))
			{
				handler.post(new Runnable()
				{
					@Override
					public void run()
					{
						if (simpleWaitDialog != null)
						{
							simpleWaitDialog.dismiss();
						}
						Toast.makeText(getApplicationContext(), "Success ...!", Toast.LENGTH_SHORT).show();
					}
				});
			}
			else
			{
				handler.post(new Runnable()
				{
					@Override
					public void run()
					{
						if (simpleWaitDialog != null)
						{
							simpleWaitDialog.dismiss();
						}
						Toast.makeText(getApplicationContext(), "Failure ...!", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/*
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		AsyncCallWS_list task = new AsyncCallWS_list();
        task.execute();
	}
	*/
}
