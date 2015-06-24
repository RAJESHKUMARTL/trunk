package com.exenta.leaveapply;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.animation.ExpandCollapseAnimation;
import com.exenta.app.AppController;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Leave_Request extends FragmentActivity {
	RadioGroup leave;
	RadioButton general, compensatory_off;
	ArrayList<String> leavetype = new ArrayList<String>();
	String jsondata;
	int JSON_Length = 0;
	LeaveRequestmodel leaverequest;
	SharedPreferences prefs;
	int check = 0, check_re = 0;
	EmpDetailsSessions session;
	String employeeID, companyID;

	// Recent Leave Summary
	RecentLeaveData recentLeaveData;
	ArrayList<String> fromDate = new ArrayList<String>();
	ArrayList<String> toDaTe = new ArrayList<String>();
	ArrayList<String> no_of_Days = new ArrayList<String>();
	ArrayList<String> approval_Status = new ArrayList<String>();
	JSONArray arrobj;
	TableLayout table_layout, table_layout_recent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.leave_request);
		Spinner category = (Spinner) findViewById(R.id.leave_category);
		
		session =  new EmpDetailsSessions(getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		
		final ImageView icon_ = (ImageView) findViewById(R.id.icon_change);
		RelativeLayout Leave_Cat_Click = (RelativeLayout) findViewById(R.id.leave_category_click);
		final LinearLayout Leave_Cat_Hide = (LinearLayout) findViewById(R.id.leave_category_hide);
		
		final ImageView icon_recent = (ImageView) findViewById(R.id.imageview2);
		RelativeLayout leave_recent_click = (RelativeLayout) findViewById(R.id.leave_recent_click);
		final LinearLayout leave_recent_hide = (LinearLayout) findViewById(R.id.leave_recent_hide);

		table_layout_recent = (TableLayout) findViewById(R.id.tableLayout2);
		table_layout_recent.removeAllViews();

		Leave_Cat_Click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {
				// TODO Auto-generated method stub
				ExpandCollapseAnimation animation = null;
				if (check == 0) {
					animation = new ExpandCollapseAnimation(Leave_Cat_Hide,
							100, 1);
					icon_.setBackgroundResource(R.drawable.arrow_right);
					check = 1;
				} else {
					animation = new ExpandCollapseAnimation(Leave_Cat_Hide,
							100, 0);
					icon_.setBackgroundResource(R.drawable.arrow);
					check = 0;
				}
				Leave_Cat_Hide.startAnimation(animation);
			}
		});
		leave_recent_click.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View view) {
				// TODO Auto-generated method stub
				ExpandCollapseAnimation animation_re = null;
				if (check_re == 0) {
					animation_re = new ExpandCollapseAnimation(
							leave_recent_hide, 100, 0);
					icon_recent.setBackgroundResource(R.drawable.arrow);
					check_re = 1;
				} else {
					animation_re = new ExpandCollapseAnimation(
							leave_recent_hide, 100, 1);
					icon_recent.setBackgroundResource(R.drawable.arrow_right);
					check_re = 0;
				}
				leave_recent_hide.startAnimation(animation_re);
			}
		});

		if (savedInstanceState == null) {

			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new General()).commit();

		}
		
		category.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				if (position == 0) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, new General()).commit();
				} else {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.container, new CompensatoryOff())
							.commit();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		jsonParsingRecent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}

	// *****************************************************************************//
	// *****************************************************************************//
	// *****************************************************************************//

	public void jsonParsingRecent() {
		fromDate.clear();
		toDaTe.clear();
		no_of_Days.clear();
		approval_Status.clear();
		

		Calendar cal = Calendar.getInstance();
					
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String sysDate = year+"-"+month+"-"+day;
		
		System.out.println("Employee ID:"+employeeID);
		System.out.println("Company ID:"+companyID);
				
		JsonObjectRequest getRecentLeaveReq = new JsonObjectRequest(
							
				
				Const.Leave.URL_JSON_ARRAY_LeaveSum_p1+employeeID+Const.Leave.URL_JSON_ARRAY_LeaveSum_p2+companyID+Const.Leave.URL_JSON_ARRAY_LeaveSum_p3+month+Const.Leave.URL_JSON_ARRAY_LeaveSum_p4+year, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

						Log.d("JSON", response.toString());

						try {
							JSONObject jsonObj;
							arrobj = response
									.getJSONArray("getLeaveSummaryResult");
							

							for (int i = 0; i < arrobj.length(); i++) {

								jsonObj = arrobj.getJSONObject(i);
								recentLeaveData = new RecentLeaveData();
								recentLeaveData.setFromDate(jsonObj
										.getString("FromDate"));
								recentLeaveData.setToDate(jsonObj
										.getString("ToDate"));
								recentLeaveData.setNo_of_Days(jsonObj
										.getString("TotalDays"));
								recentLeaveData.setStatus(jsonObj
										.getString("Status"));
								fromDate.add(recentLeaveData.getFromDate());
								toDaTe.add(recentLeaveData.getToDate());
								no_of_Days.add(recentLeaveData.getNo_of_Days());
								approval_Status.add(recentLeaveData.getStatus());
							}
							BuildRecentLeaveTable(arrobj.length(), 1);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Error: " + error.getMessage());
					}
				});
		AppController.getInstance().addToRequestQueue(getRecentLeaveReq);
	}

	protected void BuildRecentLeaveTable(int length, int col) {
		// TODO Auto-generated method stub

		System.out.println("rows" + length);
		System.out.println("cols" + col);
		// outer for loop

		try {
			for (int i = 0; i <= length; i++) {

				TableRow row = new TableRow(this);
				row.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
				row.setWeightSum(4);
				// inner for loop
				for (int j = 1; j <= col; j++) {

					TextView tv1 = new TextView(this);
					tv1.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT,1f));
					// tv1.setBackgroundResource(R.drawable.cell_shape);
					tv1.setPadding(8, 8, 8, 8);
					tv1.setGravity(Gravity.CENTER);
					tv1.setText(fromDate.get(i));
					row.addView(tv1);

					TextView tv2 = new TextView(this);
					tv2.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT,1f));
					// tv2.setBackgroundResource(R.drawable.cell_shape);
					tv2.setPadding(8, 8, 8, 8);
					tv2.setGravity(Gravity.CENTER);
					tv2.setText(toDaTe.get(i));

					row.addView(tv2);

					TextView tv3 = new TextView(this);
					tv3.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT,1f));
					// tv3.setBackgroundResource(R.drawable.cell_shape);
					tv3.setPadding(8, 8, 8, 8);
					tv3.setGravity(Gravity.CENTER);
					tv3.setTextColor(Color.RED);
					tv3.setText(no_of_Days.get(i));
					row.addView(tv3);

					Button tv4 = new Button(this);
					tv4.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT,1f));
					tv4.setPadding(8, 8, 8, 8);
					tv4.setGravity(Gravity.CENTER);
					{
						if (approval_Status.get(i).equals("Rejected")) {
							tv4.setBackgroundResource(R.drawable.reject);
						} else if (approval_Status.get(i).equals("Approved")) {
							tv4.setBackgroundResource(R.drawable.app_status);
						} else {
							tv4.setBackgroundResource(R.drawable.pending);
						}
					}
					row.addView(tv4);
				}
				table_layout_recent.addView(row);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
