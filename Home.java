package csp.ela.develop.smarthealthcare;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;

public class Home extends Activity
{
	
	String fontPath;
	Typeface tf;
	
	TextView txt_home_title;
	Button but_goto_login,donor,emp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		fontPath = "font/pacifico.ttf";
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		
		txt_home_title = (TextView) findViewById(R.id.txt_home_title);
		but_goto_login = (Button) findViewById(R.id.but_goto_login);
		donor = (Button) findViewById(R.id.donor);
		emp = (Button) findViewById(R.id.emp);
		
		txt_home_title.setTypeface(tf);
		
		but_goto_login.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intobj = new Intent(Home.this, Login.class);
				startActivity(intobj);
			}
		});
		emp.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intobj = new Intent(Home.this, DEmr.class);
				startActivity(intobj);
			}
		});
		donor.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intobj = new Intent(Home.this, Donor.class);
				startActivity(intobj);
			}
		});
	}
	
}
