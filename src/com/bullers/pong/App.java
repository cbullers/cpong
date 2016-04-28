package com.bullers.pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Dimension appSize;
	
	// How high someone can get their score
	public static final int MAX_SCORE = 7;
	
	// So the paddles can be accessed from elsewhere
	public static Paddle playerOne, playerTwo;
	
	// 
	
	// Actions for clicking the buttons
	Action wKeyPress = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(playerOne.getY() < 0) return;
			playerOne.setLocation(playerOne.getX(), playerOne.getY()-50);
		}
	};
	
	Action sKeyPressed = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if((playerOne.getY()+playerOne.getHeight()) > appSize.getHeight()) return;
			playerOne.setLocation(playerOne.getX(), playerOne.getY()+50);
		}
	};
	
	Action upKeyPressed = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(playerTwo.getY() < 0) return;
			playerTwo.setLocation(playerTwo.getX(), playerTwo.getY()-50);
		}
	};
	
	Action downKeyPressed = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if((playerTwo.getY()+playerTwo.getHeight()) > appSize.getHeight()) return;
			playerTwo.setLocation(playerTwo.getX(), playerTwo.getY()+50);
		}
	};
	
	
	public App() throws InterruptedException {
		super("Pong! by Christian Bullers");
		
		// So I can have freedom of position!
		this.setLayout(null);
		
		// Some modifiers
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation((int)(screenSize.getWidth()/2)-600,(int)(screenSize.getHeight())-700);
		this.setResizable(false);
		this.setSize(1200,600);
		
		// Add the paddles
		playerOne = new Paddle(10, 10, 20, 100, this.getSize(), new Color(0,0,0), true);
		playerTwo = new Paddle(this.getSize().width-30, 10, 20, 100, this.getSize(), new Color(0,0,0), false);
		
		// Set the appsize
		appSize = this.getSize();
		
		// Add them to the playing board
		this.add(playerTwo);
		this.add(playerOne);
		
		// Add the buttons and score, etc
		add(new UserInterface());
		
		// Add keybinds
		addKeybinds();
		
		// Add the ball
		Ball ball = new Ball((int)this.getSize().width/2-10, (int)this.getSize().height/2-10, new Color(0,0,0), this.getSize());
		this.add(ball);
		
		// And, APPEAR!
		this.setVisible(true);

		// Keep the ball there
		ball.ballSleep(500);
	}
	
	// Start er up
	public static void main(String[] args) throws InterruptedException {
		new App();
	}
	
	// So we can actually move the paddles
	private void addKeybinds() {
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "upAction");
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "downAction");

		this.getRootPane().getActionMap().put("upAction", upKeyPressed);
		this.getRootPane().getActionMap().put("downAction", downKeyPressed);
		
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "wAction");
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "sAction");

		this.getRootPane().getActionMap().put("wAction", wKeyPress);
		this.getRootPane().getActionMap().put("sAction", sKeyPressed);
	}
	
	
}
