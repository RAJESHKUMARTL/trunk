package com.exenta.pod;

public class ApprovalPODModel {

	String applied_date, company_name, first_name, jobtitle, pod_type,
			req_reason, status, req_date;
	int emp_id, pod_infoID, record_id, req_id, total_PODreq, total_days;
	
	public String getReq_date() {
		return req_date;
	}

	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}

	public String getApplied_date() {
		return applied_date;
	}

	public void setApplied_date(String applied_date) {
		this.applied_date = applied_date;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getPod_type() {
		return pod_type;
	}

	public void setPod_type(String pod_type) {
		this.pod_type = pod_type;
	}

	public String getReq_reason() {
		return req_reason;
	}

	public void setReq_reason(String req_reason) {
		this.req_reason = req_reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public int getPod_infoID() {
		return pod_infoID;
	}

	public void setPod_infoID(int pod_infoID) {
		this.pod_infoID = pod_infoID;
	}

	public int getRecord_id() {
		return record_id;
	}

	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	public int getReq_id() {
		return req_id;
	}

	public void setReq_id(int req_id) {
		this.req_id = req_id;
	}

	public int getTotal_PODreq() {
		return total_PODreq;
	}

	public void setTotal_PODreq(int total_PODreq) {
		this.total_PODreq = total_PODreq;
	}

	public int getTotal_days() {
		return total_days;
	}

	public void setTotal_days(int total_days) {
		this.total_days = total_days;
	}

}
