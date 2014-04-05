package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.settings.SettingsFragment;

public class SettingsActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		SettingsFragment fragment = new SettingsFragment();
		fragmentTransaction.replace(android.R.id.content, fragment);
		fragmentTransaction.commit();
	}
}