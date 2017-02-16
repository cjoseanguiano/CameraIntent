package com.camera.devix.cameraintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CamaraIntentActivity extends AppCompatActivity {
//Todo: Se crea una variable constante llamada ACTIVITY_START_CAMERA_APP con valor 0
private static final int ACTIVITY_START_CAMERA_APP = 0;
private ImageView mPhotoCapturedImageView;
private String mImageFileLocation = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_intent);

//Todo: Se hace referencia a la imagen que se encuentra en la activity
        mPhotoCapturedImageView = (ImageView)findViewById(R.id.imagePicture);

    }

    public void takePhoto(View view){
        //Todo: Mensaje para mostrar al usuario al precionar sobre el boton
//        Toast.makeText(getApplicationContext(),"Button pressed",Toast.LENGTH_SHORT).show();
        //Todo: Se inicializa un Intent con el nombre callCameraApplicationIntent
        Intent callCameraApplicationIntent = new Intent();
        //Todo: Se llama la accion de camara
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

       File photoFile = null;
        try {
            photoFile = createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        //Todo: Se inicia la actividad y se pasa la constante ACTIVITY_START_CAMERA_APP al metodo onActivityResult
        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Todo: Se verifica que el valor que trae requestCode sea igual que ACTIVITY_START_CAMERA_APP
        //Todo y requestCode contenga lo mismo que la constante RESULT_OK
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            /*//Todo: Mensaje para mostrar al usuario al capturar la fotografia
//            Toast.makeText(this,"Picture taken successfully",Toast.LENGTH_LONG).show();

            //Todo: Se crea el objeto Bundle con el nombre extras
            Bundle extras = data.getExtras();
            //Todo: Devuelve el bundle que contenga el intent
            Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
            //Todo: Se asigna a mPhotoCapturedImageView el valor que contenga el Bitmap photoCaptureBitmap
            mPhotoCapturedImageView.setImageBitmap(photoCaptureBitmap);
*/
            Bitmap photoCaptureBitmap = BitmapFactory.decodeFile(mImageFileLocation);
            mPhotoCapturedImageView.setImageBitmap(photoCaptureBitmap);
        }
    }

    File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(imageFileName,".jpg",storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;
    }
}
