package com.example.simpleDbFrontend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {
    public DBAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE surveyDB " + "(rowid INTEGER PRIMARY KEY AUTOINCREMENT, species TEXT, " + "area TEXT, sampler TEXT)");
    }
    /**************************************************************************
     *    Below references to the second table need to go away, or be converted
     *    to tracklogDB however some db references appear to be required. Check and
     *    see if there are other methods that are normally imported DBAdapter files.
     **************************************************************************/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==1){
            db.execSQL("create table tempDB( " +  "sampler text, tempName text, loc text)");
        }
        db.execSQL("insert into tempDB values(10, 'dept10', 'hyd')");
    }
}
