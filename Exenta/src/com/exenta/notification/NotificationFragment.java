package com.exenta.notification;

import java.util.ArrayList;
import java.util.List;

import com.example.exenta.R;
import com.exenta.cardviewnotification.NotificationActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "InflateParams", "Recycle" })
public class NotificationFragment extends ListFragment implements OnItemClickListener {
	
	
    String[] menutitles = null;
    TypedArray menuIcons = null;

    CustomNotificationAdapter adapter;
    private List<CustomNotificationItem> rowItems;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
					    			    	
				return inflater.inflate(R.layout.list_fragment, null,false);	
	}

	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {

	        super.onActivityCreated(savedInstanceState);
	        
	        	        
	        menutitles = getResources().getStringArray(R.array.notificationListView);
	        menuIcons  = getResources().obtainTypedArray(R.array.notificationIcons); 

	        rowItems = new ArrayList<CustomNotificationItem>();	    
	        CustomNotificationItem items = null;
	        for (int i = 0; i < menutitles.length; i++) {
	        	items = new CustomNotificationItem(menutitles[i], menuIcons.getResourceId(i, -1));
	            rowItems.add(items);
	        }
	        adapter = new CustomNotificationAdapter(getActivity(), rowItems);
	        setListAdapter(adapter);
	        getListView().setOnItemClickListener(this);
	    }
		
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		//Load Notification actvity
		
		
		if(position==0)
		{
			Intent intent =  new Intent(getActivity(), NotificationActivity.class);
			startActivity(intent);
			Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
	         .show();			
		}
		else
		{		
			Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT)
	         .show();			
			
		}

	}

}
