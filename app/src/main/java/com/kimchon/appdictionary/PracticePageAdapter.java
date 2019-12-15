package com.kimchon.appdictionary;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class PracticePageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Vocabulary> mList;
    public PracticePageAdapter(Context context, ArrayList<Vocabulary> list){
        this.mContext=context;
        this.mList=list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=LayoutInflater.from(mContext);

        Vocabulary vocabulary = mList.get(position);

        View layout=inflater.inflate(R.layout.pageviewpager,container,false);

        WebView mWVWord=layout.findViewById(R.id.wv_word);
        WebView mWVContent=layout.findViewById(R.id.wv_content);

        WebSettings webSettingw=mWVWord.getSettings();
        webSettingw.setTextZoom(135);

        WebSettings webSettingc=mWVContent.getSettings();
        webSettingc.setTextZoom(130);

        String word="<p><font color=\"darkviolet\">"+vocabulary.getWord()+"</font></p>";
        mWVWord.loadData(word,"text/html","UTF-8");

        Document document= Jsoup.parse(vocabulary.getContent());
        for (int i=0;i<document.getElementsByClass("type").size();i++)
        {
            document.getElementsByClass("type").get(i).html("<font color=\"red\">"+document.getElementsByClass("type").get(i).text()+"</font>");
        }

        for (int i=0;i<document.getElementsByClass("title").size();i++){
            document.getElementsByClass("title").get(i).html("<font color=\"green\">"+document.getElementsByClass("title").get(i).text()+"</font>");
        }
        mWVContent.loadDataWithBaseURL(null,document.html(),"text/html","UTF-8",null);
        container.addView(layout);
        return layout;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}