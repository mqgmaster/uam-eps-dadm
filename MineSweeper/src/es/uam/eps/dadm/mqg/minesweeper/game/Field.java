package es.uam.eps.dadm.mqg.minesweeper.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

import es.uam.eps.dadm.mqg.minesweeper.game.Tile.Status;

public class Field {
	
	private final Random random = new Random();
	private static final int COL_SIZE = 8;
    private static final int ROW_SIZE = 8;
    private static final int BOMB_SIZE = 10;
	private final List<Tile> tiles = new ArrayList<Tile>();
	private int leftBombs = BOMB_SIZE;
	
	public void installBombs() {
		clear();
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
        
        calculeStatsForTilesWithoutBomb();
	}
	
	public void installBombs(String exportedData) {
		clear();
		
		for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
            	if(exportedData.charAt(row * COL_SIZE + col) == '0') {
            		tiles.add(new Tile());
            	} else {
            		tiles.add(new Tile(true));
            	}
            }
		}
		
		calculeStatsForTilesWithoutBomb();
	}
	
	public String exportData() {
		StringBuffer buffer = new StringBuffer();
		for (Tile tile : tiles) {
			if (tile.hasBomb()) {
				buffer.append(1);
			}
			buffer.append(0);
        }
		Log.d("HOLA", "field: " + buffer.toString());
		return buffer.toString();
	}
	
	private void calculeStatsForTilesWithoutBomb() {
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
	
	public void clear() {
		tiles.clear();
	}
	
	public void decLeftBombs() {
		leftBombs--;
	}

	public int getLeftBombs() {
		return leftBombs;
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
    
    public Tile getTile(int index) {
    	return tiles.get(index);
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
}
