package com.example.servicestutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.SynchronousQueue;

import kotlin.jvm.Synchronized;

public class MainActivity extends AppCompatActivity {

    Button BtnStart, BtnTerminate;
    Runnable runnable;
    String Tag = "laura";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnStart = findViewById(R.id.btnMAstart);
        BtnTerminate = findViewById(R.id.btnMAdestroy);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, MusicService.class));
            }
        });

        BtnTerminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MusicService.class));
                Toast.makeText(MainActivity.this, "Terminate Btn Thread name: " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
            }
        });


        // Adding alarm service
        findViewById(R.id.btnMASetAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //using direct EditText without creating specific Variable because it is only used one time.
                int time = Integer.parseInt(((EditText) (findViewById(R.id.EdtTime))).getText().toString());

                long triggerTime = System.currentTimeMillis() + (time * 1000);

                Intent iBrodcast = new Intent(MainActivity.this, MyReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 100, iBrodcast, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);

            }
        });


        runnable = new Runnable() {
            final Handler handler = new Handler();

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(10000);


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

//                   new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                          //  Toast.makeText(MainActivity.this, "Name of new Thread is : " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, "Name of new Thread is : " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //  Toast.makeText(MainActivity.this, "Name of new Thread is : " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Name of new Thread is -> " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        };

        Thread thread = new Thread(runnable);
        // note app will not  run after 10sec  becoz dusre thread se dusre thread ko access kar rhe ho
        thread.start();


//        Handler handler1 = new Handler();
//       new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (this){
//                    try {
//                        wait(10000);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });
//


//        synchronized (this){
//            try {
//                wait(10000);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }


    }
}