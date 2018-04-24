package jcc.example.com.frameanim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

/**
 * Created by jincancan on 2018/4/18.
 * Description:
 */
public class JSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback{

    private SurfaceHolder mHolder;

    private boolean bRunning;
    private int mCurrentPos;
    private int[] mFrames;

    public JSurfaceView(Context context) {
        super(context);
        init();
    }

    public JSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void startAnim(int[] anim){
        if(bRunning){
            return;
        }
        mFrames = anim;
        resize();
        new Thread(this).start();
        bRunning = true;
    }

    @Override
    public void run() {
        while (mCurrentPos < mFrames.length) {
            drawBitmap();
            mCurrentPos++;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mCurrentPos = 0;
        bRunning = false;
        Canvas mCanvas = mHolder.lockCanvas();
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(paint);
        mHolder.unlockCanvasAndPost(mCanvas);
    }

    private void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
    }

    private void drawBitmap(){
        Canvas mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), mFrames[mCurrentPos]);

        Paint paint = new Paint();
        Rect mSrcRect = new Rect(0,
                0,
                mBitmap.getWidth(),
                mBitmap.getHeight()); // 图片绘制
        Rect mDestRect = new Rect(0,
                0,
                getWidth(),
                getHeight());//  图片绘制位置

        mCanvas.drawBitmap(mBitmap, mSrcRect, mDestRect, paint);
        mHolder.unlockCanvasAndPost(mCanvas);
        mBitmap.recycle();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f000);
//        float scaleBmp = mBitmap.getWidth() * 1.0f / mBitmap.getHeight();
//        float scale = width * 1.0f / height;
//        if(scale > scaleBmp){
//            ViewGroup.LayoutParams params = getLayoutParams();
//            params.width = (int) (height * scaleBmp);
//            setLayoutParams(params);
//        }else if(scale < scaleBmp){
//            ViewGroup.LayoutParams params = getLayoutParams();
//            params.height = (int) (width / scaleBmp);
//            setLayoutParams(params);
//        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void resize(){

        ViewGroup.LayoutParams params = getLayoutParams();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), mFrames[0]);
        float scaleBmp = mBitmap.getWidth() * 1.0f / mBitmap.getHeight();
        float scale = getWidth() * 1.0f / getHeight();
        if(scale > scaleBmp){
            params.height = getHeight();
            params.width = (int) (params.height * scaleBmp);
        }else if(scale < scaleBmp){
            params.width = getWidth();
            params.height = (int) (params.width / scaleBmp);
        }
        setLayoutParams(params);
    }
}
