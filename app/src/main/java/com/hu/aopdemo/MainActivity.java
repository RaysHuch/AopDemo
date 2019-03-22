package com.hu.aopdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hu.ajruntime.annotation.DebugTrace;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }


    @DebugTrace
    private void initData() {
        Log.d(TAG, "initData: ");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doooo();
            }
        });
    }

    @DebugTrace
    private void doooo() {
        Log.d(TAG, "doooo: ");
    }
}
