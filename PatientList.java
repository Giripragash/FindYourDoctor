package csp.ela.develop.smarthealthcare;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PatientList extends Activity {
ArrayAdapter <String> adpt;
	
	private Context context;
	private Handler handler = new Handler();
	HashMap<String, String> aHash;
	private ProgressDialog simpleWaitDialog;
	private String TAG ="Vik";
	private String uid="";
	String aProdi;
	List<String> list = new ArrayList<String>();
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_list);
		
		//Typeface font = Typeface.createFromAsset(getAssets(), "font/FoglihtenPCS-068.otf");
		final Bundle getdata=getIntent().getExtras();
		if(getdata!=null){
			uid=getdata.getString("uid");
				
		}
		
		
		
		
		adpt = new ArrayAdapter<String>(this,R.layout.layout_img,R.id.name);
	
		listView = (ListView) findViewById(R.id.lv_imge);
		  AsyncCallWS task =new AsyncCallWS();
		task.execute();
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{
				
//				String i=adpt.getItem(position).toString();
//				 Log.i(TAG, i);
//				
//				 
//				
//				//HashMap<String, String> hm = adpt.getItem(position);
//				//String selectedFromList =(String) (listView.getItemAtPosition(position));
//				Intent iobj = new Intent(PatientList.this,ChatActivity.class);
//				iobj.putExtra("name",i);
//		            startActivity(iobj);
				String aListname=list.get(arg2);
				Log.e("name items",aListname);
				Log.e("hashmap",""+aHash);
				 aProdi=aHash.get(aListname);
					Log.e("Selected itme",aProdi);
				// Log.i(TAG, i);
				
				
				
				//HashMap<String, String> hm = adpt.getItem(position);
				//String selectedFromList =(String) (listView.getItemAtPosition(position));
//				Intent iobj = new Intent(PatientList.this,ChatActivity.class);
//			iobj.putExtra("fid",aProdi);
//				iobj.putExtra("uid",uid);
//		            startActivity(iobj);
//			
			
				
				
			}
		});
	}
	
	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            adpt.clear();
           
            call();
          
            return null;
        }

      
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (simpleWaitDialog != null)
			{
            	simpleWaitDialog.dismiss();
			}
            listView.setAdapter(adpt);
            adpt.notifyDataSetChanged();
        }

     
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        	adpt.clear();
            simpleWaitDialog = ProgressDialog.show(PatientList.this,"Wait", "Connecting...");
        }

      
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }
	
	public void call()
	{
		Log.v("Hi","Hello3");
		try
		{
			//Log.v("Hi","Hello4");
			String SOAP_ACTION = "http://tempuri.org/nameListService";
			String METHOD_NAME = "nameListService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/Service.asmx";
			Log.v("try","try");
			try
			{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			
			SoapObject res = (SoapObject) envelope.getResponse();
			
			Log.v("try", res.getPropertyCount()+"");
			aHash=new HashMap<String,String>();
			for (int i = 0; i < res.getPropertyCount(); i++)
			{
				String s=String.valueOf(res.getPropertyCount());
				Log.v("Hi",s);
				SoapObject res_in = (SoapObject) res.getProperty(i);
				Log.v("Hi",res_in.getProperty("pname").toString());
				
				String dId=res_in.getProperty("pid").toString();
				String dName=res_in.getProperty("pname").toString();
				list.add(res_in.getProperty("pname").toString());
				
				aHash.put(dName,dId);
				adpt.add(res_in.getProperty("pname").toString());
				adpt.notifyDataSetChanged();
				
			}
			}
			catch(Exception r)
			{
				Log.v("Hi","inner");
			}
		} catch (Exception e)
		{
			
			Log.v("Hi","catch");
			// str_buf.append(res_in.getProperty("Name")+","+res_in.getProperty("ID")+"\n");
			
			/*
			HashMap<String, String> hm = new HashMap<String, String>();
			//hm.put("name", res_in.getProperty("name").toString());
			
			//hm.put("date", res_in.getProperty("date").toString());
			String na=res_in.getProperty("cont").toString();
			String na1=res_in.getProperty("date").toString();
			Log.v("Hi",res_in.getProperty("cont").toString());
			Log.v("Hi",res_in.getProperty("date").toString());
			hm.put("con",na);
			hm.put("dat", na1);
			//Log.v("hi",hm.get(1));
			aList.add(hm);
			
			*/
			
			e.printStackTrace();
		}
	}
}
