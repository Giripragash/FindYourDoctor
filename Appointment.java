package csp.ela.develop.smarthealthcare;

import java.util.ArrayList;
import java.util.Arrays;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Appointment extends Activity
{
	String did;
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title, txt_apphome_title;
	
	DatePicker app_datepic;
	
	Button btn_submit,btn_cancel;
	
	private ListView app_list;
	private ArrayAdapter<String> listAdapter;
	ArrayList<String> appList;
	
	String selectedDate;
	
	private Context context;
	private Handler handler = new Handler();
	
	private ProgressDialog simpleWaitDialog;
	private String TAG ="Vik";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment);
		
		context=this;
		
		Intent intename = getIntent();
		did = (String) intename.getSerializableExtra("did");
		Log.i("Doctor id", did);
		
		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_apphome_title = (TextView) findViewById(R.id.txt_apphome_title);
		
		app_datepic = (DatePicker) findViewById(R.id.app_datepic);
		
		app_list = (ListView) findViewById(R.id.app_list);
		
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		txt_home_title.setTypeface(tf);
		txt_apphome_title.setTypeface(tf);
		
		
		appList = new ArrayList<String>();
		//planetList.addAll(Arrays.asList(planets));
		
		btn_submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				selectedDate = getCurrentDate();
				
				//call();
				AsyncCallWS task = new AsyncCallWS();
		        task.execute();
				
				listAdapter = new ArrayAdapter<String>(Appointment.this, R.layout.simplerow, appList);
				app_list.setAdapter(listAdapter);
			}
		});
		
		btn_cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				selectedDate = getCurrentDate();
				//call_cancel();
				AsyncCallWS_cancel task = new AsyncCallWS_cancel();
		        task.execute();
			}
		});
	}
	public String getCurrentDate()
	{
		StringBuilder builder = new StringBuilder();
		// builder.append("Current Date: ");
		builder.append(app_datepic.getDayOfMonth() + "/");
		builder.append((app_datepic.getMonth() + 1) + "/");// month is 0 based
		builder.append(app_datepic.getYear());
		return builder.toString();
	}
	
	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            call();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (simpleWaitDialog != null)
			{
            	simpleWaitDialog.dismiss();
			}
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            simpleWaitDialog = ProgressDialog.show(Appointment.this,"Wait", "Connecting...");
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
			String SOAP_ACTION = "http://tempuri.org/getAppService";
			String METHOD_NAME = "getAppService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			request.addProperty("did",did);
			request.addProperty("app_date",selectedDate);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			
			SoapObject result = (SoapObject) envelope.getResponse();
			
			int dat_len = result.getPropertyCount();
			Log.i("length",dat_len+"");
			
			appList.clear();
			
			
			if (dat_len > 0)
			{
				for (int i = 0; i < dat_len; i++)
				{
					if(result.getProperty(i)==null )
					{
						appList.add("No Appointment");
					}
					else if(result.getProperty(i).toString().contains("anyType{}"))
					{
						appList.add("No Appointment");
					}
					else
					{
						appList.add(result.getProperty(i).toString());
					}
					
					//Log.i("data",result.getProperty(i).toString());
				}
			}
			
			if(listAdapter != null)
			listAdapter.notifyDataSetChanged();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private class AsyncCallWS_cancel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            call_cancel();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (simpleWaitDialog != null)
			{
            	simpleWaitDialog.dismiss();
			}
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            simpleWaitDialog = ProgressDialog.show(Appointment.this,"Wait", "Connecting...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }
	
	public void call_cancel()
	{
		try
		{
			String SOAP_ACTION = "http://tempuri.org/cancelAppService";
			String METHOD_NAME = "cancelAppService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("did", did);
			request.addProperty("app_date", selectedDate);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			
			final Object result = (Object) envelope.getResponse();
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
						
						Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
					}
				});
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
