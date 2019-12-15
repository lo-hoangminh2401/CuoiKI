package com.kimchon.appdictionary.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kimchon.appdictionary.DatabaseAccess;
import com.kimchon.appdictionary.Detail;
import com.kimchon.appdictionary.ItemClickListener;
import com.kimchon.appdictionary.R;
import com.kimchon.appdictionary.Vocabulary;
import com.kimchon.appdictionary.VocabularyAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ItemClickListener{

    ArrayList<Vocabulary> vocabulary;
    VocabularyAdapter adapter;
    DatabaseAccess dbAccess;

    public static final String Word="word";
    public static final String Content="content";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar=root.findViewById(R.id.toolbar);
        final RecyclerView recyclerView=root.findViewById(R.id.rv_listvocabulary);
        dbAccess=DatabaseAccess.getInstance(getContext());
        dbAccess.Open();
        vocabulary=dbAccess.getVocabulary();
        adapter =new VocabularyAdapter(getContext(), vocabulary, this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final AutoCompleteTextView autoCompleteTextView=root.findViewById(R.id.atctv);
        ArrayList<String> listWord=new ArrayList<>();
        listWord=dbAccess.getWords();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_item,listWord);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(arrayAdapter);
        //Toast.makeText(getContext(),""+arrayAdapter.getCount(),Toast.LENGTH_LONG).show();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),Detail.class);
                intent.putExtra(Word,autoCompleteTextView.getText().toString());
                intent.putExtra(Content,dbAccess.getDefinition(autoCompleteTextView.getText().toString()));
                startActivity(intent);
            }
        });
        return root;
    }
    @Override
    public void onClick(int position, boolean isLongClick) {
        Vocabulary vocab=vocabulary.get(position);
        Intent intent=new Intent(getContext(),Detail.class);
        intent.putExtra(Word,vocab.getWord());
        intent.putExtra(Content,vocab.getContent());
        startActivity(intent);
    }
}