package com.exenta.pod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import supportclasses.Const;
import supportclasses.EmpDetailsSessions;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.leaveapply.Leave_Request;


@SuppressLint("ValidFragment")
public class ApplyPOD extends Activity {

	// Object for Getter and Setter
	private List<ApplyPODModel> apply_podList = new ArrayList<ApplyPODModel>();
	ApplyPODModel applyPODClass;
	ArrayList<String> pod_type_list = new ArrayList<String>();
	Spinner pod_type_spinner, session_spinner;
	EditText from_date, to_date;
	int frommday, frommmonth, frommyear;
	int tomday, tommonth, tomyear;
	int getTotalDaysResult;
	private int myear;
	private int mmonth;
	private int mday;
	int Datevalidate_flag;
	Double TotalDays;
	EditText total_day,pod_reason;
	String pod_id,pod_type;
	String value_fromdate, value_todate;
	EmpDetailsSessions session;
	String employeeID, companyID;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pod_apply);
		pod_type_spinner = (Spinner) findViewById(R.id.pod_type_sp);
		session_spinner = (Spinner) findViewById(R.id.session);
		from_date = (EditText) findViewById(R.id.fromdate);
		to_date = (EditText) findViewById(R.id.todate);
		total_day = (EditText) findViewById(R.id.totaldays);
		pod_reason = (EditText) findViewById(R.id.leavereason);
		Button apply = (Button) findViewById(R.id.apply);
		Button reset = (Button) findViewById(R.id.reset);  
		session =  new EmpDetailsSessions(getApplicationContext());
		HashMap<String,String> user =  session.getEmployeeDetails();  
					
		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);
		total_day.setText("0.0");
		// Calling Json for POD Type with Employee ID
		ApplyPOD_Json(Integer.parseInt(employeeID));
		apply.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			public void onClick(View v) {
				//System.out.println("Text:"+leave_reason.getText());
				String tt=total_day.getText().toString();
				double ttldays=Double.parseDouble(tt);
				if(total_day.getText().toString().equals("0.0"))
				{
					 Toast.makeText(getApplicationContext(), "Invalid date selection", Toast.LENGTH_SHORT).show();
				}else if(ttldays<0.0)
				{
					Toast.makeText(getApplicationContext(), "Invalid date selection", Toast.LENGTH_SHORT).show();
				}
				
				
				else if(pod_reason.getText().toString().equalsIgnoreCase(""))
				{
					Toast.makeText(getApplicationContext(), "Enter reason", Toast.LENGTH_SHORT).show();
				}
				else	
				ApplyPODResult(Integer.parseInt(companyID), Integer.parseInt(employeeID));
			}
		});
		
		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			/*	total_day.setText("0.0");
				pod_reason.setText("");
*/				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		});
		
		from_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new DatePickerFrom();
				picker.show(getFragmentManager(), "datePicker");
			}
		});
		to_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new DatePickerTo();
				picker.show(getFragmentManager(), "datePicker");
			}
		});
		pod_type_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				
				pod_id = ""+apply_podList.get(position).getPod_id();
				pod_type = apply_podList.get(position).getPod_name();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here

			}

		});

		session_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				System.out.println("Spinner Selected" + session_spinner.getItemAtPosition(position));
				try {
					if (TotalDays != null) {
						if (session_spinner.getItemAtPosition(position).equals("Full")) {
							total_day.setText(TotalDays.toString());
						} else if (session_spinner.getItemAtPosition(position).equals("1st Session")
								|| session_spinner.getItemAtPosition(position).equals("2nd Session")) {
							Double value = TotalDays - 0.5;
							total_day.setText("" + value);
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
	}

	public void ApplyPODResult(int company_id, int employee_id) {
		final Calendar c = Calendar.getInstance();
		int curyear = c.get(Calendar.YEAR);
		int curmonth = c.get(Calendar.MONTH);
		int curday = c.get(Calendar.DAY_OF_MONTH);
		String currentday = curyear+"-"+(curmonth+1)+"-"+curday;
		
//		String url = Const.POD.POD_APPLY;
		String url = Const.POD.POD_APPLY + company_id + "&EmployeeID=" + employee_id +"&FromDate="+ value_fromdate + "&ToDate=" + value_todate
				+ "&PODTypeID="+ pod_id + "&AppliedDate=" + currentday + "&PODReason=" + pod_reason.getText() + "&TotalDays=" + total_day.getText()
				+ "&Status=Pending&FromSession="+session_spinner.getItemAtPosition(0)+"&ToSession="+session_spinner.getItemAtPosition(0)+"&Type="+pod_type+"&regularid=0";
		System.out.println("URL:" +url);
		JsonObjectRequest GetempprofileReq = new JsonObjectRequest(url.replaceAll(" ", "%20"),null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						System.out.println("JSON : " + response);												
						try {
							JSONArray jsonArray = response.getJSONArray("getPODApplyResult");							
							JSONObject jsonObj = jsonArray.getJSONObject(0);
							jsonObj.getString("result");
							if(jsonObj.getString("result").toString().equals("1"))
							{
								Custom_alert("POD Applied!..");								
							}
							else if(jsonObj.getString("result").toString().equals("2"))
							{
								Custom_alert("You do not have approval authority");
							}
							else if(jsonObj.getString("result").toString().equals("3"))
							{
								Custom_alert("Already POD applied on same date");
							}		
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
										
						//= response.getJSONArray("ApproveLeaveResult");																			
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						 if( error instanceof NetworkError) {
						    	
						    } else if( error instanceof ServerError) {
						    } else if( error instanceof AuthFailureError) {
						    } else if( error instanceof ParseError) {
						    } else if( error instanceof NoConnectionError) {
						    } else if( error instanceof TimeoutError) {
						    }
						}
					});
		
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(GetempprofileReq);
	}
	public void ApplyPOD_Json(int employee_id) {

		JsonObjectRequest GetempprofileReq = new JsonObjectRequest(
				Const.POD.POD_TYPE + companyID, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("JSON", response.toString());
						try {
							JSONArray arrobj = response
									.getJSONArray("podTypeDetailsResult");
							Log.d("JSON Array", arrobj.toString());
							// Parsing JSON
							for (int i = 0; i < arrobj.length(); i++) {
								JSONObject obj1 = arrobj.getJSONObject(i);
								applyPODClass = new ApplyPODModel();
								applyPODClass.setPod_name(obj1.getString("podType"));
								applyPODClass.setPod_id(obj1.getInt("podID"));
								applyPODClass.setCompany_name(obj1.getString("companyName"));
								System.out.println("POD Type: "+ obj1.getString("podType"));
								apply_podList.add(applyPODClass);
								// Array List for POD Type Spinner
								pod_type_list.add(obj1.getString("podType"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						// Set adapter to spinner
						pod_type_spinner.setAdapter(new ArrayAdapter<String>(
								ApplyPOD.this,
								android.R.layout.simple_spinner_dropdown_item,
								pod_type_list));
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						 if( error instanceof NetworkError) {
						    	
						    } else if( error instanceof ServerError) {
						    } else if( error instanceof AuthFailureError) {
						    } else if( error instanceof ParseError) {
						    } else if( error instanceof NoConnectionError) {
						    } else if( error instanceof TimeoutError) {
						    }
						}
					});
		GetempprofileReq.setRetryPolicy(new DefaultRetryPolicy(10000, 
			                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
			                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(GetempprofileReq);
	}

	public void TotalDays_Json(int employee_id, String fromdate, String todate) {
System.out.println("From: "+ fromdate + " To: " + todate);
		JsonObjectRequest GetempprofileReq = new JsonObjectRequest(
				Const.POD.POD_TOTALDAYS + fromdate + "&toDate=" + todate
						+ "&empID=" + employee_id, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("JSON", response.toString());
						try {
							TotalDays = response
									.getDouble("getTotalPODDaysResult");
							total_day.setText("" + TotalDays);
							if (TotalDays == 1.0) {
								session_spinner.setEnabled(true);
								session_spinner.setSelection(0);
							}
							else{
								session_spinner.setEnabled(false);
								session_spinner.setSelection(0);
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.err.println("Error:"+error);
						 if( error instanceof NetworkError) {
						    	
						    } else if( error instanceof ServerError) {
						    } else if( error instanceof AuthFailureError) {
						    } else if( error instanceof ParseError) {
						    } else if( error instanceof NoConnectionError) {
						    } else if( error instanceof TimeoutError) {
						    }
						}
					});
		
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(GetempprofileReq);
	}

	// ***************************************************************************************************//
	// *********************************************DATE
	// PICKER*******************************************//
	// ***************************************************************************************************//

	@SuppressLint("ValidFragment")
	public class DatePickerFrom extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();
			myear = c.get(Calendar.YEAR);
			mmonth = c.get(Calendar.MONTH);
			mday = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog _date = new DatePickerDialog(getActivity(), this,myear, mmonth, mday) 
			{
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
			value_fromdate = myear + "-" + (mmonth + 1) + "-" + mday;
			from_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);
			if ((from_date != null) && (to_date != null))
				TotalDays_Json(1, value_fromdate, value_todate);
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
					if (year < myear)
						view.updateDate(myear, mmonth, mday);

					if (monthOfYear < mmonth && year == myear)
						view.updateDate(myear, mmonth, mday);

					if (dayOfMonth < mday && year == myear
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
			// from_date.setText(mday + "-" + (mmonth + 1) + "-" + myear);
			value_todate = myear + "-" + (mmonth + 1) + "-" + mday;

			if ((from_date != null) && (to_date != null))
				TotalDays_Json(1, value_fromdate, value_todate);
		}

	}
	
	public void Custom_alert(String msg) {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
	ApplyPOD.this);

		// Setting Dialog Title
		alertDialog2.setTitle("Exenta");

		// Setting Dialog Message
		alertDialog2.setMessage(msg);

		alertDialog2.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						Intent intent = new Intent(ApplyPOD.this, ApplyPOD.class);
						startActivity(intent);						
					}
				});	
		// Showing Alert Dialog
		alertDialog2.show();
	}
}
