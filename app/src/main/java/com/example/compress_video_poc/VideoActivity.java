package com.example.compress_video_poc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoActivity extends AppCompatActivity {

    Button btnEncode,next,btnDecode;
    TextView textView,result,compressSize;
    VideoView videoView;
    String sImage;
    String s;
    float size1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        btnEncode=findViewById(R.id.Video_Encode);
        btnDecode=findViewById(R.id.VideoDecode);
        next=findViewById(R.id.Next);
        result=findViewById(R.id.Size);
        compressSize=findViewById(R.id.CompressSize);

        textView=findViewById(R.id.textView);
        videoView= findViewById(R.id.VideoView);


        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(VideoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(VideoActivity.this
                            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);


                }
                else
                {
                    selectVideo();
                }
            }

        });

//        btnDecode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // decode base64 string
//                byte[] bytes= Base64.decode(sImage,Base64.DEFAULT);
//                // Initialize bitmap
//                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                // set bitmap on imageView
//                imageView.setImageBitmap(bitmap);
//            }
//        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // check condition
        if (requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectVideo();
        }
        else
        {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            videoView.setVideoURI(uri);
            videoView.start();

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            float size=file.length()/1024;

            textView.setText(String.format("Size: % .2f KB",size));


//            try {
//                compressSize.setText((int) size1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            new CompressVideo() {
                @Override
                protected String doInBackground(String... strings) {
                    String videoPath = VideoActivity.this.compressVideo(uri, strings[0]);

                    size1=Float.parseFloat(videoPath);
//                    compressSize.setText((int) size1);
                    return videoPath;
                }
            }.execute("false",uri.toString(),file.getPath());


//
        }
    }

    private String compressVideo(Uri uri, String string) {

        return string;
    }

    private void selectVideo() {
            // clear previous data
            textView.setText("");
//            videoView.setVideoURI();
            // Initialize intent
            Intent intent=new Intent(Intent.ACTION_PICK);
            // set type
            intent.setType("video/*");
            // start activity result
            startActivityForResult(Intent.createChooser(intent,"Select video"),100);
        }
    }




//     videoPath = (String) (VideoActivity.this).CompressVideo()compressVideo(uri, strings[2]);


//            String videoPath=VideoActivity.this.compress(uri,string[2]);

//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
////                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] bytes = stream.toByteArray();
//            sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
//            textView.setText(sImage);

//            File imageFile = new File(String.valueOf(compressedFilePath));
//            float length = imageFile.length() / 1024f; // Size in KB
//            System.out.println("length = " + length);
//            String value;
//            if (length >= 1024)
//                value = length / 1024f + " MB";
//            else
//                value = length + " KB";
//
//            Toast.makeText(this, ""+value, Toast.LENGTH_SHORT).show();
//                File file=new File(s);
//                float size=file.length()/1024f;
//
//                result.setText(String.format("Size: % .2f KB",size));//







