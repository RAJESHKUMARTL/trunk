package com.exenta.cardviewnotification;

import java.util.List;

import com.example.exenta.R;
import com.exenta.leaveapproval.LeaveApprovalData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NotificationListAdapter extends BaseAdapter {

	private Context context;
	private List<NotificationList> empNotificationList;
	private LayoutInflater inflater;
	private TextView description;
	
	public NotificationListAdapter(Context context, List<NotificationList> empNotificationList ) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.empNotificationList = empNotificationList;
		
	}
	
	
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
			convertView = inflater.inflate(R.layout.notification_msg,null);
			
			description = (TextView) convertView.findViewById(R.id.description);
			
			NotificationList notList = empNotificationList.get(position);
			
			description.setText(notList.getDescription());
								
			return 	convertView;	
			
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return empNotificationList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}



}
