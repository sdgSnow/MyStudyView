package com.example.snow.mystudyview.myview.ballflow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 实现流量球第三步，实现一个无限滚动的水波纹球并显示进度
 * @author shendaogu
 * */
public class BallFlowView03 extends View {

    private int mWidth,mHeight;//view的宽高
    private Point mStartPoint;//起点位置
    private int cycleWidth;//sin曲线 1/4个周期的宽度
    private int waveHeight;//sin曲线振幅的高度
    private Path mPath;
    private Paint mPaint;
    private Paint mCirclePaint;
    private int progress;//当前进度
    private boolean mAutoIncrement = true;
    private Paint mTextPaint;

    public BallFlowView03(Context context) {
        super(context,null);
    }

    public BallFlowView03(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(dip2px(context, 5));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.parseColor("#FF4081"));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(dip2px(context, 20));
        mTextPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getViewSize(800,widthMeasureSpec);
        mHeight = getViewSize(400,heightMeasureSpec);
        //获取起点坐标
        mStartPoint = new Point(-cycleWidth * 3, mHeight / 2);

        cycleWidth = mWidth/4;
        waveHeight = 50;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //裁剪画布为圆形
        Path circlePath = new Path();
        circlePath.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CW);
        canvas.clipPath(circlePath);
        canvas.drawPaint(mCirclePaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mCirclePaint);
        //以下操作都是在这个圆形画布中操作
        //根据进度改变起点坐标的y值
        mStartPoint.y = (int) (mHeight - (progress / 100.0 * mHeight));

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

        drawText(canvas, mTextPaint, "当前"+progress + "%");

        //初始化的时候将起点移至屏幕外一个周期
        //继续在ondraw()函数最后追加平移代码
        //判断是不是平移完了一个周期
        if (mStartPoint.x + 40 >= 0) {
            //满了一个周期则恢复默认起点继续平移
            mStartPoint.x = -cycleWidth * 4;
        }
        //每次波形的平移量 40
        mStartPoint.x += 40;

        //通过控制进度，让水波纹动起来
        if (mAutoIncrement) {
            if (progress >= 100) progress = 0;
            else { progress++; }
        }

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

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, Paint paint, String text) {
        //画布的大小
        Rect targetRect = new Rect(0, 0, mWidth, mHeight);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, targetRect.centerX(), baseline, paint);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置振幅高度
     *
     * @param waveHeight 振幅
     */
    public void setWaveHeight(int waveHeight) {
        this.waveHeight = waveHeight;
        invalidate();
    }

    /**
     * 设置sin曲线 1/4个周期的宽度
     *
     * @param cycle 1/4个周期的宽度
     */
    public void setCycle(int cycle) {
        this.cycleWidth = cycle;
        invalidate();
    }

    /**
     * 设置当前进度
     *
     * @param progress 进度
     */
    public void setProgress(int progress) {
        if (progress > 100 || progress < 0)
            throw new RuntimeException(getClass().getName() + "请设置[0,100]之间的值");
        this.progress = progress;
        mAutoIncrement = false;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }
}
