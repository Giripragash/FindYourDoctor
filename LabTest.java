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
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LabTest extends Activity {

	String did;

	String fontPath;
	Typeface tf;

	private Context context;
	private Handler handler = new Handler();

	private ProgressDialog simpleWaitDialog;
	private String TAG = "Vik";

	TextView txt_home_title, txt_labtest_title;
	Spinner spin_pid;
	EditText ed_test;
	Button btn_submit;
	String name;
	List<String> plist;

	Button b1;
	Soabproductdetails sss;
	Customerview aCustomeradap;

	ListView listv;
	ArrayList<HashMap<String, String>> myArraylist = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lab_test);

		Button b12 = (Button) findViewById(R.id.ser_img_bt);
		listv = (ListView) findViewById(R.id.listView1);

		b12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				name = spin_pid.getSelectedItem().toString();
				sss = new Soabproductdetails(getApplicationContext());
				myArraylist = sss.Getmydetails(name);
				Log.e("Product arraylist", "" + myArraylist.size());
				aCustomeradap = new Customerview(getApplicationContext(),
						myArraylist, name);
				listv.setAdapter(aCustomeradap);
			}
		});

		context = this;

		Intent intename = getIntent();
		did = (String) intename.getSerializableExtra("did");

		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);

		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_labtest_title = (TextView) findViewById(R.id.txt_labtest_titles);

		ed_test = (EditText) findViewById(R.id.ed_test);

		spin_pid = (Spinner) findViewById(R.id.spin_pids);

		btn_submit = (Button) findViewById(R.id.btn_submitd);

		// txt_home_title.setTypeface(tf);
		// txt_labtest_title.setTypeface(tf);

		plist = new ArrayList<String>();

		AsyncCallWS_list task = new AsyncCallWS_list();
		task.execute();

		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!ed_test.getText().toString().equals("")) {
					AsyncCallWS task = new AsyncCallWS();
					task.execute();
				} else {
					Toast.makeText(getApplicationContext(), "Field Empty ...!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private class AsyncCallWS_list extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");
			call_patientlist();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			if (simpleWaitDialog != null) {
				simpleWaitDialog.dismiss();
			}
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
					LabTest.this, android.R.layout.simple_spinner_item, plist);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin_pid.setAdapter(dataAdapter);
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			simpleWaitDialog = ProgressDialog.show(LabTest.this, "Wait",
					"Connecting...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	public void call_patientlist() {
		try {
			/** Called when the activity is first created. */
			String SOAP_ACTION = "http://tempuri.org/getPatientListService";
			String METHOD_NAME = "getPatientListService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			// request.addProperty("Number", "3");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			// HttpTransportSE androidHttpTransport = new
			// HttpTransportSE(URL,6000);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			// Object result = (Object) envelope.getResponse();
			// tv.setText(result.toString());

			SoapObject res = (SoapObject) envelope.getResponse();
			// StringBuffer str_buf=new StringBuffer();
			for (int i = 0; i < res.getPropertyCount(); i++) {
				SoapObject res_in = (SoapObject) res.getProperty(i);

				plist.add(res_in.getProperty("pid").toString());
			}
			// tv.setText(str_buf.toString());

		} catch (Exception e) {
			// tv.setText(e.getMessage());
			e.printStackTrace();
		}
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
			if (simpleWaitDialog != null) {
				simpleWaitDialog.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			simpleWaitDialog = ProgressDialog.show(LabTest.this, "Wait",
					"Connecting...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	public void call() {
		try {
			String SOAP_ACTION = "http://tempuri.org/addlabtestService";
			String METHOD_NAME = "addlabtestService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("did", did);
			request.addProperty("pid",
					String.valueOf(spin_pid.getSelectedItem()));
			request.addProperty("test", ed_test.getText().toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			Object result = (Object) envelope.getResponse();
			if (result.toString().equals("Success")) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (simpleWaitDialog != null) {
							simpleWaitDialog.dismiss();
						}

						Toast.makeText(getApplicationContext(), "Success ...!",
								Toast.LENGTH_SHORT).show();
					}
				});

				finish();
			} else {

				handler.post(new Runnable() {
					@Override
					public void run() {
						if (simpleWaitDialog != null) {
							simpleWaitDialog.dismiss();
						}
						ed_test.setText("");
						Toast.makeText(getApplicationContext(), "Failure ...!",
								Toast.LENGTH_SHORT).show();
					}
				});

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
