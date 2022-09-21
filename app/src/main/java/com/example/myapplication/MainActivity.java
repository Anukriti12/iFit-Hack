package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView myImage;
    private Button button;

    @Override
    public void onUserLeaveHint()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            PictureInPictureParams pictureInPictureParams = new PictureInPictureParams.Builder().build();
            enterPictureInPictureMode(pictureInPictureParams);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImage = (ImageView) findViewById(R.id.myImage);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture();
            }
        });

    }


    ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result1) {
                            int result = result1.getResultCode();
                            Intent data = result1.getData();

                            if (result==RESULT_OK)
                            {
                                Bitmap b = (Bitmap)data.getExtras().get("data");
                                myImage.setImageBitmap(b);
                            }
                        }
                    }
            );

    private void TakePicture()
    {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        resultLauncher.launch(i);
    }


}