package com.bullers.pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

public class Paddle extends JPanel {

	private static final long serialVersionUID = 1L;

	private int x, y, width, height; // The x value should be contant
	private Color paddleColor;
	Dimension appSize; // So that we can calculate stuffs
	Paddle p = this;
	boolean isPlayerOne;
	
	// The players score
	private int score = 0;
	
	// So we can draw the score on it
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.white);
		g.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		g.drawString(Integer.toString(this.getScore()), 5, 50);
	}
	
	// Key listener for the thingy
	class PaddleUp extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(p.y < 0) return;
			animateUpDown(p.y-25);
			
		}
		
	}
	
	class PaddleDown extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if((p.y+p.height) > appSize.getHeight()) return;
			animateUpDown(p.y+25);
			
		}
		
	}
	
	public Paddle(int startingX, int startingY, int width, int height, Dimension appSize, Color color, boolean isPlayerOne) {
		this.x = startingX;
		this.y = startingY;
		this.width = width;
		this.height = height;
		this.paddleColor = color;
		this.appSize = appSize;
		this.isPlayerOne = isPlayerOne;
		
		this.setLocation(this.x, this.y);
		this.setSize(this.width, this.height);
		this.setBackground(this.paddleColor);
		this.setDoubleBuffered(false);

		addKeyBinds();
	}
	
	// Someone wins
	public static void winner(Paddle player) {
		
		for (int i = 0; i < 25; i++) {
			//App.setBackground(new Color((int)Math.random()*255, (int)Math.random()*255, (int)Math.random()*255));
		}
		
	}
	
	protected void addKeyBinds() {
//		// Initialize some new key binding actions
//		PaddleUp upAct = new PaddleUp();
//		PaddleDown downAct = new PaddleDown();
//		
//		// Add the key bindings
//		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upAction");
//		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downAction");
//
//		this.getActionMap().put("upAction", upAct);
//		this.getActionMap().put("downAction", downAct);
		
	}
	
	public void animateUpDown(int wantedY) {
		
		// So, the parameter is where u want it to go
//		long startTime = System.currentTimeMillis();
//		long dif = System.currentTimeMillis()-startTime;
		
		if(this.y > wantedY) {
			while(this.y > wantedY) {
				this.setLocation(this.x, this.y-=1);
			}
		}else if(this.y < wantedY) {
			while(this.y < wantedY) {
				this.setLocation(this.x, this.y+=1);
			}
		}
		
		
	}
	
	// Get the rectangle of the paddle
	public Rectangle getRectangle() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
