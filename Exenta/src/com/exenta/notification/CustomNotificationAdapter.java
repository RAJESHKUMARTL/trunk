package com.exenta.notification;

import java.util.List;

import com.example.exenta.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomNotificationAdapter extends BaseAdapter {
	
	    Context context = null;
	    List<CustomNotificationItem> rowItem = null;
	    ImageView imgIcon = null;
	    TextView txtTitle = null;

	    CustomNotificationAdapter(Context context, List<CustomNotificationItem> rowItem) {
	        this.context = context;
	        this.rowItem = rowItem;

	    }

	    public int getCount() {

	        return rowItem.size();
	    }

	    public Object getItem(int position) {

	        return rowItem.get(position);
	    }

	    public long getItemId(int position) {

	        return rowItem.indexOf(getItem(position));
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {

	        if (convertView == null) {
	            LayoutInflater mInflater = (LayoutInflater) context
	                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.navigation_homeadapter, null);
	        }

	        imgIcon = (ImageView) convertView.findViewById(R.id.iconNotification);
	        txtTitle = (TextView) convertView.findViewById(R.id.title);
	        
	        CustomNotificationItem row_pos = rowItem.get(position);
	        // setting the image resource and title
	        System.out.println("ROW POSITION:"+position);
	        System.out.println("ROW POSITION:"+rowItem.get(position).getTitle());
	        imgIcon.setImageResource(row_pos.getIcon());
	        txtTitle.setText(row_pos.getTitle());

	        return convertView;

	    }


}
