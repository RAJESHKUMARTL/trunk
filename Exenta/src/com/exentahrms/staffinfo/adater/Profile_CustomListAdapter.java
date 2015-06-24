package com.exentahrms.staffinfo.adater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.exenta.R;
import com.exenta.app.AppController;
import com.exentahrms.indexlistview.StringMatcher;
import com.exentahrms.staffinfo.FragmentStaffInfo;
import com.exentahrms.staffinfo.model.GetAllEmployeeProfile;

public class Profile_CustomListAdapter extends BaseAdapter implements SectionIndexer, Filterable{
		private Context context;
		private LayoutInflater inflater;
		private List<GetAllEmployeeProfile> allemplist;
		ImageLoader imageLoader;
		private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		ValueFilter valueFilter;
		List<GetAllEmployeeProfile> mStringFilterList;
		private int selectedIndex;
		//For Creating View for each row
//		ViewHolder viewHolder;
		
		public Profile_CustomListAdapter(Context context, List<GetAllEmployeeProfile> empProfileList) {
			
			this.context=context;
			this.allemplist = empProfileList;
			this.mStringFilterList = empProfileList;
		}

		@Override
		public int getCount() {
			return allemplist.size();
		}

		@Override
		public Object getItem(int location) {
			return location;
		}

		@Override
		public long getItemId(int position) {
			
			return position;
			
		}

		private class ViewHolder
		 {
		   TextView name ,title, department, report, extension, office, email ;
		   NetworkImageView thumbNail;
		 }
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder;
			System.out.println("Hello Welcome");
			if (inflater == null)
				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (imageLoader == null)
				imageLoader = AppController.getInstance().getImageLoader();
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.profile_list_row, null);
				viewHolder=new ViewHolder();
				//cache the views
				viewHolder.thumbNail = (NetworkImageView) convertView
						.findViewById(R.id.thumbnail);
				viewHolder.name = (TextView) convertView.findViewById(R.id.name);
				viewHolder.title = (TextView) convertView.findViewById(R.id.job_title);
				viewHolder.department = (TextView) convertView.findViewById(R.id.department);
				viewHolder.report = (TextView) convertView.findViewById(R.id.report);
				viewHolder.extension = (TextView) convertView.findViewById(R.id.extension);
				viewHolder.office = (TextView) convertView.findViewById(R.id.officeno);
				viewHolder.email = (TextView) convertView.findViewById(R.id.email);
				 //link the cached views to the convertview
			    convertView.setTag( viewHolder);
			}
			
			else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			 if(FragmentStaffInfo.selectedValue==position)
	            {
	                viewHolder.report.setVisibility(View.VISIBLE); // here I am hiding Imageview for position 4
	                viewHolder.extension.setVisibility(View.VISIBLE);
	            } 
	            else
	            {
	                viewHolder.report.setVisibility(View.GONE); // here I am showing Imageview for rest of items
	                viewHolder.extension.setVisibility(View.GONE);
	            }

			
			
			//Default image setting
			viewHolder.thumbNail.setDefaultImageResId(R.drawable.emp_icon);
			viewHolder.thumbNail.setErrorImageResId(R.drawable.emp_icon);
			viewHolder.thumbNail.setAdjustViewBounds(true);

			// getting employee data for the row
			GetAllEmployeeProfile m = allemplist.get(position);

			// thumbnail image
			viewHolder.thumbNail.setImageUrl("exentaindia.exenta.org/Images/Employees/"+m.getPhotopath(), imageLoader);
			
			viewHolder.name.setText(m.getName());		
			viewHolder.title.setText("Job Title: " + String.valueOf(m.getJobtitle()));
			viewHolder.department.setText("Department: " + String.valueOf(m.getDepartment()));
			viewHolder.report.setText("Report To: " + String.valueOf(m.getReportto()));
			viewHolder.extension.setText("Extension: " + String.valueOf(m.getExtensionto()));
			viewHolder.office.setText("Office No: " + String.valueOf(m.getOfficeno()));
			viewHolder.email.setText("Email: " + String.valueOf(m.getEmail()));
			convertView.setTag( viewHolder);
			return convertView;
		}

		@Override
		public int getPositionForSection(int section) {
			// If there is no item for current section, previous section will be selected
			System.out.println("Section Count:"+section);
			for (int i = section; i >= 0; i--) {
				for (int j = 0; j < getCount(); j++) {
					if (i == 0) {
						// For numeric section
						for (int k = 0; k <= 9; k++) {
							if (StringMatcher.match(String.valueOf(allemplist.get(j).getName().charAt(0)), String.valueOf(k)))
								return j;
						}
					} else {
						if (StringMatcher.match(String.valueOf(allemplist.get(j).getName().charAt(0)), String.valueOf(mSections.charAt(i))))
							return j;
					}
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			String[] sections = new String[mSections.length()];
			for (int i = 0; i < mSections.length(); i++)
				sections[i] = String.valueOf(mSections.charAt(i));
			return sections;
		}

		@Override
		public Filter getFilter() {
			if (valueFilter == null) {
	            valueFilter = new ValueFilter();
	        }
	        return valueFilter;
		}
		
		private class ValueFilter extends Filter {
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            FilterResults results = new FilterResults();

	            if (constraint != null && constraint.length() > 0) {
	            	List<GetAllEmployeeProfile> filterList = new ArrayList<GetAllEmployeeProfile>();
	                for (int i = 0; i < mStringFilterList.size(); i++) {
	                    if ( (mStringFilterList.get(i).getName().toUpperCase() )
	                            .contains(constraint.toString().toUpperCase()) || (mStringFilterList.get(i).getJobtitle().toUpperCase() )
	                            .contains(constraint.toString().toUpperCase())) {

	                        GetAllEmployeeProfile emplist_filter = new GetAllEmployeeProfile(
	                        		mStringFilterList.get(i).getName(),
	                        		mStringFilterList.get(i).getJobtitle(),
	                        		mStringFilterList.get(i).getDepartment(),
	                        		mStringFilterList.get(i).getEmail(),
	                        		mStringFilterList.get(i).getReportto(),
	                        		mStringFilterList.get(i).getExtensionto(),
	                        		mStringFilterList.get(i).getOfficeno(),
	                        		mStringFilterList.get(i).getPhotopath()
	                        		);
	                        /*
	                        mStringFilterList.get(i).getDepartment();
	                        mStringFilterList.get(i).getJobtitle();*/
	                        filterList.add(emplist_filter);
	                    }
	                }
	                results.count = filterList.size();
	                results.values = filterList;
	            } else {
	                results.count = mStringFilterList.size();
	                results.values = mStringFilterList;
	            }
	            return results;

	        }

	        @Override
	        protected void publishResults(CharSequence constraint,
	                FilterResults results) {
	            allemplist = (List<GetAllEmployeeProfile>) results.values;
	            notifyDataSetChanged();
	        }
		}
}
