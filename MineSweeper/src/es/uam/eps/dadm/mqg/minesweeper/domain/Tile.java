package es.uam.eps.dadm.mqg.minesweeper.domain;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class Tile {
	
	private Status status = Status.Normal;
    private int neighbourBombSize = 0;
    private boolean bomb = false;
    private int ownerPlayerId;
	
    public static enum Status {
        Normal,
        Opened
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

	public int getOwnerPlayerId() {
		return ownerPlayerId;
	}

	public void setOwnerPlayerId(int playerId) {
		this.ownerPlayerId = playerId;
	}
}
