package net.srivastav;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Main extends StateBasedGame
{
	public Main()
	{
		super("Main");
	}

	@Override
	public void initStatesList(GameContainer c) throws SlickException
	{
		addState(new Menu());
	}
	
	public static void main (String [] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new Main());
      container.setDisplayMode(800,600,false);
      container.start();
		} catch (SlickException e) {
      e.printStackTrace();
		}
	}

}
