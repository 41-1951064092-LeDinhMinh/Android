package com.example.demo_btl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class countdown_time extends AppCompatActivity {
    SeekBar timer_sb;
    TextView timer_tv;
    Button start_btn;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;
    MediaPlayer mediaPlayer;
    Button btnTatAm;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    private Button btnChuongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_time);
        timer_sb = findViewById(R.id.timer_sb);
        timer_tv = findViewById(R.id.timer_tv);

        start_btn = findViewById(R.id.start_btn);
        btnChuongBao = (Button) findViewById(R.id.btnChuongBao);
        btnTatAm = (Button) findViewById(R.id.btnTatAm);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.chuongbao);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timer_sb.setMax(300);
        timer_sb.setProgress(30);


        timer_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //chuyển màn hình
        btnChuongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(countdown_time.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //Dung lai

        Intent intent = new Intent(countdown_time.this,AlarmReceiver.class);
        btnTatAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                countDownTimer.cancel();
                timer_sb.setEnabled(true);
                counterIsActive = false;
            }
        });
    }

    private void update(int progress) {
        int minutes = progress /60;
        int seconds = progress %60;
        String secondsFinal="";
        if(seconds <=9){
            secondsFinal = "0"+ seconds;
        }else{
            secondsFinal="" + seconds;
        }
        timer_sb.setProgress(progress);
        timer_tv.setText(""+minutes+":"+secondsFinal);
    }

    public void start_timer(View view) {
        if(counterIsActive==false){
            counterIsActive=true;
            timer_sb.setEnabled(false);
            start_btn.setText("Dừng lại");
            countDownTimer= new CountDownTimer(timer_sb.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    update((int) millisUntilFinished/ 1000);
                }

                @Override
                public void onFinish() {
                    reset();
                    if(mediaPlayer != null){
                        mediaPlayer.start();
                    }
                }
            }.start();
        }else{
            reset();
        }
    }

    private void reset() {
        timer_tv.setText("0:30");
        timer_sb.setProgress(30);
        countDownTimer.cancel();
        start_btn.setText("Bắt đầu");
        timer_sb.setEnabled(true);
        counterIsActive = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }
}