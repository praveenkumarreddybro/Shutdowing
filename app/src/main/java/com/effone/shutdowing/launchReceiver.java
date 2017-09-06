package com.effone.shutdowing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sumanth.peddinti on 9/6/2017.
 */

public class launchReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String pwd = intent.getData().getHost();
            if(pwd.equals("555")){
                Intent i=new Intent(context,UnInstallApp.class);
                i.putExtra("extra_phone", pwd);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }


    }
}
