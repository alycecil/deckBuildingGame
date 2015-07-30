package com.wcecil.beans.gameobjects;

import groovy.transform.CompileStatic;

import java.util.List;

import com.wcecil.beans.GameState;

@CompileStatic
public interface Card {

	public abstract def specialAction(GameState game);

	public abstract Integer getCost();

	public abstract Integer getMoney();

	public abstract Integer getDraw();

	public abstract Integer getValue();

	public abstract String getName();

	public abstract String getDescription();

	public abstract String getSpecialActionScript();

	public abstract Integer getStartingCount();
	
	public abstract Integer getStaticCount();

	public abstract Integer getDeckCount();

	public abstract void setCost(Integer cost);

	public abstract void setMoney(Integer money);

	public abstract void setDraw(Integer draw);

	public abstract void setValue(Integer value);

	public abstract void setName(String name);

	public abstract void setDescription(String description);

	public abstract void setSpecialActionScript(String specialActionScript);

	public abstract void setStartingCount(Integer startingCount);

	public abstract void setStaticCount(Integer staticCount);
	
	public abstract void setDeckCount(Integer deckCount);

}