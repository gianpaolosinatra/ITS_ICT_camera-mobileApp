package com.example.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class activity_camera extends AppCompatActivity {

    ImageView imageView;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);


        String s =
                getIntent().
                        getExtras().
                        getString("name");

        Log.i("name", s);


        // ---
        filename =
                Environment.getExternalStorageDirectory() + "/photo.jpeg";

        Bitmap bmp = BitmapFactory.decodeFile(filename);


        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bmp);


        // ---

        Button take_photo = (Button) findViewById(R.id.Take_Photo);
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent =
                        new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filename)));

                startActivityForResult(intent, 10);//10 = cod id activity
            }
        });

        Button bshare = (Button) findViewById(R.id.BSHARE);
        bshare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "abc" + "");
            }
        });




    }


    Bitmap filterImage(Bitmap bmp) {

        bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);

        int[] pixelData = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixelData, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        int x, y, p;
        int r, g, b;
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        for (x = 0; x < w; x++) {
            for (y = 0; y < h; y++) {

                p = pixelData[y * w + x];
                r = Color.red(p);
                g = Color.green(p);
                b = Color.blue(p);
                pixelData[y * w + x] = Color.rgb(r, g, 0);

            }
        }

        bmp.setPixels(pixelData,0,w,0,0,w,h);

        return(bmp);
    }

    //METODO PERMESSO ACCESSO CAMERA UTENTE
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode!=1) return;

        for (int i = 0;i<permissions.length;i++){
            Log.i("Permission",permissions[i]+" = "+grantResults[i]);

            if (grantResults[i] == -1){
                Toast.makeText(getApplicationContext(),
                        "Dai il permesso"+permissions[i],Toast.LENGTH_LONG)
                        .show();
                finish();
                return;
            }
        }
    }

    @Override
           protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //Bitmap bmp = ( Bitmap ) data.getExtras().get("data");

        Bitmap bmp = ( Bitmap ) BitmapFactory.decodeFile(filename);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bmp);


        Bitmap bmp2 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        bmp2 = filterImage(bmp2);
        imageView.setImageBitmap(bmp2);
    }



}