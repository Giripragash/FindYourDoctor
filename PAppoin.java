package csp.ela.develop.smarthealthcare;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class PAppoin extends Activity {
    Spinner alist;
	String did;
	Button appbutton;
	TextView textView1,textView2,textView3,textView4,textView5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pappoin);
		alist=(Spinner)findViewById(R.id.alist);
		textView1=(TextView)findViewById(R.id.tv1);
		textView2=(TextView)findViewById(R.id.tv2);
		textView3=(TextView)findViewById(R.id.tv3);
		textView4=(TextView)findViewById(R.id.tv4);
		textView5=(TextView)findViewById(R.id.tv5);
		appbutton=(Button)findViewById(R.id.appbutton);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        callBusList();
    	Intent intename = getIntent();
		did = (String) intename.getSerializableExtra("did");
		Log.i("Doctor id", did);
		alist.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				String select_item_string=arg0.getItemAtPosition(arg2).toString();
				//Toast.makeText(arg0.getContext(),"Selected: " + select_item_string,Toast.LENGTH_SHORT).show();
				
				callgetBus(select_item_string);
			}

		

		


			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub
 				
			}
		});
		appbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String a=textView5.getText().toString();
				 SmsManager sms = SmsManager.getDefault(); 
	                sms.sendTextMessage(a, null, "Your Appoinment Has fixed", null, null);  
	                Toast.makeText(getApplicationContext(),"Sms Send sucessfully to"+a ,20).show();
			}
		});
	
}
	private void callgetBus(String select_item_string) {
		try
		{
		
			
		String SOAP_ACTION = "http://tempuri.org/alist";
		String METHOD_NAME = "alist";
		String NAMESPACE = "http://tempuri.org/";
		String URL = "http://cyberstudents.in/android/SmartHealthCareService/Service.asmx";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("did",select_item_string);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.call(SOAP_ACTION, envelope);
		SoapObject response = (SoapObject) envelope.getResponse();
		textView1.setText(response.getProperty(0).toString());
		textView2.setText(response.getProperty(1).toString());
		textView3.setText(response.getProperty(2).toString());
		textView4.setText(response.getProperty(3).toString());
	     textView5.setText(response.getProperty(4).toString());
	

	
		} catch (Exception e)
		{

			e.printStackTrace();
		}
		 
	}
		
	

	private void callBusList() {
		try
 		{    Bundle getdata=getIntent().getExtras();
		if(getdata!=null){
		
			
			String a=(String) getdata.getSerializable("did");
 			
 			List<String> list = new ArrayList<String>();
 			Log.e("Enter","enter");
 			Log.e("InputData",a);
 			
 			/** Called when the activity is first created. */
 			String SOAP_ACTION = "http://tempuri.org/AllBusNumbers";
 			String METHOD_NAME = "AllBusNumbers";
 			String NAMESPACE = "http://tempuri.org/";
 			String URL = "http://cyberstudents.in/android/SmartHealthCareService/Service1.asmx";
 		
 			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
 			request.addProperty("did",a);
 		    
 			//request.addProperty("from","Gandhipuram - Maruthamalai");
 		     
 			
 			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
 			envelope.dotNet = true;
 			envelope.setOutputSoapObject(request);
 			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
 			androidHttpTransport.call(SOAP_ACTION, envelope);
 			SoapObject response = (SoapObject)envelope.getResponse();
 		     // Log.v("hi",response.getProperty(0).toString());
 			
 			for(int i=0;i<response.getPropertyCount();i++)
 			{
 				
 				String AAA=response.getProperty(i).toString();
 				String bbb=AAA.replace("anyType{BusNo=", "");
 				Log.v("bbb",bbb);
 				String ccc=bbb.replace("; }", "");
 				Log.v("cccc",ccc);
 				list.add(ccc);
// 				list.add((String) response.getProperty(i).toString());
// 				Log.v("hi",response.getProperty(i).toString());
 			}
 			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
 			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 			alist.setAdapter(dataAdapter);
		}
 		} catch (Exception e)
 		{
 			//tv.setText(e.getMessage());
 			e.printStackTrace();
 			Log.v("hi","hello");
 			
 		}
 		
	}}
