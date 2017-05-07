package com.example.chen.hellonotes;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


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
    private Cursor cursor;

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
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取当前cursor的position
                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this,SelectAct.class);
                //携带数据传入另一个页面
                i.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));//ID为int类型
                i.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));//CONTENT为String类型
                i.putExtra(NotesDB.PATH,cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                i.putExtra(NotesDB.TIME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                startActivity(i);

            }
        });

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
        cursor = dbRead.query(NotesDB.TABLE_NAME,null,null,null,null,null,null);
        mAdapter = new MyAdapter(this,cursor);
        mlv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

    
}
