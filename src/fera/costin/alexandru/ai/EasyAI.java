package fera.costin.alexandru.ai;

import fera.costin.alexandru.logic.Game;
import fera.costin.alexandru.logic.ICard;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class EasyAI extends BaseAI implements AI
{
	private static final long serialVersionUID = -6703776725879641629L;

	public EasyAI(Game mGame)
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
			if (c.getValue() != '7')
				return c;

		return opHand.get(0);
	}

	public ICard responseMove()
	{
		ICard baseCard = pile.get(0);
		boolean continuation = (pile.size() > 2);

		if (continuation)
		{
			ICard cmv = continuationMove();
			if (cmv != null)
				return cmv;

		} else
		{
			int pc7 = 0;
			int pcbase = 0;

			for (ICard c : opHand)
				if (c.getValue() == '7')
					pc7++;
				else if (c.getValue() == baseCard.getValue())
					pcbase++;

			if (pcbase > 0)
			{
				for (ICard c : opHand)
					if (c.getValue() == baseCard.getValue())
						return c;
			} else
			{
				int takesnr = pcbase + pc7;
				double rnd = Math.random();
				if ((baseCard.getValue() == 't' || baseCard.getValue() == '1')
						&& takesnr > (rnd < 0.6d ? 1 : 0))
				{
					for (ICard c : opHand)
						if (c.getValue() == baseCard.getValue()
								|| c.getValue() == '7')
							return c;
				}
			}
		}

		for (ICard c : opHand)
			if (c.getValue() != '7')
				return c;
		return opHand.get(0);
	}
}
