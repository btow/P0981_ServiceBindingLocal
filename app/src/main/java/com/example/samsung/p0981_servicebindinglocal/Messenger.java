package com.example.samsung.p0981_servicebindinglocal;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by samsung on 21.04.2017.
 */

public class Messenger {

    private static final String LOG_TAG = "myLogs";

    public static void sendAllMessage(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, message);
    }

    public static void sendOnlyLogMessage(final String message) {
        Log.d(LOG_TAG, message);
    }

}
