package fera.costin.alexandru.ui;

import fera.costin.alexandru.R;
import fera.costin.alexandru.db.PersistenceAdapter;
import fera.costin.alexandru.logic.Game;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * @author Alexandru Fera
 * 
 */
public class MainActivity extends Activity
{
	private Button btnNewGame;
	private Button btnResume;
	private Button btnPreferences;
	private Button btnRules;
	private Button btnCredits;
	private AlertDialog creditsDialog;
	private AlertDialog rulesDialog;

	// public static final String TAG = "septica";

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Log.d(TAG, "onCreate in MainActivity");

		// Go full-screen.
		// Hide the title and notification bar for extra room.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		// Initialize the graphic components.
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnResume = (Button) findViewById(R.id.btnResume);
		btnPreferences = (Button) findViewById(R.id.btnPreferences);
		btnRules = (Button) findViewById(R.id.btnRules);
		btnCredits = (Button) findViewById(R.id.btnCredits);
		setResumeButtonEnabled();

		creditsDialog = new AlertDialog.Builder(this).create();
		creditsDialog.setView(LayoutInflater.from(this).inflate(
				R.layout.credits, null));

		rulesDialog = new AlertDialog.Builder(this).create();
		rulesDialog.setView(LayoutInflater.from(this).inflate(R.layout.rules,
				null));

		// Register the callback.
		btnNewGame.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Intent septicaIntent = new Intent(
						"fera.costin.alexandru.SEPTICAACTIVITY");
				septicaIntent.putExtra(Game.STATE, Game.GAME_STATES.start);
				startActivityForResult(septicaIntent, Game.START_GAME);

			}
		});

		btnResume.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Intent septicaIntent = new Intent(
						"fera.costin.alexandru.SEPTICAACTIVITY");
				septicaIntent.putExtra(Game.STATE, Game.GAME_STATES.resume);
				startActivityForResult(septicaIntent, Game.RESUME_GAME);

			}
		});

		btnPreferences.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Intent preferencesIntent = new Intent(v.getContext(),
						PreferencesActivity.class);
				startActivityForResult(preferencesIntent, 0);

			}
		});

		btnRules.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				rulesDialog.setButton("Ok",
						new DialogInterface.OnClickListener()
						{

							public void onClick(DialogInterface dialog,
									int which)
							{
								rulesDialog.dismiss();
							}
						});
				rulesDialog.show();

			}
		});

		btnCredits.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				creditsDialog.setButton("OK",
						new DialogInterface.OnClickListener()
						{

							public void onClick(DialogInterface dialog,
									int which)
							{
								creditsDialog.dismiss();
							}
						});

				creditsDialog.setButton2("Feedback",
						new DialogInterface.OnClickListener()
						{

							public void onClick(DialogInterface dialog,
									int which)
							{
								Intent intent = new Intent(Intent.ACTION_SENDTO);
								intent.setType("text/plain");
								intent.putExtra(Intent.EXTRA_SUBJECT,
										"Jocul Șeptică");
								intent.putExtra(Intent.EXTRA_TEXT, "");
								intent.setData(Uri
										.parse("mailto:septica.android@gmail.com"));
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								try
								{
									startActivity(intent);
								} catch (android.content.ActivityNotFoundException ex)
								{
									Toast.makeText(
											MainActivity.this,
											"There are no email accounts configured.",
											Toast.LENGTH_SHORT).show();
								}
							}
						});

				creditsDialog.show();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode)
		{
		case Game.MATCH_OVER:
			// Disable the resume button
			PersistenceAdapter.deleteSavedGameData(getApplicationContext());
			btnResume.setEnabled(false);
			break;
		default:
			setResumeButtonEnabled();
		}
	}

	private void setResumeButtonEnabled()
	{
		if (!PersistenceAdapter.isSavedGameDataValid(getApplicationContext()))
		{
			btnResume.setEnabled(false);
		} else
		{
			btnResume.setEnabled(true);
		}
	}
}
