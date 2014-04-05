package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import es.uam.eps.dadm.mqg.minesweeper.R;

public class StartMenuActivity extends Activity {
	
	private Button newGameButton;
	private Button settingsButton;
	private Button helpButton;
	private Button statsButton;
	private Button aboutButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_menu);
		setupButtonListeners();
	}
	
	private void setupButtonListeners() {
		newGameButton = (Button) this.findViewById(R.id.start_menu_button_new_game);
		settingsButton = (Button) this.findViewById(R.id.start_menu_button_settings);
		helpButton = (Button) this.findViewById(R.id.start_menu_button_help);
		statsButton = (Button) this.findViewById(R.id.start_menu_button_stats);
		aboutButton = (Button) this.findViewById(R.id.start_menu_button_about);
		
		newGameButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.GameActivity"));
	        }
        });
		
		settingsButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.SettingsActivity"));
	        }
        });
		
		helpButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.HelpActivity"));
	        }
        });
		
		statsButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.StatsActivity"));
	        }
        });
		
		aboutButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.AboutActivity"));
	        }
        });
	}
}