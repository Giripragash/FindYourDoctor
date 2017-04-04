package csp.ela.develop.smarthealthcare;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DoctorsListActivity extends Activity implements
		OnItemClickListener {
	String uid;
	EditText text;

	private ListView listView;
	private List<DoctorsBean> list = new ArrayList<DoctorsBean>();
	DoctorsAdapter objAdapter;
	Button search;
	private ProgressDialog pd;
	Button voice;
	List<DoctorsBean> list1 = new ArrayList<DoctorsBean>();

	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	protected static final int RESULT_SPEECH = 3;
	private Context context;
	private Handler handler = new Handler();
	String st;
	private ProgressDialog simpleWaitDialog;
	private String TAG = "Vik";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctors);

		Intent intename = getIntent();
		uid = (String) intename.getSerializableExtra("uid");

		context = this;
		search = (Button) findViewById(R.id.search);
		voice = (Button) findViewById(R.id.voice);
		text = (EditText) findViewById(R.id.text);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);

		// AsyncCallWS task = new AsyncCallWS();
		// task.execute();
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = text.getText().toString();
				if (!name.equalsIgnoreCase("")) {

					listView.setAdapter(null);
				
					// list=null;
					//
					call1(name);

					objAdapter = new DoctorsAdapter(DoctorsListActivity.this,
							R.layout.doctors_row, list1);
					Log.e("Count", "" + list1.size());
					listView.setAdapter(objAdapter);
					objAdapter.notifyDataSetChanged();

					// DoctorsAdapter objAdapter1 = new
					// DoctorsAdapter(DoctorsListActivity.this,
					// R.layout.doctors_row, list1);
					//
					// listView.setAdapter(objAdapter1);
					// objAdapter1.notifyDataSetChanged();
					//
				} else {
					Toast.makeText(getApplicationContext(),
							"Enter Doctor Name", Toast.LENGTH_LONG).show();
				}
			}
		});
		voice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

				try {
					startActivityForResult(intent, RESULT_SPEECH);
					Toast t = Toast.makeText(getApplicationContext(),
							"Speech ", Toast.LENGTH_SHORT);
					// txtText.setText("");
				} catch (ActivityNotFoundException a) {
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops* Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);

					t.show();
				}

			}
		});

		call();
		objAdapter = new DoctorsAdapter(DoctorsListActivity.this,
				R.layout.doctors_row, list);
		Log.e("Count", "" + list.size());
		listView.setAdapter(objAdapter);
		objAdapter.notifyDataSetChanged();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

		case RESULT_SPEECH:

			if (resultCode == RESULT_OK && null != data) {
				String name;

				ArrayList<String> text1 = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				Toast.makeText(getApplicationContext(), text1.get(0),
						Toast.LENGTH_LONG).show();
				String s = text1.get(0);
				text.setText(s);

			//	call1(s);


			}
		}
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View v, int position,
			long id) {
		final DoctorsBean bean = (DoctorsBean) listview
				.getItemAtPosition(position);
		// showCallDialog(bean.getid(), bean.getname());

		Intent iobj = new Intent(DoctorsListActivity.this,
				DoctorAppointment.class);
		iobj.putExtra("did", bean.getid());
		iobj.putExtra("pid", uid);
		iobj.putExtra("phone", st);
		startActivity(iobj);
	}

	private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");
			// call();
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
			simpleWaitDialog = ProgressDialog.show(DoctorsListActivity.this,
					"Wait", "Connecting...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	public void call() {
		try {
			/** Called when the activity is first created. */
			String SOAP_ACTION = "http://tempuri.org/getDoctorsListService";
			String METHOD_NAME = "getDoctorsListService";
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
				// str_buf.append(res_in.getProperty("Name")+","+res_in.getProperty("ID")+"\n");

				DoctorsBean obj = new DoctorsBean();
				obj.setid(res_in.getProperty("id").toString());
				obj.setname(res_in.getProperty("name").toString());
				obj.setSpec(res_in.getProperty("spec").toString());
				list.add(obj);
				Log.e("Hello", res_in.getProperty("name").toString());
			}
			// tv.setText(str_buf.toString());

		} catch (Exception e) {
			// tv.setText(e.getMessage());
			e.printStackTrace();
		}
	}

	public void call1(String name) {
		try {
			DoctorsAdapter objAdapter1;

			/** Called when the activity is first created. */
			String SOAP_ACTION = "http://tempuri.org/SearchDoctorList";
			String METHOD_NAME = "SearchDoctorList";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			// request.addProperty("Number", "3");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;

			request.addProperty("name", name);
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
				// str_buf.append(res_in.getProperty("Name")+","+res_in.getProperty("ID")+"\n");

				DoctorsBean obj = new DoctorsBean();

				obj.setid(res_in.getProperty("a").toString());
				obj.setname(res_in.getProperty("b").toString());
				obj.setSpec(res_in.getProperty("c").toString());
				st=res_in.getProperty("d").toString();

				list1.add(obj);
				Log.e("Hello", res_in.getProperty("a").toString()
						+ res_in.getProperty("b").toString()
						+ res_in.getProperty("d").toString());
			}
			// tv.setText(str_buf.toString());

		} catch (Exception e) {
			// tv.setText(e.getMessage());
			e.printStackTrace();
		}
	}

}
