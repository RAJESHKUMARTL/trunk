package com.exentahrms.staffinfo.adater;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exentahrms.staffinfo.model.GetAllEmployee;

public class Staff_CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<GetAllEmployee> allemplist;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public Staff_CustomListAdapter(Activity placeholderFragment, List<GetAllEmployee> allemployeelist) {
		this.activity = placeholderFragment;
		this.allemplist = allemployeelist;
	}

	@Override
	public int getCount() {
		return allemplist.size();
	}

	@Override
	public Object getItem(int location) {
		return allemplist.get(location);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.view_active_list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView title = (TextView) convertView.findViewById(R.id.job_title);
		TextView employee_id = (TextView) convertView.findViewById(R.id.empid);

		// getting movie data for the row
		GetAllEmployee m = allemplist.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		
		// title
		name.setText(m.getTitle());		
		// rating
		title.setText("Job Title: " + String.valueOf(m.getjobtitle()));
		
		// release year
		employee_id.setText("Employee ID "+ String.valueOf(m.getempcode()));

		return convertView;
	}

}