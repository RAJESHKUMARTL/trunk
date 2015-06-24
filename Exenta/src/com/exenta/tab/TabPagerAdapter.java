package com.exenta.tab;
//test 
import com.exenta.leaveapply.LeaveRequestFragment;
import com.exenta.permissions.HomePermission;
import com.exenta.pod.POD_Main;
import com.exenta.regularization.RegularizationMainPage;
import com.exenta.regularization.RegularizationRequest;
import com.exenta.timesheet.TimeSheetEntry;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			
			// Fragement for Android Tab
			return new MyAttandenceSummary();
		case 1:
			// Fragment for Ios Tab
			return new A();
		case 2:
			// Fragment for Windows Tab
			return new HomePermission();	
		case 3:
			return new POD_Main();
		case 4:
			return new RegularizationMainPage();
		case 5:
			return new TimeSheetEntry();		
			
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6; // No of Tabs
	}

}