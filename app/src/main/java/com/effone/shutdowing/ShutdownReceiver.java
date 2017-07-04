package com.effone.shutdowing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by sumanth.peddinti on 4/3/2017.
 */

public class ShutdownReceiver extends BroadcastReceiver {
    private static final String TAG = "ShutdownReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e(TAG, "Shutting Down..........................");
        context.startService(new Intent(context, PowerButtonService.class));
    }

}
