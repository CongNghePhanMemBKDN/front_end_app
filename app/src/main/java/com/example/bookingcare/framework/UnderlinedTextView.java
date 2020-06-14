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

    private Rect mRect;
    private Paint mPaint;
    private float mStrokeWidth;

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
        mStrokeWidth = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 15);
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

        Path path = new Path();
        path.moveTo(20, 20);
        path.lineTo(100,20);
        path.lineTo(130, 70);
        path.lineTo(20, 70);
        path.lineTo(20, 20);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        PathShape pathShape = new PathShape(path,getWidth() + 15 ,getHeight() + 15);

        ShapeDrawable shapeDrawable = new ShapeDrawable(pathShape);
        shapeDrawable.getPaint().set(paint);
        shapeDrawable.setBounds(0, 0, getWidth() + 15, getHeight() + 15);
        shapeDrawable.draw(canvas);

        final Layout layout = getLayout();
        float x_start, x_stop, x_diff;
        int firstCharInLine, lastCharInLine;

            int baseline = getLineBounds(0, mRect);
            firstCharInLine = layout.getLineStart(0);
            lastCharInLine = 5;

            x_start = layout.getPrimaryHorizontal(firstCharInLine);
            x_diff = layout.getPrimaryHorizontal(firstCharInLine + 1) - x_start;
            x_stop = layout.getPrimaryHorizontal(lastCharInLine - 1) + x_diff;

            canvas.drawLine(x_start, baseline + mStrokeWidth +25 , x_stop, baseline + mStrokeWidth, mPaint);


        super.onDraw(canvas);
    }
}
