package com.exenta.permissions;

import com.example.exenta.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {
	LinearLayout permission_req, permission_manage, permission_approval;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permission_home);

		permission_req = (LinearLayout) findViewById(R.id.per_myreq);
		permission_manage = (LinearLayout) findViewById(R.id.per_manage);
		permission_approval = (LinearLayout) findViewById(R.id.per_approve);

		permission_req.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent req = new Intent(getApplicationContext(),
						MyPermissionRequest.class);
				startActivity(req);

			}
		});

		permission_manage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent manage = new Intent(getApplicationContext(),
						ManagePermission.class);
				startActivity(manage);
			}
		});
		permission_approval.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent approval = new Intent(getApplicationContext(),
						PermissionApproval.class);
				startActivity(approval);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
