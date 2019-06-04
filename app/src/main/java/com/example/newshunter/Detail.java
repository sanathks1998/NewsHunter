package com.example.newshunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Detail extends AppCompatActivity {
    WebView webView;
    ProgressBar pbar;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviews);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        pbar = (ProgressBar) findViewById(R.id.pbar);
        webView = (WebView) findViewById(R.id.webviews);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);
Intent i=new Intent(Detail.this,MainActivity.class);
startActivity(i);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    pbar.setVisibility(View.GONE);
                } else {
                    pbar.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
