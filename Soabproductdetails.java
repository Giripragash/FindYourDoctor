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

public class Soabproductdetails {
	String s1, s2, s3, s4, s5, s6, s7;
	Handler handle = new Handler();

	HashMap<String, String> ss = new HashMap<String, String>();
	HashMap<String, String> sss = new HashMap<String, String>();
	String out;
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	int flag = 0;
	public static String url = "http://cyberstudents.in/android/BloodBank/Service.asmx";
	private static final String NAMESPACE = "http://tempuri.org/";

	private static final String SOAP_ACTION = "http://tempuri.org/ImageDisplay";
	private static final String METHOD_NAME = "ImageDisplay";
	Context b;
	String aDept;

	public Soabproductdetails(Context a) {
		this.b = a;
	}

	public ArrayList<HashMap<String, String>> Getmydetails(String adeptss) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		request.addProperty("name", adeptss);

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
		} catch (IOException e) {

			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SoapObject res;
		try {
			res = (SoapObject) envelope.getResponse();
			flag = res.getPropertyCount();

			for (int i = 0; i < res.getPropertyCount(); i++) {
				String s = String.valueOf(res.getPropertyCount());

				Log.i("Hello", s);

				SoapObject res_in = (SoapObject) res.getProperty(i);
				sss = new HashMap<String, String>();
				sss.put("str", res_in.getProperty("name").toString());
				sss.put("str7", res_in.getProperty("dimage").toString());
				myList.add(sss);
				Log.v("MYLISTSIZE", "" + myList.size());
			}

		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myList;
	}
}
