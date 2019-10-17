package com.example.fingerprint.mainui;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.fingerprint.R;


public class MainUiFLog extends BaseFragment {




    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.mainui_toolbar_log);
        toolbar.setTitle("日志");
        setTitleCenter(toolbar);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("test","createview");
        View view = inflater.inflate(R.layout.fragment_main_ui_flog, null);
        return view;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test","resume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("test","pause");
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("test","destroyview");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","destroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test","detach");
    }



}
