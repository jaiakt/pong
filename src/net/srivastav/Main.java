package net.srivastav;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import net.srivastav.states.*;
import net.srivastav.states.Game;

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
		addState(new Game());
		addState(new GameOver());
		addState(new Settings());
		addState(new Levels());
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
