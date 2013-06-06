package net.srivastav.states;

import net.srivastav.Constants;
import net.srivastav.Fonts;
import net.srivastav.Singleton;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.*;
import java.awt.Rectangle;

public class Levels extends BasicGameState 
{
	UnicodeFont buttonFont;
	Rectangle[] buttons = new Rectangle[3];
	boolean[] hovers = new boolean[buttons.length];
	String[] buttonLabels = {"Easy", "Medium", "Hard"};

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		buttonFont = Fonts.getRetroFont(java.awt.Color.black, 40);
		int xStart = gc.getWidth() / 4;
		int width = gc.getWidth() / 2;
		int height = gc.getHeight() / 6;
		int h = gc.getHeight();
		buttons[0] = new Rectangle(xStart, h / 8, width, height);
		buttons[1] = new Rectangle(xStart, h * 3 / 8, width, height);
		buttons[2] = new Rectangle(xStart, h * 5 / 8, width, height);
		for (int i = 0; i < hovers.length; ++i)
		{
			hovers[i] = false;
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		Rectangle b;
		for (int i = 0; i < buttons.length; ++i)
		{
			b = buttons[i];
			if (hovers[i])
				g.setColor(Constants.buttonHoverColor);
			else
				g.setColor(Constants.buttonDefaultColor);
			g.fillRoundRect(b.x, b.y, b.width, b.height, 40);
		}
		g.setFont(buttonFont);
		for (int i = 0; i < buttons.length; ++i)
		{
			g.drawString(buttonLabels[i], 
					buttons[i].x + buttons[i].width / 2 - g.getFont().getWidth(buttonLabels[i]) / 2,
					buttons[i].y + buttons[i].height / 2 - g.getFont().getHeight(buttonLabels[i]) / 2);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		MouseOverArea[] MOAS = new MouseOverArea[3];
		Rectangle b;
		for (int i = 0; i < MOAS.length; ++i)
		{
			b = buttons[i];
			MOAS[i] = new MouseOverArea(gc, null, b.x, b.y, b.width, b.height);
		}
		
		Input in = gc.getInput();
		
		if (MOAS[0].isMouseOver())
		{
			hovers[0] = true;
			if (in.isMouseButtonDown(0))
			{
				launch(sbg, 0);
			}
		}
		else
		{
			hovers[0] = false;
		}
		
		if (MOAS[1].isMouseOver())
		{
			hovers[1] = true;
			if (in.isMouseButtonDown(0))
			{
				launch(sbg, 1);
			}
		}
		else
		{
			hovers[1] = false;
		}
		
		if (MOAS[2].isMouseOver())
		{
			hovers[2] = true;
			if (in.isMouseButtonDown(0))
			{
				launch(sbg, 2);
			}
		}
		else
		{
			hovers[2] = false;
		}
		
	}
	
	private void launch(StateBasedGame sbg, int diff)
	{
		Singleton.difficulty = diff;
		sbg.enterState(GameStates.IN_GAME.ordinal());
	}

	@Override
	public int getID()
	{
		return GameStates.LEVELS.ordinal();
	}

}
