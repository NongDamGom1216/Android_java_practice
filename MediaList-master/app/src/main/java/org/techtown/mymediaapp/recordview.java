package org.techtown.mymediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class recordview extends AppCompatActivity implements SurfaceHolder.Callback {

    private final static String TAG = "recordview";

    Button mBtStart = null;

    MediaRecorder mRecorder = null; // media recorder 객체 생성
    public String mPath = null;

    boolean isRecording = false;
    boolean isPlaying = false;
    boolean hasVideo = false;

    SurfaceView mSurface = null; //프리뷰를 위한 surfaceview
    SurfaceHolder mSurfaceHolder = null;
    Camera mCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordview);

        mSurface = (SurfaceView) findViewById(R.id.sv);
        mBtStart = (Button) findViewById(R.id.start);

        //권한 체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        } else {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
            requestPermissions(permission, 10);
        }*/
        //권한이 허용되면
            mRecorder = new MediaRecorder();

            mBtStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasVideo = true;
                    initVideoRecorder();
                    startVideoRecorder();
                }
            });
        }
    }
    void initVideoRecorder() { //카메라 사용과 프리뷰 등록에 대한 메소드
        Log.d(TAG, "initVideoRecorder");

        if (isRecording == false) {
            mCamera = Camera.open();
        }
            mCamera.setDisplayOrientation(90); //카메라를 90도로 회전
            mSurfaceHolder = mSurface.getHolder(); //surfaceview의 콜백 설정
            mSurfaceHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (mCamera == null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    void startVideoRecorder() {
        Log.d(TAG, "startVideoRecorder");

        if (isRecording) { //녹화 중이었을 때
            mRecorder.stop();
            mRecorder.release();

            mRecorder = null;
            mCamera.lock();

            isRecording = false;
            finish();
        } else { //녹화를 시작할 때
            mRecorder = new MediaRecorder();
            mCamera.unlock();
            mRecorder.setCamera(mCamera);
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //녹화에 사용되는 오디오 소스를 선택한다
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 녹화에 사용되는 비디오 소스를 선택한다
            mRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
            mRecorder.setOrientationHint(90);

            long time = System.currentTimeMillis();  //시간 받기
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            //포멧 변환 형식 만들기
            Date dd = new Date(time);  //받은 시간을 Date 형식으로 바꾸기
            String strTime = sdf.format(dd); //Data 정보를 포멧 변환하기
            mPath = getExternalCacheDir().getAbsolutePath()+ "/record" + strTime + ".mp4";
            Log.d(TAG, "file path is " + mPath);
            mRecorder.setOutputFile(mPath); //출력 파일의 경로 설정
            mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface()); //녹화되는 비디오의 프리뷰를 서비스
            try {
                mRecorder.prepare(); //비디오를 캡쳐하고 인코딩하기 위한 레코더를 준비한다
                mRecorder.start(); //비디오를 캡쳐하고 인코딩하여 파일에 저장한다
            } catch (Exception e) {
                e.printStackTrace();
            }
            isRecording = true;

            mBtStart.setText("Stop Recording");
        }
    }
}