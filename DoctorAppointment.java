package csp.ela.develop.smarthealthcare;

import java.util.ArrayList;
import java.util.Arrays;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorAppointment extends Activity
{
	String did;
	String pid;
	
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title, txt_apphome_title;
	
	DatePicker app_datePicker1;
	Spinner spin_time;
	Button btn_submit;
	
	String[] timings;
	ArrayList<String> time_List;
	
	String[] app_timings;
	ArrayList<String> app_timings_List;
	
	String selectedDate;
	String col_ind;
	String time;
	
	private Context context;
	private Handler handler = new Handler();
	
	private ProgressDialog simpleWaitDialog;
	private String TAG ="Vik";
	String phone;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_appointment);
		
		context=this;
		
		Intent intename = getIntent();
		did = (String) intename.getSerializableExtra("did");
		pid = (String) intename.getSerializableExtra("pid");
		phone = (String) intename.getSerializableExtra("phone");

		
		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_apphome_title = (TextView) findViewById(R.id.txt_apphome_title);
		
		app_datePicker1 = (DatePicker) findViewById(R.id.app_datePicker1);
		spin_time = (Spinner) findViewById(R.id.spin_time);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		
		txt_home_title.setTypeface(tf);
		txt_apphome_title.setTypeface(tf);
		
		timings = new String[] { "10.00 AM", "10.30 AM", "11.00 AM", "11.30 AM", "12.00 PM", "12.30 PM", "01.00 PM", "01.30 PM"
								,"02.00 PM", "02.30 PM", "03.00 PM", "03.30 PM", "04.00 PM", "04.30 PM", "05.00 PM", "05.30 PM"};
		time_List = new ArrayList<String>();
		time_List.addAll(Arrays.asList(timings));
		
		app_timings = new String[] { "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"
									,"c9", "c10", "c11", "c12", "c13", "c14", "c15", "c16"};
		app_timings_List = new ArrayList<String>();
		app_timings_List.addAll(Arrays.asList(app_timings));
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,time_List);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_time.setAdapter(dataAdapter);
		
		btn_submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				selectedDate = getCurrentDate();
				Log.i("Date", selectedDate);
				
				int ind=spin_time.getSelectedItemPosition();
				Log.i("index", ind+"");
				Log.i("ind_val", app_timings[ind]);
				time=String.valueOf(ind);
				
				col_ind=app_timings[ind];
				
				//call();
				AsyncCallWS task = new AsyncCallWS();
		        task.execute();
			}
		});
	}
	
	public String getCurrentDate()
	{
		StringBuilder builder = new StringBuilder();
		// builder.append("Current Date: ");
		builder.append(app_datePicker1.getDayOfMonth() + "/");
		builder.append((app_datePicker1.getMonth() + 1) + "/");// month is 0 based
		builder.append(app_datePicker1.getYear());
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
            simpleWaitDialog = ProgressDialog.show(DoctorAppointment.this,"Wait", "Connecting...");
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
			String SOAP_ACTION = "http://tempuri.org/setAppService";
			String METHOD_NAME = "setAppService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("did", did);
			request.addProperty("app_date", selectedDate);
			request.addProperty("time_ind", col_ind);
			request.addProperty("pid",pid);
			
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
						sms("8098062285","Your Appointment Fixed to "+"Doctor id : "+did+"\nDate : "+selectedDate+"\nTime : "+time);
						
						Toast.makeText(getApplicationContext(), "Success ...!", Toast.LENGTH_SHORT).show();
					}
				});
				finish();
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

	protected void sms(String ph, String msg) {
		// TODO Auto-generated method stub
		
		
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(ph, null, msg, null, null);
		Toast.makeText(getApplicationContext(), "SMS sent."+ph,
				Toast.LENGTH_LONG).show();
	}
}
