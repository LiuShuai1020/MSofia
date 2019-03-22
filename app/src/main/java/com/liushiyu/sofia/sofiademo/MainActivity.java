package com.liushiyu.sofia.sofiademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.liushiyu.sofia.msofia.Sofia;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Sofia.with(this).statusBarBackground(getResources().getColor(R.color.colorPrimaryDark)).navigationBarBackground(getResources().getColor(R.color.colorAccent));
    }
}