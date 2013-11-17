package fera.costin.alexandru.ui;

import fera.costin.alexandru.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
//import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class PreferencesActivity extends PreferenceActivity
{
	//public static final String TAG = "septica";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// Go full-screen.
		// Hide the title and notification bar for extra room.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		//Log.d(TAG, "onCreate in PreferencesActivity");

	}

}
