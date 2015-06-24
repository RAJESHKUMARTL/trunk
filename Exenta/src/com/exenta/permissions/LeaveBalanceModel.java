package com.exenta.permissions;

public class LeaveBalanceModel  {
int comapanyID;
int employeeID;
int maxPermissionType;
double remainingTime;
double maxPermissionperMon;
double perday;
int permissionRuleID;
int permissionSettingId;

public int getComapanyID() {
	return comapanyID;
}
public void setComapanyID(int comapanyID) {
	this.comapanyID = comapanyID;
}
public int getEmployeeID() {
	return employeeID;
}
public void setEmployeeID(int employeeID) {
	this.employeeID = employeeID;
}
public int getMaxPermissionType() {
	return maxPermissionType;
}
public void setMaxPermissionType(int maxPermissionType) {
	this.maxPermissionType = maxPermissionType;
}
public double getRemainingTime() {
	return remainingTime;
}
public void setRemainingTime(double remainingTime) {
	this.remainingTime = remainingTime;
}
public double getMaxPermissionperMon() {
	return maxPermissionperMon;
}
public void setMaxPermissionperMon(double maxPermissionperMon) {
	this.maxPermissionperMon = maxPermissionperMon;
}
public double getPerday() {
	return perday;
}
public void setPerday(double perday) {
	this.perday = perday;
}
public int getPermissionRuleID() {
	return permissionRuleID;
}
public void setPermissionRuleID(int permissionRuleID) {
	this.permissionRuleID = permissionRuleID;
}
public int getPermissionSettingId() {
	return permissionSettingId;
}
public void setPermissionSettingId(int permissionSettingId) {
	this.permissionSettingId = permissionSettingId;
}

}

