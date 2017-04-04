package csp.ela.develop.smarthealthcare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AdminHome extends Activity {
	String uid;
	String utype;

	String fontPath;
	Typeface tf;

	TextView txt_home_title, txt_adminhome_title;
	Button btn_logout, btn_profile,donorview;
	ImageButton btn_adddoc, btn_addstaff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_home);

		Intent intename = getIntent();
		utype = (String) intename.getSerializableExtra("utype");
		uid = (String) intename.getSerializableExtra("uid");

		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);

		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_adminhome_title = (TextView) findViewById(R.id.txt_adminhome_title);

		txt_home_title.setTypeface(tf);
		txt_adminhome_title.setTypeface(tf);

		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_profile = (Button) findViewById(R.id.btn_profile);

		btn_adddoc = (ImageButton) findViewById(R.id.btn_adddoc);
		btn_addstaff = (ImageButton) findViewById(R.id.btn_addstaff);

		btn_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(AdminHome.this, Home.class);
				startActivity(iobj);
				finish();
			}
		});

		btn_profile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(AdminHome.this, ChangePass.class);
				iobj.putExtra("utype", utype);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});

		btn_adddoc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(AdminHome.this, AddDoctor.class);
				startActivity(iobj);
			}
		});

		btn_addstaff.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(AdminHome.this, AddStaff.class);
				startActivity(iobj);
			}
		});
		
		Button adminview = (Button) findViewById(R.id.adminview);
		adminview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(AdminHome.this, Addmindoc.class);
				startActivity(iobj);
			}
		});
		Button donor = (Button) findViewById(R.id.donorview);
		donor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iobj = new Intent(AdminHome.this, DAdminview.class);
				startActivity(iobj);
			}
		});
	}

}
