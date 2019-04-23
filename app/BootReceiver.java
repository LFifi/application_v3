package com.example.ledzi.application_v3.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Roxi on 24.06.2018.
 */

public class BootReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MySensorService.class));

        Toast.makeText(context, "Steps Counter is active", Toast.LENGTH_LONG).show();
    }
}