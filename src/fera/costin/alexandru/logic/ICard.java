package fera.costin.alexandru.logic;

import java.io.Serializable;

/**
 * This interface specifies the behavior of a playing card.
 * 
 * @author Alexandru Fera
 * 
 */

public interface ICard extends Serializable
{

	public static final char SPADES = 's';
	public static final char HEARTS = 'h';
	public static final char DIAMONDS = 'd';
	public static final char CLUBS = 'c';
	public static final char JACK = 'j';
	public static final char QUEEN = 'q';
	public static final char KING = 'k';
	public static final char TEN = 't';
	public static final char ACE = '1';

	public char getSuit();

	public char getValue();
	
	public int getImageResourceId();
	
	public Integer getIntegerValue();

}