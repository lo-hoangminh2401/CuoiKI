package com.kimchon.appdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.kimchon.appdictionary.ui.home.HomeFragment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Detail extends AppCompatActivity {
    private WebView mWVWord;
    private WebView mWVContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWVWord=findViewById(R.id.wv_word);
        mWVContent=findViewById(R.id.wv_content);
        try {
            Intent intent=getIntent();
            String word="<p><font color=\"darkviolet\">"+intent.getStringExtra(HomeFragment.Word)+"</font></p>";
            String content=intent.getStringExtra(HomeFragment.Content);
            WebSettings webSettingw=mWVWord.getSettings();
            webSettingw.setTextZoom(135);

            WebSettings webSettingc=mWVContent.getSettings();
            webSettingc.setTextZoom(130);

            mWVWord.loadData(" "+word,"text/html","UTF-8");
            Document document=Jsoup.parse(content);

            for (int i=0;i<document.getElementsByClass("type").size();i++)
            {
                document.getElementsByClass("type").get(i).html("<font color=\"red\">"+document.getElementsByClass("type").get(i).text()+"</font>");
            }

            for (int i=0;i<document.getElementsByClass("title").size();i++){
                document.getElementsByClass("title").get(i).html("<font color=\"green\">"+document.getElementsByClass("title").get(i).text()+"</font>");
            }
            //Log.e("Tag",document.html());
            mWVContent.loadDataWithBaseURL(null," "+document.html(),"text/html","UTF-8",null);
        }catch (Exception e){
            Log.e("Tag",HomeFragment.Word);
            Intent intent=getIntent();
            String word=intent.getStringExtra(HomeFragment.Word);
            mWVWord.loadData(word,"text/html","UTF-8");
        }

    }
}
