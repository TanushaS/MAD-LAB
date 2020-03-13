package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    public DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        final DatabaseHelper  myDbHelper = new DatabaseHelper(MainActivity.this);
        final EditText name_txt=(EditText)findViewById(R.id.editText);
        final EditText marks_txt=(EditText)findViewById(R.id.editText2);
        Button insert_btn = (Button) findViewById(R.id.button);
        Button display_btn = (Button) findViewById(R.id.button2);
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button) {
                    myDbHelper.insert_record(name_txt.getText().toString(),
                            Integer.parseInt(marks_txt.getText().toString()));
                }
            }

            });
        display_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button2) {
                    StringBuffer record_details = (StringBuffer) myDbHelper.display_all_records();
                    //showMessage("Display Status", record_details.toString());
                }
            }
        });
       Button update_btn = (Button) findViewById(R.id.button3);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button3) {
                    myDbHelper.update_record(name_txt.getText().toString(),Integer.parseInt(marks_txt.getText().toString()));
                   // showMessage("Success","Record Updated");
            }
        }});
       Button delete_btn = (Button) findViewById(R.id.button4);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button4) {
                    myDbHelper.delete_record(name_txt.getText().toString());
                    // showMessage("Success","Record Deleted");
                }
            }
        });


    }
        }
        
 ***************************************************************************************
 
 import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "department.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID"; public static final String COL_2 = "NAME";
    public static final String COL_3 = "MARKS";
    private SQLiteDatabase db;

    public DatabaseHelper(Context context)
    { super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT,MARKS INTEGER)"); }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db); }
    public void insert_record(String name, int marks){
//String query="INSERT INTO student_table (Name, Marks)
// VALUES ('"+name+"',"+marks+");";

        String query="INSERT INTO student_table VALUES (null, '"+name+"',"+marks+");";
        db.execSQL(query); }

    public StringBuffer display_all_records(){
        StringBuffer buffer=new StringBuffer();
        Cursor c=db.rawQuery("SELECT * FROM student_table", null);
        if(c.getCount()==0) {
            buffer.append("Error"+ "No records found");
            return(buffer); }
        while(c.moveToNext()) {
            buffer.append("ID: "+c.getString(0)+"\n");
            buffer.append("Name: "+c.getString(1)+"\n");
            buffer.append("Marks: "+c.getString(2)+"\n\n");
        }return(buffer); }

    public void update_record(String name, int marks) {
        String query = "UPDATE student_table SET Marks=" + marks +
                " WHERE Name='" + name + "';";
        db.execSQL(query);
    }
    public void delete_record(String name){
        String query="DELETE FROM student_table WHERE Name='"+name+"';";
        db.execSQL(query);}

}

