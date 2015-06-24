package com.exentahrms.staffinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.app.ActionBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.cardviewnotification.NotificationList;
import com.exenta.leaveapproval.LeaveApprovalData;
import com.exenta.mainmenu.MainMenuActivity;
import com.exenta.notification.NotificationMainActivity;
import com.exentahrms.indexlistview.IndexableListView;
import com.exentahrms.profiledetails.EmployeeProfile;
import com.exentahrms.staffinfo.adater.Profile_CustomListAdapter;
import com.exentahrms.staffinfo.json.GetEmployeeProfileJSON;
import com.exentahrms.staffinfo.json.VolleyCallback;
import com.exentahrms.staffinfo.model.GetAllEmployeeProfile;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;


public class FragmentStaffInfo extends Fragment implements OnQueryTextListener {
//	private static final String Getcompany = Const.ProfileDetails.GET_EMPLOYEE_DETAILS;

	EmpDetailsSessions session;
	String employeeID, companyID;
	ActionBar actionbar = null;
	// Object for Getter and Setter
	private List<GetAllEmployeeProfile> empProfileList = new ArrayList<GetAllEmployeeProfile>();
	GetAllEmployeeProfile staff;
	public static int selectedValue = -1;

	// Views
	private IndexableListView listView;
	private EditText searchText;
	// Adapter for custom listview
	private Profile_CustomListAdapter adapter;
	
	// Variable Assigned
	private static final String TAG = EmployeeProfile.class.getSimpleName();
	private static final int DIALOG_LOADING = 1;
	ActionBar actionBar;
	//getActivity().getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
		    getActivity().supportInvalidateOptionsMenu();
		    setHasOptionsMenu(true);
		    
		    actionBar = getActivity().getActionBar();
	        actionBar.removeAllTabs();
	        
	        
	       
	        
						
			session =  new EmpDetailsSessions(getActivity().getApplicationContext());
			HashMap<String,String> user =  session.getEmployeeDetails();  
						
			employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
			companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		    
		    
	        View rootview = inflater.inflate(R.layout.employee_profile, container, false);	        
	        listView = (IndexableListView)rootview. findViewById(R.id.emp_profile);
	        searchText = (EditText) rootview.findViewById(R.id.myFilter);
	        
	        	        
	        
	        
			adapter = new Profile_CustomListAdapter(getActivity(), empProfileList);
			listView.setAdapter(adapter);
			listView.setFastScrollEnabled(true);
			listView.setTextFilterEnabled(true);
			GetAllEmployee();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					selectedValue =position;								
					//View v = (View)view.getParent();
					//System.out.println("position"+v.toString());
					/*ViewHolder holder= (ViewHolder) view.getTag();
				        holder.text2.setVisibility(View.VISIBLE);*/
					//TextView deleteRowButton = (TextView) v.findViewById(R.id.report);
				    //deleteRowButton.setVisibility(View.VISIBLE);
				  
				    adapter.notifyDataSetChanged();
				     //listView.getPositionForView(v);
				}

			});
			
			
			searchText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					adapter.getFilter().filter(s);
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			
			

	        return rootview;
}

	   private void GetAllEmployee() {
			// empList.clear();
			//showDialog(DIALOG_LOADING);
			JsonArrayRequest arrobj = new JsonArrayRequest(Const.ProfileDetails.GET_EMPLOYEE_DETAILS_P1+companyID+Const.ProfileDetails.GET_EMPLOYEE_DETAILS_P2,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							// Parsing json
							for (int i = 0; i < response.length(); i++) {
								try {
									JSONObject obj1 = response.getJSONObject(i);
									staff = new GetAllEmployeeProfile();
									staff.setName(obj1.getString("EmpNameAndCode"));
									staff.setJobtitle(obj1
											.getString("JobTitleName"));
									staff.setDepartment(obj1
											.getString("DepartmentName"));
									staff.setEmail(obj1.getString("Email"));
									staff.setReportto(obj1.getString("ReportTo"));
									staff.setExtensionto(obj1
											.getString("HomePhone"));
									staff.setOfficeno(obj1.getString("Mobile"));
									staff.setPhotopath(obj1.getString("PhotoPath"));
									// array String for name sorting in
									// indexlistview
									// nameItems.add(obj1.getString("EmpNameAndCode").toString());
									// adding array
									empProfileList.add(staff);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}						
							adapter.notifyDataSetChanged();							
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError error) {
							System.err.println(error);
							// TODO Auto-generated method stub
							 // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
						    // For AuthFailure, you can re login with user credentials.
						    // For ClientError, 400 & 401, Errors happening on client side when sending api request.
						    // In this case you can check how client is forming the api and debug accordingly.
						    // For ServerError 5xx, you can do retry or handle accordingly.
						    if( error instanceof NetworkError) {
						    	
						    } else if( error instanceof ServerError) {
						    } else if( error instanceof AuthFailureError) {
						    } else if( error instanceof ParseError) {
						    } else if( error instanceof NoConnectionError) {
						    } else if( error instanceof TimeoutError) {
						    }
						}
					});
			arrobj.setRetryPolicy(new DefaultRetryPolicy(5000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			AppController.getInstance().addToRequestQueue(arrobj);

			// Adding request to request queue			
		}
   
	   
	   
	   public boolean onCreateOptionsMenu(Menu menu) {
//		   
//		   		   Log.d("Search box", "Search box!........");
			MenuInflater inflater = getActivity().getMenuInflater();

			super.onCreateOptionsMenu(menu,inflater);
			
			inflater.inflate(R.menu.search_profile, menu);
			
//			SearchManager searchManager = (SearchManager)getActivity(). getSystemService(Context.SEARCH_SERVICE);
//			SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//			searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//			searchView.setOnQueryTextListener(this);

			return true;
		}
	   
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
		 
}
