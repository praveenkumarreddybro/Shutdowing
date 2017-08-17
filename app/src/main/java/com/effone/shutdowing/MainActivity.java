package com.effone.shutdowing;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static int REQUEST_CODE = 10101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkDrawOverlayPermission()) {
            startService(new Intent(this, PowerButtonService.class));
        }

    }


    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, PowerButtonService.class));
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN
                || event.getKeyCode() == KeyEvent.KEYCODE_CAMERA
                || event.getKeyCode() == KeyEvent.KEYCODE_POWER) {

            Log.i("Key", "keycode " + event.getKeyCode());

            Toast.makeText(this,"KeyCode"+ event.getKeyCode(),Toast.LENGTH_SHORT).show();

        }
        return  super.dispatchKeyEvent(event);
    }
    @Override
    protected void onPause() {
        super.onPause();

    }
}
