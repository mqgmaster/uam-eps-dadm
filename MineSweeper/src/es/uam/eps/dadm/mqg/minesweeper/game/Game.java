package es.uam.eps.dadm.mqg.minesweeper.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import es.uam.eps.dadm.mqg.minesweeper.game.Tile.Status;

/*
 * Essa classe representa a lógica do jogo.
 * 
 * @author Mauricio Quatrin Guerreiro
 */

public class Game {
	
	private static final int COL_SIZE = 8;
    private static final int ROW_SIZE = 8;
    private static final int BOMB_SIZE = 10;
    private final List<Tile> tiles = new ArrayList<Tile>();
    private final Random random = new Random();
    private final Player playerOne = new Player(1);
    private final Player playerTwo = new Player(2);
    private Player currentPlayer = playerOne;
    private int leftBombs = BOMB_SIZE;
    
    public void newGame() {
    	this.leftBombs = BOMB_SIZE;
    	this.currentPlayer = playerOne;
    	this.playerOne.resetPoints();
    	this.playerTwo.resetPoints();
        tiles.clear();
        
        List<Integer> titleIndices = new ArrayList<Integer>();
        
        for (int i = 0; i < COL_SIZE * ROW_SIZE; i++) {
            tiles.add(new Tile());
            titleIndices.add(i);
        }
        
        assert(BOMB_SIZE <= titleIndices.size());
        for (int i = 0; i < BOMB_SIZE; i++) {
            int bombIndex = random.nextInt(titleIndices.size());
            tiles.get(titleIndices.get(bombIndex)).setBomb(true);
            titleIndices.remove((int)bombIndex);
        }
        
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
                int neighbourBombSize = 0;
                
                // arriba
                if ((row-1) >= 0 && (col-1) >= 0) {
                    if (getTile(row-1, col-1).hasBomb()) {
                        neighbourBombSize++;
                    }
                }
                
                if ((row-1) >= 0) {
                    if (getTile(row-1, col).hasBomb()) {
                        neighbourBombSize++;
                    }
                }

                if ((row-1) >= 0 && (col+1) < COL_SIZE) {
                    if (getTile(row-1, col+1).hasBomb()) {
                        neighbourBombSize++;
                    }
                }
                
                // al lado
                if ((col-1) >= 0) {
                    if (getTile(row, col-1).hasBomb()) {
                        neighbourBombSize++;
                    }
                }

                if ((col+1) < COL_SIZE) {
                    if (getTile(row, col+1).hasBomb()) {
                        neighbourBombSize++;
                    }
                }
                
                // abajo
                if ((row+1) < ROW_SIZE && (col-1) >= 0) {
                    if (getTile(row+1, col-1).hasBomb()) {
                        neighbourBombSize++;
                    }
                }
                
                if ((row+1) < ROW_SIZE) {
                    if (getTile(row+1, col).hasBomb()) {
                        neighbourBombSize++;
                    }
                }

                if ((row+1) < ROW_SIZE && (col+1) < COL_SIZE) {
                    if (getTile(row+1, col+1).hasBomb()) {
                        neighbourBombSize++;
                    }
                } 
                
                getTile(row, col).setNeighbourBombSize(neighbourBombSize);
            }
        }
        
    }
    
    public void checkTile(int position) {
    	Tile tile = tiles.get(position);
    	if (tile.hasBomb()) {
        	currentPlayer.incPoints();
        	leftBombs--;
        	tile.setOwnerPlayer(currentPlayer);
        } else {
        	changeTurn();
        }
    }
    
    public void openAllTilesIfPossible(int position) {
        if (position < 0 || position >= tiles.size()) {
            return;
        }
        Tile tile = tiles.get(position);
        
        if (tile.getStatus() != Status.Normal) {
            return;
        }
        
        tile.setStatus(Status.Opened);
        
        int row = position / COL_SIZE;
        int col = position % COL_SIZE;
        
        if (tile.hasBomb() == false && tile.getNeighbourBombSize() == 0) {
        	
            // arriba
            if ((row-1) >= 0 && (col-1) >= 0) {
                int index = (row-1) * COL_SIZE + (col-1);
                openAllTilesIfPossible(index);
            }
            
            if ((row-1) >= 0) {
                int index = (row-1) * COL_SIZE + col;
                openAllTilesIfPossible(index);
            }

            if ((row-1) >= 0 && (col+1) < COL_SIZE) {
                int index = (row-1) * COL_SIZE + (col+1);
                openAllTilesIfPossible(index);
            }
            
            // al lado
            if ((col-1) >= 0) {
                int index = row * COL_SIZE + (col-1);
                openAllTilesIfPossible(index);
            }

            if ((col+1) < COL_SIZE) {
                int index = row * COL_SIZE + (col+1);
                openAllTilesIfPossible(index);
            }
            
            // abajo
            if ((row+1) < ROW_SIZE && (col-1) >= 0) {
                int index = (row+1) * COL_SIZE + (col-1);
                openAllTilesIfPossible(index);
            }
            
            if ((row+1) < ROW_SIZE) {
                int index = (row+1) * COL_SIZE + col;
                openAllTilesIfPossible(index);
            }

            if ((row+1) < ROW_SIZE && (col+1) < COL_SIZE) {
                int index = (row+1) * COL_SIZE + (col+1);
                openAllTilesIfPossible(index);
            } 
        }
    }
    
    public Tile getTile(int row, int col) {
        int index = row * COL_SIZE + col;
        return tiles.get(index);
    }
    
    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }
    
    public int getBombSize() {
        return BOMB_SIZE;
    }
    
    public boolean isGameOver() {
    	if (leftBombs == 0) {
    		return true;
    	}
        if (playerOne.getPoints() > playerTwo.getPoints() + leftBombs) {
        	return true;
        } else if (playerTwo.getPoints() > playerOne.getPoints() + leftBombs) {
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
