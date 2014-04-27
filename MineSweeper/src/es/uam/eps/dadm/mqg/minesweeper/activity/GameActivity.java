package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.adapter.FieldAdapter;
import es.uam.eps.dadm.mqg.minesweeper.database.DatabaseAdapter;
import es.uam.eps.dadm.mqg.minesweeper.database.Match;
import es.uam.eps.dadm.mqg.minesweeper.dialog.GameOverDialog;
import es.uam.eps.dadm.mqg.minesweeper.game.Game;
import es.uam.eps.dadm.mqg.minesweeper.game.Player;
import es.uam.eps.dadm.mqg.minesweeper.game.Tile;
import es.uam.eps.dadm.mqg.minesweeper.settings.Settings;

/*
 * Essa classe representa a interface inicial
 * do jogo.
 * 
 * @author Mauricio Quatrin Guerreiro
 */

public class GameActivity extends Activity {
	
    private Game gameEngine;
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
        setContentView(R.layout.game);        
        setFieldsInView();
        gameEngine = new Game(getLocalPlayerName(), getDefaultPlayerName());
        newGame();
        
        statusGameText.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            newGame();
	        }
        });
        
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
            	if (gameEngine.isOver()) {
            		return;
            	}
                final FieldAdapter fieldAdapter = (FieldAdapter) gridView.getAdapter();
                Tile tile = fieldAdapter.getItem(position);
                
                if (tile.getStatus() == Tile.Status.NORMAL) {
                	gameEngine.openTile(position);
                	fieldAdapter.notifyDataSetChanged();
                    setPoints(gameEngine.getPlayerOne(), gameEngine.getPlayerTwo());
                    setTurn(gameEngine.getCurrentPlayer());
                } 
                if (gameEngine.isOver()) {
                	setMainMessage(R.string.game_over);
                	showWinner();
                	showGameOverDialog();
            	}
            }
        });
    }

	private String getLocalPlayerName() {
		SharedPreferences sharedPreferences = 
		PreferenceManager.getDefaultSharedPreferences(this);
		return sharedPreferences.getString(Settings.PLAYER_NAME, Settings.PLAYER_NAME_DEFAULT);
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
    	playerOneText.setText(p1.getName() + " " + p1.getPoints());
        playerTwoText.setText(p2.getName() + " " + p2.getPoints());
	}

	public String getDefaultPlayerName() {
		return getResources().getString(R.string.player);
	}
    
    private void showWinner() {
    	if (gameEngine.getPlayerOne().getPoints() > gameEngine.getPlayerTwo().getPoints()) {
    		setTurn(gameEngine.getPlayerOne());
    	} else if (gameEngine.getPlayerOne().getPoints() < gameEngine.getPlayerTwo().getPoints()) {
    		setTurn(gameEngine.getPlayerTwo());
    	} else {
    		setMatchDrawn();
    	}
    	DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
    	databaseAdapter.open();
    	databaseAdapter.insertMatch(new Match(
    			gameEngine.getPlayerOne().getName(), 
    			gameEngine.getPlayerOne().getPoints(), 
    			"Player 2", 
    			gameEngine.getPlayerTwo().getPoints()));
    	databaseAdapter.close();
    }
    
    private void setTurn(Player player) {
	    switch(player.getNumber()) {
		    case ONE:
		    	playerOneText.setBackgroundColor(BLUE);
				playerTwoText.setBackgroundColor(GRAY);
				break;
		    case TWO:
		    	playerOneText.setBackgroundColor(GRAY);
				playerTwoText.setBackgroundColor(GREEN);
	    } 
	}
    
    public void showGameOverDialog (){
    	new GameOverDialog().show(getFragmentManager(), "ALERT DIALOG"); 
	}
    
    private void setMatchDrawn() {
    	playerOneText.setBackgroundColor(BLUE);
		playerTwoText.setBackgroundColor(GREEN);
    }

	public void newGame() {
		gameEngine.newGame();
		FieldAdapter fieldAdapter = new FieldAdapter(this, gameEngine.getField());
		gridView.setAdapter(fieldAdapter);
		setMainMessage(R.string.game_started);
		setPoints(gameEngine.getPlayerOne(), gameEngine.getPlayerTwo());
		setTurn(gameEngine.getPlayerOne());
		fieldAdapter.notifyDataSetChanged();
	}
}
