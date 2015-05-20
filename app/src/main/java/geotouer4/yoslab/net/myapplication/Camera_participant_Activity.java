package geotouer4.yoslab.net.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

public class Camera_participant_Activity extends Activity {

    private static final int CAMERA_REQUEST_CODE = 1;
        
    // カメラインスタンス
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_participant);

        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode == " + requestCode);
        if (requestCode == this.CAMERA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:    //撮影完了
                    try {//画面表示して編集したい
                        System.out.println("ただいまーーーーーー");
                        Intent cameraIntent = new Intent();
                        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                        finish();
//                        Bundle bundle = data.getExtras();
//                        Bitmap bitmap = (Bitmap) bundle.getParcelable("data");
//                        ImageView imageView = (ImageView) findViewById(R.id.photo_image2);
//                        imageView.setImageBitmap(bitmap);
                    }
                    catch(Exception e){}
            }
        }
    }
}