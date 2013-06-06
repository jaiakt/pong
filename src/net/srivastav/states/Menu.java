package net.srivastav.states;


import net.srivastav.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.gui.*;

public class Menu extends BasicGameState
{
	UnicodeFont titleFont;
	UnicodeFont buttonFont;
	UnicodeFont settingsFont;
	Image starTileImage = null;
	boolean playButtonHover;
	boolean exitButtonHover;
	boolean settingsButtonHover;
	
	int playButtonXStart;
	int playButtonYStart;
	int playButtonXEnd;
	int playButtonYEnd;

	int exitButtonXStart;
	int exitButtonYStart;
	int exitButtonXEnd;
	int exitButtonYEnd;
	
	int settingsButtonXStart;
	int settingsButtonYStart;
	int settingsButtonXEnd;
	int settingsButtonYEnd;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException
	{
		titleFont = Fonts.getRetroFont(java.awt.Color.green, 150);
		buttonFont = Fonts.getRetroFont(java.awt.Color.black, 40);
		settingsFont = Fonts.getRetroFont(java.awt.Color.black, 25);
		starTileImage = new Image("assets/starTile.gif");
		
		playButtonXStart = gc.getWidth() / 8;
		playButtonYStart = gc.getHeight() / 2;
		playButtonXEnd = gc.getWidth() / 8 * 3;
		playButtonYEnd = gc.getHeight() / 8 * 6;

		exitButtonXStart = gc.getWidth() / 8 * 5;
		exitButtonYStart = gc.getHeight() / 2;
		exitButtonXEnd = gc.getWidth() / 8 * 7;
		exitButtonYEnd = gc.getHeight() / 8 * 6;
		
		settingsButtonXStart = gc.getWidth() / 8 * 3;
		settingsButtonXEnd = gc.getWidth() / 8 * 5;
		settingsButtonYStart = gc.getHeight() * 6 / 8;
		settingsButtonYEnd = gc.getHeight() * 31 / 32;
		
		playButtonHover = exitButtonHover = settingsButtonHover = false;
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException
	{
		// Draw Background
		for (int x = 0; x < gc.getWidth(); x += starTileImage.getWidth())
		{
			for (int y = 0; y < gc.getHeight(); y += starTileImage.getHeight())
			{
				g.drawImage(starTileImage, x, y);
			}
		}

		// Draw Title
		g.setFont(titleFont);
		g.drawString("PONG",
				(gc.getWidth() / 2) - (g.getFont().getWidth("PONG") / 2),
				(gc.getHeight() / 4) - (g.getFont().getHeight("PONG") / 2));
		
		// Draw Buttons
		g.setFont(buttonFont);
		
		if (playButtonHover)
			g.setColor(Constants.buttonHoverColor);
		else
			g.setColor(Constants.buttonDefaultColor);
		
		g.fillRoundRect(
				playButtonXStart, // x play
				playButtonYStart, // y play
				playButtonXEnd - playButtonXStart, // width
				playButtonYEnd - playButtonYStart, // height
				40
		); 

		if (exitButtonHover)
			g.setColor(Constants.buttonHoverColor);
		else
			g.setColor(Constants.buttonDefaultColor);
		
		g.fillRoundRect(
				exitButtonXStart, // x play
				exitButtonYStart, // y play
				exitButtonXEnd - exitButtonXStart, // width
				exitButtonYEnd - exitButtonYStart, // height
				40
		); 
		
		if (settingsButtonHover)
			g.setColor(Constants.buttonHoverColor);
		else
			g.setColor(Constants.buttonDefaultColor);
		
		g.fillRoundRect(
				settingsButtonXStart, // x play
				settingsButtonYStart, // y play
				settingsButtonXEnd - settingsButtonXStart, // width
				settingsButtonYEnd - settingsButtonYStart, // height
				40
		);


		final int xOffset = 0;
		final int yOffset = -5;
		
		String playButtonLabel = "PLAY";
		g.drawString(playButtonLabel,
				((playButtonXStart + playButtonXEnd) / 2) - (g.getFont().getWidth(playButtonLabel) / 2) + xOffset,
				((playButtonYStart + playButtonYEnd) / 2) - (g.getFont().getHeight(playButtonLabel) / 2) + yOffset);
		
		String exitButtonLabel = "EXIT";
		g.drawString(exitButtonLabel,
				((exitButtonXStart + exitButtonXEnd) / 2) - (g.getFont().getWidth(exitButtonLabel) / 2) + xOffset,
				((exitButtonYStart + exitButtonYEnd) / 2) - (g.getFont().getHeight(exitButtonLabel) / 2) + yOffset);
		
		g.setFont(settingsFont);
		String settingsButtonLabel = "SETTINGS";
		g.drawString(settingsButtonLabel,
				((settingsButtonXStart + settingsButtonXEnd) / 2) - (g.getFont().getWidth(settingsButtonLabel) / 2) + xOffset,
				((settingsButtonYStart + settingsButtonYEnd) / 2) - (g.getFont().getHeight(settingsButtonLabel) / 2) + yOffset);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException
	{
		MouseOverArea playMouseOverArea = new MouseOverArea(
				gc, null,
				playButtonXStart,
				playButtonYStart,
				playButtonXEnd - playButtonXStart,
				playButtonYEnd - playButtonYStart
		);
		
		MouseOverArea exitMouseOverArea = new MouseOverArea(
				gc, null,
				exitButtonXStart,
				exitButtonYStart,
				exitButtonXEnd - exitButtonXStart,
				exitButtonYEnd - exitButtonYStart
		);
		
		MouseOverArea settingsMouseOverArea = new MouseOverArea(
				gc, null,
				settingsButtonXStart,
				settingsButtonYStart,
				settingsButtonXEnd - settingsButtonXStart,
				settingsButtonYEnd - settingsButtonYStart
		);

		Input in = gc.getInput();

		if (playMouseOverArea.isMouseOver())
		{
			playButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				sbg.enterState(GameStates.LEVELS.ordinal());
			}
		}
		else
		{
			playButtonHover = false;
		}
		if (exitMouseOverArea.isMouseOver())
		{
			exitButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				gc.exit();
			}
		}
		else
		{
			exitButtonHover = false;
		}
		if (settingsMouseOverArea.isMouseOver())
		{
			settingsButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				sbg.enterState(GameStates.MENU.ordinal());
			}
		}
		else
		{
			settingsButtonHover = false;
		}
	}

	@Override
	public int getID()
	{
		return GameStates.MENU.ordinal();
	}

}
