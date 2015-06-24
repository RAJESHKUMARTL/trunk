package com.exentahrms.staffinfo;


import supportclasses.Const;

import com.android.volley.toolbox.CircularNetworkImageView;
import com.android.volley.toolbox.ImageLoader;
import com.example.exenta.R;
import com.exenta.app.AppController;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class ProfileDetails extends Activity{

	String url = Const.StaffInfo.GET_EMPLOYEE_DETAILS;
	TextView name, email, cmpy_name, empl_code, experience, job_title, report_mananger,department;
	CircularNetworkImageView photo;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		
		String emp_name = bundle.getString("FullName");
		String emp_job_title = bundle.getString("JobTitleName");
		String emp_code = bundle.getString("EmpCode");
		String emp_cmpy_name = bundle.getString("CompanyName");
		String emp_department = bundle.getString("Department");
		String emp_exp = bundle.getString("ExperienceValue");
		String emp_email = bundle.getString("Email");
		String emp_report = bundle.getString("ReportManagerName");
		String emp_photo = bundle.getString("Photo");
		
		System.out.println("Profile Page-RAAA : "+emp_name);
		setContentView(R.layout.staffinfo_emp_profile);
   		
		//TextView and Image
		name = (TextView) findViewById(R.id.name);
		job_title = (TextView) findViewById(R.id.jobtitle);
		empl_code = (TextView) findViewById(R.id.empcode);
		cmpy_name = (TextView) findViewById(R.id.company_name);
		department = (TextView) findViewById(R.id.department);
		experience = (TextView) findViewById(R.id.experience);
		email = (TextView) findViewById(R.id.email);
		report_mananger = (TextView) findViewById(R.id.report_mananger);
		photo = (CircularNetworkImageView) findViewById(R.id.thumbnail);		
		name.setText(emp_name.toString());
		job_title.setText(emp_job_title.toString());
		empl_code.setText(emp_code.toString());
		cmpy_name.setText( emp_cmpy_name.toString());
		department.setText(emp_department.toString());
		experience.setText(emp_exp.toString());
		email.setText(emp_email.toString());
		report_mananger.setText(emp_report.toString());		
		photo.setImageUrl("http://exentaindia.exenta.org/Images/Employees/" + emp_photo, imageLoader);		
		//photo.setD("http://192.168.1.76:1234/Images/Employees/" + emp_photo);
	}		
}

