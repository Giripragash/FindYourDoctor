package csp.ela.develop.smarthealthcare;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Customerview2 extends BaseAdapter {
	Context b;
	LayoutInflater lf;
	TextToSpeech ttobj;
	Soabproductdetails2 so;
	ArrayList<HashMap<String, String>> ll = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> hash = new HashMap<String, String>();

	public Customerview2(Context a, ArrayList<HashMap<String, String>> aListdata) {
		this.b = a;

		so = new Soabproductdetails2(a);
		this.ll = aListdata;
		ll = so.Getmydetails();
		Log.e("arraylist custview", "" + ll.size());

	}

	@Override
	public int getCount() {
		return ll.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ll.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		lf = (LayoutInflater) b
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = lf.inflate(R.layout.listviewcontrol2, null);

		TextView txt1 = (TextView) convertView.findViewById(R.id.btxt1);
		TextView txt2 = (TextView) convertView.findViewById(R.id.btxt2);
		TextView txt3 = (TextView) convertView.findViewById(R.id.btxt3);
		TextView txt4 = (TextView) convertView.findViewById(R.id.btxt4);
		TextView txt5 = (TextView) convertView.findViewById(R.id.btxt5);
		TextView txt6 = (TextView) convertView.findViewById(R.id.btxt6);
		TextView txt7 = (TextView) convertView.findViewById(R.id.btxt7);
		TextView txt8 = (TextView) convertView.findViewById(R.id.btxt8);

		hash = ll.get(position);

		String astr1 = hash.get("str");
		String astr2 = hash.get("str1");
		String astr3 = hash.get("str2");
		String astr4 = hash.get("str3");
		String astr5 = hash.get("str4");
		String astr6 = hash.get("str5");
		String astr7 = hash.get("str6");

		Log.e("string1", astr1);

		txt1.setText("Staff Id - " + astr1);
		txt2.setText("Staff Name - " + astr2);
		txt3.setText("Staff type - " + astr3);
		txt4.setText("Sex - " + astr4);
		txt5.setText("age - " + astr5);
		txt6.setText("Phone - " + astr6);
		txt7.setText("Address - " + astr7);
		txt8.setText("");
		return convertView;
	}

}