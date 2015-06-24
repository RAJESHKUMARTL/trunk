package com.exenta.pod;
 
import java.util.List;

import com.example.exenta.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PODApprovalListView extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ApprovalPODModel> approval_POD;
 
 
    public PODApprovalListView(Activity activity, List<ApprovalPODModel> approval_POD_) {
        this.activity = activity;
        this.approval_POD = approval_POD_;
    }
 
    @Override
    public int getCount() {
        return approval_POD.size();
    }
 
    @Override
    public Object getItem(int location) {
        return approval_POD.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.pod_approval_row, null);
 
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView company = (TextView) convertView.findViewById(R.id.companyname);
        TextView leavetype = (TextView) convertView.findViewById(R.id.leavetypename);
        TextView reqdate = (TextView) convertView.findViewById(R.id.reqdate);
        TextView totaldays = (TextView) convertView.findViewById(R.id.totalday);

        // getting data for the row
        ApprovalPODModel appPOD = approval_POD.get(position);

        name.setText(appPOD.getFirst_name());
        company.setText(appPOD.getCompany_name());
        leavetype.setText(appPOD.getPod_type());
        reqdate.setText(appPOD.getReq_date());
        totaldays.setText(""+appPOD.getTotal_days());
        
 
        return convertView;
    }
 
}
