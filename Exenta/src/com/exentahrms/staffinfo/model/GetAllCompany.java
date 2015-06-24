package com.exentahrms.staffinfo.model;

public class GetAllCompany {

	int company_id;
	String company_name;
	
	public GetAllCompany(){
		
	}	
	public GetAllCompany(int company_id, String company_name){
		this.company_id = company_id;
		this.company_name = company_name;
	}
	
	public int getCompanyID(){
		return company_id;
	}
	
	public void setCompanyID(int company_id){
		this.company_id = company_id;
	}
	
	public String getCompanyName(){
		return company_name;
	}
	
	public void setCompanyName(String company_name){
		this.company_name = company_name;
	}
}
