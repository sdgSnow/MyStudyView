package com.example.snow.mystudyview.myview.ballflow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * @author shendaogu
 * 添加ShanXinViewData的集合，N个子项就代表N个扇形
 * 传入value的值是占的比例
 * 传入name的值是该扇形名称
 * */
public class SectorView extends View {
    private int[] mColors = {Color.BLUE, Color.DKGRAY, Color.CYAN, Color.RED, Color.GREEN};
    private Paint paint;    //画笔
    private ArrayList<ShanXinViewData> shanXinViewDatas;    //数据集
    private int w;          //View宽高
    private int h;
    private RectF rectF;    //矩形

    public SectorView(Context context) {
        super(context);
        initPaint();    //设置画笔

    }

    //设置数据
    public void setData(ArrayList<ShanXinViewData> shanXinViewDatas) {
        this.shanXinViewDatas = shanXinViewDatas;
        initData();     //设置数据的百分度和角度
        invalidate();   //刷新UI
    }

    public SectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    //初始化画笔
    private void initPaint() {
        paint = new Paint();
        //设置画笔默认颜色
        paint.setColor(Color.WHITE);
        //设置画笔模式：填充
        paint.setStyle(Paint.Style.FILL);
        //
        paint.setTextSize(30);
        //初始化区域
        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //确定View大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;     //获取宽高
        this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(w / 2, h / 2);             //将画布坐标原点移到中心位置
        float currentStartAngle = 45;                //起始角度
        float r = (float) (Math.min(w, h) / 2);     //饼状图半径(取宽高里最小的值)
        rectF.set(-r, -r, r, r);                    //设置将要用来画扇形的矩形的轮廓
        for (int i = 0; i < shanXinViewDatas.size(); i++) {
            ShanXinViewData shanXinViewData = shanXinViewDatas.get(i);
            paint.setColor(shanXinViewData.color);
            //绘制扇形(通过绘制圆弧)
            canvas.drawArc(rectF, currentStartAngle, shanXinViewData.angle, true, paint);
            //绘制扇形上文字
            float textAngle = currentStartAngle + shanXinViewData.angle / 2;    //计算文字位置角度
            paint.setColor(Color.BLACK);
            float x = (float) (r / 2 * Math.cos(textAngle * Math.PI / 180));    //计算文字位置坐标
            float y = (float) (r / 2 * Math.sin(textAngle * Math.PI / 180));
            paint.setColor(Color.YELLOW);        //文字颜色
            canvas.drawText(shanXinViewData.name, x, y, paint);    //绘制文字

            currentStartAngle += shanXinViewData.angle;     //改变起始角度
        }
    }


    private void initData() {
        if (null == shanXinViewDatas || shanXinViewDatas.size() == 0) {
            return;
        }

        float sumValue = 0;                 //数值和
        for (int i = 0; i < shanXinViewDatas.size(); i++) {
            ShanXinViewData shanXinViewData = shanXinViewDatas.get(i);
            sumValue += shanXinViewData.value;
            int j = i % mColors.length;     //设置颜色
            shanXinViewData.color = mColors[j];
        }

        for (ShanXinViewData data : shanXinViewDatas) {
            float percentage = data.value / sumValue;    //计算百分比
            float angle = percentage * 360;           //对应的角度
            data.percentage = percentage;
            data.angle = angle;
        }
    }

}
