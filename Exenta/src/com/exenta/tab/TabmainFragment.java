package com.exenta.tab;

import java.nio.channels.GatheringByteChannel;

import com.example.exenta.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) public class TabmainFragment extends Fragment {
	
	ViewPager Tab=null;
    TabPagerAdapter TabAdapter=null;
    ActionBar actionBar=null;
	 @SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootview = inflater.inflate(R.layout.activity_main, container, false);
	       getActivity().getActionBar().setDisplayShowHomeEnabled(true);
	       getActivity().getActionBar().setDisplayShowTitleEnabled(true);
	        
	        TabAdapter = new TabPagerAdapter( getActivity().getSupportFragmentManager());
	                
	        Tab = (ViewPager)rootview.findViewById(R.id.pager);
	        
	        Tab.setOnPageChangeListener(
	                new ViewPager.SimpleOnPageChangeListener() {
	                    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
	                    public void onPageSelected(int position) {                       
	                    	actionBar =getActivity().getActionBar();
	                    	actionBar.setSelectedNavigationItem(position); 
	                    	}
	                });
	        Tab.setAdapter(TabAdapter);        
	        actionBar =getActivity().getActionBar();	        
	        //Enable Tabs on Action Bar
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
	        ActionBar.TabListener tabListener = new ActionBar.TabListener()
	        {
				@Override
				public void onTabReselected(android.app.ActionBar.Tab tab,
						FragmentTransaction ft) {
					// TODO Auto-generated method stub				
				}

				@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
				 public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) 
				{	         
		            Tab.setCurrentItem(tab.getPosition());
		        }

				@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
				public void onTabUnselected(android.app.ActionBar.Tab tab,
						FragmentTransaction ft) {				
					// TODO Auto-generated method stub				
					}
				};
				//Add New Tab
				
				
				actionBar.removeAllTabs();												
				actionBar.addTab(actionBar.newTab().setText("Summary").setTabListener(tabListener));				
				actionBar.addTab(actionBar.newTab().setText("Leave").setTabListener(tabListener));
				actionBar.addTab(actionBar.newTab().setText("Permission").setTabListener(tabListener));	
				actionBar.addTab(actionBar.newTab().setText("POD").setTabListener(tabListener));
				actionBar.addTab(actionBar.newTab().setText("Regularization").setTabListener(tabListener));
				actionBar.addTab(actionBar.newTab().setText("Timesheet Entry").setTabListener(tabListener));
				
				getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.list_background)));
				actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.default_white)));
 return rootview;
	 }
	 
	 
	 public void onDestroy() {
		 actionBar.removeAllTabs();
		 actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		 super.onDestroy();
	 }
	 
//	  public boolean onCreateOptionsMenu(Menu menu) 
//	    {
//	        // Inflate the menu items for use in the action bar
//	        MenuInflater inflater = getActivity().getMenuInflater();
//	        inflater.inflate(R.menu.main, menu);        
//	        //getMenuInflater().inflate(R.menu.sub_menu, action_settings.getSubMenu());
//	        return true;                      
//	    		// TODO Auto-generated method stub
//	    }
//	    
//	    public boolean onOptionsItemSelected(MenuItem item) 
//	    {
//	        switch (item.getItemId()) 
//	        {
//	        case R.id.one:            
//	        	SharedPreferences sharedPref = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE);
//	    		Editor editor =  sharedPref.edit();
//	    		editor.clear();
//	    		editor.commit();
//	    		getActivity().moveTaskToBack(true);
//	    		getActivity().finish();
//	    	 	        	
//	            break;
//	        case R.id.two:
//	            Toast.makeText(getActivity().getApplicationContext(), "Clicked on help",
//	                    Toast.LENGTH_SHORT).show();
//	            break;
//	        case R.id.three:
//	        	getActivity().moveTaskToBack(true);
//	        	getActivity().finish();
//	            break;        
//	        }
//			return true;
//	    }    
}
