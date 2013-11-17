package fera.costin.alexandru.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.os.Handler;

import fera.costin.alexandru.ai.AI;
import fera.costin.alexandru.ai.EasyAI;
import fera.costin.alexandru.ai.HardAI;
import fera.costin.alexandru.ai.MediumAI;

/**
 * 
 * @author Alexandru Fera
 * 
 */
public class Game extends Observable implements Serializable
{
	private static final long serialVersionUID = -4338427787075462549L;

	public enum GameDifficulty
	{
		EASY, MEDIUM, HARD
	};

	public static final String STATE = "state";
	public static final int START_GAME = 1;
	public static final int RESUME_GAME = 2;
	public static final int MATCH_OVER = 3;

	public static enum GAME_STATES
	{
		start, resume, end
	}

	public static enum GameState
	{
		MYTURN, COMPTURN, DISTRIBUTE, HANDEND, END
	};

	public static enum GameTurn
	{
		MYTURN, OPTURN
	};

	private CardDeck deck;
	private List<ICard> myHand;
	private List<ICard> opHand;
	private List<ICard> pile;
	private GameState state;
	private int myPoints = 0;
	private int opPoints = 0;

	private List<ICard> myTakenCards;
	private List<ICard> opTakenCards;
	private GameTurn firstTurn;
	private AI mAI;
	private String gameDifficulty;
	private String btnDoneState;

	private transient Handler handler;

	public Game(String gameDifficulty)
	{
		this.gameDifficulty = gameDifficulty;

		deck = new CardDeck();
		myHand = new ArrayList<ICard>();
		opHand = new ArrayList<ICard>();
		pile = new ArrayList<ICard>();
		myTakenCards = new ArrayList<ICard>();
		opTakenCards = new ArrayList<ICard>();
		state = GameState.MYTURN;
		firstTurn = GameTurn.MYTURN;
		mAI = new MediumAI(this);
		btnDoneState = "INVISIBLE";

		handler = new Handler();
		// updateGameModel();
	}

	public void init()
	{
		setGameDifficulty();
		deck.shuffle();
		distributeCards();

		setChanged();
		notifyObservers(this);

		// updateGameModel();
		// redraw();

		double rnd = Math.random();
		if (rnd < 0.5d)
		{
			state = GameState.MYTURN;
			firstTurn = GameTurn.MYTURN;
		} else
		{
			firstTurn = GameTurn.OPTURN;
			state = GameState.COMPTURN;
			processState();
		}
	}

	private void setGameDifficulty()
	{
		if (gameDifficulty.equals("1"))
			mAI = new EasyAI(this);
		else if (gameDifficulty.equals("2"))
			mAI = new MediumAI(this);
		else
			mAI = new HardAI(this);
	}

	private void distributeCards()
	{
		int myhn = 4 - myHand.size();
		int ophn = 4 - opHand.size();
		if (myhn + ophn > deck.cardsLeft())
		{
			myhn = deck.cardsLeft() / 2;
			ophn = deck.cardsLeft() / 2;
		}

		for (int i = 0; i < myhn; i++)
		{
			myHand.add(deck.dealCard());
		}

		for (int j = 0; j < ophn; j++)
		{
			opHand.add(deck.dealCard());
		}
	}

	public void playCard(ICard mCard)
	{
		int index = -1;
		for (int i = 0; i < myHand.size(); i++)
			if (mCard.equals(myHand.get(i)))
			{
				index = i;
				break;
			}
		if (index == -1)
		{
			System.out.println("Nu există cartea!!!");
		} else
		{
			this.putMyCardDown(index);
		}
	}

	private void putMyCardDown(int index)
	{
		if (state == GameState.MYTURN && index < myHand.size() && index >= 0
				&& canPutMyCardDown(index))
		{
			pile.add(myHand.remove(index));
			state = GameState.COMPTURN;
			// updateGameModel();
			setChanged();
			notifyObservers(this);
			handler.postDelayed(new Runnable()
			{

				public void run()
				{
					processState();

				}

			}, 900);
		}
	}

	private void processState()
	{
		switch (state)
		{
		case COMPTURN:
			computerMove();
			break;
		case HANDEND:
			processHandEnd();
			if (deck.cardsLeft() > 0 || myHand.size() > 0 || opHand.size() > 0)
			{
				state = GameState.DISTRIBUTE;
				// redraw update game model?
				handler.postDelayed(new Runnable()
				{
					public void run()
					{
						processState();
					}
				}, 300);
			} else
			{
				// updateGameModel();
				endGame();
			}
			break;
		case DISTRIBUTE:
			distributeCards();
			setChanged();
			notifyObservers(this);
			// updateGameModel();
			if (firstTurn == GameTurn.MYTURN)
			{
				state = GameState.MYTURN;
			} else
			{
				state = GameState.COMPTURN;
				// redraw, update game model
				handler.postDelayed(new Runnable()
				{
					public void run()
					{
						processState();
					}
				}, 400);

			}
			break;
		}
	}

