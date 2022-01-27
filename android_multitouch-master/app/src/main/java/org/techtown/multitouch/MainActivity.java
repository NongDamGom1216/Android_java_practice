package org.techtown.multitouch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Myview(this, null));
    }

    static class Myview extends View {

        private static final int SIZE = 60;

        final int MAX_POINTS = 10; //터치 포인터의 갯수를 설정할 수 있다.
        float[] x = new float[MAX_POINTS];
        float[] y = new float[MAX_POINTS];
        boolean[] touching = new boolean[MAX_POINTS];

        private Paint mPaint;

        public Myview(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initView();
        }
        private void initView() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //setAntialias
            mPaint.setColor(Color.GREEN);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 채우기, 외곽선
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            int index = event.getActionIndex();
            int id = event.getPointerId(index); //인덱스로부터 포인터의 id를 구한다.
            int action = event.getActionMasked();

            switch (action){
                case MotionEvent.ACTION_DOWN: //터치하는 첫 번재 포인터
                case MotionEvent.ACTION_POINTER_DOWN: //첫 번째 포인터 이외의 포인터
                    x[id] = (int)event.getX(index);
                    y[id] = (int)event.getY(index);
                    touching[id] = true; //현재 터치가 되고 있다는 상태를 의미한다.
                    break;
                case MotionEvent.ACTION_MOVE: //화면을 누르면서 이동할 때
                    break;
                case MotionEvent.ACTION_UP: //화면을 떠나는 마지막 포인터
                case MotionEvent.ACTION_POINTER_UP: // 마지막 포인터 이외의 다른 포인터가 화면을 떠날 때
                case MotionEvent.ACTION_CANCEL:
                    touching[id] = false; //터치 종료, 주석 처리하면 원을 유지할 수 있다.
                    break;
            }
            invalidate();

            return true;
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);

            for (int i =0; i < MAX_POINTS; i++){ //현재 터치되고 있는 위치에 원을 그린다.
                if(touching[i]){
                    canvas.drawCircle(x[i], y[i], SIZE, mPaint);
                }
            }
        }
    }
}

