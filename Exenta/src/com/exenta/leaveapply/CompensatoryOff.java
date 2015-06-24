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
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint({ "ValidFragment", "ShowToast" })
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CompensatoryOff extends Fragment {
	int catId = 2;
	EditText Leavedate, totaldayscompo, leavereasoncompo;
	Spinner compotype, compodate, sessioncompo;
	ArrayList<String> leavetype = new ArrayList<String>();
	private String tag_json_obj = "jobj_req";
	String jsondata;
	int JSON_Length = 0;
	ArrayList<String> compdate = new ArrayList<String>();
	LeaveRequestmodel leaverequest;
	Button apply, reset;
	private int myear;
	private int mmonth;
	private int mday;
	EmpDetailsSessions session;
	String employeeID, companyID;
	private String leaveTypeID;
	private String GetLeaveTypeID;

	public String getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(String leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}

	public String requesturl;

	@SuppressLint("ShowToast")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_compoff, container,
				false);

		session = new EmpDetailsSessions(getActivity().getApplicationContext());
		HashMap<String, String> user = session.getEmployeeDetails();

		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);

		System.out.println("employeeID employeeID:" + employeeID);
		System.out.println("companyID companyID companyID:" + companyID);

		Leavedate = (EditText) rootView.findViewById(R.id.leavdate);
		totaldayscompo = (EditText) rootView.findViewById(R.id.totaldays);
		leavereasoncompo = (EditText) rootView.findViewById(R.id.leavreason);
		compotype = (Spinner) rootView.findViewById(R.id.comtypespinner);
		compodate = (Spinner) rootView.findViewById(R.id.comdatespinner);
		sessioncompo = (Spinner) rootView.findViewById(R.id.sessionspinner);
		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		mmonth = c.get(Calendar.MONTH);
		mday = c.get(Calendar.DAY_OF_MONTH);
		Leavedate.setText(mday + "-" + (mmonth + 1) + "-" + myear);
		apply = (Button) rootView.findViewById(R.id.apply);
		apply.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("ShowToast")
			public void onClick(View v) {
				if ((totaldayscompo.getText().toString()
						.equalsIgnoreCase("0.0"))
						|| (totaldayscompo.getText().toString()
								.equalsIgnoreCase(""))) {
					Toast.makeText(getActivity(),"No comp off days available", Toast.LENGTH_SHORT).show();
				} else if (leavereasoncompo.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(getActivity(),"Please enter the reason", Toast.LENGTH_SHORT).show();					
				} else if (totaldayscompo.getText().toString().equals("0.5")
						&& sessioncompo.getSelectedItem().toString()
								.equalsIgnoreCase("full")) {
					Toast.makeText(getActivity(),"Please select Session", Toast.LENGTH_SHORT).show();					
				} else if (totaldayscompo.getText().toString().equals("1")
						&& (sessioncompo.getSelectedItem().toString()
								.equalsIgnoreCase("1st session") || sessioncompo
								.getSelectedItem().toString()
								.equalsIgnoreCase("2nd session"))) {
					Toast.makeText(getActivity(),"Please select Session", Toast.LENGTH_SHORT).show();
				} else {
					get_leave_TypeID();
				}
			}
		});
		reset = (Button) rootView.findViewById(R.id.reset);
		
		reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*	totaldayscompo.setText("0.0");
				leavereasoncompo.setText("");*/
				Intent inte = getActivity().getIntent();
				getActivity().finish();
				startActivity(inte);
			}
		});
		
		makeJsonObjReq();

		Leavedate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DialogFragment picker = new LeaveDatePicker();
				picker.show(getActivity().getFragmentManager(), "datePicker");
			}
		});

		return rootView;

	}

	protected void apply_leave() {
		// TODO Auto-generated method stub
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Const.Leave.URL_COMPENSATORY_OFF_p1
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p2 + employeeID
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p3 + companyID
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p4
						+ getLeaveTypeID()
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p5
						+ Leavedate.getText()
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p6
						+ sessioncompo.getSelectedItem().toString()
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p7
						+ totaldayscompo.getText()
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p8
						+ compodate.getSelectedItem().toString()
						+ Const.Leave.URL_COMPENSATORY_OFF_p1_p9
						+ leavereasoncompo.getText(), null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						// LeaveCategory
						try {
							JSONArray jsonArray = response
									.getJSONArray("applyCompOffResult");

							JSONObject jsonObj = jsonArray.getJSONObject(0);
							if (jsonObj.getString("result").equals("1")) {
								Custom_alert("Comp-off applied!..");
							} else if (jsonObj.getString("result").equals("2")) {
								Custom_alert("Sorry cannot apply for Comp-off!..");
							}
						}

						catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("JSON", "Error: " + error.getMessage());
						// hideProgressDialog();
					}
				});
		AppController.getInstance().addToRequestQueue(jsonObjectRequest,
				tag_json_obj);
	}

	public void get_leave_TypeID() {
		// TODO Auto-generated method stub
String catid="1";
GetLeaveTypeID=Const.Leave.URL_GENERAL_P1+catid+Const.Leave.URL_GENERAL_P2+employeeID;
		System.out.println("GetLeaveTypeID:" +GetLeaveTypeID);
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				GetLeaveTypeID, null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						// LeaveCategory
						System.out.println("" + response);

						try {
							JSONArray jsonArray = response
									.getJSONArray("leavetypeResult");

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObj = jsonArray.getJSONObject(i);
								if (jsonObj.getString("LeaveCategory").equals(
										"2")) {
									setLeaveTypeID(jsonObj
											.getString("LeaveTypeID"));
									System.out.println("LeaveTypeID "
											+ getLeaveTypeID());
								}
							}
							apply_leave();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("JSON", "Error: " + error.getMessage());
						// hideProgressDialog();
					}

				});

		AppController.getInstance().addToRequestQueue(jsonObjectRequest,
				tag_json_obj);

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void makeJsonObjReq() {

		// showProgressDialog();

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String sysDate = year + "-" + month + "-" + day;

		System.out.println("EMPLOYEE ID:" + employeeID);
		System.out.println("Company ID:" + companyID);

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Const.Leave.URL_COMPENSATORY_OFF + employeeID, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						System.out
								.println("URL REQUEST:"
										+ Const.Leave.URL_COMPENSATORY_OFF
										+ employeeID);

						Log.d("JSON", response.toString());
						System.out.println(response.toString());
						jsondata = response.toString();

						if (jsondata != null) {
							compdate.clear();
							compdate.add("Please Select");
							System.out.println("in" + jsondata);
							try {
								JSONObject jsonObject = new JSONObject(jsondata
										.trim());
								JSONArray jsonArray = jsonObject
										.getJSONArray("compOffRequestorListResult");
								JSON_Length = jsonArray.length();
								System.out.println("jarrayyy2"
										+ jsonArray.length());
								// leaverequest=new LeaveRequestmodel();
								for (int i = 0; i < JSON_Length; i++) {
									JSONObject jsonObj = jsonArray
											.getJSONObject(i);
									System.out.println("Comp-OFF:"
											+ jsonObj.getString("workDate"));
									compdate.add(jsonObj.getString("workDate"));
									totaldayscompo.setText(jsonObj
											.getString("days"));
								}
								ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
										getActivity(),
										android.R.layout.simple_spinner_item,
										compdate);
								dataAdapter
										.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								compodate.setAdapter(dataAdapter);
							} catch (Exception e) {
								System.out.println(e);
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("JSON", "Error: " + error.getMessage());
						// hideProgressDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
	}

	public class LeaveDatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			myear = c.get(Calendar.YEAR);
			mmonth = c.get(Calendar.MONTH);
			mday = c.get(Calendar.DAY_OF_MONTH);
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

			Leavedate.setText(mday + "-" + mmonth + "-" + myear);

		}

	}

	// *************************************************************************************//
	// ******************************** ALERT DIALOG
	// ***************************************//
	// *************************************************************************************//
	public void Custom_alert(String msg) {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				getActivity());

		// Setting Dialog Title
		alertDialog2.setTitle("Exenta");

		// Setting Dialog Message
		alertDialog2.setMessage(msg);

		// Setting Icon to Dialog
		// alertDialog2.setIcon(R.drawable.ic_launcher);

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog

						Intent intent = new Intent(getActivity(),
								Leave_Request.class);
						startActivity(intent);
					}
				});		
		alertDialog2.show();
	}

}
