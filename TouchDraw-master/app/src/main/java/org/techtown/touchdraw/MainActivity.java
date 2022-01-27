package org.techtown.touchdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static int color = Color.GREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Myview myview = new Myview(this);
        setContentView(new Myview(this));

    }


    class Myview extends View { //뷰를 상속한 클래스 생성
        float x, y; //좌표값
        String str = "";

        Path path = new Path(); //좌표값을 저장할 객체 생성

        public Myview(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x = event.getX();
            y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN)
                path.moveTo(x, y); //좌표의 위치 값 지정
            if (event.getAction() == MotionEvent.ACTION_MOVE)
                path.lineTo(x, y); //이동하는 좌표 값
            if (event.getAction() == MotionEvent.ACTION_UP)
                str = "ACTION_UP";


            invalidate(); // 새로 고침
            return true;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint(); //페인트 생성

            paint.setAntiAlias(true); //경계 쪽에 중간색을 삽입하여 선이 더 부드럽게 보이는 효과
            paint.setStrokeWidth(10f); //선의 굵기
            paint.setStyle(Paint.Style.STROKE); //선의 속성
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setColor(color);
            canvas.drawPath(path, paint); //onTouchEvent에서  지정한대로 그린다.

        }
    }
}



