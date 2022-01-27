package org.techtown.mybinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocalService mService;
    boolean mBound = false; //서비스 연결

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //액티비티와 서비스의 연결
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //서비스 연결이 false면 서비스와 액티비티의 연결 종료
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }
    public void onButtonClick(View v) {
        //서비스 연결이 되면 getRandomNumber 함수를 호출하여 난수값을 불러온다
        if(mBound){
            int num = mService.getRandomNumber();
            Toast.makeText(this, "number: "+num, Toast.LENGTH_SHORT).show();
        }
    }
    //서비스의 연결에 따라서 mBound 값 결정하는 객체
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder)service;
            mService = binder.getService();
            mBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;

        }
    };
}