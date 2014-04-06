package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.database.DatabaseAdapter;
import es.uam.eps.dadm.mqg.minesweeper.database.Match;

public class StatsActivity extends Activity {
	
	private static final String[] DB_FIELDS = {
		    Match.PLAYER1_NAME, Match.PLAYER1_SCORE, Match.PLAYER2_NAME, Match.PLAYER2_SCORE
	};
	private static final int[] VIEW_FIELDS = {
		    R.id.stats_list_row_p1_name, R.id.stats_list_row_p1_score,
		    R.id.stats_list_row_p2_name, R.id.stats_list_row_p2_score
	};
	
	private ListView mListView;
	private SimpleCursorAdapter mAdapter;
	private DatabaseAdapter databaseAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		databaseAdapter = new DatabaseAdapter(this);
		mListView = (ListView) findViewById(R.id.stats_list);
		databaseAdapter.open();
		mAdapter = new SimpleCursorAdapter(
		        this,                			// Current context
		        R.layout.stats_list_row, 		// Layout for a single row
		        databaseAdapter.getAll(),      	// 
		        DB_FIELDS,        				// Cursor columns to use
		        VIEW_FIELDS,           			// Layout fields to use
		        0                    			// No flags
		);
		mListView.setAdapter(mAdapter);
		databaseAdapter.close();
	}
}