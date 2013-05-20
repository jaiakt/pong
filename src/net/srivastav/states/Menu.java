package net.srivastav.states;


import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.*;
import java.awt.Font;
import org.newdawn.slick.gui.*;

public class Menu extends BasicGameState
{
	Image starTileImage = null;
	Image titleImage = null;
	boolean startButtonHover;
	boolean exitButtonHover;
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException
	{
		starTileImage = new Image("assets/starTile.gif");
		titleImage = new Image("assets/title.png");
		startButtonHover = false;
		exitButtonHover = false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException
	{
		UnicodeFont font = new UnicodeFont(new java.awt.Font ("Verdana", Font.BOLD, 40));
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addNeheGlyphs();
		font.loadGlyphs();
		g.setFont(font);
		// Draw Background
		for (int x = 0; x < gc.getWidth(); x += starTileImage.getWidth())
		{
			for (int y = 0; y < gc.getHeight(); y += starTileImage.getHeight())
			{
				g.drawImage(starTileImage, x, y);
			}
		}

		// Draw Title
		g.drawImage(titleImage,
				(gc.getWidth() / 2) - (titleImage.getWidth() / 2),
				gc.getHeight() / 20);

		final int startButtonXStart = gc.getWidth() / 8;
		final int startButtonYStart = gc.getHeight() / 2;
		final int startButtonXEnd = gc.getWidth() / 8 * 3;
		final int startButtonYEnd = gc.getHeight() / 8 * 6;

		final int exitButtonXStart = gc.getWidth() / 8 * 5;
		final int exitButtonYStart = gc.getHeight() / 2;
		final int exitButtonXEnd = gc.getWidth() / 8 * 7;
		final int exitButtonYEnd = gc.getHeight() / 8 * 6;
		
		

		// Draw Buttons
		final Color buttonDefaultColor = new Color(97, 252, 0);
		final Color buttonHoverColor = new Color(29, 252, 0);
		
		if (startButtonHover)
			g.setColor(buttonHoverColor);
		else
			g.setColor(buttonDefaultColor);
		
		g.fillRoundRect(
				startButtonXStart, // x start
				startButtonYStart, // y start
				startButtonXEnd - startButtonXStart, // width
				startButtonYEnd - startButtonYStart, // height
				40
		); 

		if (exitButtonHover)
			g.setColor(buttonHoverColor);
		else
			g.setColor(buttonDefaultColor);
		
		g.fillRoundRect(
				exitButtonXStart, // x start
				exitButtonYStart, // y start
				exitButtonXEnd - exitButtonXStart, // width
				exitButtonYEnd - exitButtonYStart, // height
				40
		); 

		g.setColor(Color.black);

		final int xOffset = 0;
		final int yOffset = -5;
		
		g.drawString("Play",
				((startButtonXStart + startButtonXEnd) / 2) - (g.getFont().getWidth("Play") / 2) + xOffset,
				((startButtonYStart + startButtonYEnd) / 2) - (g.getFont().getHeight("Play") / 2) + yOffset);
		g.drawString("Exit",
				((exitButtonXStart + exitButtonXEnd) / 2) - (g.getFont().getWidth("Exit") / 2) + xOffset,
				((exitButtonYStart + exitButtonYEnd) / 2) - (g.getFont().getHeight("Exit") / 2) + yOffset);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException
	{
		final int startButtonXStart = gc.getWidth() / 8;
		final int startButtonYStart = gc.getHeight() / 2;
		final int startButtonXEnd = gc.getWidth() / 8 * 3;
		final int startButtonYEnd = gc.getHeight() / 8 * 6;

		final int exitButtonXStart = gc.getWidth() / 8 * 5;
		final int exitButtonYStart = gc.getHeight() / 2;
		final int exitButtonXEnd = gc.getWidth() / 8 * 7;
		final int exitButtonYEnd = gc.getHeight() / 8 * 6;
		
		MouseOverArea startMouseOverArea = new MouseOverArea(
				gc, null,
				startButtonXStart,
				startButtonYStart,
				startButtonXEnd - startButtonXStart,
				startButtonYEnd - startButtonYStart
		);
		
		MouseOverArea exitMouseOverArea = new MouseOverArea(
				gc, null,
				exitButtonXStart,
				exitButtonYStart,
				exitButtonXEnd - exitButtonXStart,
				exitButtonYEnd - exitButtonYStart
		);

		Input in = gc.getInput();

		if (startMouseOverArea.isMouseOver())
		{
			startButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				sbg.enterState(GameStates.IN_GAME.ordinal());
			}
		}
		else
			startButtonHover = false;
		if (exitMouseOverArea.isMouseOver())
		{
			exitButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				gc.exit();
			}
		}
		else
			exitButtonHover = false;

	}

	@Override
	public int getID()
	{
		return GameStates.MENU.ordinal();
	}

}
