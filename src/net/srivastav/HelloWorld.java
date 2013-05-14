package net.srivastav;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class HelloWorld extends BasicGame {

	public HelloWorld()
	{
		super("Hello World!");
	}
	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("Hello World!", 100, 100);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}
	
	public static void main (String [] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new HelloWorld());
		
		app.setDisplayMode(800, 600, false);
		app.start();
	}

}
