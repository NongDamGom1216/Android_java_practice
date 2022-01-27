package org.techtown.myasynctaskimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    int flag = 1; //Error 값을 판별하기 위한 값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownload = (Button) findViewById(R.id.btn_download);

        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DLTask().execute();
            }
        });
    }


    class DLTask extends AsyncTask<String, Void, Void> {
        EditText etUrl = (EditText) findViewById(R.id.et_url);
        ImageView iView = (ImageView) findViewById(R.id.iv_image);

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... voids) {
            try {
                URL url = new URL(etUrl.getText().toString()); //사전에 입력된 URL값을 전달
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //HttpURLConnection을 통해 Web의 이미지 데이터를 다운로드합니다.
                urlConnection.connect();
                InputStream iStream = urlConnection.getInputStream(); //서버에서 가져온 값을 읽기
                final Bitmap bitmap = BitmapFactory.decodeStream(iStream); //bitmap 생성하기
                iView.post(new Runnable() {
                    @Override
                    public void run() {
                        iView.setImageBitmap(bitmap);
                        flag = 1;
                    }
                });

           } catch (MalformedURLException e) {
                e.printStackTrace();
                flag = 0; //에러에 대한 값 저장
            } catch (IOException e) {
                e.printStackTrace();
                flag = 0; //에러에 대한 값 저장
            }
            return null;
        }

        protected void onProgressUpdate(String... Bitmap) {
            super.onProgressUpdate();

        }

        protected void onPostExecute(Void result) {
           if(flag == 0) {
               Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
               //에러일 경우 메시지를 띄운다
           }

        }
    }
}