package com.exenta.notification;

import com.example.exenta.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotificationMainActivity extends Fragment 
{
	
	Fragment nf=null;
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
		  	System.out.println("Notification Fragment");
	        View android = inflater.inflate(R.layout.notification_frag_activity, container, false);
	       
	        // Include notification_frag_activity inside fragment1
	        if (savedInstanceState==null){	        	
	        	nf=new NotificationFragment();
	       
	        	FragmentTransaction t=getFragmentManager().beginTransaction();	        	
	        	t.replace(R.id.fragment1, nf);
	        	t.commit();
	        }	        
	        return android;
	  }

}
