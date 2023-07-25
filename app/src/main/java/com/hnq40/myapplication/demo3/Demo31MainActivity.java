package com.hnq40.myapplication.demo3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hnq40.myapplication.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Demo31MainActivity extends AppCompatActivity {
    EditText txt1,txt2;
    Button btn1;
    TextView tvKQ;
    String path="https://batdongsanabc.000webhostapp.com/mob403/demo2_api_get.php";
    String pathPOST="https://batdongsanabc.000webhostapp.com/mob403/demo2_api_post.php";
    String kq="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo31_main);
        txt1=findViewById(R.id.demo31Txt1);
        txt2=findViewById(R.id.demo31Txt2);
        btn1=findViewById(R.id.demo31Btn1);
        tvKQ=findViewById(R.id.demo31Tv1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new GETAsyncTask().execute();
                new POSTAsyncTask().execute();
            }
        });
    }
    class POSTAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Chuyen duong dan thanh url
                URL url=new URL(pathPOST);
                //2. xu ly tham so post
                String param="canh="+ URLEncoder.encode(txt1.getText().toString(),"utf-8");
                //3. mo ket noi
                HttpURLConnection urlConnection
                        =(HttpURLConnection) url.openConnection();
                //4. set thuoc tinh cho tham so post
                urlConnection.setDoOutput(true);//co lay ket qua tra ve
                urlConnection.setRequestMethod("POST");//xac dinh phuong thuc
                urlConnection.setFixedLengthStreamingMode(param.getBytes().length);//do dai du lieu
                //5
                urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                //6. doc du lieu
                //6.1 lay ve tham so
                PrintWriter printWriter=new PrintWriter(urlConnection.getOutputStream());
                printWriter.print(param);
                printWriter.close();
                //6.2 tien hanh doc du lieu
                String line="";
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder=new StringBuilder();
                while ((line=br.readLine())!=null){
                    stringBuilder.append(line);
                }
                //6.3 tra ve ket qua
                kq=stringBuilder.toString();
                //7. dong ket noi
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvKQ.setText(kq);
        }
    }
    class GETAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            path+="?name="+txt1.getText().toString()+"&mark="+txt2.getText().toString();
            try {
                URL url=new URL(path);//chuyen path thanh url
                //tao bo dem de doc du lieu
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(url.openConnection().getInputStream()));
                //bat dau doc
                String line="";//bien luu tung dong du lieu
                StringBuilder stringBuilder=new StringBuilder();//chua toan bo noi dung doc duoc
                while ((line=br.readLine())!=null){//doc den khi het noi dung
                    stringBuilder.append(line);//dua tung dong doc duoc vao stringBuilder
                }
                kq=stringBuilder.toString();//tra ve ket qua
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvKQ.setText(kq);
        }
    }
}