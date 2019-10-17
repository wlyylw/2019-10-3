package com.example.bitmap;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends Activity {
    PhotoViewer photoViewer;
    Button button_mirro;
    Button button_shadow;
    Button button_scale_big;
    Button button_rorate;
    Button button_album;
    Button button_scale_small;
    private  final  static  int ADD_PHOTO =501;
    String imagePath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager();
        SetObj();
        ListerManager();
    }
    private  void SetObj()
    {
        photoViewer = findViewById(R.id.costomview);
        button_scale_big = findViewById(R.id.btn_scale_big);
        button_mirro =  findViewById(R.id.btn_mirro);
        button_shadow =  findViewById(R.id.btn_shadow);
        button_rorate = findViewById(R.id.btn_rotate);
        button_album = findViewById( R.id.album);
        button_scale_small = findViewById( R.id.btn_scale_small);
    }

    private  void ListerManager()
    {
        button_rorate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewer.rotate(90);
            }
        });
        button_shadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewer.shadow();
            }
        });
        button_mirro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewer.mirror();
            }
        });
        button_scale_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewer.scale(1.3f, 1.3f);
            }
        });
        button_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });
        button_scale_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewer.scale(0.7f,0.7f);
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,ADD_PHOTO); // 打开相册
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {

        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            //ImageView picture = findViewById(R.id);
            //picture.setImageBitmap(bitmap);
            photoViewer.setmBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    //权限
    private void PermissionManager() {
        if
        (
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ||ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                        ||ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WAKE_LOCK, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_PHOTO:
                    if (resultCode == RESULT_OK) {
                        // 判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            // 4.4及以上系统使用这个方法处理图片
                            handleImageOnKitKat(data);
                        } else {
                            // 4.4以下系统使用这个方法处理图片
                            handleImageBeforeKitKat(data);
                        }
                    }
                    break;
                default:
                    break;

            }
        }
    }

    public String getImgPath()
    {
        return imagePath;
    }
}