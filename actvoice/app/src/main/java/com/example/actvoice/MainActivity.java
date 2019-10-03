package com.example.actvoice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;

import jaygoo.widget.wlv.WaveLineView;

public class MainActivity extends AppCompatActivity {
    int flag = 0;
    int first=0;
    ImageButton button_start;
    ImageButton button_stop;
    ImageButton button_play;
    ImageButton button_play_stop;
    private File recordFile;
    WaveLineView waveLineView;
    private MediaRecorder mediaRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordFile = new File("/mnt/sdcard", "wlyylw.amr");
       PermissionManagerRecord();
       SetObj();
       ListenerManager();
    }

    private void SetObj()
    {
        button_start = findViewById(R.id.start);
        button_stop = findViewById(R.id.stop);
        button_play =findViewById(R.id.start_play);
        button_play_stop =findViewById(R.id.stop_play);
        waveLineView =findViewById(R.id.waveLineView);
    }
    private void ListenerManager()
    {
        button_start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                startRecord();
                flag++;
                first=1;
            }
        });
        button_stop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                stopRecord();
            }
        });
        button_play.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
        button_play_stop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });
    }

    private void PermissionManagerRecord()
    {
        if(ContextCompat.checkSelfPermission
                (MainActivity.this,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.RECORD_AUDIO}, 200);

        if(ContextCompat.checkSelfPermission
                (MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
        )
            //未获得，向用户请求
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

        if(ContextCompat.checkSelfPermission
                (MainActivity.this,Manifest.permission.WAKE_LOCK)!= PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WAKE_LOCK}, 200);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startRecord() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //设定录音来源为主麦克风。
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

        if(recordFile.exists())
        {
            recordFile.delete();
        }
        if (flag % 2 == 0) {
            button_start.setImageResource(R.drawable.ic_yellowstart);
            if (first == 0)
            {
            try {
                // 准备好开始录音
                mediaRecorder.prepare();
                mediaRecorder.start();
                waveLineView.startAnim();

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
            else
            {

                    // 准备好开始录音
//                    mediaRecorder.prepare();
//                    mediaRecorder.start();
                    waveLineView.onResume();
            }
        }

        else
        {

            button_start.setImageResource(R.drawable.ic_yellowstop);
            waveLineView.onPause();
            try {
                // 准备好开始录音
//                mediaRecorder.stop();
                //TODO:拼接两段录音
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }


    }
    private void stopRecord()
    {
        if (recordFile != null) {

            mediaRecorder.stop();
            mediaRecorder.release();
//            waveLineView.onPause();
            waveLineView.stopAnim();
        }
    }

}
