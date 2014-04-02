package es.uam.eps.dadm.mqg.minesweeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.uam.eps.dadm.mqg.minesweeper.R;
import es.uam.eps.dadm.mqg.minesweeper.domain.Tile;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class TileAdapter extends ArrayAdapter<Tile> {
    private Context mContext;
    private List<Tile> tiles;
    public final int[] colors = {0xff839098, 0xffF7D842, 0xffF76D3C, 
    		0xffF15F74, 0xff913CCD, 0xff2CA8C2, 0xff98CB4A, 0xff5481E6};
    
    public TileAdapter(Context c, List<Tile> tiles) {
        super(c, R.layout.tile, tiles);
        mContext = c;
        this.tiles = tiles;
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
        
        Tile.Status status = tile.getStatus();
        
        if (status == Tile.Status.Opened) {
            if (tile.hasBomb()) {
            	switch (tile.getOwnerPlayerId()) {
				case 1:
					textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.blue_flag, 0, 0, 0);
					break;
				case 2:
					textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_flag, 0, 0, 0);
				default:
					break;
				}
            	
                return textView;
            }
            
            int size = tile.getNeighbourBombSize();
            textView.setBackgroundColor(0xffffffff);
            
            if (size == 0) {
                textView.setText("");
            } else {
                textView.setText("" + size);
                textView.setTextColor(colors[size - 1]);
            }
        } else if (status == Tile.Status.Normal) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        
        return textView;
    }
}
