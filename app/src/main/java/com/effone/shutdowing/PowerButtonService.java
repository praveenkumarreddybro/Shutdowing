package com.effone.shutdowing;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by praveen on 30-03-2017.
 */

public class PowerButtonService extends Service {
       public static KeyEvent keyCode;
    static final int NOTIFICATION_ID = 543;

    private BroadcastReceiver mReceiver = null;
    LinearLayout mLinear;
    View mView;
    public static int i=0;


    public PowerButtonService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        startServiceWithNotification();
        mLinear = new LinearLayout(getApplicationContext()) {

            //home or recent button
            public void onCloseSystemDialogs(String reason) {
                if ("globalactions".equals(reason)) {
                    Log.i("Key", "Long press on power button");
                    if(i==0) {
                        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                        sendBroadcast(closeDialog);

                        showAlert(getApplicationContext());
                    }


                } else if ("homekey".equals(reason)) {
                    //home key pressed
                } else if ("recentapps".equals(reason)) {
                    // recent apps button clicked
                }
            }

            private void showAlert(Context applicationContext) {
                Intent n=new Intent(applicationContext,AlertActivity.class);
                n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(n);
            }

            @Override
            public boolean dispatchKeyEvent(final KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                        || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                        || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
                        || event.getKeyCode() == KeyEvent.KEYCODE_CAMERA
                        || event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
                    Log.i("Key", "keycode " + event.getKeyCode());

                    Toast.makeText(getApplicationContext(),"Long press on power buttons "  + event.getKeyCode() ,Toast.LENGTH_SHORT).show();

                    /*new Thread() {
                        @Override
                        public void run() {
                            try {
                                Instrumentation inst = new Instrumentation();
                                inst.sendKeySync(event);
                                inst.start();

                            } catch (Exception e) {
                                Log.e("Exception", e.toString());
                            }
                        }

                    }.start();
*/

                }
                return super.dispatchKeyEvent(event);
            }


        };


        mLinear.setFocusable(true);

        mView = LayoutInflater.from(this).inflate(R.layout.service_layout, mLinear);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        //params
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
    /*    WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);*/
        params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        wm.addView(mView, params);

    }

    private void startServiceWithNotification() {
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
      // A string containing the action name
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Sumanth")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
//                .setDeleteIntent(contentPendingIntent)  // if needed
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        startForeground(NOTIFICATION_ID, notification);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(this,ShutdownReceiver.class );
        sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
