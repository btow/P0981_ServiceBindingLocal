package com.example.samsung.p0981_servicebindinglocal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String message = "";

    private boolean bound = false;
    private ServiceConnection serviceConnection;
    private Intent intent;
    private MyService myService;

    private TextView tvInterval;

    private long interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInterval = (TextView) findViewById(R.id.tvInterval);

        intent = new Intent(this, MyService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                message = "MainActivity onServiceConnected()";
                Messenger.sendAllMessage(MainActivity.this, message);
                myService = ((MyService.MyBinder) service).getService();
                interval = myService.getInterval();
                tvInterval.setText(getString(R.string.interval_) + interval);
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                message = "MainActivity onServiceDisconnected()";
                Messenger.sendAllMessage(MainActivity.this, message);
                bound = false;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, serviceConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!bound) {
            return;
        }
        unbindService(serviceConnection);
        bound = false;
    }

    public void onClickBtn(View view) {

        switch (view.getId()) {

            case R.id.btnStart :
                startService(intent);
                break;
            case R.id.btnStop :
                if (!bound) {
                    break;
                }
                stopService(intent);
                break;
            case R.id.btnUp :
                if (!bound) {
                    break;
                }
                interval = myService.upInterval(500);
                break;
            case R.id.btnDown :
                if (!bound) {
                    break;
                }
                interval = myService.downInterval(500);
                break;
            default:
                break;
        }
        tvInterval.setText(getString(R.string.interval_) + interval);

    }
}
