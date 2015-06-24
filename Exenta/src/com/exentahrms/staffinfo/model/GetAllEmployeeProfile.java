package com.exentahrms.staffinfo.model;

public class GetAllEmployeeProfile {

	String name, jobtitle, department, email, reportto, extensionto, officeno, photopath;

	public GetAllEmployeeProfile(){
	
	}
	
	public GetAllEmployeeProfile(String name, String jobtitle,
			String department, String email, String reportto,
			String extensionto, String officeno, String photopath) {
		this.name = name;
		this.jobtitle = jobtitle;
		this.department = department;
		this.email = email;
		this.reportto = reportto;
		this.extensionto = extensionto;
		this.officeno = officeno;
		this.photopath = photopath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReportto() {
		return reportto;
	}

	public void setReportto(String reportto) {
		this.reportto = reportto;
	}

	public String getExtensionto() {
		return extensionto;
	}

	public void setExtensionto(String extensionto) {
		this.extensionto = extensionto;
	}

	public String getOfficeno() {
		return officeno;
	}

	public void setOfficeno(String officeno) {
		this.officeno = officeno;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
}
