package org.techtown.mydom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@SuppressLint("NewApi")
//해당 프로젝트의 minSdkVersion보다 상위버전 SDK의 API를 사용하려 할 때
// warning을 없애고 개발자가 해당 API를 사용 가능하다

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Document doc = null;
    LinearLayout layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textview1);
    }

    public void click(View view) {
        GetXMLTask task = new GetXMLTask(this);
        task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");
    }

    @SuppressLint("NewApi")
    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        private Activity context;

        public GetXMLTask(Activity context) {
            this.context = context;
        }


        @Override
        protected Document doInBackground(String... urls) {
            URL url;

            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db;

                db = dbf.newDocumentBuilder(); //XML 문서를 빌드하는 객체
                doc = db.parse(new InputSource(url.openStream())); //XML 문서를 Parsing
                doc.getDocumentElement().normalize();

                //DocumentBuilderFactory로 페이지를 document type으로 만든 뒤에 onPostExecute에 전달한다.
            } catch (Exception e) {
                e.printStackTrace();
                Handler mHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message message) {
                        Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
                    }
                };
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            String s = "";
            NodeList nodeList = doc.getElementsByTagName("data");
            // "data" Tag를 가지고 있는 노트를 찾아서 nodelist에 받는다.

            for (int i = 0; i < nodeList.getLength(); i++) {
                s += "" + i + ": 날씨 정보: "; //날씨 정보의 갯수 Number
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;

                NodeList nameList = fstElmnt.getElementsByTagName("temp");
                //데이터 태그 중에서 temp(기온) 태그 속의 데이터를 가져와서 이것을 s에 더해준다.
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                s += "온도 = " + ((Node) nameList.item(0)).getNodeValue() + " ,";
                //(Node) nameList.item(0)).getNodeValue()로 태그 속의 값을 불러온다.

                NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
                //데이터 태그 중에서 wfkor(날씨) 태그 속의 데이터를 가져와서 이것을 s에 더해준다.
                Element websiteElement = (Element) websiteList.item(0);
                websiteList = websiteElement.getChildNodes();
                s += "날씨 = " + ((Node) websiteList.item(0)).getNodeValue() + "\n";
                //(Node) websiteList.item(0)).getNodeValue()로 태그 속의 값을 불러온다.
            }
            textView.setText(s);
        }
    }
}