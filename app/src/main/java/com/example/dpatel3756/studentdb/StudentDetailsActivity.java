package com.example.dpatel3756.studentdb;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Dpatel3756 on 6/17/2016.
 */
public class StudentDetailsActivity extends AppCompatActivity {
    private TextView tvStudentDetailId, tvStudentDetailName, tvStudentDetailMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        final DBAdapter db = new DBAdapter(this);

        tvStudentDetailId = (TextView) findViewById(R.id.tvStudentDetailID);
        tvStudentDetailName = (TextView) findViewById(R.id.tvStudentDetailName);
        tvStudentDetailMark = (TextView) findViewById(R.id.tvStudentDetailMark);

        //An intent is an abstract description of an operation to be performed.
        //An Intent provides a facility for performing late runtime binding between the code in different applications.
        //Its most significant use is in the launching of activities, where it can be thought of as the glue between activities.
        //It is basically a passive data structure holding an abstract description of an action to be performed.
        long studentID = getIntent().getLongExtra("StudentID", -1);
        db.open();
        Cursor c = db.getStudent(studentID);
        db.close();

        //the value to be returned if no value of the desired type is stored with the given name.
        tvStudentDetailId.setText(getString(R.string.student_id, String.valueOf(c.getLong(0))));//

        //the value of an item that previously added with putExtra() or null if no String array value was found.
        tvStudentDetailName.setText(getString(R.string.student_name, c.getString(1)));
        tvStudentDetailMark.setText(getString(R.string.student_mark, c.getString(2)));

    }
}
