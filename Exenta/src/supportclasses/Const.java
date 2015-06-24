package supportclasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;


public class Const {	
	public static String employeeID;
	public static String companyID;		
	
	// SharePreference FileName
	public static final String PREF_NAME = "EXENTA";
	
	//http://202.88.244.29:2020/
	//http://202.88.244.29:2020//
	
	
	public static final String HTTP = "http://202.88.244.29:2020//Rajesh/attendenceService.svc";
	public static final String URL_JSON_ARRAY_P1= "http://202.88.244.29:2020//sherif/Staffinfo.svc/GetLeaveBalance?EmpID=";
	public static final String URL_JSON_ARRAY_P2 = "&LeaveDate=";
	public static final String URL_JSON_ARRAY_P2_p1	="&IsAdmin=false";
	
	
	
	public static final String URL_JSON_ARRAY_Comp_p1 = "http://202.88.244.29:2020//vishak/Service1.svc/getLeaveCompentationfor?CompanyID=";
	public static final String URL_JSON_ARRAY_Comp_p2 =  "&EmployeeID=";
	public static final String URL_JSON_ARRAY_Comp_p2_p1 =  "&Leavetype=4";
	public static final String URL_JSON_ARRAY_Comp_p2_p2 =  "&Date=";
	
	
	public static final String URL_REGULARIZATION_leave = HTTP+"/applyregularize?EmployeeID=";
	public static final String URL_REGULARIZATION_leave_l1 ="&CompanyID=";
	public static final String URL_REGULARIZATION_leave_l2 ="&fromdate=";
	public static final String URL_REGULARIZATION_leave_l3 ="&todate=&";
	public static final String URL_REGULARIZATION_leave_l4 ="regular=3&reason=";
	public static final String URL_REGULARIZATION_leave_l5 ="&Totalhours=0.0"+"&ddlfromTme=2015-01-01"+ "&ddlToTme=2015-01-01"+ "&fromtimesec=AM"+ "&totimesec=AM";
	public static final String URL_REGULARIZATION_leave_l6 = "&fromSess=";
	public static final String URL_REGULARIZATION_leave_l7 = "&toSess=";
	public static final String URL_REGULARIZATION_leave_l8 = "&HTtotalDays=";
	
	
	
	public static final String URL_JSON_ARRAY_NOTIFICATION = HTTP+"/notification?employeeID=";
	
	public static final String URL_JSON_ARRAY_REMOVENOTIFICATION = HTTP+"/removeNotification?status=2&alertID=";
	
	
	public static final  String URL_JSON_ARRAY_LeaveSum_p1 = "http://202.88.244.29:2020/vishak/Service1.svc/getLeaveSummary?empID=";
	public static final  String URL_JSON_ARRAY_LeaveSum_p2 = "&companyId=";
	public static final  String URL_JSON_ARRAY_LeaveSum_p3 = "&month=3&year=2015";
	
		
	
	public static final String URL_GENERAL_P1 = HTTP+"/leavetype?catId=1&empID=";
	
	public static final String URL_COMPENSATORY_OFF_p1 = HTTP+"/getcompensatedays?CompanyID=";
	public static final String URL_COMPENSATORY_OFF_p1_p2 =  "&EmployeeID=";
	public static final String URL_COMPENSATORY_OFF_p1_p3 = "&LeaveTypeID="; 
	public static final String URL_COMPENSATORY_OFF_p1_p = "&Date=2015-02-19";
	
	public static final String URL_TOTALDAYSGENERAL_p1 = HTTP+"/getTotalDays?fromDate=";
	public static final String URL_TOTALDAYSGENERAL_p2 = "&toDate=";
	public static final String URL_TOTALDAYSGENERAL_p3 = "&leaveType="; 
	public static final String URL_TOTALDAYSGENERAL_p4 = "&empID=";

	public static final String URL_LEAVEREQUEST_p1 = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/requestList?EmployeeID=";
	
	public static final String URL_LEAVEREQUEST_p2 = "&rowno=0&index=0";
	
	public static final String LEAVEURL_GRAPHDATA_p1 = "http://202.88.244.29:2020//sherif/Staffinfo.svc/CompanyDetailsGraph?EmpID=";
		
	public static final String LEAVEURL_GRAPHDATA_p2 = "&Year=";
	//public static final String URL_JSON_ARRAY_Summary = "http://202.88.244.29:2020//sherif/Staffinfo.svc/GetLeaveBalance?EmpID=18&LeaveDate=2015-01-05&IsAdmin=false";
	//......................................permisson.................................................	
	
			//Details.....permission approval
					public static final String URL_PERMISSION_APPROVAL1 = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/permissionrequesterlist?companyID=";
					public static final String URL_PERMISSION_APPROVAL2 =  "&EmployeeID=";
					public static final String Approval_listImage_URL =  "http://192.168.1.76:1234/images/Employees/";
					
