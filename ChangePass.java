package csp.ela.develop.smarthealthcare;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

public class ChangePass extends Activity
{
	
	String uid;
	String utype;
	
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title, txt_changepass_title;
	EditText ed_oldpass, ed_newpass, ed_repass;
	Button btn_changepass;
	
	private Context context;
	private Handler handler = new Handler();
	
	private ProgressDialog simpleWaitDialog;
	private String TAG ="Vik";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pass);
		
		context=this;
		
		Intent intename = getIntent();
		utype = (String) intename.getSerializableExtra("utype");
		uid = (String) intename.getSerializableExtra("uid");
		
		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_changepass_title = (TextView) findViewById(R.id.txt_changepass_title);
		
		ed_oldpass = (EditText) findViewById(R.id.ed_oldpass);
		ed_newpass = (EditText) findViewById(R.id.ed_newpass);
		ed_repass = (EditText) findViewById(R.id.ed_repass);
		
		txt_home_title.setTypeface(tf);
		txt_changepass_title.setTypeface(tf);
		
		btn_changepass = (Button) findViewById(R.id.btn_changepass);
		
		btn_changepass.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (ed_newpass.getText().toString().equals(ed_repass.getText().toString()))
				{
					//call();
					AsyncCallWS task = new AsyncCallWS();
			        task.execute();
				}
				else
				{
					ed_oldpass.setText("");
					ed_newpass.setText("");
					ed_repass.setText("");
					Toast.makeText(getApplicationContext(), "Password not match ...!", Toast.LENGTH_SHORT).show();
				}
			}
		});
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
            simpleWaitDialog = ProgressDialog.show(ChangePass.this,"Wait", "Connecting...");
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
			String SOAP_ACTION = "http://tempuri.org/changepassService";
			String METHOD_NAME = "changepassService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("type", utype);
			request.addProperty("uid", uid);
			request.addProperty("oldpass", ed_oldpass.getText().toString());
			request.addProperty("pass", ed_newpass.getText().toString());
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
						
						ed_oldpass.setText("");
						ed_newpass.setText("");
						ed_repass.setText("");
						Toast.makeText(getApplicationContext(), "Failure ...!", Toast.LENGTH_SHORT).show();
					}
				});
				
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
