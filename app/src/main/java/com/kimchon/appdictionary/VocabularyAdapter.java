package com.kimchon.appdictionary;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kimchon.appdictionary.ui.home.HomeFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabularyViewHolder>{
    private Context mContext;
    private ArrayList<Vocabulary> mList;
    private ItemClickListener itemClickListener;
    public VocabularyAdapter(Context mContext, ArrayList<Vocabulary> mList,ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public VocabularyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview,parent,false);
        return new VocabularyViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabularyViewHolder holder, int position) {
        final Vocabulary vocabulary=mList.get(position);
        holder.mTVWord.setText(" "+vocabulary.getWord());
        Document document= Jsoup.parse(vocabulary.getContent());
        Elements elements=document.select("ul");
        Elements elm=elements.select("li");
        holder.mTVContent.setText(" "+elm.text());
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VocabularyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView mTVWord;
        private TextView mTVContent;
        private ItemClickListener itemClickListener;
        public VocabularyViewHolder(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);
            mTVWord=itemView.findViewById(R.id.tv_word);
            mTVContent=itemView.findViewById(R.id.tv_content);
            this.itemClickListener=itemClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setOnClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(getAdapterPosition(),true);
            return true;
        }
    }
}
