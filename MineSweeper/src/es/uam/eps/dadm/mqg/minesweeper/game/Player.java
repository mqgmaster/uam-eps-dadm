package es.uam.eps.dadm.mqg.minesweeper.game;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class Player {
	
	private int points = 0;
	private String name; 
	private int flagResource;
	private PlayerNumber number;
	
	public static enum PlayerNumber {
		ONE,
		TWO
	}
	
	public Player(PlayerNumber number, int flagResource) {
		this.number = number;
		this.flagResource = flagResource;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void resetPoints() {
		this.points = 0;
	}

	public void incPoints() {
		this.points++;
	}
	
	public boolean equals(Player another) {
		return this.number.equals(another.getNumber()) && this.flagResource == another.getFlagResource();
	}

	public int getFlagResource() {
		return flagResource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayerNumber getNumber() {
		return number;
	}
}
