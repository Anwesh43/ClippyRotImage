package com.anwesome.ui.clippyrotimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 30/01/17.
 */
public class ClippyRotImage {
    private Bitmap bitmap;
    private Activity activity;
    private View.OnClickListener onClickListener;
    private boolean shouldAnimate = false,showed = false;
    private int w,h;
    private ClippyRotImageView clippyRotImageView;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public ClippyRotImage(Activity activity,Bitmap bitmap) {
        this.activity = activity;
        this.bitmap = bitmap;
        initDimensions();
    }
    private void initDimensions() {
        DisplayManager displayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display!=null) {
            Point size = new Point();
            display.getRealSize(size);
            w = size.x;
            h = size.y;
        }
    }
    public ClippyRotImage(Activity activity,int resources) {
        this.activity = activity;
        this.bitmap = BitmapFactory.decodeResource(activity.getResources(),resources);
        initDimensions();
    }
    public void show(int... coords) {
        if(!showed) {
            clippyRotImageView = new ClippyRotImageView(activity);
            if (coords.length == 2) {
                int x = coords[0], y = coords[1];
                clippyRotImageView.setX(x);
                clippyRotImageView.setY(y);
            }
            int dimen = w/2;
            if(h<w) {
                dimen = h/2;
            }
            bitmap = Bitmap.createScaledBitmap(bitmap,dimen,dimen, true);
            activity.addContentView(clippyRotImageView, new ViewGroup.LayoutParams(dimen,dimen));
            showed = true;
        }
    }
    private class ClippyRotImageView extends View {
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private float scale = 0,deg=0,degSpeed = 36,scaleSpeed = 0.1f;
        public ClippyRotImageView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            int r = canvas.getWidth()/2;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#616161"));
            Path path = new Path();
            path.addCircle(canvas.getWidth()/2,canvas.getHeight()/2,r, Path.Direction.CCW);
            canvas.drawPath(path,paint);
            Path clipePath = new Path();
            clipePath.addCircle(canvas.getWidth()/2,canvas.getHeight()/2,r*scale, Path.Direction.CCW);
            canvas.clipPath(clipePath);
            canvas.save();
            canvas.translate(canvas.getWidth()/2,canvas.getHeight()/2);
            canvas.rotate(deg);
            canvas.scale(scale,scale);
            canvas.drawBitmap(bitmap,-canvas.getWidth()/2,-canvas.getHeight()/2,paint);
            canvas.restore();
            if(shouldAnimate) {
                scale+=ClippyRotImageConstants.SCALE_SPEED;
                deg+=ClippyRotImageConstants.DEG_SPEED;
                if(scale>=1) {
                    scale = 1;
                    deg = 0;
                    shouldAnimate = false;
                    click();
                }
                try {
                    Thread.sleep(40);
                    invalidate();
                } catch (Exception ex) {

                }
            }
        }
        private void click() {
            if(onClickListener!=null) {
                onClickListener.onClick(this);
            }
        }
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                if(!shouldAnimate) {
                    if(scale == 0) {
                        shouldAnimate = true;
                        postInvalidate();
                    }
                    else {
                        click();
                    }
                }
            }
            return true;
        }
    }
}
