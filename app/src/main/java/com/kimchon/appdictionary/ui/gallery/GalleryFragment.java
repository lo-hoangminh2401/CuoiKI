package com.kimchon.appdictionary.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.kimchon.appdictionary.DatabaseAccess;
import com.kimchon.appdictionary.PracticePageAdapter;
import com.kimchon.appdictionary.R;
import com.kimchon.appdictionary.Vocabulary;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private ViewPager mViewPager;
    private ArrayList<Vocabulary> mList;
    private PracticePageAdapter mAdapter;
    private int mCurrent=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mViewPager=root.findViewById(R.id.vp_vocabulary);
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getContext());
        mList=databaseAccess.getVocabulary();
        mAdapter=new PracticePageAdapter(getContext(),mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                mCurrent=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        return root;
    }
}