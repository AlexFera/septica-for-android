package fera.costin.alexandru.logic;

import java.io.Serializable;
import java.lang.reflect.Field;

import fera.costin.alexandru.R;

/**
 * 
 * @author Alexandru Fera An object of the type Card represents a playing card
 *         from a deck with only 32 cards. Each card has four suits: spades,
 *         hearts, diamonds, clubs and each suit has the values: 7, 8, 9, 10,
 *         ace, jack, queen, king. Note ace has the value 11.
 * 
 * 
 */
public class Card implements ICard, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7808138213617063077L;
	private char suit;
	private char value;

	/**
	 * Creates a card with the specified rank and suit.
	 * 
	 * @param suit
	 *            the suit of the new card. This must be one of the values
	 *            ICard.SPADES, ICard.HEARTS, ICard.DIAMONDS, ICard.CLUBS.
	 * @param value
	 *            the rank of the new card.
	 */
	public Card(char suit, char value)
	{
		this.suit = suit;
		this.value = value;
	}

	/**
	 * Returns the suit of this card.
	 * 
	 * @return the suit, which is one of the constants ICard.SPADES,
	 *         ICard.HEARTS, ICard.DIAMONDS, ICard.CLUBS.
	 */

	public char getSuit()
	{
		return suit;
	}

	/**
	 * Returns the value of this card.
	 * 
	 * @return the value of the card.
	 */

	public char getValue()
	{
		return value;
	}

	/**
	 * Returns the string representation of this card, including both its suit
	 * and its rank.
	 * 
	 * @return the string representation of a card
	 */
	public String toString()
	{
		return new String(new char[] { getSuit(), getValue() });
	}

	public String getPngName()
	{
		return new String(new char[] { suit, value });
	}

	public int getImageResourceId()
	{
		Field f;
		int id = -1;
		try
		{
			f = R.drawable.class.getDeclaredField(getPngName());
			id = f.getInt(null);
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public boolean equals(Object obj){
		if( (this instanceof ICard && this != null) && (obj instanceof ICard && obj != null )){
			boolean hasTheSameSuit = this.getSuit() == ((ICard) obj).getSuit()? true : false;
			boolean hasTheSameValue = this.getIntegerValue() == ((ICard) obj).getIntegerValue()? true : false;
			
			if(hasTheSameSuit && hasTheSameValue){
				return true;
			}
		}
		return false;
	}
	
	public Integer getIntegerValue()
	{
		Integer retVal = Character.getNumericValue(this.value);
		switch (value) {
		case TEN:
			retVal = 10;
			break;
		case JACK:
			retVal = 11;
			break;
		case QUEEN:
			retVal = 12;
			break;
		case KING:
			retVal = 13;
			break;
		default:
			break;
		}

		return retVal;
	}

}
