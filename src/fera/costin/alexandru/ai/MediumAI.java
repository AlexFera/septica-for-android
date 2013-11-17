package fera.costin.alexandru.ai;

import fera.costin.alexandru.logic.Game;
import fera.costin.alexandru.logic.ICard;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class MediumAI extends BaseAI implements AI
{
	private static final long serialVersionUID = 5595996829657463316L;

	public MediumAI(Game mGame)
	{
		super(mGame);
	}

	public ICard firstMove()
	{
		int pc10 = 0, pc11 = 0, pc7 = 0;
		for (ICard c : opHand)
			if (c.getValue() == '7')
				pc7++;
			else if (c.getValue() == 't')
				pc10++;
			else if (c.getValue() == '1')
				pc11++;

		boolean put10 = (pc10 > 0 && pc10 + pc7 > 1);
		boolean put11 = (pc11 > 0 && pc11 + pc7 > 1);

		for (ICard c : opHand)
			if (put10 && c.getValue() == 't')
				return c;
			else if (put11 & c.getValue() == '1')
				return c;

		for (ICard c : opHand)
			if (c.getValue() != '7' && c.getValue() != 't'
					&& c.getValue() != '1')
				return c;

		for (ICard c : opHand)
			if (c.getValue() != '7')
				return c;

		return opHand.get(opHand.size() - 1);
	}

	public ICard responseMove()
	{
		ICard baseCard = pile.get(0);
		boolean continuation = (pile.size() > 2);

		int pc10 = 0;
		int pc11 = 0;
		int pc7 = 0;
		int pcbase = 0;

		for (ICard c : opHand)
			if (c.getValue() == '7')
				pc7++;
			else if (c.getValue() == 't')
				pc10++;
			else if (c.getValue() == '1')
				pc11++;
			else if (c.getValue() == baseCard.getValue())
				pcbase++;

		boolean take10 = (baseCard.getValue() == 't' && pc10 + pc7 > (continuation ? 0
				: 1));
		boolean take11 = (baseCard.getValue() == '1' && pc11 + pc7 > (continuation ? 0
				: 1));

		for (ICard c : opHand)
			if (take10 && c.getValue() == 't')
				return c;
			else if (take11 && c.getValue() == '1')
				return c;
		for (ICard c : opHand)
			if ((take10 || take11) && c.getValue() == '7')
				return c;

		if (pcbase > 0 && baseCard.getValue() != 't'
				&& baseCard.getValue() != '1')
		{
			for (ICard c : opHand)
				if (c.getValue() == baseCard.getValue())
					return c;
		}

		for (ICard c : opHand)
			if (c.getValue() != '7' && c.getValue() != 't'
					&& c.getValue() != '1')
				return c;
		for (ICard c : opHand)
			if (c.getValue() != '7')
				return c;

		return opHand.get(opHand.size() - 1);
	}

}
