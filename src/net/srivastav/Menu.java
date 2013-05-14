package net.srivastav;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.*;
import java.awt.Font;

public class Menu extends BasicGameState
{
	Image starTileImage = null;
	Image titleImage = null;
	@Override
	public void init(GameContainer c, StateBasedGame sbg)
			throws SlickException
	{
		starTileImage = new Image("assets/starTile.gif");
		titleImage = new Image("assets/title.png");
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
		
		final int endButtonXStart = c.getWidth() / 8 * 5;
		final int endButtonYStart = c.getHeight() / 2;
		final int endButtonXEnd = c.getWidth() / 8 * 7;
		final int endButtonYEnd = c.getHeight() / 8 * 6;
		g.setColor(Color.green);
		
		// Draw Buttons
		g.fillRoundRect(
				startButtonXStart, // x start
				startButtonYStart, // y start
				startButtonXEnd - startButtonXStart, // width
				startButtonYEnd - startButtonYStart, // height
				40
				); 
		
		g.fillRoundRect(
				endButtonXStart, // x start
				endButtonYStart, // y start
				endButtonXEnd - endButtonXStart, // width
				endButtonYEnd - endButtonYStart, // height
				40
				); 
		
		g.setColor(Color.black);
		
		g.drawString("Play",
				((startButtonXStart + startButtonXEnd) / 2) - (g.getFont().getWidth("Play") / 2),
				((startButtonYStart + startButtonYEnd) / 2) - (g.getFont().getHeight("Play") / 2));
		g.drawString("Exit",
				((endButtonXStart + endButtonXEnd) / 2) - (g.getFont().getWidth("Exit") / 2),
				((endButtonYStart + endButtonYEnd) / 2) - (g.getFont().getHeight("Exit") / 2));
	}

	@Override
	public void update(GameContainer c, StateBasedGame sbg, int delta)
			throws SlickException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getID()
	{
		return GameStates.MENU.ordinal();
	}

}
