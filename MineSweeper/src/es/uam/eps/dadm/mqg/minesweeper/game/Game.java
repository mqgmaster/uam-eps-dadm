package es.uam.eps.dadm.mqg.minesweeper.game;


/*
 * Essa classe representa a lógica do jogo.
 * 
 * @author Mauricio Quatrin Guerreiro
 */

public class Game {
	
    private final Field field = new Field();
    private final Player playerOne = new Player(1);
    private final Player playerTwo = new Player(2);
    private Player currentPlayer = playerOne;
    
    public void newGame() {
    	this.currentPlayer = playerOne;
    	this.playerOne.resetPoints();
    	this.playerTwo.resetPoints();
    	field.installBombs("00000000000000000100101001000001000010000000001000010100100000000000000000");
    }
    
    public void openTile(int position) {
    	Tile tile = field.getTile(position);
    	if (tile.hasBomb()) {
        	currentPlayer.incPoints();
        	field.decLeftBombs();
        	tile.setOwnerPlayer(currentPlayer);
        } else {
        	changeTurn();
        }
    	
    	field.openAllTilesIfPossible(position);
    }
    
    public Field getField() {
    	return field;
    }
    
    public boolean isGameOver() {
    	if (field.getLeftBombs() == 0) {
    		return true;
    	}
        if (playerOne.getPoints() > playerTwo.getPoints() + field.getLeftBombs()) {
        	return true;
        } else if (playerTwo.getPoints() > playerOne.getPoints() + field.getLeftBombs()) {
        	return true;
        }
        return false;
    }
    
	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	private void changeTurn() {
		if (currentPlayer.equals(playerOne)) {
			currentPlayer = playerTwo;
		} else {
			currentPlayer = playerOne;
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
