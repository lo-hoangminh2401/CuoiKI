package com.kimchon.appdictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }
    public static DatabaseAccess getInstance(Context context){
        if (instance ==null){
            instance =new DatabaseAccess(context);
        }
        return instance;
    }
    public void Open(){
        this.database=openHelper.getWritableDatabase();
    }
    public void Close(){
        if (database!=null) this.database.close();
    }
    public ArrayList<String> getWords(){
        ArrayList<String> list=new ArrayList<>();
        Cursor cursor=database.rawQuery("Select*from anh_viet limit 10000",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<Vocabulary> getVocabulary(){
        ArrayList<Vocabulary> list=new ArrayList<>();
        Cursor cursor=database.rawQuery("Select*from anh_viet limit 10000",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String word = cursor.getString(1);
            String content = cursor.getString(2);
            list.add(new Vocabulary(word,content));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }
    public String getDefinition(String word){
        String defnition="";
        Cursor cursor=database.rawQuery("Select*from anh_viet where word='"+word+"' LIMIT 100",null);
        cursor.moveToFirst();
        defnition=cursor.getString(2);
        cursor.close();
        return defnition;
    }
}