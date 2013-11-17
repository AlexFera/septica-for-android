package fera.costin.alexandru.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fera.costin.alexandru.logic.Card;
import fera.costin.alexandru.logic.CardDeck;
import fera.costin.alexandru.logic.ICard;
import fera.costin.alexandru.logic.Player;

/**
 * @author Alexandru Fera
 * 
 */
public class PlayerTest {

	/**
	 * Test method for {@link șeptică.Player#getNumberOfPoints()}.
	 */
	@Test
	public void testGetNumberOfPoints() {
		ArrayList<ICard> cards = new ArrayList<ICard>();
		cards.add(new Card(10, ICard.DIAMONDS));
		cards.add(new Card(8, ICard.CLUBS));
		cards.add(new Card(14, ICard.HEARTS));
		cards.add(new Card(11, ICard.HEARTS));

		Player p1 = new Player("Ion");
		p1.collectCards(cards);

		assertEquals(2, p1.getNumberOfPoints());
	}

	/**
	 * Test method for {@link șeptică.Player#collectCards(java.util.ArrayList)}.
	 */
	@Test
	public void testCollectCards() {
		Player p1 = new Player("Ion");
		ArrayList<ICard> cards = new ArrayList<ICard>();
		cards.add(new Card(10, ICard.DIAMONDS));
		cards.add(new Card(8, ICard.CLUBS));
		cards.add(new Card(14, ICard.HEARTS));
		cards.add(new Card(11, ICard.HEARTS));

		p1.collectCards(cards);

		assertEquals(4, p1.getNumTakenCards());
	}

	/**
	 * Test method for {@link șeptică.Player#playCard()}.
	 */
	@Test
	public void testPlayCard() {
		CardDeck deck = new CardDeck();

		Player p1 = new Player("Ion");
		p1.takeCard(deck.dealCard());

		ICard c = p1.playCard();

		assertEquals(c, new Card(7, ICard.SPADES));
		assertEquals(0, p1.getNumCards());
	}

	/**
	 * Test method for {@link șeptică.Player#requestCard(șeptică.CardDeck)}.
	 */
	@Test
	public void testRequestCard() {
		CardDeck deck = new CardDeck();

		Player p1 = new Player("Ion");

		assertEquals(0, p1.getNumCards());
		p1.takeCard(deck.dealCard());
		assertEquals(1, p1.getNumCards());
	}

	/**
	 * Test method for {@link șeptică.Player#hasACut(șeptică.ICard)}.
	 */
	@Test
	public void testHasACut() {
		Player p1 = new Player("Ion");
		// 12 and 7 are cuts here
		p1.takeCard(new Card(12, ICard.HEARTS));
		p1.takeCard(new Card(10, ICard.DIAMONDS));
		p1.takeCard(new Card(10, ICard.SPADES));
		p1.takeCard(new Card(7, ICard.SPADES));
		assertTrue(p1.hasACut(new Card(12, ICard.CLUBS)));

		// 7 is the only cut
		Player p2 = new Player("Leana");
		p2.takeCard(new Card(10, ICard.SPADES));
		p2.takeCard(new Card(14, ICard.DIAMONDS));
		p2.takeCard(new Card(14, ICard.HEARTS));
		p2.takeCard(new Card(7, ICard.CLUBS));
		assertTrue(p2.hasACut(new Card(11, ICard.DIAMONDS)));

		// there is no cut here
		Player p3 = new Player("Costel");
		p3.takeCard(new Card(10, ICard.SPADES));
		p3.takeCard(new Card(13, ICard.DIAMONDS));
		p3.takeCard(new Card(11, ICard.HEARTS));
		p3.takeCard(new Card(9, ICard.CLUBS));
		assertFalse(p3.hasACut(new Card(8, ICard.DIAMONDS)));
	}

	/**
	 * Test method for {@link șeptică.Player#getTheCut(șeptică.ICard)}.
	 */
	@Test
	public void testGetTheCut() {
		Player p1 = new Player("Ion");
		// 12 is the cut here
		p1.takeCard(new Card(12, ICard.HEARTS));
		p1.takeCard(new Card(10, ICard.DIAMONDS));
		p1.takeCard(new Card(10, ICard.SPADES));
		p1.takeCard(new Card(8, ICard.SPADES));
		
		ICard c1 = new Card(12, ICard.HEARTS);
		ICard c2 = p1.getTheCut(c1);
		
		assertEquals(c1, c2);
	}

}
