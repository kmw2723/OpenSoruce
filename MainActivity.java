package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//xml에 작성한 것을 코드로 들고옴
//Chronometer를 가지고옴
        mChronometer = (Chronometer) findViewById(R.id.chronometer_view);
//버튼 세개(시작,정지,리셋)를 가지고옴
        Button startBtn = (Button) findViewById(R.id.start_button);
        Button stopBtn = (Button) findViewById(R.id.stop_button);
        Button resetBtn = (Button) findViewById(R.id.reset_button);
//이벤트를 연결함
//엑티비티에 클릭이벤트를 직접 받도록함
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

    }
//이벤트를 처리함
    @Override
    public void onClick(View v) {    //클릭이 된 뷰가 들어옴, 버튼이 뷰를 상속받음
        switch (v.getId()) {
            case R.id.start_button:
                mChronometer.setBase(SystemClock.elapsedRealtime());  //현재 버튼이 눌러진 시점으로 시작
                mChronometer.start();  //시작함
                break;
            case R.id.stop_button:
                mChronometer.stop();   //정지함
                break;
            case R.id.reset_button:
                mChronometer.stop();   //정지하고
                mChronometer.setBase(SystemClock.elapsedRealtime());  //다시 00:00으로 바뀜(리셋)
                break;
        }
    }
}