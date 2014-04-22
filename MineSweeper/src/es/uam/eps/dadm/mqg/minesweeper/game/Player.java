package es.uam.eps.dadm.mqg.minesweeper.game;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class Player {
	
	public static final int ID_PLAYER1 = 1;
	public static final int ID_PLAYER2 = 2;
	
	private int points = 0;
	private int id;
	
	public Player(int id) {
		this.id = id;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getId() {
		return id;
	}
	
	public void resetPoints() {
		this.points = 0;
	}

	public void incPoints() {
		this.points++;
	}
	
	public boolean equals(Player another) {
		return this.id == another.getId();
	}
}
