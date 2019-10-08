package com.example.actvoice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jaygoo.widget.wlv.WaveLineView;

public class MainActivity extends AppCompatActivity {

    int flag_play=0;    //是否有录音
    int flag_record=0;
    ImageButton button_start;
    ImageButton button_play;
    private File recordFile;
    PlayRecord playRecord;
    WaveLineView waveLineView;
    private MediaRecorder mediaRecorder;
    String RecordPath;
    String RecordFileName;

    public static final String SD_APP_DIR_NAME = "mnt"; //存储程序在外部SD卡上的根目录的名字
    public static final String VOICE_DIR_NAME = "sdcard";    //存储音频在根目录下的文件夹名字
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManagerRecord();//授权
//        recordFile = new File("/mnt/sdcard", "a.amr");
                recordFile = new File("/mnt/sdcard", "a.amr");
        //TODO:
//       createRecordFile();  //创建目录
       SetObj();
        CreateRecorder();
       ListenerManager();
    }
    private void ListenerManager()
    {
        playRecord = new PlayRecord(MainActivity.this);
        //录音和暂停录音
        button_start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //切图逻辑
                if(flag_record==0) { startRecording();flag_record=1; }
                else { stopRecording();flag_record=0; }
                if(flag_record==0) { button_start.setImageResource(R.drawable.ic_yellowstart); }
                if(flag_record==1) { button_start.setImageResource(R.drawable.ic_yellowstop); }
            }
        });
        //播放和停止播放
        button_play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(flag_play==0) { playRecord();flag_play=1; }
                else { stopPlayRecord();flag_play=0; }
//                if(flag_play==0) { button_play.setImageResource(R.drawable.ic_bluestart); }
//                if(flag_play==1) { button_play.setImageResource(R.drawable.ic_bluepause); }


            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    //开始录音
    private void startRecording() {

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
            mediaRecorder.prepare();
            mediaRecorder.start();
            waveLineView.startAnim();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"开始录音",Toast.LENGTH_SHORT).show();

    }
    //停止录音
    private void stopRecording() {
        Toast.makeText(this,"结束录音",Toast.LENGTH_SHORT).show();
        if (recordFile != null) {
            waveLineView.onPause();
            mediaRecorder.stop();
            mediaRecorder.release();
        }
        //TODO:
//        saveVoiceToSD(RecordPath);
    }


    //开始播放
    private void playRecord()
    {
        playRecord.playRecordFile(recordFile);
        waveLineView.startAnim();
    }
    //停止播放
    private void stopPlayRecord()
    {
        playRecord.stopRecordFile();
        waveLineView.onPause();
    }



    private void saveVoiceToSD(String path) {
        //创建输入输出
        InputStream isFrom = null;
        OutputStream osTo = null;
        try {
            //设置输入输出流
            isFrom = new FileInputStream(path);
            osTo = new FileOutputStream(RecordPath);
            byte bt[] = new byte[1024];
            int len;
            while ((len = isFrom.read(bt)) != -1) {
                Log.d("storeRec", "len = " + len);
                osTo.write(bt, 0, len);
            }
            Log.d("storeRec", "保存录音完成。");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osTo != null) {
                try {
                    //不管是否出现异常，都要关闭流
                    osTo.close();
                    Log.d("storeRec", "关闭输出流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isFrom != null) {
                try {
                    isFrom.close();
                    Log.d("storeRec", "关闭输入流");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //创建音频目录
    private void createRecordFile()
    {
        RecordFileName = getStoreTime()+".amr";
        //用于存储录音的最终目录，即根目录 / 录音的文件夹 / 录音
        recordFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/" + SD_APP_DIR_NAME + "/" + VOICE_DIR_NAME + "/", RecordFileName);
        //TODO:
//        recordFile = new File("/" + SD_APP_DIR_NAME + "/" + VOICE_DIR_NAME + "/", RecordFileName);
        RecordPath =recordFile.getAbsolutePath();

        recordFile.getParentFile().mkdir();
        recordFile.setWritable(true);

    }

    //初始化
    private void SetObj()
    {
        button_start = findViewById(R.id.start);
        button_play =findViewById(R.id.start_play);
        waveLineView =findViewById(R.id.waveLineView);
    }
    //权限
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
    //用时间命名文件
    private String getStoreTime()
    {
        String time ;
        String ampTime;
        int apm = Calendar.getInstance().get(Calendar.AM_PM);
        ampTime = apm==0? "am":"pm";
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd E " + ampTime + " kk:mm:ss");
        time = format.format(new Date());
        return time;
    }
    //录音参数设置
    public void CreateRecorder()
    {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //设定录音来源为主麦克风。
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
    }
}
