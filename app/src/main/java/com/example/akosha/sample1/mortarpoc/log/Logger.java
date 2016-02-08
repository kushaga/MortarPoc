package com.example.akosha.sample1.mortarpoc.log;

import android.util.Log;

/**
 * Created by kushagarlall on 31/01/16.
 */
public class Logger {
    public Logger() {
    }

    /**
     * @param TAG
     * @param msg
     */
    public void log(String TAG, String msg) {
        Log.d(TAG, msg);
    }
}
