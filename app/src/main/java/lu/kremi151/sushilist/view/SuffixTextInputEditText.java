package lu.kremi151.sushilist.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.design.widget.TextInputEditText;
import android.text.TextPaint;
import android.util.AttributeSet;

import lu.kremi151.sushilist.R;

/**
 * Created by michm on 18.02.2018.
 */

public class SuffixTextInputEditText extends TextInputEditText {
    private TextPaint textPaint = new TextPaint();
    private String suffix = "";
    private float suffixPadding;

    public SuffixTextInputEditText(Context context) {
        super(context);
    }

    public SuffixTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs, 0);
    }

    public SuffixTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, 0);
    }

    @Override
    public void onDraw(Canvas c){
        super.onDraw(c);
        int suffixXPosition = (int) textPaint.measureText(getText().toString()) + getPaddingLeft();
        c.drawText(suffix, Math.max(suffixXPosition, suffixPadding), getBaseline(), textPaint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextSize(getTextSize());
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SuffixTextInputEditText, defStyleAttr, 0);
        if(a != null) {
            suffix = a.getString(R.styleable.SuffixTextInputEditText_suffix);
            if(suffix == null) {
                suffix = "";
            }
            suffixPadding = a.getDimension(R.styleable.SuffixTextInputEditText_suffixPadding, 0);
        }
        a.recycle();
    }
}
