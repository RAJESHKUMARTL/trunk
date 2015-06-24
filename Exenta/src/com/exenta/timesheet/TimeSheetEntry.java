package com.exenta.timesheet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import supportclasses.EmpDetailsSessions;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exenta.customUI.CustomLoading;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class TimeSheetEntry extends Fragment implements OnCameraChangeListener {
	private SessionDateMaintain session = null;
	TextView entry_date, entry_time;
	TableLayout table_layout1, table_layout2;
	TextView tv1, tv2, tv3;
	TextView tv20;
	TextView entrystatus;
	EditText fromdate, todate;
	Button timein, timeout;
	Calendar c;
	ImageButton Search;
	int year, month, day;
	String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" };
	static String Clockin, Clockout;
	static String totalhours;
	String formattedTime, formattedDate;
	String SessionDate, SessionTime;
	private static final String TimeIn = "TIME_ENTRY";
	private static final String Status = "TIMEENTRY_STATUS";
	public static final String URL = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/timeSheetEntry?";
	public static final String PREVIOUSDATA_URL = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/getTimeSheetEntry?";
	public static String URL_TIMEENTRY;
	String timeenrtyin, timeenrtyout;
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	static int flag = 0;
	int frommday, frommmonth, frommyear;
	String URL_inout;
	String Empid;
	String fromdatpass, FROMDATE, todatpass, TODATE;
	ArrayList<String> EnDate = new ArrayList<String>();
	ArrayList<String> In = new ArrayList<String>();
	ArrayList<String> Out = new ArrayList<String>();
	ArrayList<String> Hours = new ArrayList<String>();
	EmpDetailsSessions sessionDetails;
	String employeeID, companyID;
	String defFromdate, DefTodate;
	// CustomLoading customui = new CustomLoading();
	// ^^^^^^^^^^^^^^^^^^^^^^^^^Geofence^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	MapView mapView;
	private GoogleMap mMap;
	GPSTracker gps;
	ArrayList<Geofence> mGeofences;
	private GeoConstants Geofencelocations;
	private GeofenceStore mGeofenceStore;
	String GPSDATA = null;
	static LatLng MY_LOCATION;
	static LatLng MY_LOCATION_DEST;
	BroadcastReceiver receiver;
	List<Address> addresses;
	List<Address> destaddresses;
	static String result = null;
	static String destresult = null;
	int JSON_Length;
	static String locaddress;
	static String destaddress;

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^Geofence^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * getActivity().getWindow().setFlags(
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		View rootview = inflater.inflate(R.layout.timesheet_entry, container,
				false);
		setRetainInstance(true);

		sessionDetails = new EmpDetailsSessions(getActivity().getApplicationContext());
		HashMap<String, String> user = sessionDetails.getEmployeeDetails();

		employeeID = user.get(EmpDetailsSessions.EMPLOYEE_ID);
		companyID = user.get(EmpDetailsSessions.COMPANY_ID);

		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Geofence^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		Geofencelocations = new GeoConstants();
		mGeofences = new ArrayList<Geofence>();
		mGeofences = Geofencelocations.Geofence();
		gps = new GPSTracker(getActivity().getApplicationContext());
		mGeofenceStore = new GeofenceStore(getActivity(), mGeofences);
		mapView = (MapView) rootview.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Geofence^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

		table_layout1 = (TableLayout) rootview.findViewById(R.id.tableLayout1);
		timein = (Button) rootview.findViewById(R.id.Clockin);
		timeout = (Button) rootview.findViewById(R.id.Clockout);
		entry_date = (TextView) rootview.findViewById(R.id.timesheetDate);
		entry_time = (TextView) rootview.findViewById(R.id.timesheetTime);
		entrystatus = (TextView) rootview.findViewById(R.id.entrystatus);
		fromdate = (EditText) rootview.findViewById(R.id.Datefrom);
		todate = (EditText) rootview.findViewById(R.id.Dateto);
		Search = (ImageButton) rootview.findViewById(R.id.imageView1);
		session = new SessionDateMaintain(getActivity());
		table_layout2 = (TableLayout) rootview
				.findViewById(R.id.tablepreviousreport);
		c = Calendar.getInstance();
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH);
		year = c.get(Calendar.YEAR);

		formattedDate = new StringBuilder().append(day).append(" ")
				.append(months[month]).append(" ").append(year).toString();
		System.out.println(formattedDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
		formattedTime = dateFormat.format(new Date()).toString();
		entry_date.setText(formattedDate);
		entry_time.setText(formattedTime);
		session.createTimeEntrySession(formattedDate, formattedTime);
		defFromdate = "&month=" + (month + 1) + "&year=" + year + "&datefrom="
				+ getYesterdayDateString();		
		System.out.println("asdsadsadsa"+defFromdate);
		DefTodate = "&dateTill=" + year + "-" + (month + 1) + "-" + day;
		System.out.println(DefTodate);
		GetPrevioustimeSheet(defFromdate, DefTodate);
		/*
		 * SharedPreferences preference =
		 * getActivity().getSharedPreferences(getClass().getSimpleName(),
		 * Context.MODE_PRIVATE); String prefValue=preference.getString(Status,
		 * null);
		 * 
		 * System.out.println("prefValueeeeeeeeeeeeeeeeeeeeeee"+prefValue);
		 * if(prefValue.equalsIgnoreCase("IN")) { System.out.println("INn");
		 * 
		 * }else if(prefValue.equalsIgnoreCase("OUT")) {
		 * System.out.println("OUTTTT");
		 * 
		 * }else if(prefValue.equalsIgnoreCase("NONE")) {
		 * System.out.println("IN NONE"); }
		 */

		HashMap<String, String> date_time = session.getSessionDatetimeDetails();

		SessionDate = date_time.get(SessionDateMaintain.SYSTEM_DATE);
		SessionTime = date_time.get(SessionDateMaintain.SYSTEM_TIME);

		System.out.println("SessionDate  " + SessionDate);
		System.out.println("SessionTime  " + SessionTime);

		if (flag == 0) {
			System.out.println("flagggggggggggggggggggg 0");
			timeout.setVisibility(View.INVISIBLE);
			timein.setVisibility(View.VISIBLE);

		} else if (flag == 1) {
			System.out.println("flagggggggggggggggggggg 1");
			timein.setVisibility(View.INVISIBLE);
			timeout.setVisibility(View.VISIBLE);
			entrystatus.setText("");
			BuildTable(1, 1);

		} else if (flag == 2) {

			timeout.setVisibility(View.INVISIBLE);
			timein.setVisibility(View.INVISIBLE);
			entrystatus.setText("Sucess...!!! Have a nice time");
			// entrystatus.setTextSize(20);
			BuildTable(1, 1);
		}

		/*
		 * else { entrystatus.setText("");
		 * 
		 * TimeSheetEntry.reset(); }
		 */// System.out.println(new
			// StringBuilder().append(day).append(" ").append(month).append(" ").append(year));

		timein.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flag = 1;
				// FlagStatusInPreferences("IN");
				SimpleDateFormat formatin = new SimpleDateFormat(
						"MM/dd/yyyy HH:mm:ss");
				timeenrtyin = formatin.format(new Date()).toString();
				TimeEntryInPreferences(timeenrtyin);
				SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
				Clockin = dateFormat.format(new Date()).toString();
				System.out.println("Clockin" + Clockin);
				timein.setVisibility(View.INVISIBLE);
				timeout.setVisibility(View.VISIBLE);
				Toast.makeText(getActivity(), "Thank you....! You are in", 1)
						.show();

				try {
					// check if GPS enabled
					if (gps.canGetLocation()) {

						double latitude = gps.getLatitude();
						double longitude = gps.getLongitude();
						MY_LOCATION = new LatLng(latitude, longitude);

						Geocoder geocoder;

						geocoder = new Geocoder(getActivity(), Locale
								.getDefault());
						addresses = geocoder.getFromLocation(latitude,
								longitude, 1);
						if (addresses != null && addresses.size() > 0) {
							Address address = addresses.get(0);
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < address
									.getMaxAddressLineIndex(); i++) {
								sb.append(address.getAddressLine(i)).append(
										"\n");
							}
							// sb.append(address.getLocality()).append("\n");
							// sb.append(address.getPostalCode()).append("\n");
							// sb.append(address.getCountryName());
							result = sb.toString();
							locaddress = addresses.get(0).getAddressLine(0);

							/*
							 * city = addresses.get(0).getAddressLine(1);
							 * country = addresses.get(0).getAddressLine(2);
							 */
							mMap.addMarker(new MarkerOptions()
									.position(MY_LOCATION).title("MY LOCATION")
									.snippet(result).draggable(true));
						}

					} else {
						// can't get location
						// GPS or Network is not enabled
						// Ask user to enable GPS/network in settings
						gps.showSettingsAlert(getActivity().getWindow()
								.getContext());
					}
				} catch (Exception e) {

					System.out.println("EXXX" + e);

				}
				table_layout1.removeAllViews();
				BuildTable(1, 1);
				entrystatus.setText("");

			}
		});

		fromdate.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new FromDatePicker();
				picker.show(getActivity().getFragmentManager(), "datePicker");

			}
		});
		todate.setOnClickListener(new View.OnClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogFragment picker = new ToDatePicker();
				picker.show(getActivity().getFragmentManager(), "datePicker");

			}
		});

		Search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (fromdate.equals("") || todate.equals("")) {

					Toast.makeText(getActivity(), "Please Enter Dates",
							Toast.LENGTH_SHORT).show();

				} else {

					GetPrevioustimeSheet(FROMDATE, TODATE);
				}

			}

		});
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

		MapsInitializer.initialize(getActivity());
		if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {
			setUpMapIfNeeded();
		} else {
			GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(getActivity()),
					getActivity(), 0);
		}

		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^according to location
		// change^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub
				String GEODATA = arg1.getStringExtra("COPA_MESSAGE");
				System.out
						.println("Action hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeee zz................................"
								+ GEODATA);
				if (GEODATA.equals("Geofence Entered")) {

				} else if (GEODATA.equals("Geofence Dwell")) {

				} else if (GEODATA.equals("Geofence Exited")) {

				} else {

				}

			}
		};
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^according to location
		// change^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		return rootview;
	}

	@Override
	public void onStart() {
		super.onStart();
		LocalBroadcastManager.getInstance(getActivity())
				.registerReceiver((receiver),
						new IntentFilter(GeofenceIntentService.COPA_RESULT));
	}

	@Override
	public void onStop() {
		// mGeofenceStore.disconnect();
		super.onStop();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				receiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
		System.out.println("in on resume");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	private String getYesterdayDateString() {
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);
		return dateFormat1.format(cal.getTime());
	}

	private void BuildTable(int rows, int cols) {
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
					// tv1.setBackgroundResource(R.color.grey);
					tv1.setBackgroundResource(R.drawable.cell_shape);
					tv1.setGravity(Gravity.CENTER);
					tv1.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv1.setTextSize(15);
						tv1.setBackgroundResource(R.color.lightwhite);
						tv1.setText("IN");
					} else {
						tv1.setText(Clockin);
					}
					row.addView(tv1);

					TextView tv = new TextView(getActivity());
					tv.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					// tv1.setBackgroundResource(R.color.grey);
					tv.setBackgroundResource(R.drawable.cell_shape);
					tv.setGravity(Gravity.CENTER);
					tv.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv.setTextSize(15);
						tv.setBackgroundResource(R.color.lightwhite);
						tv.setText("Location");
					} else {
						tv.setText(locaddress);
					}
					row.addView(tv);

					// row.setBackgroundColor(Color.GRAY);

					tv2 = new TextView(getActivity());
					tv2.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					// tv2.setBackgroundColor(Color.CYAN);
					tv2.setBackgroundResource(R.drawable.cell_shape);
					tv2.setGravity(Gravity.CENTER);
					tv2.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv2.setTextSize(15);
						tv2.setBackgroundResource(R.color.lightwhite);
						tv2.setText("OUT");
					} else {
						tv2.setText(Clockout);

					}
					// row.setBackgroundColor(Color.CYAN);
					row.addView(tv2);

					tv20 = new TextView(getActivity());
					tv20.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					// tv1.setBackgroundResource(R.color.grey);
					tv20.setBackgroundResource(R.drawable.cell_shape);
					tv20.setGravity(Gravity.CENTER);
					tv20.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv20.setTextSize(15);
						tv20.setBackgroundResource(R.color.lightwhite);
						tv20.setText("Location");
					} else {
						tv20.setText(destaddress);
					}
					row.addView(tv20);

					tv3 = new TextView(getActivity());
					tv3.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					// tv3.setBackgroundColor(Color.LTGRAY);
					tv3.setBackgroundResource(R.drawable.cell_shape);
					tv3.setGravity(Gravity.CENTER);
					tv3.setPadding(8, 8, 8, 8);
					// tv3.setTextColor(color);
					if (i == 0) {
						tv3.setTextSize(15);
						tv3.setBackgroundResource(R.color.lightwhite);
						tv3.setText("Hours");
					} else {
						tv3.setText(totalhours);

						timeout.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub

								SimpleDateFormat formatout = new SimpleDateFormat(
										"MM/dd/yyyy HH:mm:ss");
								timeenrtyout = formatout.format(new Date())
										.toString();
								TimeEntryOutPreferences(timeenrtyout);
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"hh.mm aa");
								Clockout = dateFormat.format(new Date())
										.toString();
								System.out.println(Clockout);
								Toast.makeText(getActivity(),
										"Thank you....! You are out",
										Toast.LENGTH_SHORT).show();
								tv2.setText(Clockout);
								timeout.setVisibility(View.INVISIBLE);
								timein.setVisibility(View.VISIBLE);
								/*
								 * System.out.println(timeenrtyin);
								 * System.out.println(timeenrtyout);
								 */
								String datetimein = RetrieveTimeInFromPreferences();
								String datetimeout = RetrieveTimeOUTFromPreferences();
								// 2015-04-01%2009:00:00.0002015-01-02%2018:00:00.000
								URL_inout = "timeIn=" + datetimein
										+ "&timeOut=" + datetimeout;
								totalhours = Calculatehour(datetimein,
										datetimeout);
								tv3.setText(totalhours);
								// check if GPS enabled
								try {
									if (gps.canGetLocation()) {

										double latitude_dest = gps
												.getLatitude();
										double longitude_dest = gps
												.getLongitude();

										MY_LOCATION_DEST = new LatLng(
												latitude_dest, longitude_dest);

										Geocoder geocoder;

										geocoder = new Geocoder(getActivity(),
												Locale.getDefault());
										destaddresses = geocoder
												.getFromLocation(latitude_dest,
														longitude_dest, 1);
										if (destaddresses != null
												&& destaddresses.size() > 0) {
											Address address = destaddresses
													.get(0);
											StringBuilder sb = new StringBuilder();
											for (int i = 0; i < address
													.getMaxAddressLineIndex(); i++) {
												sb.append(
														address.getAddressLine(i))
														.append("\n");
											}
											// sb.append(address.getLocality()).append("\n");
											// sb.append(address.getPostalCode()).append("\n");
											// sb.append(address.getCountryName());
											destresult = sb.toString();
											destaddress = destaddresses.get(0)
													.getAddressLine(0);
											tv20.setText(destaddress);
											/*
											 * city =
											 * addresses.get(0).getAddressLine
											 * (1); country =
											 * addresses.get(0).getAddressLine
											 * (2);
											 */
											mMap.addMarker(new MarkerOptions()
													.position(MY_LOCATION_DEST)
													.title("MY LOCATION")
													.snippet(destresult)
													.draggable(true));
											// \n is for new line
										}
									} else {
										// can't get location
										// GPS or Network is not enabled
										// Ask user to enable GPS/network in
										// settings
										gps.showSettingsAlert(getActivity()
												.getWindow().getContext());
									}

									MakeURL_TIMEENTRY();
									// FlagStatusInPreferences("OUT");
									if (SessionDate.equals(formattedDate)) {
										flag = 2;
										timeout.setVisibility(View.GONE);
										timein.setVisibility(View.GONE);
										entrystatus
												.setText("Sucess...!!! Have a nice time");
										// entrystatus.setTextSize(20);
									} else {
										/*
										 * entrystatus.setText("");
										 * 
										 * reset();
										 */
									}
								} catch (Exception e) {

								}
							}
						});

					}

					row.addView(tv3);

				}
				table_layout1.addView(row);

			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public String Calculatehour(String timein, String timeout) {
		String totalhr = null;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(timein);
			d2 = format.parse(timeout);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.println(diffDays + " days, ");
			System.out.println(diffHours + " hours, ");
			System.out.println(diffMinutes + " minutes, ");
			System.out.println(diffSeconds + " seconds.");
			if (diffDays == 0) {
				totalhr = diffHours + ":" + diffMinutes;
			} else {
				totalhr = diffDays + " day ," + diffHours + ":" + diffMinutes;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalhr;
	}

	private void TimeEntryInPreferences(String intime) {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				getClass().getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();

		prefsEditor.putString("TimeIn", intime);

		prefsEditor.commit();
	}

	private String RetrieveTimeInFromPreferences() {
		String intme;
		SharedPreferences prefs = getActivity().getSharedPreferences(
				getClass().getSimpleName(), Context.MODE_PRIVATE);

		intme = prefs.getString("TimeIn", null);

		return intme;
	}

	private void TimeEntryOutPreferences(String outtime) {
		SharedPreferences prefs = getActivity().getSharedPreferences(
				getClass().getSimpleName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = prefs.edit();

		prefsEditor.putString("TimeOut", outtime);

		prefsEditor.commit();
	}

	private String RetrieveTimeOUTFromPreferences() {
		String outtme;
		SharedPreferences prefs = getActivity().getSharedPreferences(
				getClass().getSimpleName(), Context.MODE_PRIVATE);

		outtme = prefs.getString("TimeOut", null);

		return outtme;
	}

	private void GetPrevioustimeSheet(String FROMDATE_url,String TODATE_url) {
		// TODO Auto-generated method stub
		
		Empid = "empId="+employeeID;
		String PREVIOUS_TIMESHEET_URL=PREVIOUSDATA_URL+Empid+FROMDATE_url+TODATE_url;
	//	customui.LoadingScreen(getActivity());
		System.out.println("sssssssss"+PREVIOUS_TIMESHEET_URL);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(
				PREVIOUS_TIMESHEET_URL, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());
						
						
						if (response.toString() != null) {

							EnDate.clear();
							In.clear();
							Out.clear();
							Hours.clear();
							EnDate.add("Date");
							In.add("IN");
							Out.add("OUT");
							Hours.add("Hr(S)");
						
   try {
							JSONObject jsonObj;
							JSONArray arrobj = response
									.getJSONArray("getTimeSheetEntryDetailsResult");   
							JSON_Length = arrobj.length();
							System.out.println("jarrayyy2" + JSON_Length);
							for (int i = 0; i < arrobj.length(); i++) {

								jsonObj = arrobj.getJSONObject(i);
								
						Previous_timeEntryModel model=new Previous_timeEntryModel();		
								
						model.setEntryDate(jsonObj.getString("entryDate"));
						model.setIndex(jsonObj.getString("index"));	
						model.setTimeIn(jsonObj.getString("timeIn"));	
						model.setTimeOut(jsonObj.getString("timeOut"));		
						model.setTotalTime(jsonObj.getString("totalTime"));		
						EnDate.add(model.getEntryDate());
						In.add(model.getTimeIn());
						Out.add(model.getTimeOut());
						Hours.add(model.getTotalTime());
						System.out.println("entryDate" + EnDate);
						System.out.println("timeIn" + In);
						System.out.println("timeOut" + Out);
						System.out.println("totalTime" + Hours);
					//	customui.LoadingHide();
							}

						} catch (Exception e) {
							System.out.println(e);
						}
  
             table_layout2.removeAllViews();
             
                      Build_PreviousTimeSheet(JSON_Length,1);
  
						} else {
							Log.e("jsonerror", "Couldn't get any data from the url");
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("TAG", "Error: " + error.getMessage());
						//customui.LoadingHide();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);	

	}

	private void Build_PreviousTimeSheet(int rows, int cols) {
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
					// tv1.setBackgroundResource(R.color.grey);
					tv1.setBackgroundResource(R.drawable.cell_shape);
					tv1.setGravity(Gravity.CENTER);
					tv1.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv1.setTextSize(15);
						tv1.setBackgroundResource(R.color.lightwhite);

					}
					tv1.setText(EnDate.get(i));

					row.addView(tv1);
					// row.setBackgroundColor(Color.GRAY);

					TextView tv2 = new TextView(getActivity());
					tv2.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					// tv2.setBackgroundColor(Color.CYAN);
					tv2.setBackgroundResource(R.drawable.cell_shape);
					tv2.setGravity(Gravity.CENTER);
					tv2.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv2.setTextSize(15);
						tv2.setBackgroundResource(R.color.lightwhite);
					}
					tv2.setText(In.get(i));
					// row.setBackgroundColor(Color.CYAN);
					row.addView(tv2);

					TextView tv3 = new TextView(getActivity());
					tv3.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					// tv3.setBackgroundColor(Color.LTGRAY);
					tv3.setBackgroundResource(R.drawable.cell_shape);
					tv3.setGravity(Gravity.CENTER);
					tv3.setPadding(8, 8, 8, 8);
					// tv3.setTextColor(color);
					if (i == 0) {
						tv3.setTextSize(15);
						tv3.setBackgroundResource(R.color.lightwhite);
					}
					tv3.setText(Out.get(i));
					row.addView(tv3);
					// row.setBackgroundColor(Color.LTGRAY);
					TextView tv4 = new TextView(getActivity());
					tv4.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					// tv4.setBackgroundColor(Color.YELLOW);
					tv4.setBackgroundResource(R.drawable.cell_shape);
					tv4.setGravity(Gravity.CENTER);
					tv4.setPadding(8, 8, 8, 8);
					if (i == 0) {
						tv4.setTextSize(15);
						tv4.setBackgroundResource(R.color.lightwhite);

					}
					tv4.setText(Hours.get(i));
					row.addView(tv4);
					// row.setBackgroundColor(Color.YELLOW);
				}
				table_layout2.addView(row);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void MakeURL_TIMEENTRY() {
		Empid = "empId=1&"; // URL_inout
		URL_TIMEENTRY = URL + Empid + URL_inout;
		URL_TIMEENTRY = URL_TIMEENTRY.replaceAll(" ", "%20");
		// customui.LoadingScreen(getActivity());
		// timeIn=2015-04-01%2009:00:00.000&timeOut=2015-01-02%2018:00:00.000
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(URL_TIMEENTRY,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
						System.out.println(response.toString());
						try {

							JSONObject jsonObject = new JSONObject(response
									.toString().trim());
							String responsetimeinout = jsonObject
									.getString("timeSheetEntryResult");
							System.out.println(responsetimeinout);
							// Toast.makeText(getActivity(), responsetimeinout,
							// Toast.LENGTH_SHORT).show();
							// customui.LoadingHide();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("TAG", "Error: " + error.getMessage());
						// customui.LoadingHide();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public class FromDatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		private int myear;
		private int mmonth;
		private int mday;
		private int hour;
		private int mminute;

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			myear = c.get(Calendar.YEAR);
			mmonth = c.get(Calendar.MONTH);
			mday = c.get(Calendar.DAY_OF_MONTH);
			hour = c.get(Calendar.HOUR_OF_DAY);
			mminute = c.get(Calendar.MINUTE);
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
			int yy = cc.get(Calendar.YEAR);
			int mm = cc.get(Calendar.MONTH);
			int dd = cc.get(Calendar.DAY_OF_MONTH);
			return _date;
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			myear = yy;
			mmonth = mm + 1;
			mday = dd;
			frommday = mday;
			frommmonth = mmonth;
			frommyear = myear;

			fromdate.setText(mday + "/" + mmonth + "/" + myear);
			fromdatpass = frommyear + "-" + frommmonth + "-" + frommday;
			// empID=1&month=04&year=2015&datefrom=2015-04-09&dateTill=2015-04-10

			FROMDATE = "&month=" + frommmonth + "&year=" + frommyear
					+ "&datefrom=" + fromdatpass;

		}

	}

	public class ToDatePicker extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		private int myear;
		private int mmonth;
		private int mday;
		private int hour;
		private int mminute;

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			myear = c.get(Calendar.YEAR);
			mmonth = c.get(Calendar.MONTH);
			mday = c.get(Calendar.DAY_OF_MONTH);
			hour = c.get(Calendar.HOUR_OF_DAY);
			mminute = c.get(Calendar.MINUTE);
			DatePickerDialog _date = new DatePickerDialog(getActivity(), this,
					myear, mmonth, mday) {
				public void onDateChanged(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					try {
						if (year > myear)
							view.updateDate(myear, mmonth, mday);

						if (monthOfYear > mmonth && year == myear)
							view.updateDate(myear, mmonth, mday);

						if (dayOfMonth > mday && year == myear
								&& monthOfYear == mmonth)
							view.updateDate(myear, mmonth, mday);
						/*
						 * if (year < frommyear) view.updateDate(myear, mmonth,
						 * mday);
						 * 
						 * if (monthOfYear < frommmonth && year == frommyear)
						 * view.updateDate(myear, mmonth, mday);
						 * 
						 * if (dayOfMonth < frommday && year == frommyear &&
						 * monthOfYear == frommmonth) view.updateDate(myear,
						 * mmonth, mday);
						 */
					} catch (Exception e) {

					}

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

			todate.setText(mday + "/" + mmonth + "/" + myear);
			todatpass = myear + "-" + mmonth + "-" + mday;
			TODATE = "&dateTill=" + todatpass;

		}

	}

	// *************************************date&***********************************************************************

	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^map^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.

		// Gets to GoogleMap from the MapView and does initialization stuff
		if (mapView != null) {
			mMap = mapView.getMap();
			mMap.getUiSettings().setMyLocationButtonEnabled(true);
			mMap.setMyLocationEnabled(true);
			setUpMap();
			/*
			 * CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new
			 * LatLng(43.1, -87.9), 10); mMap.animateCamera(cameraUpdate);
			 */
		}

		/*
		 * if (mMap == null) { // Try to obtain the map from the
		 * SupportMapFragment. mMap = ((SupportMapFragment)
		 * getFragmentManager().findFragmentById( R.id.map)).getMap();
		 * 
		 * // Check if we were successful in obtaining the map. if (mMap !=
		 * null) { setUpMap(); } }
		 */
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera. In this case, we just add a marker near Africa.
	 * <p/>
	 * This should only be called once and when we are sure that {@link #mMap}
	 * is not null.
	 */
	private void setUpMap() {
		// Centers the camera over the building and zooms int far enough to
		// show the floor picker.
		/*
		 * mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
		 * LatLng(8.5563532, 76.8821253), 5));
		 */
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				new LatLng(8.5563532, 76.8821253), 5);
		mMap.animateCamera(cameraUpdate);
		// Hide labels.
		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		mMap.setIndoorEnabled(false);
		mMap.setMyLocationEnabled(true);

		mMap.setOnCameraChangeListener(this);

	}

	@Override
	public void onCameraChange(CameraPosition position) {
		// Makes sure the visuals remain when zoom changes.
		for (int i = 0; i < Geofencelocations.mGeofenceCoordinates.size(); i++) {
			mMap.addCircle(new CircleOptions()
					.center(Geofencelocations.mGeofenceCoordinates.get(i))
					.radius(Geofencelocations.mGeofenceRadius.get(i).intValue())
					.fillColor(0x40ff0000).strokeColor(Color.TRANSPARENT)
					.strokeWidth(2));

		}
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^map^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
}
