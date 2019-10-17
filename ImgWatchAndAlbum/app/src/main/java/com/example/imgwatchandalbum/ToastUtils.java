package com.example.imgwatchandalbum;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;
    private static Toast styleToast;

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //如果在主线程弹静态吐司可以使用这个，传入上下文即可
    public static void showToast(Context context, final String msg) {
        if (toast == null) toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    //弹静态吐司，无论子线程还是主线程都能执行（里面做了一个线程判断）
    public static void showStaticToast(final Activity act, final String msg) {
        //获取当前线程
        String nowThreadName = Thread.currentThread().getName();
        //如果为主线程
        if ("main".equals(nowThreadName)) {
            if (toast == null) toast = Toast.makeText(act, msg, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
            //如果为子线程
        } else {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) toast = Toast.makeText(act, msg, Toast.LENGTH_SHORT);
                    toast.setText(msg);
                    toast.show();
                }
            });
        }
    }



    /**
     * 短时间显示Toast
     */
    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        Toast.makeText(context, message, duration).show();
    }
    
}
