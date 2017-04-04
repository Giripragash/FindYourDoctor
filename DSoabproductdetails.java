package csp.ela.develop.smarthealthcare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

public class DSoabproductdetails  {
	String s1,s2,s3,s4,s5,s6,s7;
	Handler handle=new Handler();

	HashMap<String,String> ss=new HashMap<String, String>();
	HashMap<String,String> sss=new HashMap<String, String>();
	String out;
	ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
	int flag=0;
	public static String url="http://cyberstudents.in/android/BloodBank/Service.asmx";
	private static final String NAMESPACE = "http://tempuri.org/";
	
	 private static final String SOAP_ACTION = "http://tempuri.org/ListService4";
	 private static final String METHOD_NAME = "ListService4";
	Context b;
	String aDept;
	public DSoabproductdetails(Context a){
		this.b=a;


	}
	public void call(String dept)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

		aDept=dept;
		Log.v("Hi",dept);
		try
		{
			Log.v("Hi","Hello4");
			//pbar = ProgressDialog.show(Adminview.this, "Wait for Some Time","Registering in Online Help Service");
			//pbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;

			request.addProperty("bgroup",dept);


			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			SoapObject res = (SoapObject) envelope.getResponse() ;


			flag=res.getPropertyCount();
			for (int current = 0; current < flag; current++)
			{ 

				for (int i = 0; i < res.getPropertyCount(); i++)
				{
					String s=String.valueOf(res.getPropertyCount());

					Log.i("Hello", s);

					SoapObject res_in = (SoapObject) res.getProperty(i);


					//			   t1.setText(res_in.getProperty("proid").toString());
					//			
					//			  t3.setText(res_in.getProperty("product").toString());
					//				t4.setText(res_in.getProperty("category").toString());
					//				out=res_in.getProperty("image1").toString();
					//				
					//				t5.setText(res_in.getProperty("brand").toString());
					//				t6.setText(res_in.getProperty("maxcost1").toString());
					 s1=res_in.getProperty("name").toString();
					    s2=res_in.getProperty("bgroup").toString();
				s3=res_in.getProperty("weight").toString();
					   s4=res_in.getProperty("sufferd").toString();
					 s5=res_in.getProperty("previous").toString();
					   s6=res_in.getProperty("optionselect").toString();
					   s7=res_in.getProperty("phone").toString();
					   out=res_in.getProperty("dimage").toString();
					   
//					s1=res_in.getProperty("proid").toString();
//					 s2=res_in.getProperty("product").toString();
//					 s5=res_in.getProperty("deptname").toString();
//					 out=res_in.getProperty("image1").toString();
//					 s3=res_in.getProperty("maxcost1").toString();
//					 s4=res_in.getProperty("qty").toString();
					//Log.v("Hello", res_in.getProperty("proid").toString());
					//Log.v("Hello", res_in.getProperty("deptname").toString());
					//adpt.add("Sno: " + res_in.getProperty("Sno").toString() + "\nSam:" + res_in.getProperty("Sam").toString()+ "\nEfficience:" + res_in.getProperty("Efficience").toString()+ "\nProduction:" + res_in.getProperty("Production").toString()+ "\nWIP:" + res_in.getProperty("WIP").toString()+ "\nWI:" + res_in.getProperty("WI").toString());
					//adpt.notifyDataSetChanged();
					 Log.v("Hello4",s1);
						Log.v("Hello4",s2);
						Log.v("Hello4",s3);
						ss.put("str",s1);
						ss.put("str1",s2);
						ss.put("str2", out);
						ss.put("str3",s3);
						ss.put("str4",s4);
						ss.put("str5", s5);
						ss.put("str6", s6);
						ss.put("str7", s7);
						list.add(ss);
						Log.v("Hello2",""+ list.size());
						//ttobj.speak("prodcut id"+" "+a1+" "+"department name"+" "+a2+" "+"category"+" "+a3+" "+"brand"+" "+a4+" "+"product"+" "+a5+" "+"cost"+" "+a6, TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			//            byte[] decodedString = Base64.decode(out, Base64.DEFAULT);
			//			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			//			//writeFile(decodedString, "/sdcard/" + text);
			//			//Toast.makeText(getApplicationContext(), "File Stored : /sdcard/" + text, Toast.LENGTH_SHORT).show();
			//			i.setImageBitmap(decodedByte);
			//listView.setAdapter(adpt);
			// adpt.notifyDataSetChanged();

		} catch (Exception e)
		{
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


	public ArrayList<HashMap<String, String>> Getmydetails(String adeptss)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		
				StrictMode.setThreadPolicy(policy);
		ArrayList<HashMap<String,String>> myList=new ArrayList<HashMap<String,String>>();
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;

		request.addProperty("bgroup",adeptss);


		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SoapObject res;
		try {
			res = (SoapObject) envelope.getResponse();
			flag=res.getPropertyCount();
			//for (int current = 0; current < flag; current++)
			//{ 

				for (int i = 0; i < res.getPropertyCount(); i++)
				{
					String s=String.valueOf(res.getPropertyCount());

					Log.i("Hello", s);

					SoapObject res_in = (SoapObject) res.getProperty(i);
					sss=new HashMap<String, String>();
					sss.put("str", res_in.getProperty("name").toString());
					sss.put("str1", res_in.getProperty("bgroup").toString());
					sss.put("str2", res_in.getProperty("weight").toString());
					sss.put("str3",res_in.getProperty("sufferd").toString());
					sss.put("str4", res_in.getProperty("previous").toString());
					sss.put("str5", res_in.getProperty("optionselect").toString());
					sss.put("str6", res_in.getProperty("phone").toString());
					sss.put("str7", res_in.getProperty("dimage").toString());
					//Log.v("Hello4",res_in.getProperty("dimage").toString());
					myList.add(sss);
					Log.v("MYLISTSIZE",""+ myList.size());
				}
				
		
				//Log.v("Hello2",""+ myList.size());

		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		

			return myList;
		


	}
	}
