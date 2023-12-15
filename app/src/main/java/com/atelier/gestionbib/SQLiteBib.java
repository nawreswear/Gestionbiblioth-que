package com.atelier.gestionbib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBib extends SQLiteOpenHelper {
    public SQLiteBib(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Sql="create table livre (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titre text NOT NULL,nbpage INTEGER );";
        db.execSQL(Sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}