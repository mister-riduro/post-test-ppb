package com.example.todolistapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolistapp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TITLEDOES = "titledoes";
    private static final String DATEDOES = "datedoes";
    private static final String DESCDOES = "descdoes";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLEDOES + " TEXT, " + DATEDOES + " TEXT, " + DESCDOES + " TEXT)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(Task task){
        ContentValues cv = new ContentValues();
        cv.put(TITLEDOES, task.getTitledoes());
        cv.put(DATEDOES, task.getDatedoes());
        cv.put(DESCDOES, task.getDescdoes());
        db.insert(TODO_TABLE, null, cv);
    }

    public List<Task> getAllTasks(){
        List<Task> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        Task task = new Task();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTitledoes(cur.getString(cur.getColumnIndex(TITLEDOES)));
                        task.setDatedoes(cur.getString(cur.getColumnIndex(DATEDOES)));
                        task.setDescdoes(cur.getString(cur.getColumnIndex(DESCDOES)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateTask(int id, String task, String date, String hours) {
        ContentValues cv = new ContentValues();
        cv.put(TITLEDOES, task);
        cv.put(DATEDOES, date);
        cv.put(DESCDOES, hours);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}