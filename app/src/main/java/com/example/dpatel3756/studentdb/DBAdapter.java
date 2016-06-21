package com.example.dpatel3756.studentdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dpatel3756 on 6/17/2016.
 */
public class DBAdapter {

    //variable listing
    static final String KEY_ID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_MARK = "mark";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "student";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "CREATE TABLE " +  DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT NOT NULL,"
                    + KEY_MARK + " TEXT NOT NULL" + ")";


    final Context context;
    DatabaseHelper DBHelper; //A helper class to manage database creation and version management.
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //this class takes care of opening the database if it exists,creating it if it does not, and upgrading it as necessary.
        //Transactions are used to make sure the database is always in a sensible state.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS student");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        //Once opened successfully, the database is cached, so you can call this method every time you need to write to the database.
        db = DBHelper.getWritableDatabase();//
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a Student into the database---
    public long insertStudent(Student s)
    {
        ContentValues initialValues = new ContentValues(); //Creates an empty set of values using the default initial size
        initialValues.put(KEY_NAME, s.getName()); //Adds a value to the set
        initialValues.put(KEY_MARK, s.getMark()); //Adds a value to the set

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular Student---
    public boolean deleteStudent(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    //---retrieves all the Student---
    public Cursor getAllStudent()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_MARK},null, null, null,null,null);
    }

    //---retrieves a particular Student---
    public Cursor getStudent(long rowId) throws SQLException
    {
        //This interface provides random read-write access to the result set returned by a database query.
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_MARK}, KEY_ID + "=" + rowId, null, null, null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a Student---
    public void updateStudent(long rowId, Student s)
    {
        ContentValues args = new ContentValues();//Creates an empty set of values using the default initial size
        args.put(KEY_NAME, s.getName());//Adds a value to the set.
        args.put(KEY_MARK, s.getMark());//Adds a value to the set.

        db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null);
    }

}
