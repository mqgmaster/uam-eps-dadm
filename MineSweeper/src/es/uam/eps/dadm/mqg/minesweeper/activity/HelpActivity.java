package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import es.uam.eps.dadm.mqg.minesweeper.R;

public class HelpActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.StartMenuActivity"));
		return true;
	}
}