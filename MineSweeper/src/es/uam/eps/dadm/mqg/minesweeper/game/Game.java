package es.uam.eps.dadm.mqg.minesweeper.game;

import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.exception.EncodedFieldInvalid;
import es.uam.eps.dadm.mqg.minesweeper.game.Player.PlayerNumber;


/*
 * Essa classe representa a lógica do jogo.
 * 
 * @author Mauricio Quatrin Guerreiro
 */

public class Game {
	
	private final Player playerOne = new Player(PlayerNumber.ONE, R.drawable.blue_flag);
	private final Player playerTwo = new Player(PlayerNumber.TWO, R.drawable.green_flag);
    private Player currentPlayer;
    private Field field;
    
    public Game(String playerOneName, String playerTwoName) {
    	playerOne.setName(playerOneName);
    	playerTwo.setName(playerTwoName);
    	this.field = new Field(this);
	}
    
    public void newGame() {
    	this.currentPlayer = playerOne;
    	playerOne.resetPoints();
    	playerTwo.resetPoints();
    	try {
			field.importData("0044000000000000000000044004040000004000000400000004000000040000");
		} catch (EncodedFieldInvalid e) {
			e.printStackTrace();
		}
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
    
    public boolean isOver() {
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
