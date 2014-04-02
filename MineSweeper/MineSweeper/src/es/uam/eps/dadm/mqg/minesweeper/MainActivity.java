package es.uam.eps.dadm.mqg.minesweeper;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import es.uam.eps.dadm.mqg.minesweeper.adapter.TileAdapter;
import es.uam.eps.dadm.mqg.minesweeper.domain.Player;
import es.uam.eps.dadm.mqg.minesweeper.domain.Tile;
import es.uam.eps.dadm.mqg.minesweeper.game.Game;

/*
 * Essa classe representa a interface inicial
 * do jogo.
 * 
 * @author Mauricio Quatrin Guerreiro
 */

public class MainActivity extends Activity {
	
    private Game gameEngine = new Game();
    private TextView playerOneText;
    private TextView playerTwoText;
    private TextView statusGameText;
    private GridView gridView;
    
    public static final int GRAY = 0xff9C9C9C;
    public static final int BLUE = 0xff6B8EFF;
    public static final int GREEN = 0xff66BD60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);        
        setFieldsInView();
        setMainMessage(R.string.game_started);
        
        gameEngine.newGame();
        setPoints(gameEngine.getPlayerOne(), gameEngine.getPlayerTwo());
        setTurn(gameEngine.getCurrentPlayer());
        
        List<Tile> tiles = gameEngine.getTiles();
        TileAdapter tileAdapter = new TileAdapter(this, tiles);
        gridView.setAdapter(tileAdapter);
        
        statusGameText.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            gameEngine.newGame();
	            TileAdapter tileAdapter = new TileAdapter(MainActivity.this, gameEngine.getTiles());
	            gridView.setAdapter(tileAdapter);
	            setMainMessage(R.string.game_started);
	            setPoints(gameEngine.getPlayerOne(), gameEngine.getPlayerTwo());
	        }
        });
        
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
            	if (gameEngine.isGameOver()) {
            		return;
            	}
                final TileAdapter tileAdapter = (TileAdapter)gridView.getAdapter();
                Tile tile = tileAdapter.getItem(position);
                
                if (tile.getStatus() == Tile.Status.Normal) {
                	gameEngine.checkTile(position);
                	gameEngine.openAllTilesIfPossible(position);
                    tileAdapter.notifyDataSetChanged();
                    setPoints(gameEngine.getPlayerOne(), gameEngine.getPlayerTwo());
                    setTurn(gameEngine.getCurrentPlayer());
                } 
                if (gameEngine.isGameOver()) {
                	setMainMessage(R.string.game_over);
                	showWinner();
            	}
            }

			
        });
    }

	private void setMainMessage(int resource) {
		statusGameText.setText(getResources().getString(resource));
	}

	private void setFieldsInView() {
		playerOneText = (TextView)this.findViewById(R.id.player_one);
        playerTwoText = (TextView)this.findViewById(R.id.player_two); 
        statusGameText = (TextView)this.findViewById(R.id.status_text_view);
        gridView = (GridView)this.findViewById(R.id.grid_view);
	}
    
    private void setPoints(Player p1, Player p2) {
    	playerOneText.setText(getResources().getString(R.string.player) + " 1 " + p1.getPoints());
        playerTwoText.setText(getResources().getString(R.string.player) + " 2 " + p2.getPoints());
	}
    
    private void showWinner() {
    	if (gameEngine.getPlayerOne().getPoints() > gameEngine.getPlayerTwo().getPoints()) {
    		setTurn(gameEngine.getPlayerOne());
    	} else if (gameEngine.getPlayerOne().getPoints() < gameEngine.getPlayerTwo().getPoints()) {
    		setTurn(gameEngine.getPlayerTwo());
    	} else {
    		setMatchDrawn();
    	}
    }
    
    private void setTurn(Player player) {
	    switch(player.getId()) {
		    case 1:
		    	playerOneText.setBackgroundColor(BLUE);
				playerTwoText.setBackgroundColor(GRAY);
				break;
		    case 2:
		    	playerOneText.setBackgroundColor(GRAY);
				playerTwoText.setBackgroundColor(GREEN);
	    } 
	}
    
    private void setMatchDrawn() {
    	playerOneText.setBackgroundColor(BLUE);
		playerTwoText.setBackgroundColor(GREEN);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
