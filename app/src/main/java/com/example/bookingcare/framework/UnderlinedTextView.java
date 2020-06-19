package com.example.bookingcare.framework;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.bookingcare.R;

public class UnderlinedTextView extends AppCompatTextView {
    public static final int GRAVITY_CENTER = 0;
    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_RIGHT = 2;


    private Rect mRect;
    private Paint mPaint;
    private float mStrokeWidth;
    private float mSpacing;
    private float mLength;
    private int mGravity;

    public UnderlinedTextView(Context context) {
        this(context, null, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyle) {

        float density = context.getResources().getDisplayMetrics().density;

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UnderlinedTextView, defStyle, 0);
        int underlineColor = typedArray.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
        mStrokeWidth = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2);
        mSpacing = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineSpacing, density * 5);
        mLength = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineLength, density * 20);
        mGravity = typedArray.getInt(R.styleable.UnderlinedTextView_underlineGravity, GRAVITY_CENTER);
        typedArray.recycle();

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(underlineColor);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    public int getUnderLineColor() {
        return mPaint.getColor();
    }

    public void setUnderLineColor(int mColor) {
        mPaint.setColor(mColor);
        invalidate();
    }

    public float getUnderlineWidth() {
        return mStrokeWidth;
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {

//        int count = getLineCount();

        final Layout layout = getLayout();
        float x_start, x_stop, x_diff;
        int firstCharInLine, lastCharInLine;

//        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(0, mRect);
            firstCharInLine = layout.getLineStart(0);
            lastCharInLine = layout.getLineEnd(0);


        float start = layout.getPrimaryHorizontal(firstCharInLine);
        float stop = layout.getPrimaryHorizontal(lastCharInLine) ;

            switch (mGravity){
                case GRAVITY_LEFT:
                    x_start = layout.getPrimaryHorizontal(firstCharInLine);
                    x_stop = x_start + mLength;
                    break;
                case GRAVITY_RIGHT:
                    x_stop = stop;
                    x_start = x_stop - mLength;
                    break ;
                case GRAVITY_CENTER:
                    x_start = (stop - start - mLength) / 2;
                    x_stop = x_start + mLength;
                    break;
                default:
                    x_start = start;
                    x_stop = stop;
            }



            canvas.drawLine(x_start, baseline + mStrokeWidth + mSpacing , x_stop, baseline + mStrokeWidth + mSpacing, mPaint);


        super.onDraw(canvas);
    }
}
