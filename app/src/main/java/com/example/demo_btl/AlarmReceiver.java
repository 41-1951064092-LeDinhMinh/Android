package com.example.demo_btl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Tôi trong receiver", "Xin chào");
        String chuoi_string = intent.getExtras().getString("extra");
        Log.e("Bạn truyền key",chuoi_string);
        Intent myintent = new Intent(context,Music.class);
        myintent.putExtra("extra",chuoi_string);
        context.startService(myintent);
    }
}
