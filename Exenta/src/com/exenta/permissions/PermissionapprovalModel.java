package com.exenta.permissions;

import android.os.Parcel;
import android.os.Parcelable;

public class PermissionapprovalModel  implements Parcelable{
	String appliedDate;
	String companyID;
	String companyName;
	int empId;
	String firstName;
	String jobTitle;
	int permissionInfoID;
	String permissionDate;
	String photopath;
	String reasonEmp;
	int recordID;
	int requestedID;
	String status;
	int totalhrs;
	String Timings;
	String permissionType;
	
	
	public String getTimings() {
		return Timings;
	}
	public void setTimings(String timings) {
		Timings = timings;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	
	public String getPermissionDate() {
		return permissionDate;
	}
	public void setPermissionDate(String permissionDate) {
		this.permissionDate = permissionDate;
	}

	
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public int getPermissionInfoID() {
		return permissionInfoID;
	}
	public void setPermissionInfoID(int permissionInfoID) {
		this.permissionInfoID = permissionInfoID;
	}
	public String getPhotopath() {
		return photopath;
	}
	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}
	public String getReasonEmp() {
		return reasonEmp;
	}
	public void setReasonEmp(String reasonEmp) {
		this.reasonEmp = reasonEmp;
	}
	public int getRecordID() {
		return recordID;
	}
	public void setRecordID(int recordID) {
		this.recordID = recordID;
	}
	public int getRequestedID() {
		return requestedID;
	}
	public void setRequestedID(int requestedID) {
		this.requestedID = requestedID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotalDays() {
		return totalhrs;
	}
	public void setTotalDays(int totalhrs) {
		this.totalhrs = totalhrs;
	}
	
	public static final Parcelable.Creator<PermissionapprovalModel> CREATOR = new Creator<PermissionapprovalModel>() { 
		
		  public PermissionapprovalModel createFromParcel(Parcel source) { 
	
		      PermissionapprovalModel mPermissionapprovalModel = new PermissionapprovalModel(); 
	
		      mPermissionapprovalModel.appliedDate = source.readString(); 
		      mPermissionapprovalModel.companyID = source.readString(); 
		      mPermissionapprovalModel.companyName = source.readString(); 
		      mPermissionapprovalModel.empId = source.readInt(); 
		      mPermissionapprovalModel.firstName = source.readString(); 
		      mPermissionapprovalModel.jobTitle = source.readString(); 
		      
		      mPermissionapprovalModel.permissionInfoID = source.readInt(); 
		      mPermissionapprovalModel.permissionDate = source.readString(); 
		      mPermissionapprovalModel.photopath = source.readString();
		      mPermissionapprovalModel.reasonEmp = source.readString();
		      mPermissionapprovalModel.recordID = source.readInt(); 
		      mPermissionapprovalModel.requestedID = source.readInt(); 
		      mPermissionapprovalModel.status = source.readString();  
		      mPermissionapprovalModel.totalhrs = source.readInt();  
		      mPermissionapprovalModel.Timings = source.readString();  
		      mPermissionapprovalModel.permissionType = source.readString();
		      return mPermissionapprovalModel; 
		    
		
		  } 
	
		  public PermissionapprovalModel[] newArray(int size) { 
		
		      return new PermissionapprovalModel[size]; 
		
		  } 
	
		     }; 

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		
		 parcel.writeString(appliedDate); 
	
		   parcel.writeString(companyID); 
		   parcel.writeString(companyName); 
		   parcel.writeInt(empId); 

		   parcel.writeString(firstName); 
		   parcel.writeString(jobTitle); 
		   parcel.writeInt(permissionInfoID); 
			 
		
				   parcel.writeString(permissionDate); 
				   parcel.writeString(photopath); 
				   parcel.writeString(reasonEmp); 
				   parcel.writeInt(recordID); 
				   parcel.writeInt(requestedID); 
				   parcel.writeString(status); 
				   parcel.writeInt(totalhrs); 
				   parcel.writeString(Timings); 
				   parcel.writeString(permissionType); 
		
		
	}
	
	
}
