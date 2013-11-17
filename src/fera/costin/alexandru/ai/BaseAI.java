package fera.costin.alexandru.ai;

import java.io.Serializable;
import java.util.List;

import fera.costin.alexandru.logic.Game;
import fera.costin.alexandru.logic.ICard;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class BaseAI implements Serializable
{
	private static final long serialVersionUID = -8446827282289749116L;

	protected List<ICard> pile;
	protected List<ICard> opHand;
	protected List<ICard> myHand;

	public BaseAI(Game mGame)
	{
		pile = mGame.getPile();
		opHand = mGame.getOpHand();
		myHand = mGame.getMyHand();
	}

	public ICard continuationMove()
	{
		ICard baseCard = pile.get(0);
		boolean havePoint = (baseCard.getValue() == 't' || baseCard.getValue() == '1');

		for (ICard c : opHand)
			if (c.getValue() == baseCard.getValue()
					|| (c.getValue() == '7' && havePoint))
				return c;
		return null;
	}

}
