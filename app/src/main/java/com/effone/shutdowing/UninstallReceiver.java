package com.effone.shutdowing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sumanth.peddinti on 9/6/2017.
 */

public class UninstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"sumi",Toast.LENGTH_SHORT).show();
    }
}