	private void computerMove()
	{
		// The computer is second to move.
		if (firstTurn == GameTurn.MYTURN)
		{
			int opCardIndex = responseComputerMove();
			putOpCardDown(opCardIndex);

			if (pile.size() > 1 && pile.size() % 2 == 0)
			{
				ICard topCard = pile.get(pile.size() - 1);
				ICard baseCard = pile.get(0);
				if ((topCard.getValue() == baseCard.getValue() || topCard
						.getValue() == '7') && hasContinuation(myHand))
				{
					state = GameState.MYTURN;
					btnDoneState = "VISIBLE";

					setChanged();
					notifyObservers(this);
					// updateGameModel();
					// update game model, redraw?
					// btnDone.setVisibility(View.VISIBLE);
				}
				if (state != GameState.MYTURN)
				{
					state = GameState.HANDEND;
					// update game model, redraw?
					handler.postDelayed(new Runnable()
					{
						public void run()
						{
							processState();
						}

					}, 900);
				}

			}
		} else
		{
			// The computer is first to move.
			if (pile.size() == 0)
			{
				int opCardIndex = firstComputerMove();
				putOpCardDown(opCardIndex);
				state = GameState.MYTURN;
			} else
			{
				int opCardIndex = -1;
				if (canComputerContinue())
				{
					opCardIndex = continuationComputerMove();
				}
				if (opCardIndex >= 0)
				{
					putOpCardDown(opCardIndex);
					state = GameState.MYTURN;
					// updateGameModel();
					// redrawMyHand();
					// redrawOpHand();
				} else
				{
					state = GameState.HANDEND;
					processState();
				}
			}
		}
	}

	private void putOpCardDown(int index)
	{
		pile.add(opHand.remove(index));
		setChanged();
		notifyObservers(this);
	}

	private void processHandEnd()
	{
		if (pile.size() > 1)
		{
			ICard topCard = pile.get(pile.size() - 1);
			ICard baseCard = pile.get(0);
			List<ICard> cards = new ArrayList<ICard>();
			if (topCard.getValue() == baseCard.getValue()
					|| topCard.getValue() == '7')
				cards = ((firstTurn == GameTurn.MYTURN) ? opTakenCards
						: myTakenCards);
			else
				cards = ((firstTurn == GameTurn.MYTURN) ? myTakenCards
						: opTakenCards);
			for (int i = 0; i < pile.size(); i++)
				cards.add(pile.get(i));
			firstTurn = (cards == myTakenCards) ? GameTurn.MYTURN
					: GameTurn.OPTURN;
		}
		pile.clear();
		btnDoneState = "INVISIBLE";
		calculatePoints();
		setChanged();
		notifyObservers(this);
	}

	private void endGame()
	{
		state = GameState.END;
		calculatePoints();

		setChanged();
		notifyObservers(this);

		System.out.println("Ai obținut " + myPoints + " puncte!");

	}

	private void calculatePoints()
	{
		myPoints = 0;
		opPoints = 0;
		
		for (int i = 0; i < myTakenCards.size(); i++)
			if (myTakenCards.get(i).getValue() == 't'
					|| myTakenCards.get(i).getValue() == '1')
				myPoints++;

		for (int i = 0; i < opTakenCards.size(); i++)
			if (opTakenCards.get(i).getValue() == 't'
					|| opTakenCards.get(i).getValue() == '1')
				opPoints++;
	}

	private boolean canComputerContinue()
	{
		if (firstTurn == GameTurn.OPTURN && pile.size() > 1
				&& pile.size() % 2 == 0)
		{
			ICard topCard = pile.get(pile.size() - 1);
			ICard baseCard = pile.get(0);
			if ((topCard.getValue() == baseCard.getValue() || topCard
					.getValue() == '7') && hasContinuation(opHand))
				return true;
		}
		return false;
	}

	private boolean canPutMyCardDown(int index)
	{
		if (pile.size() == 0 || firstTurn == GameTurn.OPTURN)
			return true;
		ICard baseCard = pile.get(0);
		ICard myCard = myHand.get(index);
		if (myCard.getValue() == '7'
				|| myCard.getValue() == baseCard.getValue())
			return true;
		return false;
	}

	public void clickDoneButton()
	{
		state = GameState.HANDEND;

		// update game model, redraw?
		handler.postDelayed(new Runnable()
		{
			public void run()
			{
				processState();
			}

		}, 200);
	}

	private int continuationComputerMove()
	{
		return opHand.indexOf(mAI.continuationMove());
	}

	private int firstComputerMove()
	{
		return opHand.indexOf(mAI.firstMove());
	}

	private boolean hasContinuation(List<ICard> cards)
	{
		if (pile.size() > 0)
		{
			ICard baseCard = pile.get(0);
			for (int i = 0; i < cards.size(); i++)
				if (cards.get(i).getValue() == '7'
						|| cards.get(i).getValue() == baseCard.getValue())
					return true;
			return false;
		} else
			return true;
	}

	private int responseComputerMove()
	{
		return opHand.indexOf(mAI.responseMove());
	}

	public List<ICard> getPile()
	{
		return pile;
	}

	public List<ICard> getOpHand()
	{
		return opHand;
	}

	public List<ICard> getMyHand()
	{
		return myHand;
	}

	/**
	 * @return the deck
	 */
	public CardDeck getDeck()
	{
		return deck;
	}

	/**
	 * @return the state
	 */
	public GameState getState()
	{
		return state;
	}

	/**
	 * @return the myPoints
	 */
	public int getMyPoints()
	{
		return myPoints;
	}

	/**
	 * @return the opPoints
	 */
	public int getOpPoints()
	{
		return opPoints;
	}

	/**
	 * @return the btnDoneState
	 */
	public String getBtnDoneState()
	{
		return btnDoneState;
	}

	public void createNewHandler()
	{
		handler = new Handler();
	}

}