package com.exenta.permissions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MyPermissionReqModel implements Parcelable {
	String appliedDate; 
	String fromTime;
	String permissionDate;
	String permissionType;
	String responseReason;
	String status;
	String toTime;
	String totalHours;
	int employeeID;
	int permissionInfoID;
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getPermissionDate() {
		return permissionDate;
	}
	public void setPermissionDate(String permissionDate) {
		this.permissionDate = permissionDate;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public String getResponseReason() {
		return responseReason;
	}
	public void setResponseReason(String responseReason) {
		this.responseReason = responseReason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getPermissionInfoID() {
		return permissionInfoID;
	}
	public void setPermissionInfoID(int permissionInfoID) {
		this.permissionInfoID = permissionInfoID;
	}
	
	public static final Parcelable.Creator<MyPermissionReqModel> CREATOR = new Creator<MyPermissionReqModel>() { 
		
		  public MyPermissionReqModel createFromParcel(Parcel source) { 
			
		      MyPermissionReqModel mMyPermissionReqModel = new MyPermissionReqModel(); 
	
		      mMyPermissionReqModel.appliedDate = source.readString(); 
		      mMyPermissionReqModel.fromTime = source.readString();
		      mMyPermissionReqModel.permissionDate = source.readString(); 
		      mMyPermissionReqModel.permissionType = source.readString();
		      mMyPermissionReqModel.responseReason = source.readString();
		      mMyPermissionReqModel.status = source.readString(); 
		      mMyPermissionReqModel.toTime = source.readString(); 
		      mMyPermissionReqModel.totalHours = source.readString();  
		      mMyPermissionReqModel.employeeID = source.readInt(); 
		      mMyPermissionReqModel.permissionInfoID = source.readInt(); 
		    
		 
		      return mMyPermissionReqModel; 
		    
		
		  } 
	
		  public MyPermissionReqModel[] newArray(int size) { 
		
		      return new MyPermissionReqModel[size]; 
		
		  } 
	
		     }; 

		
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		 parcel.writeString(appliedDate); 
			
		   parcel.writeString(fromTime); 
		   parcel.writeString(permissionDate); 
		   
		   parcel.writeString(status); 
		   parcel.writeString(permissionType); 
		   parcel.writeString(responseReason); 
		 
		   parcel.writeString(toTime);  
		   parcel.writeString(totalHours);
		   parcel.writeInt(employeeID);
		   parcel.writeInt(permissionInfoID); 	
	}
	
}
