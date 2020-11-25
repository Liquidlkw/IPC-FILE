package com.example.ipc_file;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recoverFromFile();
    }

    private void recoverFromFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
              User user = null;
                File saveFile = new File( Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"cachedFiled"+File.separator+"user.txt");
              if (saveFile.exists()){
                  ObjectInputStream input = null;
                  try {
                      input = new ObjectInputStream(new FileInputStream(saveFile));
                      user = (User) input.readObject();
//                      Toast.makeText(MainActivity2.this, user.userName, Toast.LENGTH_SHORT).show();
                      Log.d(TAG, "c: " +user+user.userName);
                  } catch (IOException | ClassNotFoundException e) {
                      e.printStackTrace();
                  }finally {
                      if (input != null) {
                          try {
                              input.close();
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                      }
                  }
              }
            }
        }).start();
    }



}