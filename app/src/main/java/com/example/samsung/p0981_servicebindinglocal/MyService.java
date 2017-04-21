package com.example.samsung.p0981_servicebindinglocal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private String message = "";

    private final MyBinder myBinder = new MyBinder();

    private Timer timer;
    private TimerTask timerTask;
    private long interval = 1000;

    private void schedule() {
        if (timerTask != null) {
            timerTask.cancel();
        }
        if (interval > 0) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    message = "schedule() -> TimerTask().run()";
                    Messenger.sendOnlyLogMessage(message);
                }
            };
            timer.schedule(timerTask, 1000, interval);
        }
    }

    public long getInterval() {
        return interval;
    }

    public long upInterval(final long gap) {
        interval += gap;
        schedule();
        return interval;
    }

    public long downInterval(final long gap) {
        interval -= gap;
        if (interval < 0) {
            interval = 0;
        }
        schedule();
        return interval;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        message = "MyService onCreate()";
        Messenger.sendAllMessage(this, message);
        timer = new Timer();
        schedule();
    }

    @Override
    public IBinder onBind(Intent intent) {
        message = "MyService onBind()";
        Messenger.sendAllMessage(this, message);
        return myBinder;
    }

    public class MyBinder extends Binder {

        MyService getService() {
            return MyService.this;
        }
    }
}