					//Details.....permission my permission	
				public static final String URL_MYPERMISSION1="http://202.88.244.29:2020/Rajesh/attendenceService.svc/myPermissionRequest?employeeID=";
				public static final String URL_MYPERMISSION2="&month=";
				public static final String URL_MYPERMISSION3="&year=";

				//Manage permission ......remaining leave
				public static final String URL_LEAVEBalance1="http://202.88.244.29:2020/Rajesh/attendenceService.svc/getRemainingPermission?CompanyID=";
				public static final String URL_LEAVEBalance2="&EmployeeID=";
				public static final String URL_LEAVEBalance3="&sysDate=";
				 //&companyID=1&permissionSettingID=13&permissionRuleID=14&permissionDate=5/5/2015&appliedDate=2015-5-5&reason=null&totalHours=1:0&fromTime=5/5/2015 5:15 PM&toTime=5/5/2015 6:15 PM&fromTimeSec=PM&toTimeSec=PM

				//1&companyID=1&permissionSettingID=13&permissionRuleID=14&permissionDate=06-05-2015&appliedDate=05-05-2015&reason=ss&totalHours=1.0&fromTime=05-05-2015 3:00&toTime=05-05-2015 4:00&fromTimeSec=pm&toTimeSec=pm
				//public static final String URL_APPLY_PERMISSION="http://202.88.244.29:2020/Rajesh/attendenceService.svc/applyPermission";
				public static final String URL_APPLY_PERMISSION0="http://202.88.244.29:2020/Rajesh/attendenceService.svc/getPermissionApply?employeeID=";
				public static final String URL_APPLY_PERMISSION1="&companyID=";
				public static final String URL_APPLY_PERMISSION2="&permissionSettingID=";
				public static final String URL_APPLY_PERMISSION3="&permissionRuleID=";
				public static final String URL_APPLY_PERMISSION4="&permissionDate=";
				public static final String URL_APPLY_PERMISSION5="&appliedDate=";
				public static final String URL_APPLY_PERMISSION6="&reason=";
				public static final String URL_APPLY_PERMISSION7="&totalHours=";
				public static final String URL_APPLY_PERMISSION8="&fromTime=";
				public static final String URL_APPLY_PERMISSION9="&toTime=";
				public static final String URL_APPLY_PERMISSION10="&fromTimeSec=";
				public static final String URL_APPLY_PERMISSION11="&toTimeSec=";

				// permission approval ......>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

				//http://202.88.244.29:2020/Rajesh/attendenceService.svc/PermissionApproval?record={recordID}&status={status}&permissionInfoId={permissionInfoID}&EmpID={EmpID}& sessionEmpId={sessionEmpID}&requestID={requestID}&response={txtResponseValue}&reason={reason}&companyID={companyID}
				public static final String URL_PermissionApproval0="http://202.88.244.29:2020/Rajesh/attendenceService.svc/PermissionApproval?record=";
				public static final String URL_PermissionApproval1="&status=";
				public static final String URL_PermissionApproval2="&permissionInfoId=";
				public static final String URL_PermissionApproval3="&EmpID=";
				public static final String URL_PermissionApproval4="&sessionEmpId=";
				public static final String URL_PermissionApproval5="&requestID=";
				public static final String URL_PermissionApproval6="&response=";
				public static final String URL_PermissionApproval7="&reason=";
				public static final String URL_PermissionApproval8="&companyID=";	
					
					
						
		
		
