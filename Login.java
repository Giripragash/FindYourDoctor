package csp.ela.develop.smarthealthcare;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	String fontPath;
	Typeface tf;

	TextView txt_login_title;
	EditText ed_uname, ed_upass;
	Spinner spin_usr_type;
	Button but_login;

	private Context context;
	private Handler handler = new Handler();

	private ProgressDialog simpleWaitDialog;
	private String TAG = "Vik";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		context = this;

		txt_login_title = (TextView) findViewById(R.id.txt_login_title);

		ed_uname = (EditText) findViewById(R.id.ed_uname);
		ed_upass = (EditText) findViewById(R.id.ed_upass);

		spin_usr_type = (Spinner) findViewById(R.id.spin_usr_type);

		but_login = (Button) findViewById(R.id.but_login);

		txt_login_title.setTypeface(tf);

		but_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!ed_uname.getText().toString().equals("")
						&& !ed_upass.getText().toString().equals("")
						&& !String.valueOf(spin_usr_type.getSelectedItem())
								.equalsIgnoreCase("User Type")) {
					// call();
					AsyncCallWS task = new AsyncCallWS();
					task.execute();
				} else if (String.valueOf(spin_usr_type.getSelectedItem())
						.equalsIgnoreCase("New Register")) {
					Intent iobj = new Intent(Login.this, ReceptionistHome.class);
					startActivity(iobj);
				} else {
					Toast.makeText(getApplicationContext(),
							"Do not empty username and password",
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
			simpleWaitDialog = ProgressDialog.show(Login.this, "Wait",
					"Connecting...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

	public void call() {
		try {
			String SOAP_ACTION = "http://tempuri.org/loginService";
			String METHOD_NAME = "loginService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("type",
					String.valueOf(spin_usr_type.getSelectedItem()));
			request.addProperty("name", ed_uname.getText().toString());
			request.addProperty("pass", ed_upass.getText().toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);

			SoapObject result = (SoapObject) envelope.getResponse();
			int rcount = (result.getPropertyCount());
			if (rcount >= 2) {
				String res_info = result.getProperty(0).toString();
				final String res_uid = result.getProperty(1).toString();
				if (res_info.equals("Login Success")) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (simpleWaitDialog != null) {
								simpleWaitDialog.dismiss();
							}

							Toast.makeText(getApplicationContext(),
									"Login Success ...!", Toast.LENGTH_SHORT)
									.show();
							if (String.valueOf(spin_usr_type.getSelectedItem())
									.equalsIgnoreCase("admin")) {
								Intent iobj = new Intent(Login.this,
										AdminHome.class);
								iobj.putExtra("utype", String
										.valueOf(spin_usr_type
												.getSelectedItem()));
								iobj.putExtra("uid", res_uid);
								startActivity(iobj);
							} else if (String.valueOf(
									spin_usr_type.getSelectedItem())
									.equalsIgnoreCase("doctor")) {
								Intent iobj = new Intent(Login.this,
										DoctorHome.class);
								iobj.putExtra("utype", String
										.valueOf(spin_usr_type
												.getSelectedItem()));
								iobj.putExtra("uid", res_uid);
								startActivity(iobj);
							} else if (String.valueOf(
									spin_usr_type.getSelectedItem())
									.equalsIgnoreCase("patient")) {
								Intent iobj = new Intent(Login.this,
										PatientHome.class);
								iobj.putExtra("utype", String
										.valueOf(spin_usr_type
												.getSelectedItem()));
								iobj.putExtra("uid", res_uid);
								startActivity(iobj);
							} else if (String.valueOf(
									spin_usr_type.getSelectedItem())
									.equalsIgnoreCase("Receptionist")) {
								Intent iobj = new Intent(Login.this,
										ReceptionistHome.class);
								iobj.putExtra("utype", String
										.valueOf(spin_usr_type
												.getSelectedItem()));
								iobj.putExtra("uid", res_uid);
								startActivity(iobj);
							} else if (String.valueOf(
									spin_usr_type.getSelectedItem())
									.equalsIgnoreCase("Lab Admin")) {
								Intent iobj = new Intent(Login.this,
										LabadminHome.class);
								iobj.putExtra("utype", String
										.valueOf(spin_usr_type
												.getSelectedItem()));
								iobj.putExtra("uid", res_uid);
								startActivity(iobj);
							} else if (String.valueOf(
									spin_usr_type.getSelectedItem())
									.equalsIgnoreCase("Pharmacist")) {
								Intent iobj = new Intent(Login.this,
										PharmacistHome.class);
								iobj.putExtra("utype", String
										.valueOf(spin_usr_type
												.getSelectedItem()));
								iobj.putExtra("uid", res_uid);
								startActivity(iobj);
							}
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

							Toast.makeText(getApplicationContext(),
									"Login Failure ...!", Toast.LENGTH_SHORT)
									.show();
						}
					});
				}

			} else {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (simpleWaitDialog != null) {
							simpleWaitDialog.dismiss();
						}

						Toast.makeText(getApplicationContext(),
								"Login Failure ...!", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
