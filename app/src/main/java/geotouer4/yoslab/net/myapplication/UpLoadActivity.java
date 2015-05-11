package geotouer4.yoslab.net.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UpLoadActivity extends Activity
        implements OnClickListener {

    Button pickButton;
    int RESULT_PICK_FILENAME = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        System.out.println("UpLoadActivityStart!");
        pickButton = (Button)findViewById(R.id.pickButton);
        pickButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    public void onClick(View v) {
        switch(v.getId()){
            case R.id.pickButton:
                pickFilenameFromGallery();
                break;
        }
    }

    private void pickFilenameFromGallery() {
        Intent i = new Intent(
        Intent.ACTION_PICK,
        Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_PICK_FILENAME);
    }


    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_PICK_FILENAME
                && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { Media.DATA };

            Cursor cursor = getContentResolver().query(
                    selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex
                    = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Toast.makeText(
                    this,
                    picturePath,
                    Toast.LENGTH_LONG).show();
        }
    }
}