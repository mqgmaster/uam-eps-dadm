package es.uam.eps.dadm.mqg.minesweeper.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import es.uam.eps.dadm.mqg.minesweeper.R;

public class SettingsFragment extends PreferenceFragment {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}