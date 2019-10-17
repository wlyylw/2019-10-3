package com.example.fingerprint.mainui;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Switch;
import android.widget.Toast;


import com.example.fingerprint.R;

import com.example.fingerprint.otheractivity.LoginActivity;
import com.example.fingerprint.otheractivity.UserService;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.io.File;
import java.lang.reflect.Method;


/**
 * Author : wlyylw
 * Date : 2019/8/7
 */
public class MainUiHome extends AppCompatActivity {


    public static final int PHOTO_STATUS_CODE = 100;



    private Fragment currentFragment  = new Fragment();
    private MainUiFCase mainUiFCaseContainer = new MainUiFCase();
    private MainUiFMine mainUiFMine = new MainUiFMine();
    private MainUiFCompare mainUiFCompare = new MainUiFCompare();
    private MainUiFLog mainUiFLog = new MainUiFLog();




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui_home);
        File file = Environment.getExternalStorageDirectory();
        String path = file.getPath();

        BottomNavigationView bottomNavigationView = findViewById(R.id.mainUiBottomNavigation);

        BottomNavigationItem bottomNavigationItemCase = new BottomNavigationItem
                ("案例", ContextCompat.getColor(this, R.color.colorActive), R.drawable.ic_mainui_case);
        BottomNavigationItem bottomNavigationItemCompare = new BottomNavigationItem
                ("比对", ContextCompat.getColor(this, R.color.colorActive), R.drawable.ic_mainui_compare);
        BottomNavigationItem bottomNavigationItemLog = new BottomNavigationItem
                ("日志", ContextCompat.getColor(this, R.color.colorActive), R.drawable.ic_mainui_log);
        BottomNavigationItem bottomNavigationItemMine = new BottomNavigationItem
                ("我的", ContextCompat.getColor(this, R.color.colorActive), R.drawable.ic_mainui_mine);

        bottomNavigationView.addTab(bottomNavigationItemCase);
        bottomNavigationView.addTab(bottomNavigationItemCompare);
        bottomNavigationView.addTab(bottomNavigationItemLog);
        bottomNavigationView.addTab(bottomNavigationItemMine);

        int ExtraParamLogin = getIntent().getIntExtra("LoginExtra", 0);
        JudgeExtraParam(ExtraParamLogin);
        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index)
                {
                    case 0:
                        switchToCase();
                        break;
                    case 1:
                        switchToCompare();
                        break;
                    case 2:
                        switchToLog();
                        break;
                    case 3:
                        switchToMine();
                        break;
                        default:
                            break;
                }
            }
        });
        switchToCase();//default

    }

    private void switchToMine() {
        UserService userService = UserService.getInstance();
        if(!userService.isLogined())
        {
            Toast.makeText(this, "您尚未登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
            return;
        }
        switchFragment(mainUiFMine);

    }

    private void switchToCompare() {
        switchFragment(mainUiFCompare);
    }

    private void switchToLog() {
        switchFragment(mainUiFLog);
    }

    private void switchToCase() {
        switchFragment(mainUiFCaseContainer);
    }

    /**
     * 解决重复实例化
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {//如果要显示的targetFragment没有添加过
            transaction
                    .hide(currentFragment)//隐藏当前Fragment
                    .add(R.id.frame_content, targetFragment,targetFragment.getClass().getName())//添加targetFragment
                    .commit();
        } else {//如果要显示的targetFragment已经添加过
            transaction//隐藏当前Fragment
                    .hide(currentFragment)
                    .show(targetFragment)//显示targetFragment
                    .commit();
        }
        //更新当前Fragment为targetFragment
        currentFragment = targetFragment;

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainui_menu_toolbar_case,menu);
        setIconVisible(menu);
        return true;
    }
    public void setIconVisible(Menu menu){
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void JudgeExtraParam(int ExtraParam)
    {

        if (ExtraParam == 1) {
            switchToMine();
        }
    }

}