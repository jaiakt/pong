package net.srivastav;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.*;
import java.awt.Font;

public class Menu extends BasicGameState
{
	Image starTileImage = null;
	Image titleImage = null;
	boolean startButtonHover;
	boolean exitButtonHover;
	@Override
	public void init(GameContainer c, StateBasedGame sbg)
	throws SlickException
	{
		starTileImage = new Image("assets/starTile.gif");
		titleImage = new Image("assets/title.png");
		startButtonHover = false;
		exitButtonHover = false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(GameContainer c, StateBasedGame sbg, Graphics g)
	throws SlickException
	{
		UnicodeFont font = new UnicodeFont(new java.awt.Font ("Verdana", Font.BOLD, 40));
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addNeheGlyphs();
		font.loadGlyphs();
		g.setFont(font);
		// Draw Background
		for (int x = 0; x < c.getWidth(); x += starTileImage.getWidth())
		{
			for (int y = 0; y < c.getHeight(); y += starTileImage.getHeight())
			{
				g.drawImage(starTileImage, x, y);
			}
		}

		// Draw Title
		g.drawImage(titleImage,
				(c.getWidth() / 2) - (titleImage.getWidth() / 2),
				c.getHeight() / 20);

		final int startButtonXStart = c.getWidth() / 8;
		final int startButtonYStart = c.getHeight() / 2;
		final int startButtonXEnd = c.getWidth() / 8 * 3;
		final int startButtonYEnd = c.getHeight() / 8 * 6;

		final int exitButtonXStart = c.getWidth() / 8 * 5;
		final int exitButtonYStart = c.getHeight() / 2;
		final int exitButtonXEnd = c.getWidth() / 8 * 7;
		final int exitButtonYEnd = c.getHeight() / 8 * 6;
		
		if (startButtonHover)
			g.setColor(new Color(97, 252, 0));
		else
			g.setColor(new Color(29, 252, 0));

		// Draw Buttons
		g.fillRoundRect(
				startButtonXStart, // x start
				startButtonYStart, // y start
				startButtonXEnd - startButtonXStart, // width
				startButtonYEnd - startButtonYStart, // height
				40
		); 

		if (exitButtonHover)
			g.setColor(new Color(97, 252, 0));
		else
			g.setColor(new Color(29, 252, 0));
		
		g.fillRoundRect(
				exitButtonXStart, // x start
				exitButtonYStart, // y start
				exitButtonXEnd - exitButtonXStart, // width
				exitButtonYEnd - exitButtonYStart, // height
				40
		); 

		g.setColor(Color.black);

		g.drawString("Play",
				((startButtonXStart + startButtonXEnd) / 2) - (g.getFont().getWidth("Play") / 2),
				((startButtonYStart + startButtonYEnd) / 2) - (g.getFont().getHeight("Play") / 2));
		g.drawString("Exit",
				((exitButtonXStart + exitButtonXEnd) / 2) - (g.getFont().getWidth("Exit") / 2),
				((exitButtonYStart + exitButtonYEnd) / 2) - (g.getFont().getHeight("Exit") / 2));
	}

	@Override
	public void update(GameContainer c, StateBasedGame sbg, int delta)
	throws SlickException
	{
		final int startButtonXStart = c.getWidth() / 8;
		final int startButtonYStart = c.getHeight() / 2;
		final int startButtonXEnd = c.getWidth() / 8 * 3;
		final int startButtonYEnd = c.getHeight() / 8 * 6;

		final int exitButtonXStart = c.getWidth() / 8 * 5;
		final int exitButtonYStart = c.getHeight() / 2;
		final int exitButtonXEnd = c.getWidth() / 8 * 7;
		final int exitButtonYEnd = c.getHeight() / 8 * 6;

		Input in = c.getInput();
		int posX = in.getMouseX();
		int posY = in.getMouseY();

		if (posX > startButtonXStart && posX < startButtonXEnd && 
				posY > startButtonYStart && posY < startButtonYEnd)
		{
			startButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				sbg.enterState(GameStates.IN_GAME.ordinal());
			}
		}
		else
			startButtonHover = false;
		if (posX > exitButtonXStart && posX < exitButtonXEnd &&
				posY > exitButtonYStart && posY < exitButtonYEnd)
		{
			exitButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				c.exit();
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
