package com.example.chen.hellonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
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
    private File phoneFile,videoFile;

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
        initView();
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
    public void initView(){
        //判断是文字 图片 还是视频
        if (val.equals("1")){
            mc_img.setVisibility(View.GONE);
            mc_video.setVisibility(View.GONE);
        }
        if (val.equals("2")){
            mc_img.setVisibility(View.VISIBLE);
            mc_video.setVisibility(View.GONE);
            //调取相机
            Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            phoneFile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() +"/"+getTime()+".jpg");
            iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
            startActivityForResult(iimg,1);
        }if (val.equals("3")){
            mc_img.setVisibility(View.GONE);
            mc_video.setVisibility(View.VISIBLE);
            Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoFile = new File(Environment.getExternalStorageDirectory()
                    .getAbsoluteFile() +"/"+getTime()+".mp4");
            video.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
            startActivityForResult(video,2);
        }
    }
    //添加数据的方法
    public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,mettext.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        cv.put(NotesDB.PATH,phoneFile+"");
        cv.put(NotesDB.VIDEO,videoFile+"");
        mSQLiteDatabase.insert(NotesDB.TABLE_NAME,null,cv);
    }
    public String getTime(){
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = dateFormat.format(date);*/
        Time t = new Time();
        t.setToNow();
        int year = t.year;
        int month = t.month;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        String str = year+"年"+month+"月"+date+"日"+hour+"时"+minute+"分"+second+"秒";
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            Bitmap bitmap = BitmapFactory.decodeFile(phoneFile.getAbsolutePath());
            mc_img.setImageBitmap(bitmap);
        }
        if (requestCode == 2){
            mc_video.setVideoURI(Uri.fromFile(videoFile));
            mc_video.start();
        }
    }
}
