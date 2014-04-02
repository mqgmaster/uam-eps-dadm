package es.uam.eps.dadm.mqg.minesweeper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/*
 * @author Mauricio Quatrin Guerreiro
 */

public class SquareTextView extends TextView {

    public SquareTextView(Context context) {
        super(context);
    }
    
    public SquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public SquareTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