				//......................................permisson.................................................
		
			
	public final class Connection {
		public static final String URL_IP = "http://202.88.244.29:2020/";
		public static final String FOLDER_NAME_SHERIF= "sherif/";
		public static final String STAFF_INFO= "Staffinfo.svc/";
		public static final String EMPLOYEE_PROFILE= "Staffinfo.svc/";
	}	
	public final static class StaffInfo {
		public static final String GET_ALL_COMPANY_p1 = Connection.URL_IP+Connection.FOLDER_NAME_SHERIF+Connection.STAFF_INFO+"CompanyDetails?EmpID=";
		public static final String GET_ALL_COMPANY_p2 =  "&CompID=";
		public static final String GET_ALL_COMPANY_p3 =  "&Level=0&isadmin=true";
		
		
		public static final String GET_ALL_EMPLOYEE_P1 = Connection.URL_IP+Connection.FOLDER_NAME_SHERIF+Connection.STAFF_INFO+"GetEmployeeDetail?DepartmentID=&EmployeeID=0&Name=&JobTitleID=&CompanyId=";
		public static final String GET_ALL_EMPLOYEE_P2_1 = "&AccountStatus=";
		public static final String GET_ALL_EMPLOYEE_P2_2 = "&mod=0&Status=0";
		public static final String GET_ALL_EMPLOYEE_P2_3 =  "&fromdate=1/1/1900&todate=1/1/1900&startIndex=0&endIndex=1000&IsManagement=";
		public static final String GET_ALL_EMPLOYEE_P3 = "&keytype=1";		
		public static final String GET_EMPLOYEE_DETAILS = Connection.URL_IP+Connection.FOLDER_NAME_SHERIF+Connection.STAFF_INFO+"GetFunctions?Security=";
	
	
	}
	public final static class ProfileDetails {
		public static final String GET_EMPLOYEE_DETAILS_P1 = Connection.URL_IP+Connection.FOLDER_NAME_SHERIF+Connection.STAFF_INFO+"GetEmployeeProfile?LastLevel=0&JobTitle=0&CompanyID=";
		public static final String GET_EMPLOYEE_DETAILS_P2 = "&Name=&IsManagement=0";
	}	
	public final static class Leave {
		public static final String URL_GENERAL_P1 = HTTP+"/leavetype?catId=";
		public static final String URL_GENERAL_P2 = "&empID=";
		
		public static final String URL_COMPENSATORY_OFF =  HTTP+"/compOFFRequestor?employeeID=";
		
		
		public static final String URL_COMPENSATORY_OFF_p1 = HTTP+"/getCompensateApply?";
		public static final String URL_COMPENSATORY_OFF_p1_p2 = "&employeeID=";
		public static final String URL_COMPENSATORY_OFF_p1_p3 = "&companyID="; 
		public static final String URL_COMPENSATORY_OFF_p1_p4 = "&leaveTypeID=";
		public static final String URL_COMPENSATORY_OFF_p1_p5 = "&fromDate=";
		public static final String URL_COMPENSATORY_OFF_p1_p6 = "&session=";
		public static final String URL_COMPENSATORY_OFF_p1_p7 = "&totalDays=";
		public static final String URL_COMPENSATORY_OFF_p1_p8 = "&argCompDate=";
		public static final String URL_COMPENSATORY_OFF_p1_p9 = "&reason=";
		
		public static final String URL_TOTALDAYSGENERAL11 = HTTP+"/getTotalDays?fromDate=";
		public static final String URL_JSON_ARRAY_LeaveSum_p1 = "http://202.88.244.29:2020/vishak/Service1.svc/getLeaveSummary?empID=";
		public static final String URL_JSON_ARRAY_LeaveSum_p2 =  "&companyId=";
		public static final String URL_JSON_ARRAY_LeaveSum_p3 =  "&month=";
		public static final String URL_JSON_ARRAY_LeaveSum_p4 = "&year=";				
	}		
	
	public final class POD {
		public static final String POD_TYPE = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/podtypedetails?pcompanyID=";
		public static final String POD_TOTALDAYS = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/getTotalPODDays?fromDate=";
		public static final String POD_APPROVAL = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/podapprovallist?EmployeeID=";
		public static final String POD_APPROVE_OR_NOT = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/podapprovalpage?EmpID=";
		public static final String POD_APPLY = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/getPODApply?companyID=";
	}
	
	
	/*----------------------------------------Missed Punch*----------------------------------------------------------------------*/
		
	public final class MissedPunch
	{							
		public static final String MissedPunch_Apply_emp =HTTP+"/getMissedApply?employeeId=";
		public static final String MissedPunch_Apply_cmp ="&companyId=";
		public static final String MissedPunch_Apply_fmd ="&fromDate=";
		public static final String MissedPunch_Apply_td = "&toDate=";
		public static final String MissedPunch_Apply_reg = "&regular=";
		public static final String MissedPunch_Apply_reason = "&reason=";
		public static final String MissedPunch_Apply_th = "&totalHours=";
		public static final String MissedPunch_Apply_ft = "&ddlfromTme=";
		public static final String MissedPunch_Apply_tt = "&ddlToTme=";
		public static final String MissedPunch_Apply_fts = "&fromtimesec=";
		public static final String MissedPunch_Apply_tts = "&totimesec=";
		public static final String MissedPunch_Apply_end = "&ddlFromtime=Full"+"&ddlTotime=Full"+ "&HTtotalDays=1";		
	}
	
	public final class Regularization {
		public static final String REG_APPROVAL_1 = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/listregularizeRequests?CompanyID=";
		public static final String REG_APPROVAL_2 = "&EmployeeID=";
		public static final String REG_APPROVAL_OR_NOT = "http://202.88.244.29:2020/Rajesh/attendenceService.svc/approveregularization?sessionEmployeeID=";
	}
	
	
}
