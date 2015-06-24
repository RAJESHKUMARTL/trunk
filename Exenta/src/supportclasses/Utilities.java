package supportclasses;

import java.net.InetAddress;




import com.example.exenta.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.Toast;

public final class Utilities {
	
	private Utilities(){
		
	}

	////////////////////////////////////////////////////////////////////////////////
    //////////////////WIFI or Mobile Network Available or Not //////////////////////
	////////////////////////////////////////////////////////////////////////////////
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			// There are no active networks.
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
			if (isInternetAvailable()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isInternetAvailable() {
		try {
			InetAddress ipAddr = InetAddress.getByName("www.google.com");
			// You can replace it with your name
			if (ipAddr.equals("")) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return false;
		}

	}

	// *************************************************************************************//
	// ******************************** ALERT DIALOG ***************************************//
	// *************************************************************************************//
	// Message, context, number of buttons, btn-name1, btn-name2
	public static void Custom_alert_Internet(String msg, final Context context) {
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);

		// Setting Dialog Title
		alertDialog2.setTitle("Exenta");

		// Setting Dialog Message
		alertDialog2.setMessage(msg);

		// Setting Icon to Dialog
		
		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						/* context.startActivity(new Intent(
		                            android.provider.Settings.ACTION_WIFI_SETTINGS));		*/
						Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        context.startActivity(intent);
						dialog.cancel();
					}
				});
		// Setting Negative "NO" Btn
		alertDialog2.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						
						dialog.cancel();
					}
				});

		// Showing Alert Dialog
		alertDialog2.show();
	}
}
