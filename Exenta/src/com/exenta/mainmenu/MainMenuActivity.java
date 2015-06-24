package com.exenta.mainmenu;

import java.util.HashMap;
import java.util.List;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exenta.R;
import com.exenta.cardviewnotification.NotificationList;
import com.exenta.leaveapproval.LeaveApprovalData;
import com.exenta.login.Login;
import com.exenta.notification.NotificationMainActivity;
import com.exenta.tab.TabmainFragment;
import com.exentahrms.staffinfo.FragmentStaffInfo;
import com.exentahrms.staffinfo.ProfileDetails;
import com.exentahrms.staffinfo.json.GetEmployeeProfileJSON;
import com.exentahrms.staffinfo.json.VolleyCallback;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;


public class MainMenuActivity extends FragmentActivity {

	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	EmpDetailsSessions session;
	String employeeID, companyID;
	private boolean backPressedToExitOnce = false;
	private Toast toast = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
				
		
		setContentView(R.layout.drawer_main);		
		
		session =  new EmpDetailsSessions(getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		System.out.println("(MainMenuActivity)Employee ID:"+employeeID);
		
		// Generate title
		title = new String[] { "Home","Attendance", "Staff Info"};
				
		// Generate icon
		icon = new int[] { R.drawable.homeicon,R.drawable.attendance, R.drawable.staffinfo};

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass results to MenuListAdapter Class
		mMenuAdapter = new MenuListAdapter(this, title, icon);
		
		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);
		
		// Capture button clicks on side menu
				mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

				// Enable ActionBar app icon to behave as action to toggle nav drawer
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setHomeButtonEnabled(true);
				// ActionBarDrawerToggle ties together the the proper interactions
				// between the sliding drawer and the action bar app icon
				mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
						R.drawable.ic_drawer, R.string.drawer_open,
						R.string.drawer_close) 
				{
					public void onDrawerClosed(View view) {
						// TODO Auto-generated method stub						
//						invalidateOptionsMenu();
						super.onDrawerClosed(view);
					}

					public void onDrawerOpened(View drawerView) {
						// TODO Auto-generated method stub
						super.onDrawerOpened(drawerView);
//						invalidateOptionsMenu();
					}
				};
				mDrawerLayout.setDrawerListener(mDrawerToggle);		
				if (savedInstanceState == null) {
					selectItem(0);
				}		
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_profile, menu);		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		
		switch (item.getItemId()) 
        {
        case R.id.one:            
        	SharedPreferences sharedPref = getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
    		Editor editor =  sharedPref.edit();
    		editor.clear();
    		editor.commit();
    		moveTaskToBack(true);
    		MainMenuActivity.this.finish();
    		Intent intent =  new Intent(getBaseContext(), Login.class );
    		startActivity(intent);        	        	
            break;
        case R.id.three:
        	moveTaskToBack(true);
        	MainMenuActivity.this.finish();
            break;  
         
        case R.id.profile:
//			   Intent i = new Intent(MainMenuActivity.this,ProfileDetails.class);
        	System.out.println("EmployeeIDDDDDDDDD"+employeeID);
				new GetEmployeeProfileJSON( employeeID, new VolleyCallback() {
					
				@Override
					public void onSuccess(
							List<GetEmployeeProfile> emp_details_list) {
						Intent i = new Intent(MainMenuActivity.this,ProfileDetails.class);
						// TODO Auto-generated method stub
						System.out.println("Name :"+emp_details_list.get(0).getName());
						System.out.println("Name :"+emp_details_list.get(0).getName());
						i.putExtra("FullName", emp_details_list.get(0).getName());
						i.putExtra("JobTitleName", emp_details_list.get(0).getJob_title());
						i.putExtra("EmpCode", emp_details_list.get(0).getEmp_code());
						i.putExtra("CompanyName", emp_details_list.get(0).getCmpy_name());
						i.putExtra("Department", emp_details_list.get(0).getDepartment());
						i.putExtra("ExperienceValue", emp_details_list.get(0).getExperience());
						i.putExtra("Email", emp_details_list.get(0).getEmail());
						i.putExtra("ReportManagerName", emp_details_list.get(0).getReport_mananger());
						i.putExtra("Photo", emp_details_list.get(0).getPhoto());	
			            startActivity(i);
			         //   hidePDialog();
					}
					@Override
					public void onSuccessLeaveApproval(
							List<LeaveApprovalData> emp_leave_list) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void onSuccessNotificationList(
							List<NotificationList> not_List) {
						// TODO Auto-generated method stub
						
					}
				});
            
                             
        }
		
		return super.onOptionsItemSelected(item);
	}
	
	
	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
		


	private void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			getActionBar().removeAllTabs();
			ft.remove(new TabmainFragment());
			ft.replace(R.id.content_frame, new NotificationMainActivity());
			break;
		case 1:				
//			Intent intent = new Intent(MainMenuActivity.this,TabMainActivity.class);
//        	startActivity(intent);	
			ft.remove(new NotificationMainActivity());
			ft.replace(R.id.content_frame, new TabmainFragment());
			break;
		case 2:			
			ft.replace(R.id.content_frame, new FragmentStaffInfo());
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	public void onBackPressed() {
	    if (backPressedToExitOnce) {
	        super.onBackPressed();
	    } else {
	        this.backPressedToExitOnce = true;
	        Toast.makeText(MainMenuActivity.this,"Press again to exit", Toast.LENGTH_SHORT).show();
	        new android.os.Handler().postDelayed(new Runnable() {

	            @Override
	            public void run() {
	                backPressedToExitOnce = false;
	            }
	        }, 2000);
	    }
	}
	
	
}
