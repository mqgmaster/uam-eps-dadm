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
	
	private boolean intro = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
	}
	
	//touch eh executado tantas vezes quantos toques percebe o android// perigo
	public boolean onTouchEvent(MotionEvent event) {
		if (!intro) {
			intro = true;
			startActivity(new Intent("es.uam.eps.dadm.mqg.minesweeper.activity.StartMenuActivity"));
			ConnectivityManager connMgr = (ConnectivityManager) 
			getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
			boolean isWifiConn = networkInfo.isConnected();
			networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			boolean isMobileConn = networkInfo.isConnected();
			Log.d("HOLA", "Wifi connected: " + isWifiConn);
			Log.d("HOLA", "Mobile connected: " + isMobileConn);
			finish(); 
		}
		
		return true;
	}
}