package com.example.dialogtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class CustomDialog extends Dialog {


    public CustomDialog(Activity activity) {
        super(activity);
    }

    public static class Builder   {
        private Activity activity;
        private ImageView camera;
        private ImageView album;
        public ImageView showimage;
        private CustomDialog dialog;
        public ImageView getCamera(){return  camera;}
        public ImageView getAlbum(){return album;}
        public  CustomDialog getDialog(){
            return  dialog;
        }

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new CustomDialog(activity);
            View layout = inflater.inflate(R.layout.picture_dialog, null);
            camera = layout.findViewById(R.id.camera);
            album = layout.findViewById(R.id.album);
            showimage = layout.findViewById(R.id.picture);
            dialog.setContentView(layout);
            dialog.show();
            return dialog;
        }


    }
}
