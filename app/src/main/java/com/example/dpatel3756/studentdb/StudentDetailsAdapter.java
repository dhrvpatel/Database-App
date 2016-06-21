package com.example.dpatel3756.studentdb;

/**
 * Created by Dpatel3756 on 6/17/2016.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentDetailsAdapter extends BaseAdapter {
    private ArrayList<Student> arrayListStudent;
    private Context context;
    private LayoutInflater inflater;
    DBAdapter db;

    public StudentDetailsAdapter(Context context, ArrayList<Student> arrayListStudent) {
        this.context = context;
        this.arrayListStudent = arrayListStudent;
        db = new DBAdapter(this.context);

        //Instantiates a layout XML file into its corresponding View objects. It is never used directly.
        /*Instead, use getLayoutInflater() or getSystemService(Class) to retrieve a standard LayoutInflater instance
        that is already hooked up to the current context and correctly configured for the device you are running on.*/
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayListStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListStudent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // Holder (arbitrary) object holds child widgets of each row and when row is out of View then findViewById() won't be called
        // but View will be recycled and widgets will be obtained from Holder.
        // keep the application’s main thread (the UI thread) free from heavy processing.
        // Ensure you do any disk access or SQL access in a separate thread.
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.list_student, null);
            holder = new Holder();
            holder.StudentName = (TextView) v.findViewById(R.id.StudentName);
            holder.StudentMark = (TextView) v.findViewById(R.id.StudentMark);
            holder.EditStudent = (ImageView) v.findViewById(R.id.EditStudent);
            holder.DeleteStudent = (ImageView) v.findViewById(R.id.DeleteStudent);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.StudentName.setText(arrayListStudent.get(position).getName());
        holder.StudentMark.setText(String.valueOf(arrayListStudent.get(position).getMark()));
        holder.EditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddOrUpdateStudentActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Position", position); //Add extended data to the intent.
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.DeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowConfirmDialog(context, arrayListStudent.get(position).getId(), position);
            }
        });
        return v;
    }

    class Holder {
        TextView StudentName,StudentMark;
        ImageView DeleteStudent, EditStudent;
    }



    public void ShowConfirmDialog(final Context context, final long studentId, final int position) {

        //Interface used to allow the creator of a dialog to run some code when an item on the dialog is clicked
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this ?")
                .setCancelable(true)

                //This method will be invoked when a yes button in the dialog is clicked.
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.open();
                        boolean result = db.deleteStudent(studentId);
                        db.close();
                        MainActivity.getListView().remove(position);
                        MainActivity.getListViewAdapter().notifyDataSetChanged();
                    }
                })

                //This method will be invoked when a no button in the dialog is clicked.
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}