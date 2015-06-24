package com.exenta.login;

import supportclasses.Const;

import com.example.exenta.R;
import com.example.exenta.R.layout;
import com.exenta.mainmenu.MainMenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by rajesh on 19/02/15.
 */
public class splashExenta extends Activity {
	private static int SPLASH_TIME_OUT = 3000;
	private SharedPreferences prefs;
	int private_Mode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		new Handler().postDelayed(new Runnable() {
			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */
			@Override
			public void run() {

				prefs = getSharedPreferences(Const.PREF_NAME, private_Mode);
				String status = prefs.getString("_status", null);
				if (status != null) {
					// Intent to Main Menu
					Intent intent = new Intent(splashExenta.this,
							MainMenuActivity.class);
					startActivity(intent);
					splashExenta.this.finish();
					System.out.println("Main Menu Page");

				} else {
					// Intent to Login
					Intent i = new Intent(splashExenta.this, Login.class);
					startActivity(i);
					splashExenta.this.finish();
					System.out.println("Login Page");
				}

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}