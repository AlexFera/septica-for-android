package fera.costin.alexandru.ai;

import java.io.Serializable;

import fera.costin.alexandru.logic.ICard;

/**
 * 
 * @author Alexandru Fera
 *
 */
public interface AI extends Serializable
{

	public ICard continuationMove();

	public ICard firstMove();

	public ICard responseMove();

}
