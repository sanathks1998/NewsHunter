package com.example.newshunter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Magazine extends AppCompatActivity {
    String API="ccea21be03fa4060bf06b2428e5a29bf";
    String source="new-york-magazine";

    ListView lw;
    ProgressBar pbar;
    ArrayList<HashMap<String,String>> lists=new ArrayList<HashMap<String, String>>();
    final static String heading="title";
    final static String subtitles="description";
    final static String srcs="author";
    final static String imgs="urlToImage";
    final static String url="url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mag_bar);
               FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Magazine.this,MainActivity.class);
                startActivity(i);
            }
        });
        lw=(ListView)findViewById(R.id.newsa);
        pbar=(ProgressBar)findViewById(R.id.pbar);
        if(UrlChecker.isNetAvialable(getApplicationContext())){
            Magazine.DownloadNews dw=new Magazine.DownloadNews();
            dw.execute();}
        else{
            Toast.makeText(getApplicationContext(),"Check Ineternet Connection",Toast.LENGTH_LONG).show();

        }

    }
    class DownloadNews extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String se="";
            String urlParameters = "";
            se = UrlChecker.excuteGet("https://newsapi.org/v1/articles?source="+source+"&sortBy=top&apiKey="+API, urlParameters);
            return  se;

        }

        @Override
        protected void onPostExecute(String se) {
            if (se.length() > 10) {
                try {
                    JSONObject jsonResponse = new JSONObject(se);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(srcs, jsonObject.optString(srcs).toString());
                        map.put(heading, jsonObject.optString(heading).toString());
                        map.put(subtitles, jsonObject.optString(subtitles).toString());
                        map.put(url, jsonObject.optString(url).toString());
                        map.put(imgs, jsonObject.optString(imgs).toString());
                        lists.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }


                NewsMagAdapter adapter = new NewsMagAdapter(Magazine.this, lists);
                lw.setAdapter(adapter);

                lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(Magazine.this, DetailMag.class);
                        i.putExtra("url", lists.get(+position).get(url));
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }

        }}
}
