package com.example.snow.mystudyview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.snow.mystudyview.myview.CircleProgressView;
import com.example.snow.mystudyview.myview.MoveCircleView;
import com.example.snow.mystudyview.myview.ProgressView01;
import com.example.snow.mystudyview.myview.SubsectionProgressView;
import com.example.snow.mystudyview.myview.T01CircleView;
import com.example.snow.mystudyview.myview.T02CirleView;
import com.example.snow.mystudyview.sign.SignView;
import com.example.snow.mystudyview.sign.T02View;
import com.example.snow.mystudyview.myview.T03ProgressView;
import com.example.snow.mystudyview.myview.ballflow.SectorView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int progress;
    private T01CircleView mT01CircleView;
    private T02CirleView mT02CirleView;
    private CircleProgressView mCircleProgressView;
    private T03ProgressView mT03ProgressView;
    private float number;
    private MoveCircleView mMoveCircleView;
    private SubsectionProgressView mSubsectionProgressView;
    private ProgressView01 mProgressView01;
    private String[] ranks = new String[]{"一级","二级","三级","四级","五级","六级","七级"};
    private SectorView mSectorView;
    private T02View sign;
    private SignView signView;
    private ImageView imageView;
    private final String filepath = Environment.getExternalStorageDirectory() + "/01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_start = findViewById(R.id.bt_start);
        Button bt_submit = findViewById(R.id.bt_submit);
        Button bt_reset = findViewById(R.id.bt_reset);
        signView = findViewById(R.id.signview);
        sign = findViewById(R.id.sign);
        imageView = findViewById(R.id.image);
        bt_submit.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(runnable,10);
            }
        });

//        mT01CircleView = findViewById(R.id.t01CircleView);
//        mT01CircleView.setRadio(50);
//        new Thread(mT01CircleView).start();

//        mT02CirleView = findViewById(R.id.t02CirleView);
//        mT02CirleView.setRadio(50);

//        mCircleProgressView = findViewById(R.id.circleProgressView);
//        mCircleProgressView.setLevel("V1");
//        mCircleProgressView.setMaxProgress(100);
//        handler.postDelayed(runnable,10);

//        mT03ProgressView = findViewById(R.id.t03ProgressView);
//        mT03ProgressView.setMaxProgress(100);
//        handler.postDelayed(runnable,50);
//        mT03ProgressView.setProgress(60);

//        mMoveCircleView = findViewById(R.id.moveCircleView);
//        mMoveCircleView.setRadio(50);

//        mSubsectionProgressView = findViewById(R.id.subsectionProgressView);
//        mSubsectionProgressView.setProgress(0);
//        mSubsectionProgressView.setProgressHeight(60);
//        mSubsectionProgressView.setMaxProgress(100);

//        mProgressView01 = findViewById(R.id.progressView01);
//        mProgressView01.setProgress(0);
//        mProgressView01.setProgressHeight(60);
//        mProgressView01.setRankTexts(ranks);
//        mProgressView01.setRankNum(6);
//        mProgressView01.setMaxProgress(100);

//        mSectorView = findViewById(R.id.sectorView);
//        ArrayList<ShanXinViewData> shanXinViewDataList = new ArrayList<>();
//        shanXinViewDataList.add(new ShanXinViewData(1,"一号"));
//        shanXinViewDataList.add(new ShanXinViewData(1,"二号"));
//        shanXinViewDataList.add(new ShanXinViewData(1,"三号"));
//        shanXinViewDataList.add(new ShanXinViewData(1,"四号"));
//        mSectorView.setData(shanXinViewDataList);

        //审核进度
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 50);
//            mProgressView01.setProgress(number);
            number++;
            if (number > 100) {
                handler.removeCallbacks(runnable);
                number = 0;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                signView.saveImageToFile(filepath);
                Bitmap image = signView.getImage();
                imageView.setImageBitmap(image);
                break;
            case R.id.bt_reset:
                signView.clearPath();
//                sign.reset();
                break;
        }
    }
}
