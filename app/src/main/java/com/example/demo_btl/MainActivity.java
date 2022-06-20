package com.example.demo_btl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btnHenGio,btnDungLai,btnChuongBao,btnBamGio,btnDemGio;
    TextView txtHienThi;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHenGio = (Button) findViewById(R.id.btnHenGio);
        btnDungLai = (Button) findViewById(R.id.btnDungLai);
        btnChuongBao = (Button) findViewById(R.id.btnChuongBao);
        btnBamGio = (Button) findViewById(R.id.btnBamGio);
        btnDemGio = (Button) findViewById(R.id.btnDemGio);
        txtHienThi = (TextView) findViewById(R.id.textView);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //chuyển màn hình
        btnDemGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,countdown_time.class);
               startActivity(intent);
            }
        });

        Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);
                if(gio>12){
                    string_gio = String.valueOf(gio-12);
                }
                if(phut<10){
                    string_phut = "0" + String.valueOf(phut);
                }
                intent.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this,0,intent,pendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                txtHienThi.setText("Giờ bạn đã đặt là " + string_gio + ":" + string_phut);
            }
        });
        btnDungLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtHienThi.setText("Dừng lại");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });
    }
}