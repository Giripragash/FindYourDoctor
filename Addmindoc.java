package csp.ela.develop.smarthealthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Addmindoc extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admindoc);

		Button button = (Button) findViewById(R.id.ad_doc_view);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(getApplicationContext(), Adminview.class);
				startActivity(in);
			}
		});
		Button button3 = (Button) findViewById(R.id.staffdet);
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(getApplicationContext(),
						AdminStaffView.class);
				startActivity(in);
			}
		});
		Button button4 = (Button) findViewById(R.id.Button01);
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(getApplicationContext(),
						PatientList.class);
				startActivity(in);
			}
		});
	}
}
