package com.sounddly.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;



public class MyService extends Service {
    String backstring="";
    customhandler handler;
    channelHandler channelhandler;
    int count=0;

    int channel =0;

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

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand");

        handler = new customhandler();
        channelhandler = new channelHandler();



        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                Message msg2 = channelhandler.obtainMessage();
                channelhandler.sendMessage(msg2);
            }
        };
        Timer timer = new Timer();
        Timer timer2= new Timer();
        Timer timer3= new Timer();
        Timer timer4= new Timer();
        Timer timer5= new Timer();
        Timer timer6= new Timer();

        Timer change =new Timer();

        timer.schedule(gettimetask(), 0, 60000);
        timer2.schedule(gettimetask(), 10000, 60000);
        timer3.schedule(gettimetask(), 20000, 60000);
        timer4.schedule(gettimetask(), 30000, 60000);
        timer5.schedule(gettimetask(), 40000, 60000);
        timer6.schedule(gettimetask(), 50000, 60000);
        change.schedule(timerTask2, 5000, 10000);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        Log.d("test", "서비스의 onDestroy");

    }

    public class customhandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //이곳에 실행할 작업내용을 넣습니다. (메인 스레드 작업이 가능!)
            Calendar c = Calendar.getInstance();
            backstring+= c.get(Calendar.DAY_OF_MONTH) + "일,  "
                    +c.get(Calendar.HOUR_OF_DAY) + "시 , " + c.get(Calendar.MINUTE)
                    +"분, " + c.get(Calendar.SECOND)+ "초, " + String.valueOf(count++)+"번," +
                    String.valueOf(channel) + "채널 \n";
            Toast.makeText(MyService.this, backstring, Toast.LENGTH_SHORT).show();

            Log.d("test",backstring);
        }
    }
    public class channelHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //이곳에 실행할 작업내용을 넣습니다. (메인 스레드 작업이 가능!)
            if(channel == 5) channel =0;
            else channel++;

        }
    }
    public TimerTask gettimetask(){
        return new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };
    }
}