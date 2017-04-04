package csp.ela.develop.smarthealthcare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
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
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AdminStaffView extends Activity {
	TextView t1, t2, t3, t4, t5, t6, t7;
	Button build_btn;
	Soabproductdetails2 sss;
	String a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, u1;
	Spinner s1;
	Intent in;
	String[][] data = new String[50][10];
	String username, password, uid, proid;
	Boolean internetconnection = false;
	int rcount = 0;
	Handler handle = new Handler();
	String s_uid, s_pwd, nam, s_uname, s_pass, res;
	ListView listv;
	String it, it1;
	String eid, type, lan, lang, phone;
	int numProvinces = 13;
	ProgressDialog pbar, pbar1;
	ImageButton im4, im5;
	String s[] = { "", "" };
	ArrayList<HashMap<String, String>> myArraylist = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hash = new HashMap<String, String>();
	ArrayAdapter<String> adapter;
	int flag = 0;
	public static String url = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
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
		setContentView(R.layout.activity_emr);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		sss = new Soabproductdetails2(getApplicationContext());
		sss.call();

		myArraylist = sss.Getmydetails();
		Log.e("Product arraylist", "" + myArraylist.size());
		Customerview2 aCustomeradap = new Customerview2(
				getApplicationContext(), myArraylist);
		listv = (ListView) findViewById(R.id.docview);
		listv.setAdapter(aCustomeradap);

		listv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				final View v = arg1;
				AlertDialog.Builder aBuilder = new AlertDialog.Builder(
						AdminStaffView.this);
				aBuilder.setMessage("Perform action");
				aBuilder.setTitle("Do You Have Delete Doctor Details..");
				aBuilder.setPositiveButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// final TextView aprod = (TextView) v
								// .findViewById(R.id.btxt8);
								// String xyz = aprod.getText().toString();
								//
								// String a[] = xyz.split(" - ");
								// it = a[1];
								// method(it);
								// Toast.makeText(getApplicationContext(), it,
								// Toast.LENGTH_LONG).show();

							}
						});
				aBuilder.setNegativeButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								final TextView qty3 = (TextView) v
										.findViewById(R.id.btxt1);
								String xyz = qty3.getText().toString();

								String a[] = xyz.split(" - ");
								it1 = a[1];

								// try {
								//
								// // SmsManager smsManager =
								// SmsManager.getDefault();
								// // smsManager.sendTextMessage(it1, null,
								// "Address:"+myLat+""+myLng, null, null);
								// // Toast.makeText(getApplicationContext(),
								// "SMS Sent!",
								// // Toast.LENGTH_LONG).show();
								// //
								// // } catch (Exception e) {
								// // Toast.makeText(getApplicationContext(),
								// // "SMS faild, please try again later!",
								// // Toast.LENGTH_LONG).show();
								// // e.printStackTrace();
								// // }
								Toast.makeText(getApplicationContext(), it1,
										Toast.LENGTH_SHORT).show();
								call(it1);

							}
						});
				AlertDialog all = aBuilder.create();
				all.show();
				// TextView label=(TextView) v.findViewById(R.id.txt1);
				// TextView label1=(TextView) v.findViewById(R.id.txt2);
				// TextView label2=(TextView) v.findViewById(R.id.txt3);
				// String xyz = label.getText().toString();
				// String xyz1 = label1.getText().toString();
				// String xyz2 = label2.getText().toString();
				// a1=xyz;
				// a2=xyz1;
				// a3=xyz2;
				// String a[]=xyz.split(":");
				// String it=a[1];
				// // Toast.makeText(getApplicationContext(),
				// it,Toast.LENGTH_LONG).show();
				// in=new Intent(getApplicationContext(),Purchase.class);
				// in.putExtra("proid",it);
				// //Toast.makeText(getApplicationContext(),data[arg2][0],Toast.LENGTH_SHORT).show();
				// startActivity(in);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void method(String a) {
		try {
			final String b = a;

			internetconnection = checkInternetConnection();
			if (internetconnection) {

				pbar = ProgressDialog.show(AdminStaffView.this, "Wait",
						"Connecting To Server");
				pbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

				new Thread() {
					public void run() {

						SoapObject request = new SoapObject(NAMESPACE1,
								WORK_METHOD_NAME1);
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						request.addProperty("name", b);
						envelope.dotNet = true;
						envelope.setOutputSoapObject(request);
						HttpTransportSE androidHttpTransport = new HttpTransportSE(
								url);
						try {
							try {
								androidHttpTransport.call(WORK_SOAP_ACTION1,
										envelope);
							} catch (XmlPullParserException e) {
								display("Error While calling Web Method");
								e.printStackTrace();
							}
							SoapObject response = (SoapObject) envelope
									.getResponse();
							pbar.dismiss();
							rcount = response.getPropertyCount();

							if (rcount > 0) {
								int i = 0;
								while (i < rcount) {
									SoapObject res = (SoapObject) response
											.getProperty(i);
									if (!res.getProperty("lat").toString()
											.equalsIgnoreCase("")) {
										data[i][0] = res.getProperty("lat")
												.toString();
										data[i][1] = res.getProperty("lan")
												.toString();

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
									for (int j = 0; j < rcount; j++) {
										for (int k = 0; k < 2; k++) {

											a1 = s[k] + data[j][0];
											a2 = s[k] + data[j][1];

										}
									}
									Log.e("Lat", a1);
									Log.e("Log", a2);
									Uri uri = Uri
											.parse("http://maps.google.com/?q="
													+ a2 + "+" + a1 + "");
									Intent intent = new Intent(
											Intent.ACTION_VIEW, uri);
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

		} catch (Exception e) {
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
			Toast.makeText(getApplicationContext(), "No Network Connection ",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public void display(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public void call(final String aa) {
		try {
			final String bb = aa;
			pbar1 = ProgressDialog.show(AdminStaffView.this,
					"Wait for Some Time", "Registering in Online Help Service");
			pbar1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			SoapObject request = new SoapObject(NAMESPACE1, METHOD_NAME1);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			// if((ed1.getText().length()!=0)&&(ed1.getText().length()!=0)&&(ed3.getText().length()!=0)&&(ed4.getText().length()!=0)&&(ed5.getText().length()!=0)&&(ed6.getText().length()!=0)&&(ed7.getText().length()!=0)&&(ed8.getText().length()!=0)){
			request.addProperty("name", bb);

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

				final SoapObject result = (SoapObject) envelope.bodyIn;
				// String resp = result.getProperty(0).toString();

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
