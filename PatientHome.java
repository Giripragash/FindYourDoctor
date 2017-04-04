
package csp.ela.develop.smarthealthcare;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PatientHome extends Activity
{
	String uid;
	String utype;
	
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title, txt_patienthome_title;
	Button btn_logout, btn_profile;
	ImageButton btn_doclist,btn_notify,btn_chatt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_home);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Intent intename = getIntent();
		utype = (String) intename.getSerializableExtra("utype");
		uid = (String) intename.getSerializableExtra("uid");
		
	
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_patienthome_title = (TextView) findViewById(R.id.txt_patienthome_title);
		
		txt_home_title.setTypeface(tf);
		txt_patienthome_title.setTypeface(tf);
		
		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_profile = (Button) findViewById(R.id.btn_profile);
		
		btn_doclist = (ImageButton) findViewById(R.id.btn_doclist);
		btn_notify = (ImageButton) findViewById(R.id.btn_notify);
		btn_chatt=(ImageButton) findViewById(R.id.btn_chatt);
		btn_logout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj=new Intent(PatientHome.this, Home.class);
				startActivity(iobj);
				finish();
			}
		});
		
		btn_profile.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj = new Intent(PatientHome.this, ChangePass.class);
				iobj.putExtra("utype", utype);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});
		btn_chatt.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj = new Intent(PatientHome.this, DoctorList.class);
				iobj.putExtra("utype", utype);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});
		btn_doclist.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj = new Intent(PatientHome.this, DoctorsListActivity.class);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});
		
		btn_notify.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj = new Intent(PatientHome.this, PharmacyNotification.class);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});
	}
	
}
