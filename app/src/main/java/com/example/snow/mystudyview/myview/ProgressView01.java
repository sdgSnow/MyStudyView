package com.example.snow.mystudyview.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ProgressView01 extends View {

    private float mWidth,mHeight;//view的宽高
    private Paint mPaint;
    private float mProHeight = 60;//进度条高度
    private float mTextHeight;//等级高度
    private float mProgress,mMaxProgress;
    private float radius;
    private float textRadius;
    private float rankTextX,rankTextY;//等级的起始坐标
    private String[] ranks = new String[]{"一级","二级","三级","四级","五级"};
    private String[] rankTexts = new String[]{};
    private int rankNum;
    private float waterRadio = 30;

    public ProgressView01(Context context) {
        super(context,null);
    }

    public ProgressView01(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔样式，描边
        mHeight = mProHeight + mTextHeight + waterRadio;
        textRadius = 30;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = this.getMeasuredWidth();
        radius = mWidth/5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制顶部圆形
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mWidth * mProgress / 100f-waterRadio,waterRadio,waterRadio,mPaint);

        //绘制进度条背景
        mPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(new RectF(0,waterRadio*2,mWidth,mProHeight+waterRadio*2),radius,radius,mPaint);

        //进度条
        mPaint.setColor(Color.GREEN);
        canvas.drawRoundRect(new RectF(0, waterRadio*2, mWidth * mProgress / 100f, mProHeight+waterRadio*2), radius, radius, mPaint);

        //绘制等级文字
        drawRank(rankNum,canvas);

    }

    //绘制等级
    public void drawRank(int rankNum,Canvas canvas){
        for (int i = 0;i<=rankNum;i++) {
            mPaint.setColor(Color.RED);
            float fontWidth = getFontWidth(mPaint, rankTexts[i]);
            float textWidth = mWidth/rankNum;
            if(i == 0){
                canvas.drawText(rankTexts[i],0,mProHeight + textRadius + waterRadio*2,mPaint);
            }
            float fontHeight = getFontHeight(mPaint);
            canvas.drawText(rankTexts[i],textWidth*i - fontWidth,mProHeight + textRadius + waterRadio*2,mPaint);
        }
    }

    /**
     * 设置进度条高度
     * */
    public void setProgressHeight(float height){
        if(height < 10){
            mProHeight = 10;
        } else {
            this.mProHeight = height;
        }
    }

    public void setRankNum(int rankNum){
        this.rankNum = rankNum;
    }
    public void setRankTexts(String[] rankTexts){
        this.rankTexts = rankTexts;
    }

    public float getProgress() {
        return mProgress;
    }

    /*设置进度条进度, 外部调用*/
    public void setProgress(float progress) {
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

    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }
}
