package com.exenta.permissions;

import java.util.List;

import supportclasses.Const;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.CircularNetworkImageView;
import com.android.volley.toolbox.ImageLoader;
import com.example.exenta.R;
import com.exenta.app.AppController;



public class PermissionApproval_Adapter extends BaseAdapter
{

	private Context context;
	private LayoutInflater inflater;
	private List<PermissionapprovalModel> allApprovalList;	
	private TextView empID, leavetype, totalDays;
	//private ImageView statusImage;	
	CircularNetworkImageView photo;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();		
	public PermissionApproval_Adapter(Context context, List<PermissionapprovalModel> perApprovalList) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.allApprovalList = perApprovalList;
		System.out.println("adapter class"+perApprovalList);
		 
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//System.out.println(convertView);
		System.out.println(position);
	//	System.out.println(parent);
		
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.permission_approvaladapter,null);			
		}
		
		empID = (TextView) convertView.findViewById(R.id.nameVal);
		leavetype = (TextView) convertView.findViewById(R.id.permission_approvaldate);
		totalDays = (TextView) convertView.findViewById(R.id.total_hrs);
		//statusImage = (ImageView) convertView.findViewById(R.id.statusView);
		photo = (CircularNetworkImageView)convertView.findViewById(R.id.thumbnail_perapproval);
		PermissionapprovalModel perData = allApprovalList.get(position);
		
		empID.setText(String.valueOf(perData.getFirstName()));
		leavetype.setText(String.valueOf(perData.getPermissionDate()));
		totalDays.setText(String.valueOf(perData.getTotalDays()));
		
		System.out.println("Service Value:"+perData.getAppliedDate());
		
		//statusImage.setImageResource(R.drawable.pending_circle);
		//System.out.println(String.valueOf(leaveData.getImgPath()));
		 //  photo.setBackgroundDrawable(R.drawable.emp_icon);
	   photo.setImageUrl(String.valueOf(Const.Approval_listImage_URL+perData.getPhotopath()), imageLoader);
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