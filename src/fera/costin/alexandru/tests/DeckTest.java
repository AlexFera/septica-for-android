package fera.costin.alexandru.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fera.costin.alexandru.logic.Card;
import fera.costin.alexandru.logic.CardDeck;
import fera.costin.alexandru.logic.ICard;

/**
 * @author Alexandru Fera
 * 
 */
public class DeckTest {

	/**
	 * Test method for {@link șeptică.CardDeck#shuffle()}.
	 */
	@Test
	public void testShuffle() {
		CardDeck deck = new CardDeck();
		deck.shuffle();
		ArrayList<ICard> cards = new ArrayList<ICard>();
		boolean isSorted = true;

		while (deck.cardsLeft() > 0) {
			cards.add(deck.dealCard());
		}

		for (int i = 1; i < cards.size(); i+=2) {
			if (cards.get(i-1).compareTo(cards.get(i)) > 0) {
				isSorted = false;
				break;
			}
		}
		assertFalse(isSorted);
	}

	/**
	 * Test method for {@link șeptică.CardDeck#cardsLeft()}.
	 */
	@Test
	public void testCardsLeft() {
		int expected = 30;

		CardDeck deck = new CardDeck();
		deck.shuffle();
		deck.dealCard();
		deck.dealCard();

		assertEquals(expected, deck.cardsLeft());
	}

	/**
	 * Test method for {@link șeptică.CardDeck#dealCard()}.
	 */
	@Test
	public void testDealCard() {
		int expected = 31;
		CardDeck deck = new CardDeck();
		ICard c2 = new Card(7, ICard.SPADES);

		ICard c1 = deck.dealCard();

		assertEquals(c1, c2);
		assertEquals(expected, deck.cardsLeft());
	}

}
