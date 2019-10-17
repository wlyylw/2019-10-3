package com.example.fingerprint.multimedia;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import java.io.File;

public class PlayRecord {
    private static MediaPlayer mediaPlayer;
    private Context context;
    public PlayRecord(Context context)
    {
        this.context = context;
    }

    public void  playRecordFile(File file)
    {
        if(file.exists() && file!=null)
        {
            if(mediaPlayer==null)
            {
                Uri uri = Uri.fromFile(file);
                mediaPlayer =MediaPlayer.create(context,uri);
            }
            mediaPlayer.start();
            Toast.makeText(context,"开始播放",Toast.LENGTH_SHORT).show();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Toast.makeText(context,"播放完成",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void pauseRecordFile()
    {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Toast.makeText(context,"已暂停",Toast.LENGTH_SHORT).show();
        }
    }

    public void stopRecordFile()
    {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);//直接进度清零
           Toast.makeText(context,"已停止播放",Toast.LENGTH_SHORT).show();
        }
    }

}
