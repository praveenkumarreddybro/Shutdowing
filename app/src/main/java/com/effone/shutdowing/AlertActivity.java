package com.effone.shutdowing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.effone.shutdowing.PowerButtonService.i;
import static com.effone.shutdowing.PowerButtonService.keyCode;

/**
 * Created by sumanth.peddinti on 4/3/2017.
 */

public class AlertActivity extends AppCompatActivity {
    Button btnOpenDialog;
    TextView textInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(i == 0){
            openDialog();
        }
    }
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText) subView.findViewById(R.id.dialogEditText);
        subEditText.setHint("9999");
        builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        //  builder.setMessage("AlertDialog Message");
        builder.setView(subView);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(subEditText.getText().toString().trim().equals("9999")) {
                    i = 1;
                    getBaseContext().stopService(new Intent(getBaseContext(), PowerButtonService.class));
                } else{
                    Toast.makeText(getApplicationContext(),"Wrong Password.....!" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Cancel.....!" ,Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alertDialog.dismiss();
    }
}
