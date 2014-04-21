package es.uam.eps.dadm.mqg.minesweeper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import es.uam.eps.dadm.mqg.minesweeper.R;

public class IntroActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
	}
	
	//touch eh executado tantas vezes quantos toques percebe o android// perigo
	public boolean onTouchEvent(MotionEvent event) {
		startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.StartMenuActivity"));
		boolean wifiConnected = false; 
		ConnectivityManager connMgr = (ConnectivityManager) 
		getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		boolean isWifiConn = networkInfo.isConnected();
		if (networkInfo != null && networkInfo.isConnected()) {
			wifiConnected = true;
		} else {
			wifiConnected = false;
		}
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = networkInfo.isConnected();
		Log.d("HOLA", "Wifi connected: " + isWifiConn);
		Log.d("HOLA", "Mobile connected: " + isMobileConn);
		finish();
		return true;
	}
}