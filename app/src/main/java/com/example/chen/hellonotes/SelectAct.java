package com.example.chen.hellonotes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectAct extends AppCompatActivity implements View.OnClickListener {
    private Button ms_delete;
    private Button ms_back;
    private EditText ms_text;
    private VideoView ms_video;
    private ImageView ms_img;
    private NotesDB mNotesDB;
    private SQLiteDatabase dbWriter;
    private Button ms_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        //System.out.println(getIntent().getIntExtra(NotesDB.ID,0));
        ms_delete = (Button) findViewById(R.id.s_delect);
        ms_back = (Button) findViewById(R.id.s_back);
        ms_save = (Button) findViewById(R.id.s_save);
        ms_text = (EditText) findViewById(R.id.s_text);
        ms_video = (VideoView) findViewById(R.id.s_video);
        ms_img = (ImageView) findViewById(R.id.s_img);
        mNotesDB = new NotesDB(this);
        dbWriter = mNotesDB.getWritableDatabase();
        ms_delete.setOnClickListener(this);
        ms_back.setOnClickListener(this);
        ms_save.setOnClickListener(this);
        if(getIntent().getStringExtra(NotesDB.PATH).equals("null")){
            ms_img.setVisibility(View.GONE);
        }else{
            ms_img.setVisibility(View.VISIBLE);
        }if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")){
            ms_video.setVisibility(View.GONE);
        }else{
            ms_video.setVisibility(View.VISIBLE);
        }
        ms_text.setText(getIntent().getStringExtra(NotesDB.CONTENT));
        ms_text.setSelection(ms_text.getText().length());
        Bitmap  bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(NotesDB.PATH));
        ms_img.setImageBitmap(bitmap);
        ms_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NotesDB.VIDEO)));
        ms_video.start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.s_delect:
                delete();
                finish();
                break;
            case R.id.s_back:
                finish();
                break;
            case R.id.s_save:
                update();
                finish();
                break;
        }
    }
    public void delete(){
        dbWriter.delete(NotesDB.TABLE_NAME,"_id="+getIntent().getIntExtra(NotesDB.ID,0),null);
    }
    public void update(){
        String content = ms_text.getText().toString();
        dbWriter.execSQL("update "+NotesDB.TABLE_NAME+" set "+
        NotesDB.CONTENT+"=? where "+NotesDB.ID+"=?",new Object[]{content,getIntent().getIntExtra(NotesDB.ID,0)});
    }
}
