package csp.ela.develop.smarthealthcare;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DoctorsAdapter extends ArrayAdapter<DoctorsBean>
{

	private Activity activity;
	private List<DoctorsBean> items;
	private int row;
	private DoctorsBean objBean;

	public DoctorsAdapter(Activity act, int row, List<DoctorsBean> items)
	{
		super(act, row, items);

		this.activity = act;
		this.row = row;
		this.items = items;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		ViewHolder holder;
		if (view == null)
		{
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else
		{
			holder = (ViewHolder) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		holder.tvname = (TextView) view.findViewById(R.id.tvname);
		holder.tvPhoneNo = (TextView) view.findViewById(R.id.tvphone);

		if (holder.tvname != null && null != objBean.getid() && objBean.getid().trim().length() > 0)
		{
			holder.tvname.setText(Html.fromHtml(objBean.getname()));
		}
		if (holder.tvPhoneNo != null && null != objBean.getname() && objBean.getname().trim().length() > 0)
		{
			holder.tvPhoneNo.setText(Html.fromHtml(objBean.getSpec()));
		}
		return view;
	}

	public class ViewHolder
	{
		public TextView tvname, tvPhoneNo;
	}

}
