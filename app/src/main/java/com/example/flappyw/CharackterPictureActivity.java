package com.example.flappyw;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CharackterPictureActivity extends Activity {

    int height, width;
    Bitmap FullImage;
    Bitmap FullImageCopy;
    Bitmap FullImageTube;
    Rect rectCrop;
    Mat FullImageMatrix;
    CharackterDrawingView RechteckView;

    ImageView photo;

    String CurrentPhotoPath;

    static final int REQUEST_IMAGE_CAPTURE =1033;
    static final int GALLERY_REQUEST_1 = 421;

    public final String APP_TAG = "FlappyWo";
    public String photoFileName = "photo.jpg";
    File photoFile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takecharackterpicture);
        Intent intent = getIntent();


        OpenCVLoader.initDebug();

        photo = (ImageView) findViewById(R.id.im);
        RechteckView = (CharackterDrawingView) findViewById(R.id.rechteckView);

        Button clickButton = (Button) findViewById(R.id.Button);
        if (clickButton != null) {
            clickButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    scale();
                }
            });
        }


        Button clickButton2 = (Button) findViewById(R.id.Button2);
        if (clickButton2 != null) {
            clickButton2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        dispatchTakePictureIntent();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("2");
                    }

                }
            });
        }

        Button clickButton3 = (Button) findViewById(R.id.FinishButton);
        if (clickButton3 != null) {
            clickButton3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finishThis();
                }
            });
        }

        Button clickButton4 = (Button) findViewById(R.id.GalleryButton2);
        if (clickButton4 != null) {
            clickButton4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    openGallery2();
                }
            });
        }


    }

    public void scale(){

        if(FullImage== null ){
            return;
        }

        try {
            FullImageCopy = FullImage.copy(Bitmap.Config.ARGB_8888, true);
            FullImageMatrix = new Mat(height, width, CvType.CV_8UC1);
            Utils.bitmapToMat(FullImageCopy, FullImageMatrix);
            FullImageTube = Bitmap.createBitmap( 200*Constants.SCREEN_WIDTH/1080 ,200*Constants.SCREEN_WIDTH/1080 , Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("0");
        }
        try {
            int StartX = (int) RechteckView.returnXCoordinate();
            int StartY = (int) RechteckView.returnYCoordinate();
            rectCrop = new Rect(StartX, StartY, 200*Constants.SCREEN_WIDTH/1080, 200*Constants.SCREEN_WIDTH/1080);
            Mat image_roi = new Mat(FullImageMatrix, rectCrop);
            Utils.matToBitmap(image_roi,FullImageTube);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("1");
        }

        try {
            ImageView img2 = (ImageView) findViewById(R.id.im2);
            img2.setImageBitmap(FullImageTube);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("3");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_1:
                    Uri selectedImage = data.getData();
                    try {
                        FullImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        setBitmap(FullImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                        System.out.println("Gallery Fehler");
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    try{
                        FullImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        setBitmap(FullImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                        System.out.println("Aufnahme Fehler");
                    }
            }
    }

    private void dispatchTakePictureIntent() {

        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                // File photoFile = null;
                // photoFile = createImageFile();
                photoFile = getPhotoFileUri(photoFileName);
                // Continue only if the File was successfully created
                try {
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(CharackterPictureActivity.this,
                                "com.example.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("88");

                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("4");
        }
    }

    private File createImageFile() throws IOException {

        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            CurrentPhotoPath = "File" + image.getAbsolutePath();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("5");
            return null;
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    public String saveBitmap(Bitmap bitmap) {
        String fileName2 = "Charackter";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName2, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error 100");
            fileName2 = null;
        }
        return fileName2;
    }

    public void setBitmap(Bitmap pic){
        FullImage = BitmapScaler.scaleToFitWidth(pic, Constants.SCREEN_WIDTH/2);
        // RESIZE BITMAP, see section below
        // Load the taken image into a preview
        photo.setImageBitmap(FullImage);
        height = FullImage.getHeight();
        width = FullImage.getWidth();

    }

    public void openGallery2(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST_1);
    }

    public void finishThis(){
        if(FullImageTube!=null){
            Constants.CheckCodeCharackter=1;
        }
        Intent intent = new Intent(CharackterPictureActivity.this,MainActivity.class);
        intent.putExtra("KEY2", saveBitmap(FullImageTube));
        startActivity(intent);
        Toast.makeText(this, "Bird has been initialized!", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent backmain = new Intent(CharackterPictureActivity.this,CreateActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }

}




