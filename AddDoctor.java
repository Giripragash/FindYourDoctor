package csp.ela.develop.smarthealthcare;

import java.util.Random;

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
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddDoctor extends Activity {

	String fontPath;
	Typeface tf;

	TextView txt_home_title, txt_adddoctor_title;
	EditText ed_docid, ed_name, ed_age, ed_dept, ed_phone, ed_add;
	Spinner spin_sex, spin_spec;
	Button btn_submit;

	private Context context;
	private Handler handler = new Handler();

	private ProgressDialog simpleWaitDialog;
	private String TAG = "Vik";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_doctor);

		context = this;

		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);

		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_adddoctor_title = (TextView) findViewById(R.id.txt_adddoctor_title);

		txt_home_title.setTypeface(tf);
		txt_adddoctor_title.setTypeface(tf);

		ed_docid = (EditText) findViewById(R.id.ed_docid);
		ed_name = (EditText) findViewById(R.id.ed_name);
		ed_age = (EditText) findViewById(R.id.ed_age);
		ed_dept = (EditText) findViewById(R.id.ed_dept);
		ed_phone = (EditText) findViewById(R.id.ed_phone);
		ed_add = (EditText) findViewById(R.id.ed_add);

		spin_sex = (Spinner) findViewById(R.id.spin_sex);
		spin_spec = (Spinner) findViewById(R.id.spin_spec);

		btn_submit = (Button) findViewById(R.id.btn_submit);

		// Random number generate
		Random r = new Random();
		String rand_number = "d" + r.nextInt(9) + r.nextInt(9) + r.nextInt(9)
				+ r.nextInt(9) + r.nextInt(9);

		ed_docid.setText(rand_number);

		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!ed_docid.getText().toString().equals("")
						&& !ed_name.getText().toString().equals("")
						&& !ed_age.getText().toString().equals("")
						&& !ed_dept.getText().toString().equals("")
						&& !ed_phone.getText().toString().equals("")
						&& !ed_add.getText().toString().equals("")) {
					if (ed_phone.getText().toString().length() == 7
							|| ed_phone.getText().toString().length() == 10) {
						// call();
						AsyncCallWS task = new AsyncCallWS();
						task.execute();
					} else {
						Toast.makeText(
								getApplicationContext(),
								"Please type correct Phone Number(7 digit) or Mobile Number(10 digit)...!",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Field Empty ...!",
							Toast.LENGTH_SHORT).show();
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
			if (simpleWaitDialog != null) {
				simpleWaitDialog.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			simpleWaitDialog = ProgressDialog.show(AddDoctor.this, "Wait",
					"Connecting...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	public void call() {
		try {
			String SOAP_ACTION = "http://tempuri.org/adddoctorService";
			String METHOD_NAME = "adddoctorService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("id", ed_docid.getText().toString());
			request.addProperty("name", ed_name.getText().toString());
			request.addProperty("sex",
					String.valueOf(spin_sex.getSelectedItem()));
			request.addProperty("age", ed_age.getText().toString());
			request.addProperty("dept", ed_dept.getText().toString());

			Log.i("zxcv", String.valueOf(spin_spec.getSelectedItem()));

			request.addProperty("spec",
					String.valueOf(spin_spec.getSelectedItem()));
			request.addProperty("phone", ed_phone.getText().toString());
			request.addProperty("add", ed_add.getText().toString());
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

						// Random number generate
						Random r = new Random();
						String rand_number = "d" + r.nextInt(9) + r.nextInt(9)
								+ r.nextInt(9) + r.nextInt(9) + r.nextInt(9);
						ed_docid.setText(rand_number);

						ed_name.setText("");
						ed_age.setText("");
						ed_dept.setText("");
						ed_phone.setText("");
						ed_add.setText("");
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
