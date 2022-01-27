package org.techtown.mycapture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 10;
    Button mCaptureBtn;
    ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1; //사진 요청 코드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }
    public void capture(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //버전이 Android Mashmallow 이상에서만 실행된다
            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission, PERMISSION_CODE);
            //카메라와 내부 저장소에 접근 권한을 체크한다.
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //권한이 허용되었으면 카메라를 실행시킨다.
                openCamera();
            }
            else {
                //허용되지 않았으면 권한 체크를 다시 물어본다.
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }


    private void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라를 실행한다
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) { //카메라 앱의 존재 여부
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); //찍은 사진을 인텐트에 담는다
        }
    }

    //권한 요청
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else{
                    Toast.makeText(this, "권한이 거부되엇음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //캡쳐한 사진을 이미지뷰로 가져오는 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //데이터를 받아 bitmap 형태로 이미지뷰에 내보낸다.
            imageView.setImageBitmap(imageBitmap);
        }
    }
}
