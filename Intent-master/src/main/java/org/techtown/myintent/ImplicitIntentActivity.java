package org.techtown.myintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ImplicitIntentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = null;
        //인텐트 객체에 원하는 액션을 담아서 호출한다.
        switch (view.getId()){
            case R.id.web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                break;
            case R.id.call:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)123456789"));
                break;
            case R.id.map:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:87Q7+M2"));
                // geo : 위도, 경도
                break;
            case R.id.contact:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}