package fera.costin.alexandru.ai;

import fera.costin.alexandru.logic.Game;
import fera.costin.alexandru.logic.ICard;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class HardAI extends BaseAI implements AI
{
	private static final long serialVersionUID = 4611029703019334439L;

	public HardAI(Game mGame)
	{
		super(mGame);
	}

	public ICard firstMove()
	{
		int pc10 = 0;
		int pc11 = 0;
		int pc7 = 0;

		for (ICard c : opHand)
			if (c.getValue() == '7')
				pc7++;
			else if (c.getValue() == 't')
				pc10++;
			else if (c.getValue() == '1')
				pc11++;

		boolean put10, put11;
		if (pile.size() <= 4)
		{
			int p10hit = 0, p11hit = 0;
			for (ICard c : myHand)
				if (c.getValue() == '7')
				{
					p10hit++;
					p11hit++;
				} else if (c.getValue() == 't')
					p10hit++;
				else if (c.getValue() == '1')
					p11hit++;
			put10 = (pc10 > 0 && pc10 + pc7 > p10hit);
			put11 = (pc11 > 0 && pc11 + pc7 > p11hit);
		} else
		{
			double rnd = Math.random();
			put10 = (pc10 > 0 && pc10 + pc7 > (rnd < 0.4d ? 1 : 2));
			put11 = (pc11 > 0 && pc11 + pc7 > (rnd < 0.4d ? 1 : 2));
		}

		for (ICard c : opHand)
			if (put10 && c.getValue() == 't')
				return c;
			else if (put11 && c.getValue() == '1')
				return c;

		for (ICard c : opHand)
			if (c.getValue() != '7' && c.getValue() != 't'
					&& c.getValue() != '1')
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

			boolean take10, take11;
			if (pile.size() <= 4)
			{
				int p10hit = 0, p11hit = 0;
				for (ICard c : myHand)
					if (c.getValue() == '7')
					{
						p10hit++;
						p11hit++;
					} else if (c.getValue() == 't')
						p10hit++;
					else if (c.getValue() == '1')
						p11hit++;
				take10 = (baseCard.getValue() == 't' && pc10 + pc7 > p10hit);
				take11 = (baseCard.getValue() == '1' && pc11 + pc7 > p11hit);
			} else
			{
				double rnd = Math.random();
				take10 = (baseCard.getValue() == 't' && pc10 + pc7 > (rnd < 0.6d ? 1
						: 2));
				take11 = (baseCard.getValue() == '1' && pc11 + pc7 > (rnd < 0.6d ? 1
						: 2));
			}

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
		}

		for (ICard c : opHand)
			if (c.getValue() != '7' && c.getValue() != 't'
					&& c.getValue() != '1')
				return c;
		return opHand.get(0);
	}
}
