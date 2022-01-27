package org.techtown.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    int value; //숫자 값을 저장하는 변수
    int operator; //연산자 구분 1,2,3,4 = +,-,*,/

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonplus;
    Button buttonminus;
    Button buttonmulti;
    Button buttondiv;
    Button buttonok;
    Button buttonclear;

    EditText myEditText; //EditText 객체 변수 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button)findViewById(R.id.number0); //각각의 버튼에 대해서 아이디를 찾는 메소드 findViewByID을 사용한다.
        button1 = (Button)findViewById(R.id.number1); //R.id.number는 id의 위치를 의미한다.
        button2 = (Button)findViewById(R.id.number2);
        button3 = (Button)findViewById(R.id.number3);
        button4 = (Button)findViewById(R.id.number4);
        button5 = (Button)findViewById(R.id.number5);
        button6 = (Button)findViewById(R.id.number6);
        button7 = (Button)findViewById(R.id.number7);
        button8 = (Button)findViewById(R.id.number8);
        button9 = (Button)findViewById(R.id.number9);
        buttonplus = (Button)findViewById(R.id.ButtonPlus);
        buttonminus = (Button)findViewById(R.id.ButtonMinus);
        buttonmulti = (Button)findViewById(R.id.ButtonMulti);
        buttondiv = (Button)findViewById(R.id.ButtonDiv);

        myEditText = (EditText) findViewById(R.id.number);

        operator =0;
    }

    //onClick 속성을 이용하여 버튼의 클릭 이벤트를 처리한다.
    public void onClick1(View view) {
        myEditText.setText(myEditText.getText().toString()+1); }
        // setText를 이용하여 숫자 버튼 클릭시 myEditText에 숫자 값을 입력

    public void onClick2(View view) {
        myEditText.setText(myEditText.getText().toString()+2);
    }

    public void onClick3(View view) {
        myEditText.setText(myEditText.getText().toString()+3);
    }

    public void onClickplus(View view) {
        value = Integer.valueOf(myEditText.getText().toString().trim()); //string 값을 integer 값으로 변경
        myEditText.setText("");
        operator = 1;
    }

    public void onClick4(View view) {
        myEditText.setText(myEditText.getText().toString()+4);
    }

    public void onClick5(View view) {
        myEditText.setText(myEditText.getText().toString()+5);
    }

    public void onClick6(View view) {
        myEditText.setText(myEditText.getText().toString()+6);
    }

    public void onClickminus(View view) {
        value = Integer.valueOf(myEditText.getText().toString().trim());
        myEditText.setText("");
        operator = 2;
    }

    public void onClick7(View view) {
        myEditText.setText(myEditText.getText().toString()+7);
    }

    public void onClick8(View view) {
        myEditText.setText(myEditText.getText().toString()+8);
    }

    public void onClick9(View view) {
        myEditText.setText(myEditText.getText().toString()+9);
    }

    public void onClickmul(View view) {
        value = Integer.valueOf(myEditText.getText().toString().trim());
        myEditText.setText("");
        operator = 3;
    }

    public void onClick0(View view) {
        myEditText.setText(myEditText.getText().toString()+0);
    }

    public void onClickclear(View view) {
        myEditText.setText("");
    }

    public void onClickdiv(View view) {
        value = Integer.valueOf(myEditText.getText().toString().trim());
        myEditText.setText("");
        operator = 4;
    }

    public void onClickok(View view) {
        if(operator ==1) //plus
        {
            value = value + Integer.valueOf(myEditText.getText().toString().trim()); //공백을 제거하는 trim 메소드를 사용했다.
            myEditText.setText(Integer.toString(value)); //계산되어 있는 저장된 값 value를 가져온다.
        }
        if(operator ==2) //minus
        {
            value = value - Integer.valueOf(myEditText.getText().toString().trim());
            myEditText.setText(Integer.toString(value));
        }
        if(operator ==3) //multi
        {
            value = value * Integer.valueOf(myEditText.getText().toString().trim());
            myEditText.setText(Integer.toString(value));
        }
        if(operator ==4) //div
        {
            value = value / Integer.valueOf(myEditText.getText().toString().trim());
            myEditText.setText(Integer.toString(value));
        }
    }

}