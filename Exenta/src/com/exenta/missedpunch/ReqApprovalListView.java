package com.exenta.missedpunch;
 
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.exenta.R;

 
public class ReqApprovalListView extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ApprovalRegularizationModel> approval_Reg;
 
 
    public ReqApprovalListView(Activity activity, List<ApprovalRegularizationModel> approval_Reg_) {
        this.activity = activity;
        this.approval_Reg = approval_Reg_;
    }
 
    @Override
    public int getCount() {
        return approval_Reg.size();
    }
 
    @Override
    public Object getItem(int location) {
        return approval_Reg.get(location);
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
            convertView = inflater.inflate(R.layout.regular_approval_row, null);
 
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView company = (TextView) convertView.findViewById(R.id.companyname);
        TextView leavetype = (TextView) convertView.findViewById(R.id.leavetypename);
        TextView reqdate = (TextView) convertView.findViewById(R.id.reqdate);
        TextView totalhrs = (TextView) convertView.findViewById(R.id.totalday);

        // getting data for the row
        ApprovalRegularizationModel modelRegularization = approval_Reg.get(position);

        name.setText(modelRegularization.getFirst_name());
        company.setText(modelRegularization.getCompany_name());
        leavetype.setText(modelRegularization.getRegular_type());
        reqdate.setText(modelRegularization.getFrom_date());
        totalhrs.setText(modelRegularization.getTotalhour());
        
 
        return convertView;
    }
    
 

 
}
