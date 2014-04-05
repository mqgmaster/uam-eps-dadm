package es.uam.eps.dadm.mqg.minesweeper.activity;

import es.uam.eps.dadm.mqg.minesweeper.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class IntroActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.StartMenuActivity"));
		finish();
		return true;
	}
}