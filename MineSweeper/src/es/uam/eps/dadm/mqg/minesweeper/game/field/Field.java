package es.uam.eps.dadm.mqg.minesweeper.game.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;
import es.uam.eps.dadm.mqg.minesweeper.game.Game;
import es.uam.eps.dadm.mqg.minesweeper.game.Tile;
import es.uam.eps.dadm.mqg.minesweeper.game.Tile.Status;
import es.uam.eps.dadm.mqg.minesweeper.game.field.exception.EncodedFieldInvalid;

public class Field {
	
	private final Random random = new Random();
	private static final int COL_SIZE = 8;
    private static final int ROW_SIZE = 8;
    private static final int BOMB_SIZE = 10;
	private final List<Tile> tiles = new ArrayList<Tile>();
	private int leftBombs = BOMB_SIZE;
	private Game game;
	
	public Field(Game game) {
		this.game = game;
	}
	
	public void build() {
		clear();
		List<Integer> tileIndexes = new ArrayList<Integer>();
        
        for (int i = 0; i < COL_SIZE * ROW_SIZE; i++) {
            tiles.add(new Tile());
            tileIndexes.add(i);
        }
        
        assert(BOMB_SIZE <= tileIndexes.size());
        for (int i = 0; i < BOMB_SIZE; i++) {
            int bombIndex = random.nextInt(tileIndexes.size());
            tiles.get(tileIndexes.get(bombIndex)).setBomb(true);
            tileIndexes.remove((int)bombIndex);
        }
        
        calculeStatsForTilesWithoutBomb();
	}
	
	public void update(String data) throws EncodedFieldInvalid {
		Tile tile;
		
		for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
            	tile = getTile(row, col);
            	switch(data.charAt(row * COL_SIZE + col)) {
            		case EncodedField.TILE_FLAG_PLAYER1:
            			tile.setOwnerPlayer(game.getPlayerOne());
            			break;
            		case EncodedField.TILE_FLAG_PLAYER2:
            			tile.setOwnerPlayer(game.getPlayerTwo());
            			break;
            		case EncodedField.TILE_OPENED:
            			tile.setStatus(Status.OPENED);
            			break;
            		default :
            			throw new EncodedFieldInvalid();
            	}
            }
		}
	}
	
	public void importData(String data) throws EncodedFieldInvalid {
		clear();
		Tile tile = null;
		if (data.length() < 64) {
			throw new EncodedFieldInvalid();
		}
		
		for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COL_SIZE; col++) {
            	switch(data.charAt(row * COL_SIZE + col)) {
	            	case EncodedField.TILE_FLAG_PLAYER1:
	        			tile = new Tile();
	        			tile.setOwnerPlayer(game.getPlayerOne());
	        			break;
	        		case EncodedField.TILE_FLAG_PLAYER2:
	        			tile = new Tile();
	        			tile.setOwnerPlayer(game.getPlayerTwo());
	        			break;
	        		case EncodedField.TILE_OPENED:
	        			tile = new Tile();
	        			tile.setStatus(Status.OPENED);
	        			break;
            		case EncodedField.TILE_NORMAL_BOMB:
            			tile = new Tile();
            			tile.setBomb(true);
            			break;
            		case EncodedField.TILE_NORMAL_CLEAN:
            			tile = new Tile();
            			break;
            		default :
            			throw new EncodedFieldInvalid();
            	}
            	tiles.add(tile);
            }
		}
		
		calculeStatsForTilesWithoutBomb();
	}
	
	public String exportData() {
		StringBuffer buffer = new StringBuffer();
		for (Tile tile : tiles) {
			switch (tile.getStatus()) {
				case NORMAL		:
					if (tile.hasBomb()) {
						buffer.append(EncodedField.TILE_NORMAL_BOMB);
					} else {
						buffer.append(EncodedField.TILE_NORMAL_CLEAN);
					}
					break;
				case OPENED		: 
					buffer.append(EncodedField.TILE_OPENED);
					break;
				case FLAGGED	:
					switch(tile.getOwnerPlayer().getNumber()) {
						case ONE:
							buffer.append(EncodedField.TILE_FLAG_PLAYER1);
							break;
						case TWO:
							buffer.append(EncodedField.TILE_FLAG_PLAYER2);
						default:
						}
				default:
			}
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
        
        if (tile.getStatus() != Status.NORMAL) {
            return;
        }
        
        tile.setStatus(Status.OPENED);
        
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
