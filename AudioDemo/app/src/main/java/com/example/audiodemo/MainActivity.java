package com.example.audiodemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jaygoo.widget.wlv.WaveLineView;

public class MainActivity extends AppCompatActivity {
    int flag_play = 0;    //是否有录音
    int flag_record = 0;
    ImageButton button_start;
    ImageButton button_play;
    File recordFile = null;
    MediaPlayer mPlayer;
    WaveLineView waveLineView;
    private MediaRecorder mediaRecorder = null;
    String RecordPath;
    String RecordFileName;
    String Path;
    private  final  static  int PERMISSION_REQUEST_VOICE =201;//录音相关的权限 单个demo随意
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager();
        SetObj();
        ListenerManager();
    }
    private void ListenerManager() {
        //录音和暂停录音
        button_start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                //切图逻辑
                if (flag_record == 0) {
                    startRecording();
                    flag_record = 1;
                    waveLineView.setVisibility(View.VISIBLE);
                    waveLineView.startAnim();
                } else {
                    stopRecording();
                    flag_record = 0;
                    waveLineView.setVisibility(View.INVISIBLE);
                    waveLineView.onPause();
                }
                if (flag_record == 0) {
                    button_start.setImageResource(R.drawable.ic_bluestart);
                    button_play.setVisibility(View.VISIBLE);
                }
                if (flag_record == 1) {
                    button_start.setImageResource(R.drawable.ic_bluestop);
                }
            }
        });
        //播放和停止播放
        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag_play == 0) {
                    playRecord();
                    waveLineView.setVisibility(View.VISIBLE);
                    waveLineView.startAnim();
                    flag_play = 1;
                } else {
                    waveLineView.setVisibility(View.INVISIBLE);
                    waveLineView.onPause();
                    stopPlayRecord();
                    flag_play = 0;
                    button_play.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    //开始录音
    private void startRecording() {
        createRecordFile();
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RecordFileName = timesdf.format(new Date()).toString();
        RecordFileName = RecordFileName.replace(":", "") + ".amr";

        RecordPath = Path + "/" + RecordFileName;

        recordFile = new File(RecordPath);

        mediaRecorder = new MediaRecorder();


        // 判断，若当前文件已存在，则删除
        if (recordFile.exists()) {
            recordFile.delete();
        }

        //设定录音来源为主麦克风。
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
        try {
            // 准备好开始录音
            waveLineView.startAnim();
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "开始录音", Toast.LENGTH_SHORT).show();

    }

    //停止录音
    private void stopRecording() {
        Toast.makeText(MainActivity.this, "结束录音", Toast.LENGTH_SHORT).show();
        if (recordFile != null) {
            waveLineView.onPause();
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

    }


    //开始播放
    private void playRecord() {

        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(RecordPath);
            mPlayer.prepare();
            Toast.makeText(this, "开始播放", Toast.LENGTH_SHORT).show();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                    waveLineView.setVisibility(View.INVISIBLE);
                    waveLineView.onPause();
                    flag_play = 0;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //停止播放
    private void stopPlayRecord() {
        waveLineView.onPause();
        Toast.makeText(this, "停止播放", Toast.LENGTH_SHORT).show();
        mPlayer.release();
        mPlayer = null;
        waveLineView.stopAnim();
    }


    //创建音频目录
    private void createRecordFile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Audio";
        Path = path;
        File Filedir = new File(path);
        if (!Filedir.exists()) {
            Filedir.mkdirs();
        }
    }

    //初始化
    @SuppressLint("WrongViewCast")
    private void SetObj() {
        button_start = findViewById(R.id.audio_start);
        button_play = findViewById(R.id.audio_play_start);
        waveLineView = findViewById(R.id.waveLineView);
    }

    //权限
    private void PermissionManager() {
        if
        (
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ||ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WAKE_LOCK, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_VOICE);
    }
}
