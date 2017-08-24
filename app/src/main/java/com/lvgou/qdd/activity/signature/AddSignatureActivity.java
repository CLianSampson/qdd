package com.lvgou.qdd.activity.signature;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.lvgou.qdd.R;
import com.lvgou.qdd.activity.BaseActivity;
import com.lvgou.qdd.http.httpsUpload.UploadBitmap;

public class AddSignatureActivity extends BaseActivity {

    private Button backButton;
    private Button completeButton;

//    private ImageView imageSign;
    private PaintView mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void childImpl(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_signature);

        backButton = (Button) findViewById(R.id.AddSignatureActivity_backButton);
        completeButton = (Button) findViewById(R.id.AddSignatureActivity_complete);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                netRequest();
            }
        });
        completeButton.setBackgroundColor(Color.TRANSPARENT);


        //签名所用
//        imageSign = (ImageView) findViewById(R.id.iv_sign);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);

        mView = new PaintView(this);
        frameLayout.addView(mView);
        mView.requestFocus();

        Button btnClear = (Button) findViewById(R.id.tablet_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mView.clear();
            }
        });

        Button btnOk = (Button) findViewById(R.id.tablet_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                netRequest();
            }
        });
    }

    class PaintView extends View {
        private Paint paint;
        private Canvas cacheCanvas;
        private Bitmap cachebBitmap;
        private Path path;

        public Bitmap getCachebBitmap() {
            return cachebBitmap;
        }

        public PaintView(Context context) {
            super(context);
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            path = new Path();
            cachebBitmap = Bitmap.createBitmap(10, 10, Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(Color.WHITE);
        }

        public void clear() {
            if (cacheCanvas != null) {
                paint.setColor(Color.WHITE);
                cacheCanvas.drawPaint(paint);
                paint.setColor(Color.BLACK);
                cacheCanvas.drawColor(Color.WHITE);
                invalidate();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // canvas.drawColor(BRUSH_COLOR);
            canvas.drawBitmap(cachebBitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
            int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
            if (curW >= w && curH >= h) {
                return;
            }

            if (curW < w)
                curW = w;
            if (curH < h)
                curH = h;

            Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
            Canvas newCanvas = new Canvas();
            newCanvas.setBitmap(newBitmap);
            if (cachebBitmap != null) {
                newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
            }
            cachebBitmap = newBitmap;
            cacheCanvas = newCanvas;
        }

        private float cur_x, cur_y;

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    cur_x = x;
                    cur_y = y;
                    path.moveTo(cur_x, cur_y);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    path.quadTo(cur_x, cur_y, x, y);
                    cur_x = x;
                    cur_y = y;
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    break;
                }
            }
            invalidate();
            return true;
        }
    }

    @Override
    protected void netRequest() {

        final Bitmap bitmap = mView.cachebBitmap;

        final String url = "https://192.168.1.100:8443/http/upload";

        //final String url = URLConst.URL_ADD_SIGNATURE + TokenUtil.token ;

//        Logger.getInstance(getApplicationContext()).info("手写签名的数据是: "  + bitmap);
//        UploadApi.uploadImg(bitmap,new ResponseListener<String>() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("zgy","===========VolleyError========="+error) ;
//                Toast.makeText(AddSignatureActivity.this,"上传失败",Toast.LENGTH_SHORT).show() ;
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.v("zgy","===========onResponse========="+response) ;
//                Toast.makeText(AddSignatureActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
//            }
//        },getApplicationContext(), url) ;



//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FileImageUpload.uploadFile(bitmap, url);
//
//            }
//        });
//
//        thread.start();

        httpsUpload(bitmap);

    }

    private void httpsUpload(Bitmap bitmap){
        final String url = "https://192.168.1.100:8443/http/upload";

//        String path = "/sdcard/DCIM/Camera/20150429_115313.jpg";
        UploadBitmap uploadBitmap = new UploadBitmap(bitmap, url);
        uploadBitmap.start();
    }

}