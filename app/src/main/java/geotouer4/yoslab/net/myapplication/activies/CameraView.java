package geotouer4.yoslab.net.myapplication.activies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

        private Camera myCamera;
        private Context context;
        private Boolean bool = true;

        public CameraView(Context context) {
            super(context);
            this.context = context;
            getHolder().addCallback(this);
            getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            myCamera = Camera.open();
            try {
                myCamera.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = myCamera.getParameters();

            //parameters.setPreviewSize(width, height)不要でした。すいません;
            myCamera.setParameters(parameters);
            myCamera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            myCamera.release();
            myCamera = null;
        }

        // シャッターが押されたときに呼ばれるコールバック
        private Camera.ShutterCallback mShutterListener = new Camera.ShutterCallback() {
            public void onShutter() {
                // TODO Auto-generated method stub
            }
        };

        // JPEGイメージ生成後に呼ばれるコールバック
        private Camera.PictureCallback mPictureListener = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                if (data != null) {
                    if (!sdcardWriteReady()) {
                        Toast.makeText(context, "SDCARDが認識されません。", Toast.LENGTH_SHORT).show();
                        bool = true;
                        camera.startPreview();
                        return;
                    }
                    FileOutputStream foStream = null;
                    //フォルダのパスを表示します。。
                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/cmr/");
                    //フォルダが存在しなかった場合にフォルダを作成します。
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    //これで他のとかぶらない名前の設定ができました。
                    String imgName = Environment.getExternalStorageDirectory().getPath() + "/cmr/" + System.currentTimeMillis() + ".jpg";

                    try {
                        foStream = new FileOutputStream(imgName);
                        foStream.write(data);
                        foStream.close();

                        ContentValues values = new ContentValues();
                        ContentResolver contentResolver = context.getContentResolver();
                        values.put(Images.Media.MIME_TYPE, "image/jpeg");
                        values.put("_data", imgName);
                        try {
                            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        } catch (Exception e) {
                            Toast.makeText(context, "再起動後に画像が認識されます。", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "ファイルの保存中にエラーが発生しました。", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    bool = true;
                    camera.startPreview();
                } else {
                    Toast.makeText(context, "データが取得できませんでした。", Toast.LENGTH_SHORT).show();
                    bool = true;
                    camera.startPreview();
                }
            }
        };

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (myCamera != null && bool) {
                    bool = false;
                    myCamera.takePicture(mShutterListener, null, mPictureListener);
                }
            }
            return true;
        }

        //書き込みができるかどうかを判別する関数
        private boolean sdcardWriteReady() {
            String state = Environment.getExternalStorageState();
            return (Environment.MEDIA_MOUNTED.equals(state));
        }
}



