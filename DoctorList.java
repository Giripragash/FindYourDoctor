package csp.ela.develop.smarthealthcare;


import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DoctorList extends Activity implements OnItemClickListener {
String uid;
	
	private ListView listView;
	private List<DoctorsBean> list = new ArrayList<DoctorsBean>();
	DoctorsAdapter objAdapter;
	
	private ProgressDialog pd;
	
	private Context context;
	private Handler handler = new Handler();
	
	private ProgressDialog simpleWaitDialog;
	private String TAG ="Vik";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_list);
		Intent intename = getIntent();
		uid = (String) intename.getSerializableExtra("uid");
		
		context = this;
		
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		
	//	AsyncCallWS task = new AsyncCallWS();
        //task.execute();
		call();
      
		objAdapter = new DoctorsAdapter(DoctorList.this, R.layout.doctors_row, list);
		Log.e("Count", ""+list.size());
		listView.setAdapter(objAdapter);
		objAdapter.notifyDataSetChanged();
	}
	
	private void showToast(String msg)
	{
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onItemClick(AdapterView<?> listview, View v, int position, long id)
	{
		final DoctorsBean bean = (DoctorsBean) listview.getItemAtPosition(position);
		// showCallDialog(bean.getid(), bean.getname());
		
		Intent iobj = new Intent(DoctorList.this, ChatActivity.class);
		iobj.putExtra("fid", bean.getid());
		iobj.putExtra("uid", uid);
		Log.e("Count xyz",uid+""+ bean.getid());
		startActivity(iobj);
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
            simpleWaitDialog = ProgressDialog.show(DoctorList.this,"Wait", "Connecting...");
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
			/** Called when the activity is first created. */
			String SOAP_ACTION = "http://tempuri.org/getDoctorsListService";
			String METHOD_NAME = "getDoctorsListService";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "http://cyberstudents.in/android/SmartHealthCareService/service.asmx";
			
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			// request.addProperty("Number", "3");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
			for (int i = 0; i < res.getPropertyCount(); i++)
			{
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
			
		} catch (Exception e)
		{
			// tv.setText(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
