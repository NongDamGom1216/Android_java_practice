package org.techtown.myshakedsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener, TextToSpeech.OnInitListener {
    private SensorManager mSensorManager; //센서 객체
    private Sensor mAccelerometer; //가속도 객체
    private static int SHAKE_THRESHOLD = 3;
    private TextToSpeech mTts; //텍스트를 음성으로 변환
    private TextView mNumber; //주사위로 나온 숫자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumber = (TextView) findViewById(R.id.number);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        //해당 액티비티에서 호출되었을 때 가속도 값을 얻을 수 있도록 리스너를 등록한다
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        //호출이 끝난 뒤 리스너를 해제한다
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR) {
            mTts.setLanguage(getResources().getConfiguration().locale);
        }
    }

    @Override
    protected void onDestroy() {
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    private void generateRandomNumber() { //숫자를 랜덤으로 받아온 뒤 텍스트 변수에 넣어준다
        Random randomGenerator = new Random();
        int randomNum = randomGenerator.nextInt(6) + 1;
        mNumber.setText(Integer.toString(randomNum));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        //Math.sqrt(x*x + y*y + z*z) : 9.8
        float acceleration = (float) Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;
        //중력을 이용한 흔들림 센서를 사용하여 감지한다

        if (acceleration > SHAKE_THRESHOLD) {
            generateRandomNumber();
        }
    }
}

