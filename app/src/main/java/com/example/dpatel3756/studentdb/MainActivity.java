package com.example.dpatel3756.studentdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //variable listing
    FloatingActionButton fab;
    private ListView lvStudent;
    private static ArrayList<Student> arrayListStudent = new ArrayList<>(); //Creating Array collection of student
    private static StudentDetailsAdapter studentDetailsAdapter; //Initializing details adapter class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Setting layout to main activity class
        final DBAdapter db = new DBAdapter(this); //Using DBAdapter class for CRUD functionality

        //Setting toolbar to Action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Look for a child view with the given id. If this view has the given id, return this view.
        fab = (FloatingActionButton) findViewById(R.id.fab);
        lvStudent = (ListView) findViewById(R.id.StudentList);

        //Look for a child view with the given id. If this view has the given id, return this view.
        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,StudentDetailsActivity.class);
                intent.putExtra("StudentID", arrayListStudent.get(position).getId());
                startActivity(intent);
            }
        });


        //Interface definition for a callback to be invoked when a view is clicked.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddOrUpdateStudentActivity.class);
                intent.putExtra("Position", -1);
                startActivity(intent);
            }
        });

        studentDetailsAdapter = new StudentDetailsAdapter(MainActivity.this, arrayListStudent);
        //Sets the adapter that provides the data and the views to represent the data in this widget.
        lvStudent.setAdapter(studentDetailsAdapter);

        db.open();
        Cursor c = db.getAllStudent();
        if (c.moveToFirst())//A Cursor object, which is positioned before the first entry.
        {
            do {
                Student s = new Student();
                s.setId(c.getLong(0));
                s.setName(c.getString(1));
                s.setMark(c.getString(2));
                arrayListStudent.add(s);
            } while (c.moveToNext());
        }
        db.close();
        studentDetailsAdapter.notifyDataSetChanged();

    }

    public static ArrayList<Student> getListView() {
        return arrayListStudent;
    }

    public static StudentDetailsAdapter getListViewAdapter() {
        return studentDetailsAdapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}