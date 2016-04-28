package com.bullers.pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class Ball extends JComponent {

	private static final long serialVersionUID = 1L;

	public static final int BALL_RADIUS = 20;
	
	// Direction
	public int DIR_LEFT = 0;
	public int DIR_RIGHT = 1;
	
	// A "hitbox" so to say
	Rectangle hitbox = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	
	// To access from runnable
	private Ball b = this;
	
	// So we know who to give points to
	protected Paddle lastHit;
	
	// Cuz
	protected volatile boolean isSleeping = false;
	
	// Velocity
	protected int BALL_VELOCITY = 10;
	
	// Delay on movement
	protected int BALL_DELAY = 25;
	
	protected Color bgColor;
	protected int direction;
	protected int angle;
	protected Dimension appSize;
	
	// Where they started
	private int startingX, startingY;
	
	public Ball(int x, int y, Color bgColor, Dimension appSize) {
		this.bgColor = bgColor;
		this.setLocation(x, y);
		this.setSize(BALL_RADIUS*2, BALL_RADIUS*2);
		this.appSize = appSize;
		this.direction = (Math.random() <= .5) ? DIR_LEFT : DIR_RIGHT;
		//this.angle = (int)(Math.random()*22) + 90;
		this.angle = 120;
		this.startingX = x;
		this.startingY = y;
		// Let them know where the ball is
		ballLogic.start();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(this.bgColor);
		g.fillOval(0, 0, BALL_RADIUS, BALL_RADIUS);
	}
	
	public void ballSleep(int howlong) throws InterruptedException {
		isSleeping = true;
		Thread.sleep(howlong);
		isSleeping = false;
	}
	
	public void resetBall() {
		this.setLocation(startingX, startingY);
		this.direction = (this.direction == DIR_LEFT) ? DIR_RIGHT : DIR_LEFT;
		
		// So we can update the scores
		App.playerOne.repaint();
		App.playerTwo.repaint();
		
		try {
			b.ballSleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//this.angle = (int)(Math.random()*22) + 90;
	}
	
	// The balls logic loop
	Thread ballLogic = new Thread(new Runnable() {
		public void run() {
			
			// Time to eat some resources
			while(true) {
				
				// If anyone has the max score
//				if(App.playerOne.getScore() >= App.MAX_SCORE) {
//					// Player 1 wins
//					App.winner(App.playerOne); break;
//				}else if(App.playerTwo.getScore() >= App.MAX_SCORE) {
//					// Player 2 wins
//					App.winner(App.playerTwo); break;
//				}
				
				if(!isSleeping) {
					
					hitbox = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
					if(App.playerOne.getRectangle().intersects(hitbox)) {
						lastHit = App.playerOne;
						b.direction = DIR_RIGHT;
					}else if(App.playerTwo.getRectangle().intersects(hitbox)) {
						lastHit = App.playerTwo;
						b.direction = DIR_LEFT;
					}
					
					// Scoring
					if(b.getX() < 0) { // Player 2 scored
						App.playerTwo.setScore(App.playerTwo.getScore()+1);
						b.resetBall();
					}else if(b.getX() > (b.appSize.getWidth())) { // Player 1 scores
						App.playerOne.setScore(App.playerOne.getScore()+1);
						b.resetBall();
					}
					
					
					// Moving
					
					// Bouncing of of walls and crap
					if((b.getY() + b.getSize().height) > b.appSize.getHeight() && (b.direction == DIR_RIGHT) ||
							(b.getY() < 0) && (b.direction == DIR_RIGHT)) {
						b.angle += 90;
					}else if((b.getY() + b.getSize().height) > b.appSize.getHeight() && (b.direction == DIR_LEFT) ||
							b.getY() < 0 && (b.direction == DIR_LEFT)) {
						b.angle -= 90;
					}
					
					if(b.direction == DIR_LEFT) {
						
						b.setLocation(b.getX()-8,(int)(b.getY() + (BALL_VELOCITY * (float)Math.sin(Math.toRadians(b.angle - 90)))));
						try { Thread.sleep(BALL_DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
						
					}else if(b.direction == DIR_RIGHT) {
						
						b.setLocation(b.getX()+8,(int)(b.getY() + (BALL_VELOCITY * (float)Math.sin(Math.toRadians(b.angle - 90)))));
						try { Thread.sleep(BALL_DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
						
					}
				}
				
			}
			
		}
	}, "ballThread");
	
}
