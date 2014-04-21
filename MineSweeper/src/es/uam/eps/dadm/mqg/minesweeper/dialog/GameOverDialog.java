package es.uam.eps.dadm.mqg.minesweeper.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.activity.GameActivity;

public class GameOverDialog extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final GameActivity main = (GameActivity) getActivity();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.game_dialog_game_over_title);
		alertDialogBuilder.setMessage(R.string.game_dialog_game_over_message);
		alertDialogBuilder.setPositiveButton(R.string.game_dialog_game_over_yes, 
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					main.newGame();
					dialog.dismiss();
				}
		});
		alertDialogBuilder.setNegativeButton(R.string.game_dialog_game_over_no, 
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					main.finish();
					dialog.dismiss();
				}
		});
		return alertDialogBuilder.create();
		}
}