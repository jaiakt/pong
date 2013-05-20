package net.srivastav;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

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
	Point2D.Double ballLocation;
	int ballSpeed;
	double ballAngle;
	int paddleHeight;
	int paddle1Location;
	int paddle2Location;
	int paddleMargin;
	int paddleWidth;
	Point2D.Double mousePos;
	int collisionTime;
	int collisionReset;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		speed = 4;
		current = 0;
	
		starTileImage = new Image("assets/starTile.gif");
		topBoundary = botBoundary = 0;
		ballRadius = 10;
		ballSpeed = 1;
		ballLocation = new Point2D.Double(gc.getWidth() / 2, gc.getHeight() / 2); 
		ballAngle = 0 + (int) (Math.random() * 2) * Math.PI;
		
		paddleHeight = 100;
		paddleMargin = 15;
		paddleWidth = 5;
		
		collisionTime = 0;
		collisionReset = 50;
		paddle1Location = paddle2Location = gc.getHeight() / 2;
		mousePos = new Point2D.Double();
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
		
		g.fillOval((int) ballLocation.x - ballRadius, (int) ballLocation.y - ballRadius, ballRadius * 2, ballRadius * 2);
		
		// Draw Paddles
		final Color paddleFillColor = Color.gray;
		final Color paddleDrawColor = Color.white;
		
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
			g.drawString(String.format("%d. %d", (int) mousePos.x, (int) mousePos.y), 200, 200);
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
		
		// Move Paddle1
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
		
		// Move Paddle2
		if (in.isKeyDown(Input.KEY_A) && in.isKeyDown(Input.KEY_Z))
		{
			// DO NOTHING
		}
		else if (in.isKeyDown(Input.KEY_A))
		{
			paddle2Location = Math.max(topBoundary + paddleHeight / 2,
					paddle2Location - 1);
		}
		else if (in.isKeyDown(Input.KEY_Z))
		{
			paddle2Location = Math.min(botBoundary - paddleHeight / 2,
					paddle2Location + 1);
		}
		
		// Check for collisions
		collisionTime += 5;
		
		if (collisionTime >= collisionReset)
		{
			System.out.println(((int) ballLocation.x + " " + ballRadius) + " " + (paddleMargin + " " + paddleWidth));
			// Check for collision with paddle1
			if ((int) ballLocation.x + ballRadius >= paddleMargin &&
					(int) ballLocation.x - ballRadius <= paddleMargin + paddleWidth &&
					(int) ballLocation.y + ballRadius >= paddle1Location - paddleHeight / 2 &&
					(int) ballLocation.y - ballRadius <= paddle1Location + paddleHeight / 2)
			{
				// Change angle relative to distance from center
				int distance = paddle1Location - (int) ballLocation.y;
				int maxDistance = paddleHeight / 2;
				ballAngle = Math.PI / 4 * ((double) distance / maxDistance);
				collisionTime = 0;
			}
		}
		
		if (collisionTime >= collisionReset)
		{
			// Check for collision with paddle2
			if ((int) ballLocation.x + ballRadius >= gc.getWidth() - paddleMargin - paddleWidth &&
				(int) ballLocation.x - ballRadius <= gc.getWidth() - paddleMargin &&
				(int) ballLocation.y + ballRadius >= paddle2Location - paddleHeight / 2 &&
				(int) ballLocation.y - ballRadius <= paddle2Location + paddleHeight / 2)
			{
				// Change angle relative to distance from center
				int distance = paddle2Location - (int) ballLocation.y;
				int maxDistance = paddleHeight / 2;
				ballAngle = Math.PI - (Math.PI / 4 * ((double) distance / maxDistance));
				collisionTime = 0;
			}
		}
		
		// Move Ball
		
		System.out.println(Math.cos(ballAngle) + " " + Math.sin(ballAngle));
		ballLocation.x += ballSpeed * Math.cos(ballAngle);
		ballLocation.y -= ballSpeed * Math.sin(ballAngle);
		
	}
	
	@Override
	public int getID()
	{
		return GameStates.IN_GAME.ordinal();
	}

}
