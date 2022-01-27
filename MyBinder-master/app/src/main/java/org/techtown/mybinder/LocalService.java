package org.techtown.mybinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalService extends Service {
//클라이언트(컴포넌트)에게 반환되는 바인더
    private final IBinder mBinder = new LocalBinder();
//난수 발생기
    private final Random mGenerateor = new Random();
//클라이언트 바인더를 위한 클래스
    public class LocalBinder extends Binder {
        LocalService getService(){
            return LocalService.this;
        }
    }

    //서비스와 액티비티 사이에서 데이터를 통신할 때 사용하는 메소드
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    } //bBinder값은 서비스와 액티비티 사이의 인터페이스를 정의
    //bindService()를 실행하면 호출된다
    //컴포넌트에 난수를 반환해 줄 메소드
    public int getRandomNumber(){
        return mGenerateor.nextInt(100);
    }
}
