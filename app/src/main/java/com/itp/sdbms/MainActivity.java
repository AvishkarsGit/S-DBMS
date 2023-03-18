package com.itp.sdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_sid,et_sname,et_sdob,et_smarks;
    Button btn_insert,btn_read,btn_update,btn_delete;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      et_sid=  findViewById(R.id.et_stid);
      et_sname= findViewById(R.id.et_stname);
      et_sdob=  findViewById(R.id.et_stdob);
      et_smarks=  findViewById(R.id.et_stmarks);

     btn_insert= findViewById(R.id.btn_insert);
     btn_read= findViewById(R.id.btn_read);
     btn_update= findViewById(R.id.btn_update);
     btn_delete= findViewById(R.id.btn_delete);

      listView=findViewById(R.id.list_view);

      //1. create database
     SQLiteDatabase sqLiteDatabase= openOrCreateDatabase("RIT", Context.MODE_PRIVATE,null);

     sqLiteDatabase.execSQL("create table if not exists students(_id AUTO_INCREMENT,id integer(10),name varchar(20),dob date,marks varchar(20))");

     btn_insert.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
               String sid= et_sid.getText().toString();
               String name=et_sname.getText().toString();
               String dob=et_sdob.getText().toString();
               String marks=et_smarks.getText().toString();
               if(sid.length()>0 && name.length()>0 && dob.length()>0 && marks.length()>0)
               {
                   ContentValues contentValues=new ContentValues();
                   contentValues.put("id",sid);
                   contentValues.put("name",name);
                   contentValues.put("dob",dob);
                   contentValues.put("marks",marks);

                   Long status=sqLiteDatabase.insert("students",null,contentValues);
                   if(status<0)
                   {
                       Toast.makeText(MainActivity.this, "failed to insert", Toast.LENGTH_SHORT).show();
                   }else
                   {
                       Toast.makeText(MainActivity.this, "data inserted successfully!", Toast.LENGTH_SHORT).show();
                   }
               }
                else
               {
                   Toast.makeText(MainActivity.this, "enter value in all fields", Toast.LENGTH_SHORT).show();
               }



         }
     });

     btn_read.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
           Cursor cursor= sqLiteDatabase.query("students",null,null,null,null,null,null);

           String[] from=new String[]{"id","name","dob","marks"};
           int[] to=new int[]{R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4};

           SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter(MainActivity.this,R.layout.my_view,cursor,from,to);
           listView.setAdapter(simpleCursorAdapter);
         }
     });

     btn_update.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String sid= et_sid.getText().toString();
             String name=et_sname.getText().toString();
             String dob=et_sdob.getText().toString();
             String marks=et_smarks.getText().toString();

             ContentValues contentValues=new ContentValues();
             contentValues.put("id",sid);
             contentValues.put("name",name);
             contentValues.put("dob",dob);
             contentValues.put("marks",marks);
           int update_count=  sqLiteDatabase.update("students",contentValues,"id=?",new String[]{et_sid.getText().toString()});
           if(update_count>0)
           {
               Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
           }else
           {
               Toast.makeText(MainActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
           }
         }
     });

     btn_delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             int delete_count=sqLiteDatabase.delete("students","id=?",new String[]{et_sid.getText().toString()});

             if(delete_count>0)
             {
                 Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
             }else
             {
                 Toast.makeText(MainActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
             }
         }
     });

    }
}