package com.example.snow.mystudyview.myview.ballflow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 实现流量球第一步，实现一个无限滚动的水波纹
 * @author shendaogu
 * */
public class BallFlowView01 extends View {

    private int mWidth,mHeight;//view的宽高
    private Point mStartPoint;//起点位置
    private int cycleWidth;//sin曲线 1/4个周期的宽度
    private int waveHeight;//sin曲线振幅的高度
    private Path mPath;
    private Paint mPaint;

    public BallFlowView01(Context context) {
        super(context,null);
    }

    public BallFlowView01(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getViewSize(800,widthMeasureSpec);
        mHeight = getViewSize(400,heightMeasureSpec);
        //获取起点坐标
        mStartPoint = new Point(-cycleWidth * 3, mHeight / 2);

        cycleWidth = mWidth/4;
        waveHeight = 200;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(mStartPoint.x,mStartPoint.y);
        int j = 1;
        //循环绘制正弦曲线 循环一次半个周期
        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                //波峰
                mPath.quadTo(mStartPoint.x + (cycleWidth * j), mStartPoint.y + waveHeight,
                        mStartPoint.x + (cycleWidth * 2) * i, mStartPoint.y);
            } else {
                //波谷
                mPath.quadTo(mStartPoint.x + (cycleWidth * j), mStartPoint.y - waveHeight,
                        mStartPoint.x + (cycleWidth * 2) * i, mStartPoint.y);
            }
            j += 2;
        }
        //绘制封闭的曲线
        mPath.lineTo(mWidth, mHeight);//右下角
        mPath.lineTo(mStartPoint.x, mHeight);//左下角
        mPath.lineTo(mStartPoint.x, mStartPoint.y);//起点
        mPath.close();
        canvas.drawPath(mPath,mPaint);

        //初始化的时候将起点移至屏幕外一个周期
        //继续在ondraw()函数最后追加平移代码
        //判断是不是平移完了一个周期
        if (mStartPoint.x + 40 >= 0) {
            //满了一个周期则恢复默认起点继续平移
            mStartPoint.x = -cycleWidth * 4;
        }
        //每次波形的平移量 40
        mStartPoint.x += 40;
        postInvalidateDelayed(150);
        mPath.reset();
    }

    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        //获取测量模式
        int mode = MeasureSpec.getMode(measureSpec);
        //获取大小
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY: //如果是固定的大小，那就不要去改变它
                viewSize = size;
                break;
        }
        return viewSize;
    }
}
