package com.example.chen.hellonotes;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mtext;
    private Button mvideo;
    private Button mimg;
    private ListView mlv;
    private Intent i;
    //创建一个数据库对象
    private NotesDB mNotesDB;
    //创建一个可添加权限
    private SQLiteDatabase dbRead;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //addDB();
    }
    public void initView(){
        mimg = (Button) findViewById(R.id.img);
        mtext = (Button) findViewById(R.id.text);
        mvideo = (Button) findViewById(R.id.video);
        mlv = (ListView) findViewById(R.id.list);
        mimg.setOnClickListener(this);
        mtext.setOnClickListener(this);
        mtext.setOnClickListener(this);
        mNotesDB = new NotesDB(this);
        //添加读取权限
        dbRead = mNotesDB.getReadableDatabase();

    }

    @Override
    public void onClick(View v) {
        i = new Intent(this,AddContent.class);
        switch (v.getId()){
            case R.id.text:
                i.putExtra("flag","1");
                startActivity(i);
                break;
            case R.id.img:
                i.putExtra("flag","2");
                startActivity(i);
                break;
            case R.id.video:
                i.putExtra("flag","3");
                startActivity(i);
                break;

        }

    }
   /* //添加数据
    public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,"Hello");
        cv.put(NotesDB.TIME,getTime());
        dbWriter.insert(NotesDB.TABLE_NAME,null,cv);
    }
    public String getTime(){
        //设置时间的格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = dateFormat.format(date);
        return str;
    }*/
    public void selectDB(){
       Cursor cursor = dbRead.query(NotesDB.TABLE_NAME,null,null,null,null,null,null,null);
        mAdapter = new MyAdapter(this,cursor);
        mlv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }
}
