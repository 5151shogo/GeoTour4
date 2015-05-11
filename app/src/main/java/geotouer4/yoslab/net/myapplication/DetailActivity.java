package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends Activity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private String imageSavePlace;
    private static final int REQUEST_GALLERY = 0;
    static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri uri;
    int RESULT_PICK_FILENAME = 1;
    private RequestQueue myQueue; // Volleyで通信を行うための変数

    SharedPreferences sp;
    SharedPreferences sp2;

    // カメラ周りの変数定義
    private static String pictureName;
    private static String filename;
    private static Bitmap image;
    Button mButtonOpen;
    ImageView mImageView;
    FileOutputStream out;
    int imageHeight;
    int imageWidth;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "DetailActivity start!");

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //取得
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String explain = intent.getStringExtra("EXPLAIN");


        TextView text = (TextView) findViewById(R.id.detail);
        text.setText(name);

        TextView explain2 = (TextView) findViewById(R.id.explain2);
        explain2.setText(explain);

        // ボタンオブジェクトオブジェクト取得(地図画面へ)
        Button button1 = (Button) findViewById(R.id.map);
        button1.setTag("back1");
        button1.setOnClickListener(new ButtonClickListener());

        //ボタンオブジェクト作成(カメラ)
        Button button2 = (Button) findViewById(R.id.camera);
        button2.setTag("camera");
        button2.setOnClickListener(new ButtonClickListener());

        //ボタンオブジェクト作成(写真アップロード)
        Button button3 = (Button) findViewById(R.id.upload);
        button3.setTag("upload");
        button3.setOnClickListener(new ButtonClickListener());

    }

    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) {
            //Intent intent = getIntent();
            String tag = (String) v.getTag();
            if (tag.equals("back1")) {
                finishActivity();
            } else if (tag.equals("camera")) {
                cameraActivity();
            } else if (tag.equals("upload")) {
                SelectPictureActivity();
            }
        }
    }

    // アクティビティ終了(画面クローズ)
    private void finishActivity() {
        finish();
    }


    private void cameraActivity() {//カメラアクティビティ
        Intent intent = new Intent(DetailActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    private void SelectPictureActivity() {
        pickFilenameFromGallery();
    }

    private void pickFilenameFromGallery() {
        Intent i = new Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_PICK_FILENAME);
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_PICK_FILENAME
                && resultCode == RESULT_OK
                && null != data) {
            new AlertDialog.Builder(DetailActivity.this).setTitle("　 写真をアップロードしますか？　 　(他の人と写真共有ができます)")
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override

                                public void onClick(DialogInterface dialog, int which) {
                                    Uri selectedImage = data.getData();
                                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                                    Cursor cursor = getContentResolver().query(
                                            selectedImage,
                                            filePathColumn, null, null, null);
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    String picturePath = cursor.getString(columnIndex);
                                    System.out.println(picturePath);
                                    cursor.close();

                                    //uploadを確認するところ(アップロードしました)
                                    uploadOKActivity();

                                    //uploadするとこ
                                    uploadActivity(picturePath);

                                    //Toastするとこ
                                    Toast.makeText(
                                            getBaseContext(),
                                            picturePath,
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                    .setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .show();
        }
    }


        private void uploadOKActivity(){
            new AlertDialog.Builder(DetailActivity.this).setTitle("アップロードしました！")
                    .setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
            .show();


        }

        private void uploadActivity(String picturepath){
            myQueue = Volley.newRequestQueue(this, new MultipartStack());
            filename = picturepath;
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filename, options);
//
//
//            int imageHeight = options.outHeight;
//            int imageWidth = options.outWidth;
//            String imageType = options.outMimeType;
//
//            System.out.println("imageHeight~~~~~~~~~~"+imageHeight);
//            System.out.println("imageWidth~~~~~~~~~~~^"+imageWidth);
//
//            int imageHeight2 = imageHeight/4;
//            int imageWidth2 = imageWidth/4;
//
//            System.out.println("imageHeight~~~~~~~~~~"+imageHeight);
//            System.out.println("imageWidth~~~~~~~~~~~^"+imageWidth);
//
//            int scale = Math.max(imageHeight2,imageWidth2);
//            options.inJustDecodeBounds = false;
//
//            options.inSampleSize =scale;
//            Bitmap bitmap = BitmapFactory.decodeFile(filename, options);
//







            String url = "http://www2.yoslab.net/~taniguchi/api/UpLoad.php";
            //String url = "http://www2.yoslab.net/~taniguchi/api/img/" + sp.getString("user_id", "Guest") + "/" + sp2.getString("Spot_id", "000") +".jpg" ;
            Map<String, String> stringMap = new HashMap<String, String>();
            Map<String, File> fileMap = new HashMap<String, File>();
            fileMap.put("img",new File(filename) );

            MultipartRequest multipartRequest = new MultipartRequest(
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Upload成功
                            System.out.println("res=========" + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Upload失敗
                            System.out.println("error~~~~~~" + error);

                        }
                    },
                    stringMap,
                    fileMap);
            int custom_timeout_ms = 50000;
            DefaultRetryPolicy policy = new DefaultRetryPolicy(custom_timeout_ms,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            multipartRequest.setRetryPolicy(policy);
            myQueue.add(multipartRequest);
            myQueue.start();
        }



	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

//    private void registAndroidDB(String path) {
//        // アンドロイドのデータベースへ登録
//        // (登録しないとギャラリーなどにすぐに反映されないため)
//        ContentValues values = new ContentValues();
//        ContentResolver contentResolver = DetailActivity.this.getContentResolver();
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put("_data", path);
//        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//    }
//    public String resize(String outHeight){//リサイズ処理
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//
//
//
//        imageHeight = options.outHeight;
//
//        imageHeight = imageHeight/4;
//
//
//        return imageHeight;
//    }

//    public int resizeHeight(int outHeight){//リサイズ処理
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        int imageHeight2 = outHeight;
//        imageHeight2 = imageHeight2/4;
//        System.out.println("imageHeight2 ===="+imageHeight2);
//        return imageHeight2;
//    }
//
//    public int resizeWidth(int outWidth){//リサイズ処理
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        int imageWidth2 = outWidth;
//        imageWidth2 = imageWidth2/4;
//        System.out.println("imageWidth2 ===="+imageWidth2);
//        return imageWidth2;
//    }
}
