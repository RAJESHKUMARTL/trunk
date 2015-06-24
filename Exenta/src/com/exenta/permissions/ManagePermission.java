package com.exenta.permissions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class ManagePermission extends Activity {
	public static String URL_My_Leave_Balance = null;

	String employeeID, companyID;
	int day, month, year;
	String date;
	int JSON_Length;
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	LeaveBalanceModel lvbalancemdl;

	TextView lvblnc;
	EditText perdate;

	static EditText frmtime;

	static EditText totime;

	EditText just;

	static EditText ttlduratn;
	ImageButton imgbfrmtime, imgbtotime;
	Button apply, reset;
	static String FromTime="";
	static String permdate="";
	static String ToTime, Totalduratn;
	static String totalhr = "";
	String justification="";
	static String app_am_pm;
	public static String URL_APPLY_PERMISSION = null;
	static String Apply_fromtime;
	String appliedDate_apply;
	EmpDetailsSessions session;
	static String permissionDate_apply;
	static String Apply_totm;
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Timeeeeeeeeeeeeeee!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	static int hourtwl_from = 0;
	
	static String am_pm_from = "";
	static String minutes_from = "";
	static int totm_from=0;
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Timeeeeeeeeeeeeeeeeee!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_manage);
		
		
		session =  new EmpDetailsSessions(getApplicationContext().getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		
		
		Calendar cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1;
		year = cal.get(Calendar.YEAR);
		date = year + "-" + month + "-" + day;
		appliedDate_apply=day+"-"+ month +"-"+year;
		lvblnc = (TextView) findViewById(R.id.lvvblnc);
		My_Leave_Balance();
		perdate = (EditText) findViewById(R.id.perdate);
		frmtime = (EditText) findViewById(R.id.frmtime);
		totime = (EditText) findViewById(R.id.totime);
		just = (EditText) findViewById(R.id.jus);
		ttlduratn = (EditText) findViewById(R.id.total);

		imgbfrmtime = (ImageButton) findViewById(R.id.imageButton1);
		imgbtotime = (ImageButton) findViewById(R.id.imageButton2);
		apply = (Button) findViewById(R.id.button1);
		reset = (Button) findViewById(R.id.button2);

		perdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				DialogFragment picker = new PermissionDatePicker();
				picker.show(getFragmentManager(), "datePicker");

			}
		});
		imgbfrmtime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(permdate.equals(""))
				{
					Toast.makeText(getApplicationContext(),
							"Please enter date", Toast.LENGTH_SHORT)
							.show();		
				}
				else{
				DialogFragment newFragment0 = new TimePickerFragment();
				newFragment0.show(getFragmentManager(), "timePicker");
				}
			}
		});

		imgbtotime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DialogFragment newFragment1 = new TimePickerFragment1();
				newFragment1.show(getFragmentManager(), "timePicker");
			}
		});

		apply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				
			 justification = just.getText().toString();
			 String leavebalance=lvblnc.getText().toString();
			 /*if (permdate.equals("") || frmtime.equals("")
						|| totime.equals("") || justification.equals("")
						|| ttlduratn.equals("")) {	*/
				//System.out.println(Totalduratn);
				if (permdate.equals("") || FromTime.equals("")
						|| ToTime.equals("") || justification.equals("")
						|| Totalduratn.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter all fields", Toast.LENGTH_SHORT)
							.show();    
				}

				else if (!Totalduratn.equals("1.0")) {

					Toast.makeText(getApplicationContext(),
							"Permission allowed for 1 hour only",
							Toast.LENGTH_SHORT).show();

				}else if (leavebalance.equals("")) {
					Custom_alert("Network Error");			
				}

				else if (leavebalance.equals("0.0")) {
					Toast.makeText(getApplicationContext(), "No Permission Balance",
							Toast.LENGTH_SHORT).show();
				}

				else {

					apply_permission();

				}

			}
		});
		
		
	reset.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	});			
	}

	private void My_Leave_Balance() {
		// showProgressDialog();
		URL_My_Leave_Balance = Const.URL_LEAVEBalance1 + companyID
				+ Const.URL_LEAVEBalance2 + employeeID
				+ Const.URL_LEAVEBalance3 + date;
		System.out.println(URL_My_Leave_Balance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				URL_My_Leave_Balance, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());

						if (response.toString() != null) {

							try {
								JSONObject jsonObj;
								JSONArray arrobj = response
										.getJSONArray("getRemainingPermissionDetailsResult");
								JSON_Length = arrobj.length();
								System.out.println("jarrayyy" + JSON_Length);
								for (int i = 0; i < arrobj.length(); i++) {

									jsonObj = arrobj.getJSONObject(i);
									lvbalancemdl = new LeaveBalanceModel();
									lvbalancemdl.setComapanyID(jsonObj
											.getInt("comapanyID"));
									lvbalancemdl.setEmployeeID(jsonObj
											.getInt("employeeID"));
									lvbalancemdl.setMaxPermissionperMon(jsonObj
											.getDouble("maxPermissionperMon"));
									lvbalancemdl.setMaxPermissionType(jsonObj
											.getInt("maxPermissionType"));
									lvbalancemdl.setPerday(jsonObj
											.getDouble("perday"));
									lvbalancemdl.setPermissionRuleID(jsonObj
											.getInt("permissionRuleID"));
									lvbalancemdl.setPermissionSettingId(jsonObj
											.getInt("permissionSettingId"));
									lvbalancemdl.setRemainingTime(jsonObj
											.getDouble("remainingTime"));

								}
								System.out.println("ssssssssssss"
										+ lvbalancemdl.getRemainingTime());
								lvblnc.setText(String.valueOf(lvbalancemdl
										.getRemainingTime()));
								// hideProgressDialog();
							} catch (Exception e) {
								System.out.println(e);
							}

						} else {
							Log.e("jsonerror",
									"Couldn't get any data from the url");
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("TAG", "Error: " + error.getMessage());
						// adapter.notifyDataSetChanged();
						// hideProgressDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		// Cancelling request		
	}


	@SuppressLint("NewApi")
	public class PermissionDatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		private int myear;
		private int mmonth;
		private int mday;
		private int hour;
		private int mminute;

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Calendar c = Calendar.getInstance();
			myear = c.get(Calendar.YEAR);
			mmonth = c.get(Calendar.MONTH);
			mday = c.get(Calendar.DAY_OF_MONTH);
			hour = c.get(Calendar.HOUR_OF_DAY);
			mminute = c.get(Calendar.MINUTE);
			DatePickerDialog _date = new DatePickerDialog(getActivity(), this,
					myear, mmonth, mday) {
				public void onDateChanged(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					if (year < myear)
						view.updateDate(myear, mmonth, mday);

					if (monthOfYear < mmonth && year == myear)
						view.updateDate(myear, mmonth, mday);

					if (dayOfMonth < mday && year == myear
							&& monthOfYear == mmonth)
						view.updateDate(myear, mmonth, mday);

				}
			};
			final Calendar cc = Calendar.getInstance();
			int yy = cc.get(Calendar.YEAR);
			int mm = cc.get(Calendar.MONTH);
			int dd = cc.get(Calendar.DAY_OF_MONTH);
			return _date;
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			myear = yy;
			mmonth = mm + 1;
			mday = dd;
			permdate = mday + "/" + mmonth + "/" + myear;
			perdate.setText(" " + mday + "/" + mmonth + "/" + myear);
		
			if(mday<10 && mmonth<10){
				permissionDate_apply="0"+mday +"-0"+ mmonth +"-"+ myear;	
			}else if(mday>10 && mmonth<10){
				permissionDate_apply=mday +"-0"+ mmonth +"-"+ myear;	
			}else if(mday<10 && mmonth>10){
				permissionDate_apply="0"+mday +"-"+ mmonth +"-"+ myear;	
			}else if(mday>10 && mmonth>10){
				permissionDate_apply=mday + "-" + mmonth + "-" + myear;	
			}

		}

	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		int hour, minute;
		Calendar c;

		public TimePickerFragment() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			int pm = c.get(Calendar.AM_PM);
			System.out.println("am---pm" + pm);
			TimePickerDialog tmepic = new TimePickerDialog(getActivity(), this,
					hour, minute, DateFormat.is24HourFormat(getActivity())) {

				@Override
				public void onTimeChanged(TimePicker view, int hourOf, int min) {
					// TODO Auto-generated method stub
					super.onTimeChanged(view, hourOf, min);

					Calendar temp = Calendar.getInstance();
					temp.set(Calendar.HOUR_OF_DAY, hourOf);
					temp.set(Calendar.MINUTE, min);

					if (temp.before(GregorianCalendar.getInstance())) {
						updateTime(hour, minute);

						Toast.makeText(getActivity(),
								"Cannot select a past time", Toast.LENGTH_SHORT)
								.show();
					}

				}

			};
			return tmepic;
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			
System.out.println("hrrrrrrrrrrrr"+hourOfDay);
			if (hourOfDay > 12) {

				hourtwl_from = hourOfDay - 12;

				am_pm_from = "PM";

			}

			else if (hourOfDay == 0) {
				hourtwl_from = hourOfDay + 12;
				am_pm_from = "AM";
			}

			else if (hourOfDay == 12) {
				hourtwl_from = 12;
				am_pm_from = "PM";

			} else {
				hourtwl_from = hourOfDay;
				
				am_pm_from = "AM";
			}

			if (minute < 10)
				minutes_from = "0" + minute;
			else
				minutes_from = String.valueOf(minute);
			app_am_pm = am_pm_from;

			FromTime = hourtwl_from + ":" + minutes_from + " " + am_pm_from;
			System.out.println("FromTime.........."+FromTime);
			frmtime.setText(FromTime);
			totm_from = hourtwl_from + 1;

			if (totm_from > 12) {

				totm_from = totm_from - 12;

			}else if(totm_from == 12){
				
				am_pm_from="PM"	;
				
			}

			ToTime = totm_from + ":" + minutes_from + " " + am_pm_from;
			
			totime.setText(ToTime);
		
			Totalduratn = calculateTotaltome(FromTime, ToTime);
			ttlduratn.setText(Totalduratn);

		}

	}

	public static class TimePickerFragment1 extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		int hour, minute;
		Calendar c;

		public TimePickerFragment1() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);

			TimePickerDialog tmepic = new TimePickerDialog(getActivity(), this,
					hour, minute, DateFormat.is24HourFormat(getActivity())) {

				@Override
				public void onTimeChanged(TimePicker view, int hourOf, int min) {
					// TODO Auto-generated method stub
					super.onTimeChanged(view, hourOf, min);

					Calendar temp = Calendar.getInstance();
					temp.set(Calendar.HOUR_OF_DAY, hourOf);
					temp.set(Calendar.MINUTE, min);

					if (temp.before(GregorianCalendar.getInstance())) {
						updateTime(hour, minute);

						Toast.makeText(getActivity(),
								"Cannot select a past time", Toast.LENGTH_SHORT)
								.show();
					}

				}

			};
			return tmepic;
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			int hourtwl = 0;
			String am_pm = "";
			String minutes = "";

			if (hourOfDay > 12) {
				hourtwl = hourOfDay - 12;

				am_pm = "PM";

			} else if (hourOfDay == 0) {
				hourtwl = hourOfDay + 12;
				am_pm = "AM";

			} else if (hourOfDay == 12) {
				am_pm = "PM";

			}

			else {
				hourtwl = hourOfDay;
				am_pm = "AM";
			}

			if (minute < 10)
				minutes = "0" + minute;
			else
				minutes = String.valueOf(minute);
			ToTime = hourtwl + ":" + minutes + " " + am_pm;
			
		
			totime.setText(ToTime);

			if (FromTime.equals("") || ToTime.equals("")) {
				Toast.makeText(getActivity(), "Enter time", Toast.LENGTH_SHORT)
						.show();
			} else {
				Totalduratn = calculateTotaltome(FromTime, ToTime);
				ttlduratn.setText(Totalduratn);
			}

		}
	}

	private static String calculateTotaltome(String fromTime, String toTime) {
		// TODO Auto-generated method stub

		System.out.println(fromTime);
		System.out.println(toTime);

		Date t1 = null;
		Date t2 = null;
		try {
			fromTime = permdate + " " + fromTime;
			toTime = permdate + " " + toTime;
			
			System.out.println(fromTime);
			System.out.println(toTime);

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

			t1 = format.parse(fromTime);
			t2 = format.parse(toTime);
			System.out.println(t1);
			System.out.println(t2);
			// in milliseconds
			long diff = t2.getTime() - t1.getTime();

			System.out.println(diff);

			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;

			System.out.println(diffHours + " hours, ");
			System.out.println(diffMinutes + " minutes, ");

			totalhr = diffHours + "." + diffMinutes;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalhr;
	}
	
	private void apply_permission() {
		Apply_fromtime =permissionDate_apply+" "+ hourtwl_from + ":" + minutes_from ;
		Apply_totm =permissionDate_apply+" "+totm_from + ":" + minutes_from;	
	
		String empid=employeeID;
		String cmpid=companyID;
		String prmstid=String.valueOf(lvbalancemdl.getPermissionSettingId());
		String prmruleid=String.valueOf(lvbalancemdl.getPermissionRuleID());
		String prdt=permissionDate_apply;
		String apdt=appliedDate_apply;
		String reason=justification;
		String ttlhr=Totalduratn;
		String ftme=Apply_fromtime;
		String ttme=Apply_totm;
		String frmtmsec=app_am_pm;
		String totmsec=app_am_pm;
URL_APPLY_PERMISSION=Const.URL_APPLY_PERMISSION0+empid+Const.URL_APPLY_PERMISSION1+cmpid+Const.URL_APPLY_PERMISSION2+prmstid+Const.URL_APPLY_PERMISSION3+prmruleid+Const.URL_APPLY_PERMISSION4+prdt+Const.URL_APPLY_PERMISSION5+apdt+Const.URL_APPLY_PERMISSION6+reason+Const.URL_APPLY_PERMISSION7+ttlhr+
Const.URL_APPLY_PERMISSION8+ftme+Const.URL_APPLY_PERMISSION9+ttme+Const.URL_APPLY_PERMISSION10+frmtmsec+Const.URL_APPLY_PERMISSION11+totmsec+"&regularId=0";
	
URL_APPLY_PERMISSION = URL_APPLY_PERMISSION.replaceAll(" ", "%20");

// timeIn=2015-04-01%2009:00:00.000&timeOut=2015-01-02%2018:00:00.000
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				URL_APPLY_PERMISSION, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());
						try {

							JSONObject jsonObject = new JSONObject(response
									.toString().trim());
							String Apply_response = jsonObject
									.getString("getPermissionApplyResult");
							System.out.println(Apply_response);
							if(Apply_response.equals("1")){
								Custom_alert("Permission applied successfully");	
							}else if(Apply_response.equals("2")){
								Custom_alert("Permission already applied for the same date");		
							}else{
								Custom_alert("You do not have approval authority, So you cannot apply for leave");		
							}
							
						
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("TAG", "Error: " + error.getMessage());

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}
	
	public void Custom_alert(String msg) {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				getApplicationContext());

		// Setting Dialog Title
		alertDialog2.setTitle("Exenta");

		// Setting Dialog Message
		alertDialog2.setMessage(msg);

		alertDialog2.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						Intent intent = new Intent(ManagePermission.this, ManagePermission.class);
						startActivity(intent);	
						
					}
				});

		// Showing Alert Dialog
		alertDialog2.show();
	}
	
	
}
