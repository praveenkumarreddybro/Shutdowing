package com.effone.shutdowing;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by sumanth.peddinti on 9/6/2017.
 */

public class UnInstallApp  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.effone.shutdowing.MainActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
      */  try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            //Enter app package name that app you wan to install
            intent.setData(Uri.parse("package:com.effone.shutdowing"));
            startActivity(intent);
        }catch (Exception e){

        }
    }

}
