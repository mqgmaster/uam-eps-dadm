package es.uam.eps.dadm.mqg.minesweeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.game.Tile;
import es.uam.eps.dadm.mqg.minesweeper.game.field.Field;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class FieldAdapter extends ArrayAdapter<Tile> {
    private Context mContext;
    private List<Tile> tiles;
    public final int[] colors = {0xff839098, 0xffF7D842, 0xffF76D3C, 
    		0xffF15F74, 0xff913CCD, 0xff2CA8C2, 0xff98CB4A, 0xff5481E6};
    
    public FieldAdapter(Context c, Field field) {
        super(c, R.layout.tile, field.getTiles());
        mContext = c;
        this.tiles = field.getTiles();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            textView = (TextView)inflater.inflate(R.layout.tile, null);
        } else {
            textView = (TextView) convertView;
        }

        Tile tile = tiles.get(position);
        
        if (tile.getStatus() == Tile.Status.OPENED) {
            int size = tile.getNeighbourBombSize();
            textView.setBackgroundColor(0xffffffff);
            
            if (size == 0) {
                textView.setText("");
            } else {
                textView.setText("" + size);
                textView.setTextColor(colors[size - 1]);
            }
        } else if (tile.getStatus() == Tile.Status.NORMAL) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else if (tile.getStatus() == Tile.Status.FLAGGED) {
			textView.setCompoundDrawablesWithIntrinsicBounds(tile.getOwnerPlayer().getFlagResource(), 0, 0, 0);
        }
        
        return textView;
    }
}
