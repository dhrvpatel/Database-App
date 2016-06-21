package com.example.dpatel3756.studentdb;

/**
 * Created by Dpatel3756 on 6/17/2016.
 */
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddOrUpdateStudentActivity extends AppCompatActivity {

    //variable listing
    Button bOK,bCancel;
    Student student;
    int position;
    EditText sName,sMark;
    CoordinatorLayout cl;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_update_student);
        final DBAdapter db = new DBAdapter(this); //creating object of DBAdapter class

        position = getIntent().getIntExtra("Position", -1); //Retrieve extended data from the intent.

        ////Look for a child view with the given id. If this view has the given id, return this view.
        cl = (CoordinatorLayout) findViewById(R.id.cdlayout);

        sName = (EditText) findViewById(R.id.sName);
        sMark = (EditText) findViewById(R.id.sMark);

        bOK = (Button) findViewById(R.id.bOk);
        bCancel = (Button) findViewById(R.id.bCancel);

        if(position != -1) {
            getSupportActionBar().setTitle("Edit Entry");
            student = new Student();
            db.open();
            c = db.getStudent(MainActivity.getListView().get(position).getId());
            sName.setText(c.getString(1));
            sMark.setText(c.getString(2));
            db.close();
        }
        else {
            getSupportActionBar().setTitle("Add Entry");
            student = null;
        }

        //Interface definition for a callback to be invoked when a view is clicked.
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sName.getText().toString().trim().equals("") || sMark.getText().toString().trim().equals("") ) {
                    final Snackbar snackBar = Snackbar.make(cl, "Please enter all the fields.", Snackbar.LENGTH_LONG);
                    snackBar.setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                }
                else {
                    Student sNew = new Student();
                    sNew.setName(sName.getText().toString());
                    sNew.setMark(sMark.getText().toString());

                    if (student == null) {
                        db.open();
                        long id =db.insertStudent(sNew);
                        sNew.setId(id);
                        db.close();
                        MainActivity.getListView().add(sNew);
                        MainActivity.getListViewAdapter().notifyDataSetChanged();
                    }
                    else {
                        db.open();
                        db.updateStudent(c.getLong(0), sNew);
                        db.close();
                        sNew.setId(c.getLong(0));
                        MainActivity.getListView().set(position, sNew);
                        MainActivity.getListViewAdapter().notifyDataSetChanged();
                    }
                    finish();
                }
            }
        });

        //Interface definition for a callback to be invoked when a view is clicked.
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

