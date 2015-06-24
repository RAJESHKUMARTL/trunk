package com.exentahrms.profiledetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.cardviewnotification.NotificationList;
import com.exenta.leaveapproval.LeaveApprovalData;
import com.exentahrms.indexlistview.IndexableListView;
import com.exentahrms.staffinfo.ProfileDetails;
import com.exentahrms.staffinfo.adater.Profile_CustomListAdapter;
import com.exentahrms.staffinfo.json.GetEmployeeProfileJSON;
import com.exentahrms.staffinfo.json.VolleyCallback;
import com.exentahrms.staffinfo.model.GetAllEmployeeProfile;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;

public class EmployeeProfile extends Activity implements OnQueryTextListener {
	
	EmpDetailsSessions session;
	String employeeID, companyID;
	public static int selectedValue=-1;
	
	
	// EmployeeProfile JSON URL
	//private static final String Getcompany = Const.ProfileDetails.GET_EMPLOYEE_DETAILS_P1++;

	// Object for Getter and Setter
	private List<GetAllEmployeeProfile> empProfileList = new ArrayList<GetAllEmployeeProfile>();
	GetAllEmployeeProfile staff;

	// Views
	private IndexableListView listView;

	// Adapter for custom listview
	private Profile_CustomListAdapter adapter;

	// Variable Assigned
	private static final String TAG = EmployeeProfile.class.getSimpleName();
	private static final int DIALOG_LOADING = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		
		session =  new EmpDetailsSessions(getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		
		
		setContentView(R.layout.employee_profile);
		// mStatusView = (TextView) findViewById(R.id.status_text);
		listView = (IndexableListView) findViewById(R.id.emp_profile);
		adapter = new Profile_CustomListAdapter(this, empProfileList);
		listView.setAdapter(adapter);
		listView.setFastScrollEnabled(true);
		listView.setTextFilterEnabled(true);
		GetAllEmployee();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				selectedValue = position;
			
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

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// dismissDialog(DIALOG_LOADING);
	}

	@SuppressWarnings("deprecation")
	private void hidePDialog() {
		dismissDialog(DIALOG_LOADING);
	}

	@SuppressWarnings("deprecation")
	private void GetAllEmployee() {
		// empList.clear();
		showDialog(DIALOG_LOADING);
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
						// notifying list adapter about data changes // so that
						// it renders the list view with updated data
						// ContentAdapter adapter = new
						// ContentAdapter(EmployeeProfile.this,
						// android.R.layout.simple_list_item_1, nameItems);
						// Collections.sort(nameItems);
						adapter.notifyDataSetChanged();
						hidePDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hidePDialog();

						adapter.notifyDataSetChanged();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(arrobj);
	}

	// Custom Dialog
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_LOADING:
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// here we set layout of progress dialog
			dialog.setContentView(R.layout.custom_dialog_box);
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub

				}
			});
			return dialog;

		default:
			return null;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.search_profile, menu);

//		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
//				.getActionView();
//
//		searchView.setSearchableInfo(searchManager
//				.getSearchableInfo(getComponentName()));
//		searchView.setOnQueryTextListener(this);

		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.profile:
		   final Intent i = new Intent(EmployeeProfile.this,ProfileDetails.class);
			new GetEmployeeProfileJSON( employeeID , new VolleyCallback() {
				
				@Override
				public void onSuccess(
						List<GetEmployeeProfile> emp_details_list) {
					
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
		            hidePDialog();
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
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}*/

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		adapter.getFilter().filter(newText);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
