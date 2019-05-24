package com.example.telecamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        String s = getIntent().getExtras().getString("name");
        Button bphoto = (Button)findViewById(R.id.button4);
        bphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filename = Environment.getExternalStorageDirectory()+"/photo.jpg";

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, filename);
                startActivityForResult(intent, 10);


            }});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Bitmap bmp = (Bitmap)data.getExtras().get("data");
        Bitmap bmp = BitmapFactory.decodeFile(filename);

                ImageView imgView = (ImageView)findViewById(R.id.imageView);
                imgView.setImageBitmap(bmp);
    }
}
