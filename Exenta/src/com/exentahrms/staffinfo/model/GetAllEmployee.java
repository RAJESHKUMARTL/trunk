package com.exentahrms.staffinfo.model;

public class GetAllEmployee {
	private String title, thumbnailUrl, empCode, jobDesc, empid;

	public GetAllEmployee() {
	}

	/*public GetAllEmployee(String name, String thumbnailUrl, String employeeCode, String jobTitle, String employeeid) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.empCode = employeeCode;
		this.jobDesc = jobTitle;
		this.empid = employeeid;
	}*/
	
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String name) 
	{
		this.title = name;
	}

	public String getThumbnailUrl() 
	{
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) 
	{
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getempcode() 
	{
		return empCode;
	}

	public void setempcode(String employeeCode) 
	{
		this.empCode = employeeCode;
	}

	public String getjobtitle() 
	{
		return jobDesc;
	}

	public void setRating(String jobtitle) 
	{
		this.jobDesc = jobtitle;
	}
	
	public String getempid() 
	{
		return empid;
	}

	public void setempid(String employeeid) 
	{
		this.empid = employeeid;
	}

}
