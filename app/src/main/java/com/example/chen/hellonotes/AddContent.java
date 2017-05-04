package com.example.chen.hellonotes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.Date;

public class AddContent extends AppCompatActivity implements View.OnClickListener{
    private String val;//用来做接收
    private Button msave;
    private Button mdelete;
    private EditText mettext;
    private ImageView mc_img;
    private VideoView mc_video;
    private NotesDB mNotesDB;
    private SQLiteDatabase mSQLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        val = getIntent().getStringExtra("flag");
        msave = (Button) findViewById(R.id.save);
        mdelete = (Button) findViewById(R.id.delete);
        mettext = (EditText) findViewById(R.id.ettext);
        mc_img = (ImageView) findViewById(R.id.c_img);
        mc_video = (VideoView) findViewById(R.id.c_video);
        msave.setOnClickListener(this);
        mdelete.setOnClickListener(this);
        mNotesDB = new NotesDB(this);
        mSQLiteDatabase = mNotesDB.getWritableDatabase();//获取写入输入的权限
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.delete:
                finish();
                break;
        }

    }
    //添加数据的方法
    public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,mettext.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        mSQLiteDatabase.insert(NotesDB.TABLE_NAME,null,cv);
    }
    public String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }
}
