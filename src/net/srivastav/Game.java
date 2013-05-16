package net.srivastav;

import java.awt.Point;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends BasicGameState
{
	int speed;
	int current;
	
	Image starTileImage = null;
	int topBoundary;
	int botBoundary;
	int ballRadius;	
	Point ballLocation;
	int ballSpeed;
	double ballAngle;
	int paddleHeight;
	int paddle1Location;
	int paddle2Location;
	Point mousePos;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		speed = 4;
		current = 0;
	
		starTileImage = new Image("assets/starTile.gif");
		topBoundary = botBoundary = 0;
		ballRadius = 20;
		ballAngle = (int)(Math.random() * 2) * Math.PI;
		ballSpeed = 1;
		ballLocation = new Point(gc.getWidth() / 2, gc.getHeight() / 2); 
		
		paddleHeight = 100;
		
		paddle1Location = paddle2Location = gc.getHeight() / 2;
		mousePos = new Point();
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

		// Draw Dotted Lines
		final Color lineColor = Color.white;
		final int lineSize = 5;
		final int spaceBetween = 5;
		final int lineMargin = 10;
		
		g.setColor(lineColor);
		
		for (int y = 0; y < gc.getHeight(); y += lineSize + spaceBetween)
		{
			g.drawLine(lineMargin,
					y,
					lineMargin,
					y + lineSize
			);
		}
		for (int y = 0; y < gc.getHeight(); y += lineSize + spaceBetween)
		{
			g.drawLine(gc.getWidth() - lineMargin,
					y,
					gc.getWidth() - lineMargin,
					y + lineSize
			);
		}
		
		// Draw Walls
		final Color wallColor = Color.gray;
		final int wallThickness = 20;
		
		g.setColor(wallColor);
		
		g.fillRect(0, 0, gc.getWidth(), wallThickness);
		g.fillRect(0, gc.getHeight() - wallThickness, gc.getWidth(), wallThickness);
		
		topBoundary = wallThickness;
		botBoundary = gc.getHeight() - wallThickness;
		
		// Draw Ball
		final Color ballColor = Color.white;
		
		g.setColor(ballColor);
		
		g.fillOval(ballLocation.x, ballLocation.y, ballRadius, ballRadius);
		
		// Draw Paddles
		final Color paddleFillColor = Color.gray;
		final Color paddleDrawColor = Color.white;
		final int paddleWidth = 5;
		final int paddleMargin = 15;
		
		g.setColor(paddleFillColor);
		
		g.fillRect(paddleMargin,
				paddle1Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		g.fillRect(gc.getWidth() - paddleMargin - paddleWidth,
				paddle2Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		
		g.setColor(paddleDrawColor);
		
		g.drawRect(paddleMargin,
				paddle1Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		g.drawRect(gc.getWidth() - paddleMargin - paddleWidth,
				paddle2Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		
		
		// Write Mouse Pos
		if (mousePos != null)
			g.drawString(String.format("%d. %d", mousePos.x, mousePos.y), 200, 200);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		Input inp = gc.getInput();
		mousePos.x = inp.getMouseX();
		mousePos.y = inp.getMouseY();
		
		current += delta;
		if (current < speed)
		{
			return;
		}
		current = 0;
		
		Input in = gc.getInput();
		
		if (in.isKeyDown(Input.KEY_1))
		{
			speed++;
		}
		if (in.isKeyDown(Input.KEY_2))
		{
			speed--;
		}
		
		// Move Paddle
		if (in.isKeyDown(Input.KEY_UP) && in.isKeyDown(Input.KEY_DOWN))
		{
			// DO NOTHING
		}
		else if (in.isKeyDown(Input.KEY_UP))
		{
			paddle1Location = Math.max(topBoundary + paddleHeight / 2,
					paddle1Location - 1);
		}
		else if (in.isKeyDown(Input.KEY_DOWN))
		{
			paddle1Location = Math.min(botBoundary - paddleHeight / 2,
					paddle1Location + 1);
		}
		
		// Check for collisions
		if (ballLocation.x <= 0)
			ballAngle = 3 * Math.PI - ballAngle; 
		if (ballLocation.x >= gc.getWidth())
			ballAngle = Math.PI - ballAngle;
		/*
		if (ballLocation.y <= 0)
			ballAngle = - ballAngle + 2 * Math.PI;
			*/
		
		System.out.println(ballAngle / Math.PI);
		System.out.println(Math.sin(ballAngle));
		// Move Ball
		double tempAngle = ballAngle;
		if (tempAngle < Math.PI)
		{
			ballLocation.x += ballSpeed * Math.cos(tempAngle);
			ballLocation.y += ballSpeed * Math.sin(tempAngle);
		}
		else
		{
			ballLocation.x += ballSpeed * Math.cos(tempAngle);
			ballLocation.y += ballSpeed * Math.sin(tempAngle);
		}
		
	}
	
	@Override
	public int getID()
	{
		return GameStates.IN_GAME.ordinal();
	}

}
