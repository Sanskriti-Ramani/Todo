package com.example.sanskriti.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoOpenHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME="todos_db";
    public static final int VERSION=1;

    private static TodoOpenHelper instance;

    public static TodoOpenHelper getInstance(Context context) {
        if(instance==null){
            instance=new TodoOpenHelper(context.getApplicationContext());
        }
        return instance;
    }


    public TodoOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String expensesSql=" CREATE TABLE "+ Contract.Todo.TABLE_NAME + " ( " +
                Contract.Todo.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.Todo.COLUMN_NAME + " TEXT, " +
                Contract.Todo.COLUMN_DESCRIPTION + " TEXT, " +
                Contract.Todo.COLUMN_DATE + " TEXT, " +
                Contract.Todo.COLUMN_TIME + " TEXT )";

        sqLiteDatabase.execSQL(expensesSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
