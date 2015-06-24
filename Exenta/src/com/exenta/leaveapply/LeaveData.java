package com.exenta.leaveapply;

public class LeaveData {
	private String LeaveTypeAcronym;
	private String Leavecategory;
	private String DaysInRequest;
	private String Remaining;
	private String Balance;
	
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	public String getPlanedLeave() {
		return PlanedLeave;
	}
	public void setPlanedLeave(String planedLeave) {
		PlanedLeave = planedLeave;
	}
	private String PlanedLeave;
	
	
	public String getLeaveTypeAcronym() {
		return LeaveTypeAcronym;
	}
	public void setLeaveTypeAcronym(String leaveTypeAcronym) {
		LeaveTypeAcronym = leaveTypeAcronym;
	}
	public String getLeavecategory() {
		return Leavecategory;
	}
	public void setLeavecategory(String leavecategory) {
		Leavecategory = leavecategory;
	}
	public String getDaysInRequest() {
		return DaysInRequest;
	}
	public void setDaysInRequest(String daysInRequest) {
		DaysInRequest = daysInRequest;
	}
	public String getRemaining() {
		return Remaining;
	}
	public void setRemaining(String remaining) {
		Remaining = remaining;
	}
	

}
