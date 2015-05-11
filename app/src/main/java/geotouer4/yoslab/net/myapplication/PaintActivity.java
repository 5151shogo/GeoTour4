package geotouer4.yoslab.net.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintActivity extends Activity {

    // 独自のカスタムViewクラスのインスタンス変数
    MyView view;

    SharedPreferences sp;
    SharedPreferences sp2;
    // 描画用のPaint,Bitmap,Canvasのインスタンス変数
    private RequestQueue myQueue; // Volleyで通信を行うための変数
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    //private ImageView mImage;
    private ImageView mImageView;
    private ImageView mImage;
    private static Bitmap image;
    String fileName;
    private String pictureName;
    ProgressDialog dialog;//プログレスバー
    String path;
    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        System.out.println("PaintActivity Start");


        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //Uriから画像を読み込み、ImageViewにセットする。
        Bundle bundle =getIntent().getExtras();
        Uri uri = (Uri)bundle.get("Image");
        System.out.println("uri="+uri);

        // 描画対象のViewを生成
        mImage = new ImageView(this);
        mImage = (ImageView)findViewById(R.id.photo_image2);

        if (uri != null) {
            try {
                System.out.println("写真取れてますよ！！！");
                Bitmap bmp = Media.getBitmap(getContentResolver(), uri);//uriからbitmapにロードできる
                image = ImageUtil.resizeBitmapToDisplaySize(this, bmp);//bitmap型のimageにリサイズして入れる
                if (image != null){
                    System.out.println("10");

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        //ボタンの配置
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

        //ボタン作成
        Button saveButton = new Button(this);
        Button deleteButton = new Button(this);
        Button cancelButton = new Button(this);
        Button shareButton = new Button(this);

        saveButton.setText("保存");
        saveButton.setTag("save");
        saveButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(saveButton);

        deleteButton.setText("描きなおす");
        deleteButton.setTag("delete");
        deleteButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(deleteButton);

        cancelButton.setText("キャンセル");
        cancelButton.setTag("cancel");
        cancelButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(cancelButton);

        shareButton.setText("共有");
        shareButton.setTag("share");
        shareButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(shareButton);


        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);

        linearLayout1.addView(linearLayout2);


        // 独自のカスタムViewクラスのインスタンスを生成
        view = new MyView(this);
        linearLayout1.addView(view);
//        setContentView(view); // ActivityへViewをセット
        setContentView(linearLayout1);
    }


    // 独自のカスタムViewクラス
    // (MainActivityクラスの内部クラスとして生成)
    public class MyView extends View {

        // ドラッグ時の移動量判定の下限値（０で一番センサーが敏感になる）
        private static final float TOUCH_TOLERANCE = 2;

        private Paint mPaint;
        private Paint mBitmapPaint;
        // Path ... 描いた軌跡を線として格納するクラス
        private Path mPath;
        private float mX, mY;


        // MyViewクラスのコンストラクタ
        public MyView(Context context) {
            super(context);

            mCanvas = new Canvas(image);//()にはbitmap型

            mPath = new Path();
            // DITHER_FLAGは色の境界部分などの処理をディザで行う設定を表します。
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }



        @Override
        protected void onDraw(Canvas canvas){//タップしたらここに来る

            // 描画用の筆を生成
            mPaint = new Paint();
            mPaint.setAntiAlias(true);  // アンチエイリアスの設定
            mPaint.setDither(true);     // ディザの設定
            mPaint.setColor(0xffff0000);// デフォルトの色を設定
            mPaint.setStyle(Paint.Style.STROKE);    // 軌跡を線として描く設定
            mPaint.setStrokeJoin(Paint.Join.ROUND); // 線のつなぎ目を丸く描く設定
            mPaint.setStrokeCap(Paint.Cap.ROUND);   // 線の端点を丸く描く設定
            mPaint.setStrokeWidth(10);              // 線の幅を設定(px単位)


            // WindowManagerのインスタンス取得
            WindowManager wm = getWindowManager();
            // Displayのインスタンス取得
            Display disp = wm.getDefaultDisplay();
            int width = disp.getWidth();
            int height = disp.getHeight();

            int width2 = width/4;
            int height2 = height/2;
            System.out.println("Width = "+width2);
            System.out.println("Height = "+height2);

            //canvas.translate(25, 25);
            //canvas.rotate(90,width2,height2);//背景のCanvas回転
            canvas.drawBitmap(image, 0,0, mBitmapPaint);//ここで写真を背景に設定


            // mPaint で描かれたmPathという軌跡をcanvasへ描画
            canvas.drawPath(mPath, mPaint);

        }

        // 画面が縦横に切り替わってサイズが変更されたときにサイズを取得し直して再描画する
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh){
            super.onSizeChanged(w, h, oldw, oldh);
            if(mBitmap == null){
                mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
            }
            mCanvas = new Canvas(image);
            //mCanvas.drawColor(Color.WHITE);
            //this.loadFromCacheFile();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){

            float x = event.getX();
            float y = event.getY();

            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

        private void touch_start(float x, float y){
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y){
            float dx = Math.abs(x-mX);
            float dy = Math.abs(y-mY);
            if(dx >= TOUCH_TOLERANCE || dy>=TOUCH_TOLERANCE){
                // 軌道の変化量を自由曲線としてパス化
                mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up(){
            mPath.lineTo(mX,mY);            // パスの設定
            mCanvas.drawPath(mPath,mPaint); // 設定したパスで描画
            //mPath.reset();                  // 設定したパスの終了
        }

//        private void boolean_invalidate(){
//            invalidate(false);
//        }

    }


    //クリックリスナー
    class ButtonClickListener implements OnClickListener{
        @Override
        public void onClick(View v){
            String tag = (String)v.getTag();

            if(tag.equals("save")){
                save();
            }
            else if(tag.equals("delete")){
                delete();
            }
            else if(tag.equals("cancel")){
                Intent intent = new Intent(PaintActivity.this,CameraActivity.class);
                startActivity(intent);
            }
            else if(tag.equals("share")){
                shareActivity();
            }
        }
    }

    private void save() {//保存ボタン
        System.out.println("OKOKOK");

        //保存用Bitmap準備
//        Bitmap images = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
        //新しいcanvasに保存用Bitmapをセット
        //Canvas canvas = new Canvas(images);
        //canvasに対して描画

            new AlertDialog.Builder(this)
                    .setTitle("保存しました")
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {



                                    //出力ファイルを準備
                                    Bitmap images1 = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
                                    int i = 1;
                                    String dirName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
                                    //String fileName = dirName + getSharedPreferences("text",MODE_PRIVATE)+"_geo.jpg";
                                    fileName = dirName + sp.getString("guide_id", "Guest");
                                    pictureName = sp2.getString("Spot_id", "000") + "_" + (100 + i) + ".jpg";

                                    path = fileName + "-" + pictureName;
                                    System.out.println("path==1111111111=====" + path);

                                    Canvas canvas = new Canvas(image);
                                    //canvasに対して描画
                                    try {
                                        //出力ファイルを準備
                                        FileOutputStream fos = new FileOutputStream(new File(path));
                                        //JPEG形式で出力
                                        image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                        registAndroidDB(path);
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }




                                    //出力ファイルを準備
//                                    Bitmap images1 = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
//                                    int i = 1;
//                                    String dirName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
//                                    //String fileName = dirName + getSharedPreferences("text",MODE_PRIVATE)+"_geo.jpg";
//                                    fileName = dirName + sp.getString("guide_id", "Guest");
//                                    pictureName = sp2.getString("Spot_id", "000") + "_" + (100 + i) + ".jpg";
//
//                                    path = fileName + "/" + pictureName;
//                                    System.out.println("path==1111111111=====" + path);
//
//                                    ContentValues values = new ContentValues();
//                                    values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
//                                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");//保存する形式
//                                    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());//時間順に並ぶ
//                                    values.put(MediaStore.Images.Media.DATA, path);
//
//                                    uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//                                    try {
//                                        //出力ファイルを準備
//                                        FileOutputStream fos = new FileOutputStream(new File("sample.jpg"));
//                                        //JPEG形式で出力
//                                        images1.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                                        fos.close();
//                                    } catch (Exception e) {
//
//                                    }
//
//                                    Intent intent = new Intent(PaintActivity.this, CameraActivity.class);
//                                    startActivity(intent);










//                                    FileOutputStream fos = new FileOutputStream(new File(fileName));
//                                    //PNG形式で出力
//                                    images.compress(Bitmap.CompressFormat.JPEG.JPEG, 100, fos);
//                                    fos.close();

                                }
                            })
                    .show();



    }




    private void delete(){
        showProgressDialog();//くるくる

        Bundle bundle =getIntent().getExtras();
        Uri uri = (Uri)bundle.get("Image");
        System.out.println("uri="+uri);

        // 描画対象のViewを生成
        mImage = new ImageView(this);
        mImage = (ImageView)findViewById(R.id.photo_image2);

        if (uri != null) {
            try {
                Bitmap bmp = Media.getBitmap(getContentResolver(), uri);//uriからbitmapにロードできる
                image = ImageUtil.resizeBitmapToDisplaySize(this, bmp);//bitmap型のimageにリサイズして入れる
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        //ボタンの配置
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

        //ボタン作成
        Button saveButton = new Button(this);
        Button deleteButton = new Button(this);
        Button cancelButton = new Button(this);
        Button shareButton = new Button(this);

        saveButton.setText("保存");
        saveButton.setTag("save");
        saveButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(saveButton);

        deleteButton.setText("描きなおす");
        deleteButton.setTag("delete");
        deleteButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(deleteButton);

        cancelButton.setText("キャンセル");
        cancelButton.setTag("cancel");
        cancelButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(cancelButton);

        shareButton.setText("共有");
        shareButton.setTag("share");
        shareButton.setOnClickListener(new ButtonClickListener());
        linearLayout2.addView(shareButton);


        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.VERTICAL);

        linearLayout1.addView(linearLayout2);


        // 独自のカスタムViewクラスのインスタンスを生成
        view = new MyView(this);
        linearLayout1.addView(view);
        setContentView(linearLayout1);

        class MyView extends View {

            // ドラッグ時の移動量判定の下限値（０で一番センサーが敏感になる）
            private static final float TOUCH_TOLERANCE = 2;
            private Paint mPaint;
            private Paint mBitmapPaint;
            // Path ... 描いた軌跡を線として格納するクラス
            private Path mPath;
            private float mX, mY;


            // MyViewクラスのコンストラクタ
            public MyView(Context context) {
                super(context);

                mCanvas = new Canvas(image);//()にはbitmap型

                mPath = new Path();
                // DITHER_FLAGは色の境界部分などの処理をディザで行う設定を表します。
                mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            }


            @Override
            protected void onDraw(Canvas canvas) {//タップしたらここに来る

                // WindowManagerのインスタンス取得
                WindowManager wm = getWindowManager();
                // Displayのインスタンス取得
                Display disp = wm.getDefaultDisplay();
                int width = disp.getWidth();
                int height = disp.getHeight();

                int width2 = width / 4;
                int height2 = height / 2;
                System.out.println("Width = " + width2);
                System.out.println("Height = " + height2);

                //canvas.translate(25, 25);
                //canvas.rotate(90,width2,height2);//背景のCanvas回転
                canvas.drawBitmap(image, 0, 0, mBitmapPaint);//ここで写真を背景に設定
                // mPaint で描かれたmPathという軌跡をcanvasへ描画
                canvas.drawPath(mPath, mPaint);

            }


            @Override
            public boolean onTouchEvent(MotionEvent event) {

                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touch_start(x, y);
                        invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touch_move(x, y);
                        invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        touch_up();
                        invalidate();
                        break;
                }
                return true;
            }

            private void touch_start(float x, float y) {
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
            }

            private void touch_move(float x, float y) {
                float dx = Math.abs(x - mX);
                float dy = Math.abs(y - mY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    // 軌道の変化量を自由曲線としてパス化
                    mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                    mX = x;
                    mY = y;
                }
            }

            private void touch_up() {
                mPath.lineTo(mX, mY);            // パスの設定
                mCanvas.drawPath(mPath, mPaint); // 設定したパスで描画
                //mPath.reset();                  // 設定したパスの終了
            }
        }
    }

    public void showProgressDialog() {//くるくる
        dialog = new ProgressDialog(this);
        dialog.setMessage("削除中...");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMax(10);
        dialog.setCancelable(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        dialog.show();
        new Thread(new Runnable(){
            public void run() {
                try {
                    for (int i = 0; i < dialog.getMax(); i++) {
                        dialog.setProgress(i);
                        Thread.sleep(100);
                    }
                } catch (Exception e) {

                }
                dialog.dismiss();
            }
        }).start();
    }

    public void shareActivity(){

        System.out.println("shareActivity");
        String uri2 = path;
        System.out.println("uri~~~~~~ "+uri2);

        myQueue = Volley.newRequestQueue(this);

    }

    private void registAndroidDB(String path) {
        // アンドロイドのデータベースへ登録
        // (登録しないとギャラリーなどにすぐに反映されないため)
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = PaintActivity.this.getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}