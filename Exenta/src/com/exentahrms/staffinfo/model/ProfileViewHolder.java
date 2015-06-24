package com.exentahrms.staffinfo.model;

import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

class ProfileViewHolder {

	public TextView getName() {
		return name;
	}

	public void setName(TextView name) {
		this.name = name;
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public TextView getDepartment() {
		return department;
	}

	public void setDepartment(TextView department) {
		this.department = department;
	}

	public TextView getReport() {
		return report;
	}

	public void setReport(TextView report) {
		this.report = report;
	}

	public TextView getExtension() {
		return extension;
	}

	public void setExtension(TextView extension) {
		this.extension = extension;
	}

	public TextView getOffice() {
		return office;
	}

	public void setOffice(TextView office) {
		this.office = office;
	}

	public TextView getEmail() {
		return email;
	}

	public void setEmail(TextView email) {
		this.email = email;
	}

	public NetworkImageView getThumbNail() {
		return thumbNail;
	}

	public void setThumbNail(NetworkImageView thumbNail) {
		this.thumbNail = thumbNail;
	}

	TextView name, title, department, report, extension, office, email;
	NetworkImageView thumbNail;

}
