package com.example.snow.mystudyview.myview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class T03ProgressView extends View {

    private Paint mPaint;
    private float mWidth,mHeight;
    private float mProgress,mMaxProgress;
    private int[] RATE_COLORS = {0xFFbb59ff,0xFF44dcfc};
    private float radius;
    private float progressWidth;

    public T03ProgressView(Context context) {
        this(context,null);
    }

    public T03ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = this.getMeasuredHeight() / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWidth = this.getMeasuredWidth();
        mHeight = this.getMeasuredHeight();

        //背景
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, mWidth, mHeight), radius, radius, mPaint);

        //进度条
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        LinearGradient gradient = new LinearGradient(mWidth /2,0, mWidth /2, mHeight,RATE_COLORS,null, Shader.TileMode.MIRROR);
        mPaint.setShader(gradient);
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        canvas.drawRoundRect(new RectF(0, 0, this.getMeasuredWidth() * mProgress / 100f, mHeight), radius, radius, mPaint);

        //进度
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(this.getMeasuredHeight() / 1.2f);
        String text = "" + mProgress + "%";
        float x = mWidth * mProgress / 100f - mPaint.measureText(text) - 10;
        float y = mHeight / 2f - mPaint.getFontMetrics().ascent / 2f - mPaint.getFontMetrics().descent / 2f;
        canvas.drawText(text, x, y, mPaint);

    }

    public float getProgress() {
        return mProgress;
    }

    /*设置进度条进度, 外部调用*/
    public void setProgress(int progress) {
        if (progress > 100) {
            this.mProgress = 100;
        } else if (progress < 0) {
            this.mProgress = 0;
        } else {
            this.mProgress = progress;
        }
        postInvalidate();
    }

    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        mMaxProgress = maxProgress;
    }

    public void setProgressWidth(float width){
        this.progressWidth = width;
    }
}
