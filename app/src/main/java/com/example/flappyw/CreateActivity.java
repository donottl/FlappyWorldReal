package com.example.flappyw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateActivity extends Activity {

    Bitmap Tube;
    static final int GALLERY_REQUEST_2 = 6969;
    Bitmap Background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_main);
        Intent intent = getIntent();

        Button clickButton = (Button) findViewById(R.id.TubeButton);
        if (clickButton != null) {
            clickButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startTubePicture();
                }
            });
        }

        Button clickButton2 = (Button) findViewById(R.id.BackgroundButton);
        if (clickButton2 != null) {
            clickButton2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    chooseBackgroundPicture();
                }
            });
        }

        Button clickButton3 = (Button) findViewById(R.id.CharackterButton);
        if (clickButton3 != null) {
            clickButton3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startCharackterPicture();
                }
            });
        }

        Button clickButton4 = (Button) findViewById(R.id.DefaultTubeButton);
        if (clickButton4 != null) {
            clickButton4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setDefaultTube();
                }
            });
        }

        Button clickButton5 = (Button) findViewById(R.id.DefaultCharackterButton);
        if (clickButton5 != null) {
            clickButton5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setDefaultCharckter();
                }
            });
        }

        Button clickButton6 = (Button) findViewById(R.id.BackButtonCreate);
        if (clickButton6 != null) {
            clickButton6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        Button clickButton7 = (Button) findViewById(R.id.DefaultBackgroundButton);
        if (clickButton7 != null) {
            clickButton7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setDefaultBackground();
                }
            });
        }

      /* Button clickButton8 = (Button) findViewById(R.id.SongButton);
        if (clickButton8 != null) {
            clickButton8.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startAudioSelect();
                }
            });
        }*/

  /*      Button clickButton9 = (Button) findViewById(R.id.NoSongButton);
        if (clickButton9 != null) {
            clickButton9.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Constants.NoSong=1;
                }
            });
        }
*/    }

    public void startTubePicture() {
        Intent tube = new Intent(this, TubePictureActivity.class);
        startActivity(tube);
        this.finish();
    }

    public void chooseBackgroundPicture() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST_2);
    }

    public String saveBitmap(Bitmap bitmap) {
        String fileName2 = "Background";//no .png or .jpg needed
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_2:
                    Uri selectedImage = data.getData();
                    try {
                        Background = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        Constants.CheckCodeBackground = 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                        System.out.println("Gallery Fehler");
                    }
                    break;
            }
    }

    public void startCharackterPicture() {
        Intent tube = new Intent(this, CharackterPictureActivity.class);
        startActivity(tube);
        this.finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent backmain2 = new Intent(CreateActivity.this, MainActivity.class);
        if (Background != null) {
            try {
                backmain2.putExtra("Key 4", saveBitmap(Background));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Background Transfer not working");
            }
        }

        startActivity(backmain2);
        overridePendingTransition(R.transition.transition_quick, R.transition.transition_quick);
        finish();
    }

    public void setDefaultTube() {
        try {
            Constants.CheckCodeTube = 0;
            Toast.makeText(this, "Default Tube has been set", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Set Default Bitmaps fail");
        }
    }

    public void setDefaultBackground() {
        try {
            Constants.CheckCodeBackground = 0;
            Toast.makeText(this, "Default Background has been set", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Set Default Background fail");
        }
    }

    public void setDefaultCharckter() {
        try {
            Constants.CheckCodeCharackter = 0;
            Toast.makeText(this, "Default Bird has been set", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Set Default Bitmaps fail");
        }
    }

    public void finishThis() {
        Intent intent = new Intent(CreateActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void back(View view) {
        Intent backmain2 = new Intent(CreateActivity.this, MainActivity.class);
        if (Background != null) {
            try {
                backmain2.putExtra("Key 4", saveBitmap(Background));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Background Transfer not working");
            }
        }

        startActivity(backmain2);
        overridePendingTransition(R.transition.transition_quick, R.transition.transition_quick);
        finish();
    }

  /*  public void startAudioSelect() {
        try {
            Intent goo = new Intent(CreateActivity.this, SoundUploadMain.class);
            startActivity(goo);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OOOK");
        }*/
    }

