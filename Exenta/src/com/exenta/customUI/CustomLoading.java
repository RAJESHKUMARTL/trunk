package com.exenta.customUI;

import com.example.exenta.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class CustomLoading {
	Dialog dialog;
	public void LoadingScreen(Context context) {
		// create a Dialog component
		dialog = new Dialog(context);
		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setTitle("Exenta Loading");
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog_box);
		dialog.show();
	}
	public void LoadingHide() {
		// create a Dialog componen
		dialog.dismiss();
	}
	

}
