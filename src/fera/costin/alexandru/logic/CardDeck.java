package fera.costin.alexandru.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 
 * @author Alexandru Fera
 * 
 */
public final class CardDeck implements Serializable
{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -2722815733284738808L;
	private List<ICard> cardList;
	private char[] suits = { ICard.CLUBS, ICard.SPADES, ICard.HEARTS,
			ICard.DIAMONDS };
	private char[] values = { '7', '8', '9', ICard.TEN, ICard.JACK,
			ICard.QUEEN, ICard.KING, ICard.ACE };

	/**
	 * Constructor. Create a deck of cards, for the game șeptică, which has 32
	 * cards ranging from 7 to K.
	 */
	public CardDeck()
	{
		cardList = new ArrayList<ICard>();

		for (char suit : suits)
		{
			for (char value : values)
			{
				cardList.add(new Card(suit, value));
			}
		}
	}
	

	/**
	 * Shuffle the deck of cards into a random order.
	 */
	public void shuffle()
	{
		Collections.shuffle(cardList);
	}

	/**
	 * 
	 * @return The number of cards that are still left in the deck.
	 */
	public int cardsLeft()
	{
		return cardList.size();
	}

	/**
	 * 
	 * @return Whether the deck is empty or not.
	 */
	public boolean isEmpty()
	{
		return cardList.size() == 0;
	}

	/**
	 * Deals one card from the deck and removes it. If there is no card in the
	 * deck it returns null.
	 * 
	 * @return An object of the type Card.
	 */
	public ICard dealCard()
	{
		ICard card = null;

		try
		{
			card = cardList.remove(0);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Numai sunt cărți în pachet");
			e.printStackTrace();
		}

		return card;
	}
	
	public ICard peek(int i)
	{
		return cardList.get(i);
	}

}
