package csp.ela.develop.smarthealthcare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LabadminHome extends Activity
{
	String uid;
	String utype;
	
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title, txt_labadminhome_title;
	Button btn_logout, btn_profile;
	ImageButton btn_notify;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_labadmin_home);

		Intent intename = getIntent();
		utype = (String) intename.getSerializableExtra("utype");
		uid = (String) intename.getSerializableExtra("uid");
		
		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		txt_labadminhome_title = (TextView) findViewById(R.id.txt_labadminhome_title);
		
		txt_home_title.setTypeface(tf);
		txt_labadminhome_title.setTypeface(tf);
		
		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_profile = (Button) findViewById(R.id.btn_profile);
		
		btn_notify = (ImageButton) findViewById(R.id.btn_notify);
		
		btn_logout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj=new Intent(LabadminHome.this, Home.class);
				startActivity(iobj);
				finish();
			}
		});
		
		btn_profile.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj = new Intent(LabadminHome.this, ChangePass.class);
				iobj.putExtra("utype", utype);
				iobj.putExtra("uid", uid);
				startActivity(iobj);
			}
		});
		
		btn_notify.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent iobj = new Intent(LabadminHome.this, LabNotification.class);
				startActivity(iobj);
			}
		});
	}
	
}
