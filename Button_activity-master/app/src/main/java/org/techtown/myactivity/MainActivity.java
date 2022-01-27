package org.techtown.myactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    static final int GET_STRING = 1;
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button); //메인 액티비티의 문자여 반환하기 버튼
        text = (TextView) findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0){ //서브 액티비티로 명시적 인텐트해준다
               Intent in = new Intent(MainActivity.this, SubActivity.class);
               //인텐트 생성, 인자로 호출할 액티비티 클래스를 지정해준다
               startActivityForResult(in, GET_STRING); } //액티비티를 호출하여 결과를 받는다
        });
               //GET_STRING은 여러 개의 startActivityForResult를 구분하기 위한 인수이다
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //호출된 액티비티가 종료가 된 후 메인 액티비티에서 실행된다
        if(requestCode == GET_STRING){
            if(resultCode == RESULT_OK) {
                text.setText(data.getStringExtra("INPUT_TEXT"));
            }
        }
    }
}