package com.appstone.assignment1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StudentDetailAdapter.StudentClickListener {
    private DBhelper dBhelper;
    private RecyclerView mRcStudentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcStudentList = findViewById(R.id.rc_student_data);
        mRcStudentList.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        dBhelper = new DBhelper(MainActivity.this);

        loadDataToDatabase();
    }

    private void loadDataToDatabase() {
        ArrayList<StudentDetail> details = dBhelper.getDataFromDatabase(dBhelper.getReadableDatabase());
        StudentDetailAdapter adapter = new StudentDetailAdapter(MainActivity.this, details);
        adapter.setListener(this);
        mRcStudentList.setAdapter(adapter);
    }

    public void onSubmitClicked(View view) {
        Intent data = new Intent(MainActivity.this, ViewActivity.class);
        startActivityForResult(data, 1000);
    }

    @Override
    public void onUpdateClicked(StudentDetail studentDetail) {
        Intent updateIntent = new Intent(MainActivity.this, ViewActivity.class);
        updateIntent.putExtra("student", studentDetail);
        updateIntent.putExtra("is_update", true);
        startActivityForResult(updateIntent, 1000);
    }

    @Override
    public void onDeleteClicked(StudentDetail studentDetail) {
        dBhelper.deleteDatafromDatabase(dBhelper.getWritableDatabase(), studentDetail);
        loadDataToDatabase();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                loadDataToDatabase();
            }
        }
    }
}