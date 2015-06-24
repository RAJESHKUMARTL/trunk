package com.exenta.regularization;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.leaveapply.LeaveRequestmodel;
import com.exenta.leaveapply.General.DatePickerFrom;
import com.exenta.leaveapply.General.DatePickerTo;
import com.exenta.pod.ApprovalPODPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RegularizationLeave extends Fragment {

	EditText from_date, to_date, total_days, leave_reason;
	Spinner leave_type, session_from, session_to;
	Button apply, reset;
	int catId = 1;
	List<LeaveRequestmodel> leaverequest;
	LeaveRequestmodel leave_request_model;
	String jsondata;
	ArrayList<String> arraylist_leavetype = new ArrayList<String>();
	private String tag_json_obj = "jobj_req";
	int JSON_Length = 0;
	String todate1, fromdate1;
	int frommday, frommmonth, frommyear;
	int tomday, tommonth, tomyear;
	int getTotalDaysResult;
	private int myear;
	private int mmonth;
	private int mday;
	int Datevalidate_flag;
	Double TotalDate;
	String SessionDateFrom, SessionDateTo;
	Context context;
	int leave_id_spinner;
	String leave_name_spinner;
	EmpDetailsSessions session;
	String employeeID, companyID;

	public RegularizationLeave() {
		// TODO Auto-generated constructor stub
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.regul_leave, container, false);

		session = new EmpDetailsSessions(getActivity().getApplicationContext());
		HashMap<String, String> user = session.getEmployeeDetails();

		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);

		from_date = (EditText) rootView.findViewById(R.id.fromdate);
		session_from = (Spinner) rootView.findViewById(R.id.sessionfrom);
		to_date = (EditText) rootView.findViewById(R.id.todate);
		session_to = (Spinner) rootView.findViewById(R.id.sessionto);
		total_days = (EditText) rootView.findViewById(R.id.totaldays);
		total_days.setText("0.0");
		leave_reason = (EditText) rootView.findViewById(R.id.leavreason);
		// Spinner Leave Type
		leave_type = (Spinner) rootView.findViewById(R.id.leavetype);
		apply = (Button) rootView.findViewById(R.id.apply);
		JsonRequest_LeaveType();
		apply.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("Text:" + leave_reason.getText());
				if (leave_type.getSelectedItemPosition() == 0) {
					Toast.makeText(getActivity(), "Please select leave type",
							Toast.LENGTH_SHORT).show();//"Please select the leave type");
				} else if (leave_reason.getText().toString()
						.equalsIgnoreCase("")) {
					Toast.makeText(getActivity(), "Please Enter reason",
							Toast.LENGTH_SHORT).show();//"Please enter the reason");
				} else if (total_days.getText().toString().equals("0.0") || total_days.getText().toString().equals("")) {
					Toast.makeText(getActivity(), "Please select valid date",
							Toast.LENGTH_SHORT).show();//"Please select valid date");
				} else
					apply_leave();
			}
		});
		reset = (Button) rootView.findViewById(R.id.reset);

		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				total_days.setText("0.0");
				leave_reason.setText("");
			}
		});
		
		
		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		mmonth = c.get(Calendar.MONTH);
		mday = c.get(Calendar.DAY_OF_MONTH);
		from_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);
		to_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);
		JSONRequest_TotalDays();
		Datevalidate_flag = 1;

		from_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new DatePickerFrom();
				picker.show(getActivity().getFragmentManager(), "datePicker");
			}
		});
		to_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new DatePickerTo();
				picker.show(getActivity().getFragmentManager(), "datePicker");

			}
		});
		leave_type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				if (position != 0) {
					leave_id_spinner = leaverequest.get(position - 1)
							.getLeave_typeID();
					System.out.println("Leave Type ID-----:"
							+ leaverequest.get(position - 1).getLeave_typeID());
					leave_name_spinner = leaverequest.get(position - 1)
							.getLeave_typename();
					JSONRequest_TotalDays();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here

			}

		});
		session_from.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				System.out.println(TotalDate);
				SessionDateFrom = session_from.getItemAtPosition(position)
						.toString();
				try {
					if (TotalDate != null) {
						if (TotalDate != 0.0) {
							if (SessionDateFrom.equals("Full")
									&& (SessionDateTo.equals(""))) {
								total_days.setText(TotalDate.toString());
							} else if (SessionDateFrom.equals("Full")
									&& SessionDateTo.equals("Full")) {
								total_days.setText(TotalDate.toString());
							}

							else if (((SessionDateFrom.equals("1st Session")) && (SessionDateTo
									.equals("1st Session") || SessionDateTo
									.equals("2nd Session")))
									|| ((SessionDateTo.equals("1st Session")) && (SessionDateFrom
											.equals("1st Session") || SessionDateFrom
											.equals("2nd Session")))
									|| ((SessionDateFrom.equals("2nd Session")) && (SessionDateTo
											.equals("1st Session") || SessionDateTo
											.equals("2nd Session")))
									|| ((SessionDateTo.equals("2nd Session")) && (SessionDateFrom
											.equals("1st Session") || SessionDateFrom
											.equals("2nd Session")))) {
								Double value = TotalDate - 1.0;
								total_days.setText(value.toString());
							} else {
								Double value = TotalDate - 0.5;
								total_days.setText(value.toString());
							}
						}
					}
				} catch (NullPointerException e) {
					System.err.println("Null Point Exception");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here

			}

		});
		session_to.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				// your code here
				SessionDateTo = session_to.getItemAtPosition(position)
						.toString();
				if (TotalDate != null) {
					if (TotalDate != 0.0) {
						if (SessionDateFrom.equals("Full")
								&& SessionDateTo.equals(null)) {
							total_days.setText(TotalDate.toString());
						} else if (SessionDateFrom.equals("Full")
								&& SessionDateTo.equals("Full")) {
							total_days.setText(TotalDate.toString());
						}

						else if (((SessionDateFrom.equals("1st Session")) && (SessionDateTo
								.equals("1st Session") || SessionDateTo
								.equals("2nd Session")))
								|| ((SessionDateTo.equals("1st Session")) && (SessionDateFrom
										.equals("1st Session") || SessionDateFrom
										.equals("2nd Session")))
								|| ((SessionDateFrom.equals("2nd Session")) && (SessionDateTo
										.equals("1st Session") || SessionDateTo
										.equals("2nd Session")))
								|| ((SessionDateTo.equals("2nd Session")) && (SessionDateFrom
										.equals("1st Session") || SessionDateFrom
										.equals("2nd Session")))) {
							Double value = TotalDate - 1.0;
							total_days.setText(value.toString());
						} else {
							Double value = TotalDate - 0.5;
							total_days.setText(value.toString());
						}
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		return rootView;

	}
	

	public class DatePickerFrom extends DialogFragment implements
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
					if (year > myear)
						view.updateDate(myear, mmonth, mday);

					if (monthOfYear > mmonth && year == myear)
						view.updateDate(myear, mmonth, mday);

					if (dayOfMonth > mday && year == myear
							&& monthOfYear == mmonth)
						view.updateDate(myear, mmonth, mday);
				}
			};
			final Calendar cc = Calendar.getInstance();
			cc.get(Calendar.YEAR);
			cc.get(Calendar.MONTH);
			cc.get(Calendar.DAY_OF_MONTH);
			return _date;
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			myear = yy;
			mmonth = mm;
			mday = dd;

			frommday = mday;
			frommmonth = mmonth;
			frommyear = myear;
			from_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);

			fromdate1 = myear + "-" + (mmonth + 1) + "-" + mday;
			to_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);
			if ((from_date != null) && (to_date != null)) {
				if (from_date.getText().toString()
						.equals(to_date.getText().toString())) {
					System.out.println("Equal");
					session_to.setVisibility(View.INVISIBLE);
				} else {
					session_to.setVisibility(View.VISIBLE);
				}
				JSONRequest_TotalDays();
			}
			if ((from_date == to_date)) {
				session_to.setVisibility(View.INVISIBLE);
			}
			session_from.setSelection(0);
			session_to.setSelection(0);
		}

	}

	public class DatePickerTo extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			if (Datevalidate_flag == 0) {
				myear = frommyear;
				mmonth = frommmonth;
				mday = frommday;
			}
			if (frommyear == 0) {
				myear = c.get(Calendar.YEAR);
				mmonth = c.get(Calendar.MONTH);
				mday = c.get(Calendar.DAY_OF_MONTH);
			}
			DatePickerDialog _date = new DatePickerDialog(getActivity(), this,
					myear, mmonth, mday) {
				public void onDateChanged(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					if (year > myear)
						view.updateDate(myear, mmonth, mday);

					if (monthOfYear > mmonth && year == myear)
						view.updateDate(myear, mmonth, mday);

					if (dayOfMonth > mday && year == myear
							&& monthOfYear == mmonth)
						view.updateDate(myear, mmonth, mday);

				}
			};

			return _date;
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			Datevalidate_flag = 0;
			myear = yy;
			mmonth = mm;
			mday = dd;

			tomday = mday;
			tommonth = mmonth;
			tomyear = myear;
			to_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);

			todate1 = myear + "-" + (mmonth + 1) + "-" + mday;
			System.out.println("TO Date:" + todate1);
			if ((from_date != null) && (to_date != null)) {
				if (from_date.getText().toString()
						.equals(to_date.getText().toString())) {
					session_to.setVisibility(View.INVISIBLE);
				} else {
					session_to.setVisibility(View.VISIBLE);
				}
				JSONRequest_TotalDays();
			}

			session_from.setSelection(0);
			session_to.setSelection(0);
		}

	}

	public void JsonRequest_LeaveType() {

		// showProgressDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				Const.Leave.URL_GENERAL_P1 + "1" + Const.Leave.URL_GENERAL_P2
						+ employeeID, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println(response.toString());
						jsondata = response.toString();
						try {
							arraylist_leavetype.clear();
							arraylist_leavetype.add("Please Select");
							JSONArray jsonArray = response
									.getJSONArray("leavetypeResult");
							JSON_Length = jsonArray.length();
							leaverequest = new ArrayList<LeaveRequestmodel>();
							for (int i = 0; i < JSON_Length; i++) {
								response = jsonArray.getJSONObject(i);
								leave_request_model = new LeaveRequestmodel();

								if (response.getString("LeaveCategory").equals(
										"1")) {

									leave_request_model
											.setLeave_typeID(response
													.getInt("LeaveTypeID"));
									leave_request_model.setLeave_typename(response
											.getString("LeaveTypeName"));
									leave_request_model
											.setLeave_balance(response
													.getString("leaveBalance"));
									leave_request_model
											.setLeave_planned(response
													.getString("plannedLeave"));
									leaverequest.add(leave_request_model);
									// Adding to the array
									arraylist_leavetype.add(leave_request_model
											.getLeave_typename());
								}
							}
							ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
									getActivity(),
									android.R.layout.simple_spinner_item,
									arraylist_leavetype);
							dataAdapter
									.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							leave_type.setAdapter(dataAdapter);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("Exception: " + e.getMessage());
						}
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.err.println(error);
						// TODO Auto-generated method stub
						// Handle your error types accordingly.For Timeout & No
						// connection error, you can show 'retry' button.
						// For AuthFailure, you can re login with user
						// credentials.
						// For ClientError, 400 & 401, Errors happening on
						// client side when sending api request.
						// In this case you can check how client is forming the
						// api and debug accordingly.
						// For ServerError 5xx, you can do retry or handle
						// accordingly.
						if (error instanceof NetworkError) {

						} else if (error instanceof ServerError) {
						} else if (error instanceof AuthFailureError) {
						} else if (error instanceof ParseError) {
						} else if (error instanceof NoConnectionError) {
						} else if (error instanceof TimeoutError) {
						}
					}
				}) {
		};
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

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
						Intent intent = new Intent(getActivity(), RegularizationRequest.class);
						startActivity(intent);	
					}
				});		
		alertDialog2.show();
	}

	public void apply_leave() {
		String requestURL = "http://192.168.1.87:8010/Rajesh/attendenceService.svc/applyLeave";
		/*
		 * String requestURL =
		 * "http://192.168.1.87:8010/Rajesh/attendenceService.svc/postt";
		 */

		Map<String, String> params = new HashMap<String, String>();
		params.put("EmployeeID", employeeID);
		params.put("CompanyID", companyID);
		params.put("Fullname", "Superuser Admin");
		params.put("TotalDays", total_days.getText().toString());
		params.put("LeaveTypeID", "" + leave_id_spinner);
		params.put("FromSession", session_from.getItemAtPosition(0).toString());
		params.put("ToSession", session_to.getItemAtPosition(0).toString());
		params.put("LeaveType", leave_name_spinner);
		params.put("TextReson", leave_reason.getText().toString());
		params.put("fromdate", from_date.getText().toString());
		params.put("toDate", to_date.getText().toString());
		params.put("deliveryDate", from_date.getText().toString());
		params.put("isRegularValue", "3");
		System.out.println("Requestasas: " + params);
		JsonArrayRequest sr = new JsonArrayRequest(Request.Method.POST,
				requestURL, new JSONObject(params),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						System.out.println("Response: " + response);
						try {
							JSONObject Json_obj = response.getJSONObject(0);
							System.out.println("Result: "
									+ Json_obj.getString("result"));
							if (Json_obj.getString("result").equals("2")) {
								Custom_alert("Leave already applied for the same date");
							} else if (Json_obj.getString("result").equals("4")) {
								Custom_alert("POD is already Applied");
							} else if (Json_obj.getString("result").equals("1")) {
								Custom_alert("Leave applied successfully");
							} else {
								Custom_alert("You dont have approval authority, So you cannot apply for leave");
							}
						} catch (Exception e) {

						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						error.getStackTrace();
						System.out.println("Error: " + error.getMessage());
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				// do not add anything here
				headers.put("Content-Type", "application/json; charset=utf-8");
				// headers.put("Content-type",
				// "application/x-www-form-urlencoded");
				return headers;
			}
		};
		AppController.getInstance().addToRequestQueue(sr);
	}

	public void JSONRequest_TotalDays() {
		String URL_TOTALDAYSGENERAL = Const.URL_TOTALDAYSGENERAL_p1
				+ from_date.getText() + Const.URL_TOTALDAYSGENERAL_p2
				+ to_date.getText() + Const.URL_TOTALDAYSGENERAL_p3
				+ leave_id_spinner + Const.URL_TOTALDAYSGENERAL_p4 + employeeID;
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				URL_TOTALDAYSGENERAL, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						Log.d("JSON", response.toString());
						System.out.println(response.toString());
						String totaldays = response.toString();

						if (totaldays != null) {

							System.out.println("in totaldayscal" + totaldays);

							try {

								JSONObject jobj = new JSONObject(totaldays);
								TotalDate = jobj
										.getDouble("getTotalDaysResult");
								total_days.setText("" + TotalDate);

							} catch (Exception e) {
								// TODO Auto-generated catch block
								System.out.println("Exception: "
										+ e.getMessage());
							}

						} else {
							Log.e("jsonerror",
									"Couldn't get any data from the url");
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

}
