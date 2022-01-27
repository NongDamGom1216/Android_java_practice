package org.techtown.mynotificiation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    String NOTIFICAION_CHANNEL_ID = "my_channel_id_01";

    private void createMotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICAION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            //알림 채널을 생성한다. id, name. importance의 정도
            notificationChannel.setDescription("Channel description");
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //알림채널 객체를 만들고 매니저에게 채널 객체의 생성을 요청한다.
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMotificationChannel();
    }

    public void sendmessage(View view) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICAION_CHANNEL_ID);
        //알림 빌더를 생성한다.

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
        //액티비티를 설정한다.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        //터치했을 때 액티비티를 실행하기 위한 함수

        notificationBuilder.setSmallIcon(R.drawable.mobile).setContentTitle("메일 알림").setContentText("새로운 메일이 도착하였습니다.").setContentIntent(pendingIntent); //icon 추가
        //알림에서 보이는 icon과 title, 텍스트 문장 설정, 그리고 설정한 pendingintent를 등록합니다.

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
        //notificationmanager.notify로 등록한다. id에 대해서 알림이 생성됩니다.
    }
}