package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class CameraActivity extends Activity{

    static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri uri = null;
    SharedPreferences sp;
    SharedPreferences sp2;

    // カメラ周りの変数定義
    private  String pictureName;
    private static Bitmap image;
    Button mButtonOpen;
    ImageView mImageView;
    FileOutputStream out;
    String fileName;//ユーザー名を入れるファイル
    File file;//picturenameを入れるファイル
    Uri mImageUri;
    private int i = 1;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // SharedPreferenceの初期化
        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM), "Camera");
        //カメラ画像を保存するディレクトリ
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                return;
            }
        }
        System.out.println("1番はじめ！！");

        // 保存するファイル名を設定する
        mImageUri = getPhotoUri();
        Intent cameraIntent = new Intent();
        System.out.println("いってきます！！");
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//カメラ選択
        cameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//保存する場所を指定してる場所
        startActivityForResult(cameraIntent,CAMERA_REQUEST_CODE);

//        //描画対象のViewを生成し、
//        mImageView = new ImageView(this);
//        //mImageView = (ImageView)findViewById(R.id.photo_image);
//        mImageView.setImageURI(mImageUri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        System.out.println("requestCode == "+requestCode);
        if(requestCode == this.CAMERA_REQUEST_CODE){
            switch(resultCode){
                case RESULT_OK:    //撮影完了
                    try{//画面表示して編集したい
                        System.out.println("ただいまーーーーーー");

                        new AlertDialog.Builder(this)
                                .setTitle("写真にお絵かきしますか？")
                                .setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                next();

                                            }
                                        })
                                .setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mImageUri = getPhotoUri();
                                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                                cameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//保存する場所を指定してる場所
                                                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                                            }
                                        })
                                .show();

                        try {
                            //contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        }catch(Exception e){
                            Toast.makeText(this, "再起動後に画像が認識されます。", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }catch(NullPointerException err){
                        //画像URIがnullだった時の処理
                    }break;

                case RESULT_CANCELED:   //撮影が途中で中止
                    Toast.makeText(this,
                            "撮影が中止されました。"+ " " ,Toast.LENGTH_LONG).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    }


    private void next(){
        Intent intent = new Intent(CameraActivity.this, PaintActivity.class);
        intent.putExtra("Image",uri);
        System.out.println("uri="+uri);
        startActivity(intent);
    }


    private Uri getPhotoUri() {
        System.out.println("getPhotoURI");
        String dirName = Environment.getExternalStorageDirectory() + "/DCIM/Camera/";
        fileName = dirName + sp.getString("guide_id", "Guest");
        System.out.println("fileName==" + fileName);

        pictureName = sp2.getString("Spot_id", "000") + "_" + i + ".jpg";


        path = fileName + "-" + pictureName;
        System.out.println("path====" + path);

        File file = new File(path);
        System.out.println("file ==~~~~~ =" + file);
        System.out.println("fileget())()()( ==~~~~~ =" + file.getName());

        if (file.getName().equals(pictureName)){
            System.out.println("1回目");
            /*
            * TODO:ここで同じファイルが保存されるのを将来的には防ぎたい
            * */

        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");//保存する形式
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());//時間順に並ぶ
        values.put(MediaStore.Images.Media.DATA, path);
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length());
        }
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        System.out.println("uri==============="+uri);
        return uri;
    }


}