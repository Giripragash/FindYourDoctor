package csp.ela.develop.smarthealthcare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DoctorHome extends Activity {
	String uid;
	String utype;

	String fontPath;
	Typeface tf;

	TextView txt_home_title, txt_doctorhome_title;
	Button btn_logout, btn_profile;
	ImageButton btn_pres, btn_app, btn_labtest,btn_chatt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_home);

		Intent intename = getIntent();
		utype = (String) intename.getSerializableExtra("utype");
		uid = (String) intename.getSerializableExtra("uid");

		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);

		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_doctorhome_title = (TextView) findViewById(R.id.txt_doctorhome_title);

		txt_home_title.setTypeface(tf);
		txt_doctorhome_title.setTypeface(tf);

		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_profile = (Button) findViewById(R.id.btn_profile);

		btn_pres = (ImageButton) findViewById(R.id.btn_pres);
		btn_app = (ImageButton) findViewById(R.id.btn_app);
		btn_labtest = (ImageButton) findViewById(R.id.btn_labtest);
		btn_chatt = (ImageButton) findViewById(R.id.btn_chatt);

		btn_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(DoctorHome.this, Home.class);
				startActivity(iobj);
				finish();
			}
		});

		btn_profile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(DoctorHome.this, ChangePass.class);
				iobj.putExtra("utype", utype);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});

		btn_app.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(DoctorHome.this, PAppoin.class);
				iobj.putExtra("did", uid);
				startActivity(iobj);
			}
		});
		btn_chatt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(DoctorHome.this, ChatActivity.class);
				iobj.putExtra("did", uid);
				startActivity(iobj);
			}
		});

		btn_pres.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(DoctorHome.this, Prescription.class);
				iobj.putExtra("did", uid);
				startActivity(iobj);
			}
		});

		btn_labtest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(DoctorHome.this, LabTest.class);
				iobj.putExtra("did", uid);
				startActivity(iobj);
			}
		});
	}
}
