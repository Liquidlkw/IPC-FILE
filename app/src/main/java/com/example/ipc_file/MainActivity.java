package com.example.ipc_file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.mtp.MtpConstants;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistToFile();
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });
    }

    private void persistToFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(1,"Hello World",false);
                //sd卡文件缓存目录
                File cachedDir = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"cachedFiled");
                Log.i("main","缓存目录:"+String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"cachedFiled"));
                if (!cachedDir.exists()){
                    cachedDir.mkdirs();
                }
                //新建文件
                File saveFile = new File(cachedDir, "user.txt");
                if (!saveFile.exists()) {
                    try {
                        saveFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(new FileOutputStream(saveFile));
                    out.writeObject(user);
                    Log.d(TAG, "persist user: "+user);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }

}