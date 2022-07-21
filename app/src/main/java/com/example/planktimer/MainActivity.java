package com.example.planktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;
    ImageView plankImage;
    Button hideButton;
    Button showButton;
    boolean ImageIsHide = false;

    public void hideImg(View view){
        if(ImageIsHide == false){
            plankImage.setVisibility(View.INVISIBLE);
            hideButton.setVisibility(View.INVISIBLE);
            showButton.setVisibility(View.VISIBLE);
            ImageIsHide = true;
        }
    }

    public void showIng(View view){
        if(ImageIsHide == true){
            plankImage.setVisibility(View.VISIBLE);
            hideButton.setVisibility(View.VISIBLE);
            showButton.setVisibility(View.INVISIBLE);
            ImageIsHide = false;
        }
    }


    public void resetTimer(){
        controllerButton.setText("Go!");
        timerSeekBar.setProgress(0);
        countDownTimer.cancel();
        timerTextView.setText("0:00");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void updateTimer(int timesLeft){
        int minutes = (int)timesLeft / 60;
        int seconds = timesLeft - minutes * 60;
        String secondString = Integer.toString(seconds);

         if(seconds <= 9 ){
            secondString = "0" + secondString;
        }
        timerTextView.setText(minutes + ":" + secondString);
    }

    public void controllerButton(View view){
        if(counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);

                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer finishEffect = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                    finishEffect.start();
                }
            }.start();
        }
        else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.timerTextView);
        controllerButton = findViewById(R.id.controllerButton);
        plankImage = (ImageView) findViewById(R.id.plankImage);
        hideButton = findViewById(R.id.hideButton);
        showButton = findViewById(R.id.showButton);

        showButton.setVisibility(View.INVISIBLE);

        timerSeekBar.setMax(90);
        timerSeekBar.setProgress(0);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}