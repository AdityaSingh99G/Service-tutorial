package com.example.servicestutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button BtnStart, BtnTerminate;


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
            }
        });


        // Adding alarm service
        findViewById(R.id.btnMASetAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //using direct EditText without creating specific Variable because it is only used one time.
                int time =Integer.parseInt(((EditText)(findViewById(R.id.EdtTime))).getText().toString());

                long triggerTime = System.currentTimeMillis()+(time*1000);

                Intent iBrodcast = new Intent(MainActivity.this,MyReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,100,iBrodcast,PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP,triggerTime,pendingIntent);

            }
        });


    }
}