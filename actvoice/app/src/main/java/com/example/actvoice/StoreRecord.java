package com.example.actvoice;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StoreRecord  extends AppCompatActivity {
    String RecordPath;
    String RecordFileName;
    File RecordFile;
    public static final String SD_APP_DIR_NAME = "FingerPrintRecord"; //存储程序在外部SD卡上的根目录的名字
    public static final String VOICE_DIR_NAME = "record";    //存储音频在根目录下的文件夹名字
    /**
     * Author:wlyylw 2019/10/3 4:10pm
     * 格式化日期
     * @Param ampTime: 0 代表上午 1代表下午
    */
    private String getStoreTime()
    {
        String time ;
        String ampTime;
        int apm = Calendar.getInstance().get(Calendar.AM_PM);
        ampTime = apm==0? "上午":"下午";
        SimpleDateFormat  format = new SimpleDateFormat("yyyy_MM_dd E " + ampTime + " kk:mm:ss");
        time = format.format(new Date());
        return time;
    }

    /**
     * 创建音频目录*/
    private void CreateRecordFile()
    {
        RecordFileName = getStoreTime()+".amr";
        //用于存储录音的最终目录，即根目录 / 录音的文件夹 / 录音
        RecordFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + SD_APP_DIR_NAME + "/" + VOICE_DIR_NAME + "/", RecordFileName);
        RecordPath =RecordFile.getAbsolutePath();
        if(!RecordFile.exists())
        {
            RecordFile.getParentFile().mkdir();
            RecordFile.setWritable(true);
        }
    }

    /**
     * 通过Uri，获取录音文件的路径（绝对路径）
     *
     * @param uri 录音文件的uri
     * @return 录音文件的路径（String）
     */
    private String getAudioFilePathFromUri(Uri uri) {
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
        String temp = cursor.getString(index);
        cursor.close();
        return temp;
    }

}
