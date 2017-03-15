package com.sounddly.background;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    MediaPlayer mp; // 음악 재생을 위한 객체

    TimerTask adTast;
    Timer timer;

    Calendar c = Calendar.getInstance();
    String backstring="";
    int count=0;

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
        mp = MediaPlayer.create(this, R.raw.chacha);
        mp.setLooping(false); // 반복재생
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand");

        adTast = new TimerTask() {
            public void run() {
                backstring+="몇일 : " +c.get(Calendar.DAY_OF_MONTH) + ", 시 : "
                        +c.get(Calendar.HOUR_OF_DAY) + ", 분 : " + c.get(Calendar.MINUTE)
                        +", 초 : " + c.get(Calendar.SECOND)+ ", 카운터 :" + String.valueOf(count)+"\n";
                Log.d("test", backstring);

            }
        };
        timer = new Timer();
        //timer.schedule(adTast , 5000);  // 5초후 실행하고 종료
        timer.schedule(adTast, 0, 10000); // 0초후 첫실행, 3초마다 계속실행

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        Log.d("test", "서비스의 onDestroy");
        Log.d("test", backstring);
        backstring ="";
        count =0;

    }
}