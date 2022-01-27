package org.techtown.mymediaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordList extends AppCompatActivity implements View.OnClickListener {
    private ListView myListView;
    VideoView videoview;
    ArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        myListView = (ListView) findViewById(R.id.listView);

        //지정된 경로의 파일 포인터
        final File fp = new File("/storage/emulated/0/Android/data/org.techtown.mymediaapp/cache/");

        if (fp.exists() == false) {
            return;
        }
        final File[] files = fp.listFiles();

        final ArrayList<String> arrayList = new ArrayList<String>();

        //파일의 이름을 불러와 리스트에 하나씩 추가한다
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isHidden() && files[i].isFile())
                arrayList.add(files[i].getName());
        }
        ArrayAdapter<String> arrayAdt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        myListView.setAdapter(arrayAdt);


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int i, long id) {
                //리스트의 파일을 클릭 시 비디오를 실행시킨다
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                Uri vidFile = intent.getData();
                getIntent().putExtra("vidFile" , i);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri vidFile = data.getData();
                Intent playVid = new Intent(RecordList.this, Videoview.class);
                playVid.putExtra("vidFile", vidFile);
                startActivity(playVid);
            }
        }
    }

    @Override
    public void onClick(View view) {
    }
}
