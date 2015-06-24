package com.exenta.tab;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.customUI.CustomLoading;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.xxmassdeveloper.mpchartexample.custom.MyValueFormatter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class MyAttandenceSummary extends Fragment {

	private BarChart mChart;
	private Typeface mTf;
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	JSONArray arrobj;
	ArrayList<String> percentag = new ArrayList<String>();
	float[] graphdata;
	MyAttandanceModel myattendance;
	ArrayList<BarData> list;
	EmpDetailsSessions session;
	String employeeID, companyID;
	//CustomLoading customui = new CustomLoading();
	//Leave Summary Balance
		ArrayList<String> leavetype = new ArrayList<String>();
		ArrayList<String> Remain = new ArrayList<String>();
		ArrayList<String> Daysreq = new ArrayList<String>();
		ArrayList<String> leavecat = new ArrayList<String>();
		String serviceUrl;
		TableLayout table_layout;
		String jsondata;
		int JSON_Length = 0;
		LeaveData myleaves;

	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        View rootview = inflater.inflate(R.layout.activity_attendance, container, false);
	        
	        
	    	
			session =  new EmpDetailsSessions(getActivity().getApplicationContext());
			HashMap<String,String> user =  session.getEmployeeDetails();  
			
			employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);		
			companyID = user.get(EmpDetailsSessions.COMPANY_ID);
			
			System.out.println("Employee-ID:"+employeeID);
			
			System.out.println("Company-ID:"+companyID);

			Calendar cal = Calendar.getInstance();
						
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String sysDate = year+"-"+month+"-"+day;
			
			serviceUrl = Const.LEAVEURL_GRAPHDATA_p1+employeeID+Const.LEAVEURL_GRAPHDATA_p2+year;
	        	        	        
	    	myAttendanceGraphReport();
			MyleaveBalanceSummary();		
			
			
			mTf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
			graphdata = new float[12];

			mChart = (BarChart)rootview.findViewById(R.id.chart1);

			mChart.setDescription("");
			mChart.setBackgroundResource(R.color.white);
			// if more than 60 entries are displayed in the chart, no values will be
			// drawn
			mChart.setMaxVisibleValueCount(60);

			// scaling can now only be done on x- and y-axis separately
			mChart.setPinchZoom(false);
			mChart.setActivated(false);
			mChart.setDragEnabled(false);
			mChart.setClickable(false);
			mChart.setTouchEnabled(false);
			mChart.setSelected(false);

			mChart.setDrawBarShadow(false);
			mChart.setDrawGridBackground(false);

			XAxis xAxis = mChart.getXAxis();
			xAxis.setPosition(XAxisPosition.BOTTOM);
			xAxis.setSpaceBetweenLabels(1);
			xAxis.setTypeface(mTf);
			xAxis.setDrawGridLines(false);
			
			  ValueFormatter custom = new MyValueFormatter();
			  YAxis leftAxis = mChart.getAxisLeft();
	            leftAxis.setTypeface(mTf);
              leftAxis.setValueFormatter(custom);
	            leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
	            leftAxis.setLabelCount(5);
	            leftAxis.setSpaceTop(15f);
	            
	            leftAxis.setDrawGridLines(false);
	            YAxis rightAxis = mChart.getAxisRight();
	            rightAxis.setLabelCount(5);
	            rightAxis.setSpaceTop(15f);
	            
	            rightAxis.setDrawGridLines(false);
			// add a nice and smooth animation
			mChart.animateY(2500);

			mChart.getLegend().setEnabled(false);
			list = new ArrayList<BarData>();
			
			table_layout = (TableLayout)rootview.findViewById(R.id.tableLayout1);
			table_layout.removeAllViews();
    return rootview;
	        }
	    private BarData generateData(int cnt) {

			ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

			for (int i = 0; i < 12; i++) {
				// float[] graphdata = {20,30,80,30,40,60,80,70,90,20,18,100};
				entries.add(new BarEntry(graphdata[i], i));
				
			}

			BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
			d.setBarSpacePercent(20f);
			d.setColors(ColorTemplate.VORDIPLOM_COLORS);
			d.setBarShadowColor(Color.rgb(203, 203, 203));

			ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
			sets.add(d);

			BarData cd = new BarData(getMonths(), sets);
			mChart.setData(cd);
			return cd;
		}

		private ArrayList<String> getMonths() {

			ArrayList<String> m = new ArrayList<String>();
			m.add("Jan");
			m.add("Feb");
			m.add("Mar");
			m.add("Apr");
			m.add("May");
			m.add("Jun");
			m.add("Jul");
			m.add("Aug");
			m.add("Sep");
			m.add("Oct");
			m.add("Nov");
			m.add("Dec");
			return m;
		}

		private void myAttendanceGraphReport() {
					
			percentag.clear();
			//customui.LoadingScreen(getActivity());
			// showProgressDialog();
			JsonObjectRequest jsonObjReq = new JsonObjectRequest(
					serviceUrl, null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.d("TAG", response.toString());

							try {
								JSONObject jsonObj;
								arrobj = response
										.getJSONArray("MonthlyAttendancePercentageResult");

								for (int i = 0; i < arrobj.length(); i++) {

									jsonObj = arrobj.getJSONObject(i);

									myattendance = new MyAttandanceModel();
									myattendance.setAttMonth(jsonObj
											.getString("AttMonth"));
									myattendance.setAttYear(jsonObj
											.getString("AttYear"));
									myattendance.setLeaveDays(jsonObj
											.getString("LeaveDays"));
									myattendance.setPercentage(jsonObj
											.getString("Percentage"));
									myattendance.setPresentDays(jsonObj
											.getString("Percentage"));
									myattendance.setRemaining(jsonObj
											.getString("Remaining"));
									myattendance.setTotalDays(jsonObj
											.getString("TotalDays"));

									percentag.add(myattendance.getPercentage());
									System.out.println(percentag);
									// graphdata = new float[percentag.size()];
									graphdata[i] = Float.parseFloat(percentag
											.get(i));

									Log.d("JSON Array", arrobj.toString());
									// Parsing JSON
									//customui.LoadingHide();
								}
								
								for (int i = 0; i < 1; i++) {
									list.add(generateData(i + 1));

								}
							
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}, new Response.ErrorListener() {

						@Override
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
			
				
			jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(2000, 
	                5, 
	                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		}
		
		
		

		private void BuildTable(int rows, int cols) 
		{
			System.out.println("rows" + rows);
			System.out.println("cols" + cols);
			// outer for loop

			try {
				for (int i = 0; i <= rows; i++) {

					TableRow row = new TableRow(getActivity());
					row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					
					// inner for loop
					for (int j = 1; j <= cols; j++) {
						
						TextView tv1 = new TextView(getActivity());
						tv1.setLayoutParams(new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
						//tv1.setBackgroundResource(R.color.grey);
						tv1.setBackgroundResource(R.drawable.cell_shape);
						tv1.setGravity(Gravity.CENTER);
						tv1.setPadding(8, 8, 8, 8);
						if (i == 0) {
							tv1.setTextSize(15);
							tv1.setBackgroundResource(R.color.yellowLighting);
							
						}
						tv1.setText(leavetype.get(i));
					
						row.addView(tv1);
						//row.setBackgroundColor(Color.GRAY);

						TextView tv2 = new TextView(getActivity());
						tv2.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						//tv2.setBackgroundColor(Color.CYAN);
					tv2.setBackgroundResource(R.drawable.cell_shape);
						tv2.setGravity(Gravity.CENTER);
						tv2.setPadding(8, 8, 8, 8);
						if (i == 0) {
							tv2.setTextSize(15);
							tv2.setBackgroundResource(R.color.yellowLighting);
						}
						tv2.setText(leavecat.get(i));
						//row.setBackgroundColor(Color.CYAN);
						row.addView(tv2);

						TextView tv3 = new TextView(getActivity());
						tv3.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						//tv3.setBackgroundColor(Color.LTGRAY);
				tv3.setBackgroundResource(R.drawable.cell_shape);
						tv3.setGravity(Gravity.CENTER);
						tv3.setPadding(8, 8, 8, 8);
						// tv3.setTextColor(color);
						if (i == 0) {
							tv3.setTextSize(15);
							tv3.setBackgroundResource(R.color.yellowLighting);
						} else {
							tv3.setTextColor(Color.RED);
						}

						tv3.setText(Daysreq.get(i));
						row.addView(tv3);
						//row.setBackgroundColor(Color.LTGRAY);
						TextView tv4 = new TextView(getActivity());
						tv4.setLayoutParams(new LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT));
						//tv4.setBackgroundColor(Color.YELLOW);
						tv4.setBackgroundResource(R.drawable.cell_shape);
						tv4.setGravity(Gravity.CENTER);
						tv4.setPadding(8, 8, 8, 8);
						if (i == 0) {
							tv4.setTextSize(15);
							tv4.setBackgroundResource(R.color.yellowLighting);
							
						}
						tv4.setText(Remain.get(i));
						row.addView(tv4);
						//row.setBackgroundColor(Color.YELLOW);
					}
					table_layout.addView(row);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		public void MyleaveBalanceSummary() {
			//showProgressDialog();
		//	customui.LoadingScreen(getActivity());
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String sysDate = year+"-"+month+"-"+day;
			
			JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY_P1+employeeID+Const.URL_JSON_ARRAY_P2+sysDate+Const.URL_JSON_ARRAY_Comp_p2_p1,
					new Response.Listener<JSONArray>() {
						@Override
						public void onResponse(JSONArray response) {
							Log.d("uuu", response.toString());

							System.out.println(response.toString());
							jsondata = response.toString();
							//jsonparsing();
							if (jsondata != null) {

								leavetype.clear();
								leavecat.clear();
								Daysreq.clear();
								Remain.clear();
								leavetype.add("Leave Type");
								leavecat.add("Allotted Leave");
								Daysreq.add("Leave Taken");
								Remain.add("Available Balance");
								System.out.println("in parserclass" + jsondata);

								try {

									JSONArray jsonArray = new JSONArray(jsondata.trim());
									// JSONObject jsonObject = new JSONObject(jsondata.trim());
									JSON_Length = jsonArray.length();
									System.out.println("jarrayyy2" + jsonArray.length());
									for (int i = 0; i < JSON_Length; i++) {
										JSONObject jsonObject = jsonArray.getJSONObject(i);
										myleaves = new LeaveData();
										myleaves.setLeaveTypeAcronym(jsonObject
												.getString("LeaveTypeAcronym"));
										myleaves.setLeavecategory(jsonObject
												.getString("Leavecategory"));
										myleaves.setDaysInRequest(jsonObject
												.getString("DaysInRequest"));
										myleaves.setRemaining(jsonObject.getString("Remaining"));

										System.out.println("length..............." + JSON_Length);

										leavetype.add(myleaves.getLeaveTypeAcronym());
										leavecat.add(myleaves.getLeavecategory());
										Daysreq.add(myleaves.getDaysInRequest());
										Remain.add(myleaves.getRemaining());

										System.out.println("LeaveTypeAcronym" + leavetype);
										System.out.println("Leavecategory" + leavecat);
										System.out.println("DaysInRequest" + Daysreq);
										System.out.println("Remaining" + Remain);
									//	customui.LoadingHide();	
									}
								

									BuildTable(JSON_Length, 1);
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									System.out.println("Exception: " + e.getMessage());
								}
								
							} else {
								Log.e("jsonerror", "Couldn't get any data from the url");
							}
							//hideProgressDialog();
						}
					}, new Response.ErrorListener() {
						@Override
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
			
			req.setRetryPolicy(new DefaultRetryPolicy(1000, 
	                5, 
	                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
				
			// Adding request to request queue
			AppController.getInstance().addToRequestQueue(req, tag_json_arry);

			// Cancelling request
			// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
		}
		
		
		
		
}
