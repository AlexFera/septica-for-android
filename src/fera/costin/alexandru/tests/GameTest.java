package fera.costing.alexandru.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fera.costin.alexandru.logic.Card;
import fera.costin.alexandru.logic.ICard;
import fera.costin.alexandru.logic.Player;

public class GameTest {

	@Test
	public void testPlay() {
		List<ICard> deck = new ArrayList<ICard>();
		deck.add(new Card(10, ICard.CLUBS));
		deck.add(new Card(7, ICard.CLUBS));
		deck.add(new Card(10, ICard.DIAMONDS));
		deck.add(new Card(7, ICard.DIAMONDS));
		deck.add(new Card(10, ICard.HEARTS));
		deck.add(new Card(7, ICard.HEARTS));
		deck.add(new Card(10, ICard.SPADES));
		deck.add(new Card(8, ICard.SPADES));

		Player p1 = new Player("Leana");
		Player p2 = new Player("Costel");

		List<ICard> gamePile;

		ICard firstCard;
		ICard secondCard;

		gamePile = new ArrayList<ICard>();

		p1.takeCard(deck.remove(0));
		p1.takeCard(deck.remove(0));
		p1.takeCard(deck.remove(0));
		p1.takeCard(deck.remove(0));

		p2.takeCard(deck.remove(0));
		p2.takeCard(deck.remove(0));
		p2.takeCard(deck.remove(0));
		p2.takeCard(deck.remove(0));

		firstCard = p1.playCard();
		secondCard = p2.playCard();

		gamePile.add(firstCard);
		gamePile.add(secondCard);

		System.out.println("Jucătorul " + p1.getName() + " a jucat cartea "
				+ firstCard);
		System.out.println("Jucătorul " + p2.getName() + " a jucat cartea "
				+ secondCard);

		if ((secondCard.getRank() == firstCard.getRank() || secondCard
				.getRank() == 7)) {
			if (!p1.hasACut(firstCard)) {
				for (ICard c : gamePile) {
					System.out.print(c + " ");
					System.out.println();
				}
				System.out.println("Jucătorul " + p2.getName() + " a câștigat");
				p2.collectCards(gamePile);
				p2.setFirst(true);
				p1.setFirst(false);
			} else {
				ICard c1 = p1.getTheCut(firstCard);
				if (!p2.hasACut(firstCard)) {
					ICard c2 = p2.playCard();
					System.out.println("Jucătorul " + p1.getName()
							+ " a jucat cartea " + c1);
					System.out.println("Jucătorul " + p2.getName()
							+ " a jucat cartea " + c2);
					System.out.println("Jucătorul " + p1.getName()
							+ " a câștigat");
					gamePile.add(c1);
					gamePile.add(c2);
					for (ICard c : gamePile) {
						System.out.print(c + " ");
						System.out.println();
					}
					p1.collectCards(gamePile);
					p1.setFirst(true);
					p2.setFirst(false);
				} else {
					ICard c2 = p2.getTheCut(firstCard);
					if (!p1.hasACut(firstCard)) {
						System.out.println("Jucătorul " + p1.getName()
								+ " a jucat cartea " + c1);
						System.out.println("Jucătorul " + p2.getName()
								+ " a jucat cartea " + c2);
						System.out.println("Jucătorul " + p2.getName()
								+ " a câștigat");
						gamePile.add(c1);
						gamePile.add(c2);
						for (ICard c : gamePile) {
							System.out.print(c + " ");
							System.out.println();
						}
						p2.collectCards(gamePile);
						p2.setFirst(true);
						p1.setFirst(false);
					} else {
						System.out.println("Jucătorul " + p1.getName()
								+ " a jucat cartea " + c1);
						System.out.println("Jucătorul " + p2.getName()
								+ " a jucat cartea " + c2);
						gamePile.add(c1);
						gamePile.add(c2);
						c1 = p1.getTheCut(firstCard);
						if (!p2.hasACut(firstCard)) {
							c2 = p2.playCard();
							System.out.println("Jucătorul " + p1.getName()
									+ " a jucat cartea " + c1);
							System.out.println("Jucătorul " + p2.getName()
									+ " a jucat cartea " + c2);
							System.out.println("Jucătorul " + p1.getName()
									+ " a câștigat");
							gamePile.add(c1);
							gamePile.add(c2);
							for (ICard c : gamePile) {
								System.out.print(c + " ");
								System.out.println();
							}
							p1.collectCards(gamePile);
							p1.setFirst(true);
							p1.setFirst(false);
						} else {
							c2 = p2.getTheCut(firstCard);
							if (!p1.hasACut(firstCard)) {
								System.out.println("Jucătorul " + p1.getName()
										+ " a jucat cartea " + c1);
								System.out.println("Jucătorul " + p2.getName()
										+ " a jucat cartea " + c2);
								System.out.println("Jucătorul " + p2.getName()
										+ " a câștigat");
								gamePile.add(c1);
								gamePile.add(c2);
								for (ICard c : gamePile) {
									System.out.print(c + " ");
									System.out.println();
								}
								p2.collectCards(gamePile);
								p2.setFirst(true);
								p1.setFirst(false);
							} else {
								System.out.println("Jucătorul " + p1.getName()
										+ " a jucat cartea " + c1);
								System.out.println("Jucătorul " + p2.getName()
										+ " a jucat cartea " + c2);
								gamePile.add(c1);
								gamePile.add(c2);
								c1 = p1.getTheCut(firstCard);
								if (!p2.hasACut(firstCard)) {
									c2 = p2.playCard();
									System.out.println("Jucătorul "
											+ p1.getName() + " a jucat cartea "
											+ c1);
									System.out.println("Jucătorul "
											+ p2.getName() + " a jucat cartea "
											+ c2);
									System.out.println("Jucătorul "
											+ p1.getName() + " a câștigat");
									gamePile.add(c1);
									gamePile.add(c2);
									for (ICard c : gamePile) {
										System.out.print(c + " ");
										System.out.println();
									}
									p1.collectCards(gamePile);
									p1.setFirst(true);
									p2.setFirst(false);
								} else {
									c2 = p2.getTheCut(firstCard);
									System.out.println("Jucătorul "
											+ p1.getName() + " a jucat cartea "
											+ c1);
									System.out.println("Jucătorul "
											+ p2.getName() + " a jucat cartea "
											+ c2);
									System.out.println("Jucătorul "
											+ p2.getName() + " a câștigat");
									gamePile.add(c1);
									gamePile.add(c2);
									for (ICard c : gamePile) {
										System.out.print(c + " ");
										System.out.println();
									}
									p2.collectCards(gamePile);
									p2.setFirst(true);
									p1.setFirst(false);
								}
							}
						}
					}
				}
			}

		} else {
			System.out.println("Jucătorul " + p1.getName() + " a câștigat");
			for (ICard c : gamePile) {
				System.out.print(c + " ");
				System.out.println();
			}
			p1.collectCards(gamePile);
			p1.setFirst(true);
			p2.setFirst(false);
		}

	}

	@Test
	public void testGetWinner() {
		fail("Not yet implemented"); // TODO
	}

}
