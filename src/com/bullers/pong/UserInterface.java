package com.bullers.pong;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("unused")
public class UserInterface extends JPanel {

	private static final long serialVersionUID = 1L;

	// Scores
	private int pOneScore, pTwoScore;
	
	public UserInterface() {
		addComponents();
	}
	
	private void addComponents() {
		add(new ExitButton());
	}
	
}

class ExitButton extends JButton {

	private static final long serialVersionUID = 1L;

	public ExitButton() {
		super("EXIT");
		this.setSize(100,50);
		this.setLocation((int) (App.appSize.getWidth() - 150), 10);
	}
	
}
