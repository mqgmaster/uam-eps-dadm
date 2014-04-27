package es.uam.eps.dadm.mqg.minesweeper.game;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class Tile {
	
	private Status status = Status.NORMAL;
    private int neighbourBombSize = 0;
    private boolean bomb = false;
    private Player ownerPlayer;
    
    public Tile(Status status) {
    	this.status = status;
    }
    
    public Tile() {
    }
	
    public static enum Status {
        NORMAL,
        OPENED,
        FLAGGED
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void setNeighbourBombSize(int neighbourBombSize) {
        this.neighbourBombSize = neighbourBombSize;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public Integer getNeighbourBombSize() {
        return this.neighbourBombSize;
    }

    public boolean hasBomb() {
        return this.bomb;
    }
    
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	public void setOwnerPlayer(Player player) {
		status = Status.FLAGGED;
		this.ownerPlayer = player;
	}
}
