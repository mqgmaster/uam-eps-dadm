package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import es.uam.eps.dadm.mqg.minesweeper.R;

public class StartMenu extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_menu);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
}