package org.techtown.mysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView gx, gy, gz;
    private TextView stepsTextView, sensitiveTextView;

    private Button reset;

    private SensorManager sensorManager; //센서 연결 변수
    private float acceleration; //가속도 변수


    private float previousY, currentY;
    private int steps; //걸음수 변수

    SeekBar seekBar; //seekbar 선언
    int threshold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gx = (TextView) findViewById(R.id.gx);
        gy = (TextView) findViewById(R.id.gy);
        gz = (TextView) findViewById(R.id.gz);

        stepsTextView = (TextView) findViewById(R.id.steps);
        sensitiveTextView = (TextView) findViewById(R.id.sensitive);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
        threshold = 10;
        sensitiveTextView.setText(String.valueOf(threshold));

        previousY = currentY = steps = 0;

        acceleration = 0.0f;
        //센서 연결
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //안드로이드의 센서 주기에 대해 지정할 수 있다
        sensorManager.registerListener(stepDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        //SENSOR_DELAY_NORMAL : 적당한 속도
    }

    private SensorEventListener stepDetector = new SensorEventListener() {
        //사용자의 움직임을 감지하기 위한 리스너

        @Override
        public void onSensorChanged(SensorEvent event) {
            //x축, y축, z축에 대한 값을 얻어온다
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            currentY = y; //현재의 y값 대입

            //절대값을 받아서 threshold값보다 클 때 걸음 수를 올린다.
           if (Math.abs(currentY - previousY) > threshold) {
                steps++;
                stepsTextView.setText(String.valueOf(steps));
            }
            gx.setText(String.valueOf(x));
            gy.setText(String.valueOf(y));
            gz.setText(String.valueOf(z));

            previousY = y;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void resetSteps(View v) { //seekbar 값 초기화
        steps = 0;
        stepsTextView.setText(String.valueOf(steps));
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //seekbar의 상태가 변경되었을 때 실행됨
            threshold = seekBar.getProgress();
            sensitiveTextView.setText(String.valueOf(threshold));
            //thershold 값을 seekbar 값에 따라 변경한다
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        //seekbar가 움직일 때
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        //seekbar가 멈췄을 때
        }
    };
}