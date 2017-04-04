package csp.ela.develop.smarthealthcare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DAdminview extends Activity {
	TextView t1,t2,t3,t4,t5,t6,t7;
	 Button build_btn;
	 DSoabproductdetails1  sss;
	 String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,u1;
	 Spinner s1;
	 Intent in;
	 String[][] data=new String[50][10];
		String username,password,uid,proid;
		Boolean internetconnection=false;
		int rcount=0;
		Handler handle=new Handler();
		String s_uid,s_pwd,nam,s_uname,s_pass,res;
	 ListView listv;
	   String it,it1;
	 String eid,type,lan,lang,phone;
	 int numProvinces = 13;
		ProgressDialog pbar,pbar1;
		ImageButton im4,im5;
		String s[]={"",""};
		 ArrayList<HashMap<String, String>> myArraylist=new ArrayList<HashMap<String,String>>();
		 HashMap<String,String> hash=new HashMap<String,String>();
		ArrayAdapter<String> adapter;
	 int flag=0;
		public static String url="http://cyberstudents.in/android/BloodBank/Service.asmx";
		private static final String NAMESPACE = "http://tempuri.org/";
		
		 private static final String SOAP_ACTION = "http://tempuri.org/ListService3";
		 private static final String METHOD_NAME = "ListService3";

			private static final String NAMESPACE1 = "http://tempuri.org/";

			 private static final String WORK_SOAP_ACTION1 = "http://tempuri.org/getUserlocationService";
			 private static final String WORK_METHOD_NAME1 = "getUserlocationService";
			 private static final String SOAP_ACTION1 = "http://tempuri.org/getDelete";
			 private static final String METHOD_NAME1 = "getDelete";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dadminview);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
	
			im4=(ImageButton)findViewById(R.id.back);
			im5=(ImageButton)findViewById(R.id.close);
		 
			sss=new DSoabproductdetails1 (getApplicationContext());
			sss.call();
			
			myArraylist=sss.Getmydetails();
			Log.e("Product arraylist", ""+myArraylist.size());
			DCustomerview1 aCustomeradap=new DCustomerview1(getApplicationContext(),myArraylist);
			listv=(ListView)findViewById(R.id.listView2);
			listv.setAdapter(aCustomeradap);
		  im4.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					in=new Intent(getApplicationContext(),Home.class);
					startActivity(in);
					finish();
					
				}
			});
	im5.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			System.exit(0);
			
		}
	});
	listv.setOnItemClickListener(new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

				final View v = arg1;
				AlertDialog.Builder aBuilder=new AlertDialog.Builder(DAdminview.this);
				aBuilder.setMessage("Perform action");
				aBuilder.setTitle("Do You Have Send SMS and View Location");
				aBuilder.setPositiveButton("View Location",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						final TextView aprod=(TextView)v.findViewById(R.id.txt5);
						String xyz = aprod.getText().toString();
				       
				      
				         String a[]=xyz.split(":");
				      it=a[1];
				      method(it);
				       Toast.makeText(getApplicationContext(), it,Toast.LENGTH_LONG).show();
						
					}
				});
				aBuilder.setNegativeButton("Deleted Donor",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {

						final TextView qty3=(TextView)v.findViewById(R.id.txt5);
						String xyz = qty3.getText().toString();
				       
				      
				         String a[]=xyz.split(":");
				      it1=a[1];

				       Toast.makeText(getApplicationContext(), it1,Toast.LENGTH_SHORT).show();
				       call(it1);
						
					}
				});
			AlertDialog all=aBuilder.create();
			all.show();

		}
	});
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void method(String a){
		try {
			final String b=a;
			
			internetconnection = checkInternetConnection();
			if(internetconnection) {
				
				pbar = ProgressDialog.show(DAdminview.this,"Wait","Connecting To Server");
		pbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				
			
				new Thread() {
					public void run() {
						
						SoapObject request = new SoapObject(NAMESPACE1, WORK_METHOD_NAME1);
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						request.addProperty("name",b);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
						try {
						try {
							androidHttpTransport.call(WORK_SOAP_ACTION1, envelope);
						} catch (XmlPullParserException e) {
							display("Error While calling Web Method");
							e.printStackTrace();
						}
						SoapObject response = (SoapObject) envelope.getResponse();
						pbar.dismiss();
						rcount= response.getPropertyCount();
						
						
						if(rcount>0) {
							int i=0;
							while (i<rcount) {
								SoapObject res = (SoapObject) response.getProperty(i);
								if(!res.getProperty("lat").toString().equalsIgnoreCase("")) {
								data[i][0]=res.getProperty("lat").toString();
								data[i][1]=res.getProperty("lan").toString();
								
								}
								i++;
							}
							
						} else {
							
							runOnUiThread(new Runnable() {
						        @Override
							        public void run() {
						        	display("No Contents Found");
							        	
							        }
							    });
							
						}
						
						handle.post(new Runnable() {
							@Override
							public void run() {
								for(int j=0;j<rcount;j++) {
									for(int k=0;k<2;k++) {
							
								a1=s[k]+data[j][0];
								a2=	s[k]+data[j][1];
								
									}
								}
							Log.e("Lat", a1);
							Log.e("Log", a2);
							Uri uri =Uri.parse("http://maps.google.com/?q="+a2+"+"+a1+"");
							Intent intent = new Intent(Intent.ACTION_VIEW, uri);
							startActivity(intent);
							}
						});
						
						 
							
						
						} catch (Exception e) {
							e.printStackTrace();
							runOnUiThread(new Runnable() {
						        @Override
							        public void run() {
						        	display("Error in Retriving Information");
							        }
							    });
						e.printStackTrace();	
						}
						
						
						}
					
						
						}.start();
				
			
			}
			
		
			
			
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	private boolean checkInternetConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		// test for connection
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "No Network Connection ", Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	public void display(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	public void call(final String aa){
		try{
			final String bb=aa;
			pbar1 = ProgressDialog.show(DAdminview.this, "Wait for Some Time","Registering in Online Help Service");
			pbar1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			SoapObject request = new SoapObject(NAMESPACE1,METHOD_NAME1);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet=true;
			//if((ed1.getText().length()!=0)&&(ed1.getText().length()!=0)&&(ed3.getText().length()!=0)&&(ed4.getText().length()!=0)&&(ed5.getText().length()!=0)&&(ed6.getText().length()!=0)&&(ed7.getText().length()!=0)&&(ed8.getText().length()!=0)){
			request.addProperty("name",bb);
			
			
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);				
					
			try {			
			try {
				androidHttpTransport.call(SOAP_ACTION1, envelope);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (XmlPullParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
		
			
			final SoapObject result=(SoapObject)envelope.bodyIn;	   
			//String resp = result.getProperty(0).toString();
			
			
			int rcount = (result.getPropertyCount());
			
			pbar1.dismiss();
			
			
				runOnUiThread(new Runnable() {
			        @Override
				        public void run() {
				        	display(result.getProperty(0).toString());
				        }
				    });
				
				
			
			} catch (Exception e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
			        @Override
				        public void run() {
			        	pbar1.dismiss();
			        	
			        	display("Error in Retriving Information");
				        }
				    });
			e.printStackTrace();	
			}
			
	
		
		
	
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}