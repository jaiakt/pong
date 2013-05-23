package net.srivastav.states;

import net.srivastav.*;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.*;
import java.awt.Color;

public class GameOver extends BasicGameState 
{
	UnicodeFont titleFont;
	UnicodeFont winnerFont;
	UnicodeFont buttonFont;
	
	Image starTileImage = null;
	
	int playButtonXStart;
	int playButtonYStart;
	int playButtonXEnd;
	int playButtonYEnd;

	int exitButtonXStart;
	int exitButtonYStart;
	int exitButtonXEnd;
	int exitButtonYEnd;
	
	int menuButtonXStart;
	int menuButtonYStart;
	int menuButtonXEnd;
	int menuButtonYEnd;
	
	boolean playButtonHover;
	boolean exitButtonHover;
	boolean menuButtonHover;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		titleFont = Fonts.getRetroFont(Color.green, 80.f);
		winnerFont = Fonts.getRetroFont(Color.red, 50.f);
		buttonFont = Fonts.getRetroFont(java.awt.Color.black, 40);
		
		starTileImage = new Image("assets/starTile.gif");
		
		playButtonXStart = gc.getWidth() / 8;
		playButtonYStart = gc.getHeight() / 2;
		playButtonXEnd = gc.getWidth() / 8 * 3;
		playButtonYEnd = gc.getHeight() / 8 * 6;

		exitButtonXStart = gc.getWidth() / 8 * 5;
		exitButtonYStart = gc.getHeight() / 2;
		exitButtonXEnd = gc.getWidth() / 8 * 7;
		exitButtonYEnd = gc.getHeight() / 8 * 6;
		
		menuButtonXStart = gc.getWidth() / 8 * 3;
		menuButtonXEnd = gc.getWidth() / 8 * 5;
		menuButtonYStart = gc.getHeight() * 6 / 8;
		menuButtonYEnd = gc.getHeight() * 31 / 32;
		
		playButtonHover = exitButtonHover = menuButtonHover = false;
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
		
		// Draw titleString
		g.setFont(titleFont);
		String titleString = "GAME OVER";
		g.drawString(titleString,
				gc.getWidth() / 2 - g.getFont().getWidth(titleString) / 2,
				gc.getHeight() / 8 - g.getFont().getHeight(titleString) / 2);
		
		// Draw "PLAYER x WON"
		g.setFont(winnerFont);
		String playerString = "PLAYER " + Singleton.winner + " WON";
		g.drawString(playerString,
				gc.getWidth() / 2 - g.getFont().getWidth(playerString) / 2,
				gc.getHeight() / 16 * 5 - g.getFont().getHeight(playerString) / 2);
		
		// Draw Buttons
		
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
		
		if (menuButtonHover)
			g.setColor(Constants.buttonHoverColor);
		else
			g.setColor(Constants.buttonDefaultColor);
		
		g.fillRoundRect(
				menuButtonXStart, // x play
				menuButtonYStart, // y play
				menuButtonXEnd - menuButtonXStart, // width
				menuButtonYEnd - menuButtonYStart, // height
				40
		);

		g.setColor(org.newdawn.slick.Color.white);
		g.setFont(buttonFont);
		
		final int xOffset = 0;
		final int yOffset = -5;
		
		String exitButtonLabel = "EXIT";
		g.drawString(exitButtonLabel,
				((exitButtonXStart + exitButtonXEnd) / 2) - (g.getFont().getWidth(exitButtonLabel) / 2) + xOffset,
				((exitButtonYStart + exitButtonYEnd) / 2) - (g.getFont().getHeight(exitButtonLabel) / 2) + yOffset);
	
		// Draw "PLAY AGAIN" in two lines
		String play = "PLAY";
		String again = "AGAIN";
		g.drawString(play,
				((playButtonXStart + playButtonXEnd) / 2) - (g.getFont().getWidth(play) / 2) + xOffset,
				((playButtonYStart + playButtonYEnd) / 2) - (playButtonYEnd - playButtonYStart) * 5 / 32 - (g.getFont().getHeight(play) / 2) + yOffset);
		g.drawString(again,
				((playButtonXStart + playButtonXEnd) / 2) - (g.getFont().getWidth(again) / 2) + xOffset,
				((playButtonYStart + playButtonYEnd) / 2) + (playButtonYEnd - playButtonYStart) * 5 / 32  - (g.getFont().getHeight(again) / 2) + yOffset);
		
		String menuButtonLabel = "MENU";
		g.drawString(menuButtonLabel,
				((menuButtonXStart + menuButtonXEnd) / 2) - (g.getFont().getWidth(menuButtonLabel) / 2) + xOffset,
				((menuButtonYStart + menuButtonYEnd) / 2) - (g.getFont().getHeight(menuButtonLabel) / 2) + yOffset);
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
		
		MouseOverArea menuMouseOverArea = new MouseOverArea(
				gc, null,
				menuButtonXStart,
				menuButtonYStart,
				menuButtonXEnd - menuButtonXStart,
				menuButtonYEnd - menuButtonYStart
		);

		Input in = gc.getInput();

		if (playMouseOverArea.isMouseOver())
		{
			playButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				sbg.enterState(GameStates.IN_GAME.ordinal());
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
		
		if (menuMouseOverArea.isMouseOver())
		{
			menuButtonHover = true;
			if (in.isMouseButtonDown(0))
			{
				sbg.enterState(GameStates.MENU.ordinal());
			}
		}
		else
		{
			menuButtonHover = false;
		}
	}

	@Override
	public int getID()
	{
		return GameStates.GAME_OVER.ordinal();
	}

}
