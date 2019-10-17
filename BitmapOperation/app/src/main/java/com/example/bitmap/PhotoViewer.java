package com.example.bitmap;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

public class PhotoViewer extends View {

    private Bitmap mBitmap;
    private Matrix matrix;

    public PhotoViewer(Context context) {
        super(context);
        matrix = new Matrix();
    }

    public PhotoViewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        matrix = new Matrix();
    }

    public PhotoViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, matrix, null);
        }
    }

    public void rotate(float degree) {
        if (mBitmap != null) {
            matrix.preRotate(degree, mBitmap.getWidth() / 2,
                    mBitmap.getHeight() / 2);
            invalidate();
        }

    }
    //缩放
    public void scale(float sx, float sy) {
        if (mBitmap != null) {
            matrix.postScale(sx, sy);
            invalidate();
        }
    }
    //镜像（相当于是照镜子里的自己）
    public void mirror() {
        if (mBitmap != null) {
            matrix.postScale(-1, 1);
            matrix.postTranslate(mBitmap.getWidth(), 0);
            invalidate();
        }
    }
    //倒影
    public void shadow() {
        if (mBitmap != null) {
            matrix.postScale(1, -1);
            matrix.postTranslate(0, mBitmap.getHeight());
            invalidate();
        }
    }
}
