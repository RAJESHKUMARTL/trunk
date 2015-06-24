package com.exenta.permissions;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.CircularNetworkImageView;
import com.android.volley.toolbox.ImageLoader;
import com.example.exenta.R;




public class MyPermissionReq_Adapter extends BaseAdapter
{

	private Context context;
	private LayoutInflater inflater;
	private List<MyPermissionReqModel> myreqList;	
	private TextView perdate, reqdate, totalhrs;
	
	public MyPermissionReq_Adapter(Context context, List<MyPermissionReqModel> mypereqList) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.myreqList = mypereqList;
		System.out.println("adapter class"+myreqList);
		 
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
			convertView = inflater.inflate(R.layout.permission_myreqadapter,null);			
		}
	
		perdate = (TextView) convertView.findViewById(R.id.perdate);
		reqdate = (TextView) convertView.findViewById(R.id.reqdate);
		totalhrs = (TextView) convertView.findViewById(R.id.totalhrs);
	
		
		MyPermissionReqModel Data = myreqList.get(position);
		
		perdate.setText(String.valueOf(Data.getPermissionDate()));
		reqdate.setText(String.valueOf(Data.getAppliedDate()));
		totalhrs.setText(String.valueOf(Data.getTotalHours()));
		
		System.out.println("Service Value:"+Data.getAppliedDate());
		
		//statusImage.setImageResource(R.drawable.pending_circle);
		//System.out.println(String.valueOf(leaveData.getImgPath()));
		 //  photo.setBackgroundDrawable(R.drawable.emp_icon);
	
		return convertView;
		
	}		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myreqList.size();
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