package com.example.fingerprint.mainui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fingerprint.R;
import com.example.fingerprint.multimedia.PlayRecord;
import com.example.fingerprint.otheractivity.BuildCaseContainer;
import com.example.fingerprint.otheractivity.SendInformation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jaygoo.widget.wlv.WaveLineView;

import static android.app.Activity.RESULT_OK;


public class MainUiFCase extends BaseFragment {

    Toolbar toolbar;


    /**相机相关*/
    ImageButton chooseFromAlbum;
    public String finalDir;       //最终路径
    public static final String SD_DIR_NAME = "SDDir";   //外部sd卡的根目录名称
    public static final String PHOTO_DIR_NAME = "photo";
    public static final int PHOTO_STATUS_CODE = 100;
    private Uri pictureUri;
    private String pictureName;
    private File pictureFile;        //图片文件

    public static final int CHOOSE_PHOTO = 2;
//    private ImageView mainui_case_picture;
    private Uri imageUri;

    /***录音相关*/
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }   //fragment之间传递

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SetObj();
        ListenerManager();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_ui_fcase, null);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
            inflater.inflate(R.menu.mainui_menu_toolbar_case, menu);
        }
    }
    @Override public   void onDestroy() {
        super.onDestroy();
        waveLineView.stopAnim();
        waveLineView.clearDraw();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent;
        switch (item.getItemId()) {
            case R.id.mainui_case_send:
                intent = new Intent(getActivity(), SendInformation.class);
                startActivity(intent);
                break;
            case R.id.mainui_case_build:
                intent = new Intent(getActivity(), BuildCaseContainer.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }   //toolbar menu


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        ImageButton button = getActivity().findViewById(R.id.mainui_case_button_take_photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检查是否获得写入权限，未获得则向用户请求
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //未获得，向用户请求
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                } else {
                    //启动照相机
                    startCamera();
                }
            }
        });
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_STATUS_CODE: {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pictureUri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    savePhotoToSD(bitmap);
                    updateSystemGallery();
                    showPhoto();
                    break;
                }
                case CHOOSE_PHOTO:
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


    /**
     * 相机相关
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        createImageFile();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pictureUri = FileProvider.getUriForFile(getActivity(), "com.example.Fingerprint.fileprovider", pictureFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, PHOTO_STATUS_CODE);
    }

    private void createImageFile() {
        pictureName = Calendar.getInstance().getTimeInMillis() + ".jpg";//以当前时间的毫秒值为名称
//        pictureFile =  new File (Environment.getExternalStorageDirectory().getAbsolutePath()+
////                "/"+SD_DIR_NAME+"/"+PHOTO_DIR_NAME+"/",pictureName);
        pictureFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/", pictureName);
        finalDir = pictureFile.getAbsolutePath();//图片的绝对路径后续要用
        pictureFile.getParentFile().mkdir();//按设置好的目录层级创建
        //不加这句会报Read-only警告。且无法写入SD
        pictureFile.setWritable(true);
    }

    private void updateSystemGallery() {
        //把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                    pictureFile.getAbsolutePath(), pictureName, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + finalDir)));

    }

    //显示照片
    private void showPhoto() {
        if (finalDir != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(finalDir);
            ImageView picture = getActivity().findViewById(R.id.mainui_case_picture);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePhotoToSD(Bitmap bitmap) {

        BufferedOutputStream os = null;
        try {
            //设置输出流
            os = new BufferedOutputStream(new FileOutputStream(pictureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os); //100表示不压缩

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    //不论是否异常都要关闭流
                    os.flush();
                    os.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 相册
     * */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
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
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
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
            ImageView picture = getActivity().findViewById(R.id.mainui_case_picture);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    /***
     *录音相关
     */
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
        PermissionManager();
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
        Toast.makeText(getActivity(), "开始录音", Toast.LENGTH_SHORT).show();

    }

    //停止录音
    private void stopRecording() {
        Toast.makeText(getActivity(), "结束录音", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "开始播放", Toast.LENGTH_SHORT).show();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Toast.makeText(getActivity(), "播放完成", Toast.LENGTH_SHORT).show();
                    waveLineView.setVisibility(View.INVISIBLE);
//                    button_play.setVisibility(View.INVISIBLE);
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
        Toast.makeText(getActivity(), "暂停播放", Toast.LENGTH_SHORT).show();
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
        chooseFromAlbum = getActivity().findViewById(R.id.MainUi_Case_Album);
        toolbar = getActivity().findViewById(R.id.mainui_toolbar_case);
        button_start = getActivity().findViewById(R.id.audio_start);
        button_play = getActivity().findViewById(R.id.audio_play_start);
        waveLineView = getActivity().findViewById(R.id.waveLineView);
        SetToolBar();
    }

    private void SetToolBar() {
        toolbar.setTitle("案例");
        setTitleCenter(toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);//toolbarmenu
    }

    //权限
    private void PermissionManager() {
        if (ContextCompat.checkSelfPermission
                (getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.RECORD_AUDIO}, 201);

        if (ContextCompat.checkSelfPermission
                (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        )
            //未获得，向用户请求
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);

        if (ContextCompat.checkSelfPermission
                (getActivity(), Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.WAKE_LOCK}, 201);
    }
}
