package fera.costin.alexandru.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fera.costin.alexandru.R;
import fera.costin.alexandru.db.PersistenceAdapter;
import fera.costin.alexandru.logic.Game;
import fera.costin.alexandru.logic.ICard;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
//import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class SepticaActivity extends Activity implements Observer
{
	//public static final String TAG = "septica";

	// The player's cards.
	private ImageView plCard1;
	private ImageView plCard2;
	private ImageView plCard3;
	private ImageView plCard4;
	private List<ImageView> plCards;
	private List<ImageView> opCards;
	private int backCardId;
	private Button btnDone;
	private AlertDialog mAlertDialog;
	private ImageView midCard;
	private TextView computerPoints;
	private TextView myPoints;
	String labelPoints;

	private Game game;
	private String gameDifficulty;
	private PersistenceAdapter persistence;
	
	private Handler handler;

	/**
	 * This method is called when our activity is started up for the first time.
	 * Here we set up all the UI components and hook into the input system. This
	 * will only get called once in the life cycle of our activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//Log.d(TAG, "onCreate in SepticaActivity");

		// Go full-screen.
		// Hide the title and notification bar for extra room.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.septica);

		// Initialize the graphic components.
		plCard1 = (ImageView) findViewById(R.id.plCard1);
		plCard2 = (ImageView) findViewById(R.id.plCard2);
		plCard3 = (ImageView) findViewById(R.id.plCard3);
		plCard4 = (ImageView) findViewById(R.id.plCard4);
		plCards = new ArrayList<ImageView>();
		plCards.add((ImageView) findViewById(R.id.plCard1));
		plCards.add((ImageView) findViewById(R.id.plCard2));
		plCards.add((ImageView) findViewById(R.id.plCard3));
		plCards.add((ImageView) findViewById(R.id.plCard4));

		btnDone = (Button) findViewById(R.id.btnDone);

		mAlertDialog = new AlertDialog.Builder(this).create();

		opCards = new ArrayList<ImageView>();
		opCards.add((ImageView) findViewById(R.id.opCard1));
		opCards.add((ImageView) findViewById(R.id.opCard2));
		opCards.add((ImageView) findViewById(R.id.opCard3));
		opCards.add((ImageView) findViewById(R.id.opCard4));

		midCard = (ImageView) findViewById(R.id.midCard);
		backCardId = R.drawable.back;

		persistence = new PersistenceAdapter(getApplicationContext());
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		gameDifficulty = sharedPrefs.getString("gameDifficulty", "2");

		handler = new Handler();
		
		computerPoints = (TextView) findViewById(R.id.computerPoints);
		myPoints = (TextView) findViewById(R.id.myPoints);
		
		switch ((Game.GAME_STATES) getIntent().getExtras().get(Game.STATE))
		{
		case start:
			//Log.d(TAG, "Ai început un joc nou");
			game = new Game(gameDifficulty);
			game.addObserver(this);
			game.init();
			break;
		case resume:
			//Log.d(TAG, "Ai reluat jocul anterior");
			game = (Game) persistence.getSavedGameData();
			game.createNewHandler();
			game.addObserver(this);
			bindGraphicComponents();
			redraw();
			break;
		}

		// Register the callback.
		plCard1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Log.i(TAG, "ai apăsat cartea pl1");
				game.playCard((ICard) plCard1.getTag());
			}
		});

		plCard2.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Log.i(TAG, "ai apăsat cartea pl2");
				game.playCard((ICard) plCard2.getTag());
			}
		});

		plCard3.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Log.i(TAG, "ai apăsat cartea pl3");
				game.playCard((ICard) plCard3.getTag());
			}
		});

		plCard4.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				//Log.i(TAG, "ai apăsat cartea pl4");
				game.playCard((ICard) plCard4.getTag());
			}
		});

		btnDone.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				game.clickDoneButton();
				btnDone.setVisibility(View.INVISIBLE);
			}
		});

	}

	/*
	 * This is called after onStart() or when the activity is resumed from a
	 * paused state (e.g., the screen is unlocked).
	 */
	@Override
	protected void onResume()
	{
		super.onResume();

		//Log.d(TAG, "onResume in SepticaActivity");

	}

	/*
	 * This is called when the activity enters the paused state. It might be the
	 * last notification we receive, as the Android system might decide to
	 * silently kill our application. We should thus save all state we want to
	 * persist in this method!
	 */
	@Override
	protected void onPause()
	{
		super.onPause();

		//Log.d(TAG, "onPause in SepticaActivity");
		
		// Make sure that the model has completed running all methods and 
		// then and only then save the game state.
		handler.postDelayed(new Runnable()
		{
			public void run()
			{
				persistence.setSavedGameData(game);
			}
		}, 1000);
	}

	public void update(Observable observable, Object data)
	{
		//Log.d(TAG, "ești în metoda update()");

		game = (Game) data;

		bindGraphicComponents();
		redraw();

		if (game.getState() == Game.GameState.END)
		{
			showEndGameDialog(game.getMyPoints(), game.getOpPoints());
		}
	}

	private void redrawOpHand()
	{
		for (int i = 0; i < 4; i++)
		{
			opCards.get(i).setVisibility(View.INVISIBLE);
		}

		for (int i = 0; i < game.getOpHand().size(); i++)
		{
			opCards.get(i).setVisibility(View.VISIBLE);
			opCards.get(i).setImageResource(backCardId);
		}
	}

	private void redrawMid()
	{
		if (game.getPile().isEmpty())
		{
			midCard.setVisibility(View.INVISIBLE);
		} else
		{
			midCard.setVisibility(View.VISIBLE);
			midCard.setImageResource(game.getPile()
					.get(game.getPile().size() - 1).getImageResourceId());
		}
		btnDone.setVisibility(View.INVISIBLE);
	}

	private void redraw()
	{
		redrawMyHand();
		redrawOpHand();
		redrawMid();
		updatePointsLabels();
		if (game.getBtnDoneState().equals("VISIBLE"))
			btnDone.setVisibility(View.VISIBLE);

	}

	private void redrawMyHand()
	{
		for (int i = 0; i < 4; i++)
		{
			plCards.get(i).setVisibility(View.INVISIBLE);
		}

		for (int i = 0; i < game.getMyHand().size(); i++)
		{
			plCards.get(i).setVisibility(View.INVISIBLE);
			plCards.get(i).setImageResource(
					game.getMyHand().get(i).getImageResourceId());
			plCards.get(i).setVisibility(View.VISIBLE);
		}
	}

	private void bindGraphicComponents()
	{
		for (int i = 0; i < game.getMyHand().size(); i++)
		{
			plCards.get(i).setTag(game.getMyHand().get(i));
		}
	}

	private void updatePointsLabels()
	{
		labelPoints = getResources().getString(R.string.label_points);
		
		computerPoints.setText(labelPoints + " " + game.getOpPoints());
		myPoints.setText(labelPoints + " " + game.getMyPoints());
	}
	
	private void showEndGameDialog(int myPoints, int opPoints)
	{
		String message = (String) ((myPoints > opPoints) ? (getString(R.string.won_message)
				+ myPoints + getString(R.string.points))
				: ((myPoints < opPoints) ? (getString(R.string.lost_message)
						+ myPoints + getString(R.string.points))
						: (getString(R.string.draw))));

		mAlertDialog.setMessage(message);
		mAlertDialog.setButton("OK", new DialogInterface.OnClickListener()
		{

			public void onClick(DialogInterface dialog, int which)
			{
				PersistenceAdapter.deleteSavedGameData(getApplicationContext());
				//Log.d(TAG, "am șters fișierul");
				setResult(Game.MATCH_OVER);
				finish();
			}
		});

		mAlertDialog.show();

	}
}
