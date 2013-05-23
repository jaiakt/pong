package net.srivastav.states;


import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.awt.geom.Point2D;

import net.srivastav.Fonts;
import net.srivastav.Singleton;



public class Game extends BasicGameState
{
	int speed;
	int current;
	
	Image starTileImage = null;
	int topBoundary;
	int botBoundary;
	int ballRadius;	
	Point2D.Double ballLocation;
	double ballSpeed;
	double ballAngle;
	int paddleHeight;
	double paddle1Location;
	double paddle2Location;
	int paddleMargin;
	int paddleWidth;
	double paddleSpeed;
	int dottedLineMargin;
	Point2D.Double mousePos;
	UnicodeFont scoreFont;
	int player1Score;
	int player2Score;
	int finalScore;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		speed = 4;
		current = 0;
	
		starTileImage = new Image("assets/starTile.gif");
		topBoundary = botBoundary = 0;
		ballRadius = 10;
		ballSpeed = 3;
		ballLocation = new Point2D.Double(gc.getWidth() / 2, gc.getHeight() / 2); 
		ballAngle = (int) (Math.random() * 2) * Math.PI;
		
		paddleHeight = 75;
		paddleMargin = 15;
		paddleWidth = 5;
		paddleSpeed = 1.0;
		
		dottedLineMargin = 10;
		
		paddle1Location = paddle2Location = gc.getHeight() / 2;
		mousePos = new Point2D.Double();
		
		scoreFont = Fonts.getRetroFont(java.awt.Color.white, 50.f);
		player1Score = player2Score = 0;
		finalScore = 1;
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
		
		g.setColor(lineColor);
		
		for (int y = 0; y < gc.getHeight(); y += lineSize + spaceBetween)
		{
			g.drawLine(dottedLineMargin,
					y,
					dottedLineMargin,
					y + lineSize
			);
		}
		for (int y = 0; y < gc.getHeight(); y += lineSize + spaceBetween)
		{
			g.drawLine(gc.getWidth() - dottedLineMargin,
					y,
					gc.getWidth() - dottedLineMargin,
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
				(int) paddle1Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		g.fillRect(gc.getWidth() - paddleMargin - paddleWidth,
				(int) paddle2Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		
		g.setColor(paddleDrawColor);
		
		g.drawRect(paddleMargin,
				(int) paddle1Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		g.drawRect(gc.getWidth() - paddleMargin - paddleWidth,
				(int) paddle2Location - paddleHeight / 2,
				paddleWidth,
				paddleHeight
		);
		
		// Draw Score
		g.setFont(scoreFont);
		g.drawString("" + player1Score, (gc.getWidth() / 4) - (g.getFont().getWidth("" + player1Score) / 2),
				(gc.getHeight() / 8) - (g.getFont().getHeight("" + player1Score) / 2));
		g.drawString("" + player2Score, (gc.getWidth() * 3 / 4) - (g.getFont().getWidth("" + player2Score) / 2),
				(gc.getHeight() / 8) - (g.getFont().getHeight("" + player2Score) / 2));
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
		
		// Check for Game Over
		if (ballLocation.x - ballRadius <= dottedLineMargin)
		{
			if (score(2))
			{
				sbg.enterState(GameStates.GAME_OVER.ordinal());
			}
			reset(gc);
		}
		
		if (ballLocation.x + ballRadius >= gc.getWidth() - dottedLineMargin)
		{
			if (score(1))
			{
				sbg.enterState(GameStates.GAME_OVER.ordinal());
			}
			reset(gc);
		}
		
		// Move Paddle1
		if (in.isKeyDown(Input.KEY_UP) && in.isKeyDown(Input.KEY_DOWN))
		{
			// DO NOTHING
		}
		else if (in.isKeyDown(Input.KEY_UP))
		{
			paddle1Location = Math.max(topBoundary + paddleHeight / 2,
					paddle1Location - paddleSpeed);
		}
		else if (in.isKeyDown(Input.KEY_DOWN))
		{
			paddle1Location = Math.min(botBoundary - paddleHeight / 2,
					paddle1Location + paddleSpeed);
		}
		
		// Move Paddle2
		if (in.isKeyDown(Input.KEY_A) && in.isKeyDown(Input.KEY_Z))
		{
			// DO NOTHING
		}
		else if (in.isKeyDown(Input.KEY_A))
		{
			paddle2Location = Math.max(topBoundary + paddleHeight / 2,
					paddle2Location - paddleSpeed);
		}
		else if (in.isKeyDown(Input.KEY_Z))
		{
			paddle2Location = Math.min(botBoundary - paddleHeight / 2,
					paddle2Location + paddleSpeed);
		}
		
		// Check for collisions
		
		// Check for top or bot collisions
		if (ballLocation.y - ballRadius <= topBoundary)
		{
			ballAngle = 2 * Math.PI - ballAngle;
		}
		if (ballLocation.y + ballRadius >= botBoundary)
		{
			ballAngle = 2 * Math.PI - ballAngle;
		}
		
		
		// Check for collision with paddle1
		if ((int) ballLocation.x + ballRadius >= paddleMargin &&
				(int) ballLocation.x - ballRadius <= paddleMargin + paddleWidth &&
				(int) ballLocation.y + ballRadius >= paddle1Location - paddleHeight / 2 &&
				(int) ballLocation.y - ballRadius <= paddle1Location + paddleHeight / 2)
		{
			// Change angle relative to distance from center
			int distance = (int) paddle1Location - (int) ballLocation.y;
			int maxDistance = paddleHeight / 2;
			ballAngle = Math.PI / 4 * ((double) distance / maxDistance);
		}
	
		// Check for collision with paddle2
		if ((int) ballLocation.x + ballRadius >= gc.getWidth() - paddleMargin - paddleWidth &&
			(int) ballLocation.x - ballRadius <= gc.getWidth() - paddleMargin &&
			(int) ballLocation.y + ballRadius >= paddle2Location - paddleHeight / 2 &&
			(int) ballLocation.y - ballRadius <= paddle2Location + paddleHeight / 2)
		{
			// Change angle relative to distance from center
			int distance = (int) paddle2Location - (int) ballLocation.y;
			int maxDistance = paddleHeight / 2;
			ballAngle = Math.PI - (Math.PI / 4 * ((double) distance / maxDistance));
		}
		
		// Move Ball
		ballLocation.x += ballSpeed * Math.cos(ballAngle);
		ballLocation.y -= ballSpeed * Math.sin(ballAngle);
		
	}
	
	private boolean score (int player)
	{
		if (player == 1)
		{
			if (++player1Score >= finalScore)
			{
				Singleton.winner = 1;
				return true;
			}
		}
		if (player == 2)
		{
			if (++player2Score >= finalScore)
			{
				Singleton.winner = 2;
				return true;
			}
		}
		return false;
	}
	
	private void reset(GameContainer gc)
	{
		ballLocation.x = gc.getWidth() / 2;
		ballLocation.y = gc.getHeight() / 2;
		ballAngle = (int)(Math.random() * 2) * Math.PI;
	}
	
	@Override
	public int getID()
	{
		return GameStates.IN_GAME.ordinal();
	}

}
