package com.exenta.leaveapproval;

import java.util.List;
import com.android.volley.toolbox.CircularNetworkImageView;
import com.android.volley.toolbox.ImageLoader;
import com.example.exenta.R;
import com.exenta.app.AppController;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaveApprovalListAdapter extends BaseAdapter
{

	private Context context;
	private LayoutInflater inflater;
	private List<LeaveApprovalData> allApprovalList;	
	private TextView empID, leavetype, totalDays;
	private ImageView statusImage;	
	CircularNetworkImageView photo;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();		
	public LeaveApprovalListAdapter(Context context, List<LeaveApprovalData> empApprovalList) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.allApprovalList = empApprovalList;
		 
	}
	
	
	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
			convertView = inflater.inflate(R.layout.empapprovallist,null);			
		
		
		empID = (TextView) convertView.findViewById(R.id.nameVal);
		leavetype = (TextView) convertView.findViewById(R.id.leaveTypeVal);
		totalDays = (TextView) convertView.findViewById(R.id.noofdaysVal);
		statusImage = (ImageView) convertView.findViewById(R.id.statusView);
		photo = (CircularNetworkImageView)convertView.findViewById(R.id.thumbnail);
		LeaveApprovalData leaveData = allApprovalList.get(position);
		
		empID.setText(String.valueOf(leaveData.getFirstName()));
		leavetype.setText(String.valueOf(leaveData.getLeaveTypeName()));
		totalDays.setText(String.valueOf(leaveData.getTotalDays()));
		
//		System.out.println("Service Value:"+leaveData.getEmployeeID());
		
		statusImage.setImageResource(R.drawable.pending_circle);
//		System.out.println(String.valueOf(leaveData.getImgPath()));
		 //  photo.setBackgroundDrawable(R.drawable.emp_icon);
	   photo.setImageUrl(String.valueOf(leaveData.getImgPath()), imageLoader);
	//photo.setImageUrl("http://192.168.1.76:1234/Images/Employees/" + emp_photo, imageLoader);	
		return convertView;		
	}		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allApprovalList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
