package com.exentahrms.staffinfo.json;

import java.util.List;

import com.exenta.cardviewnotification.NotificationList;
import com.exenta.leaveapproval.LeaveApprovalData;
import com.exentahrms.staffinfo.model.GetEmployeeProfile;

public interface VolleyCallback{
	

	void onSuccessLeaveApproval(List<LeaveApprovalData> emp_leave_list);

	void onSuccess(List<GetEmployeeProfile> emp_details_list);
	
	void onSuccessNotificationList(List<NotificationList> not_List);
}