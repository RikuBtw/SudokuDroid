package com.example.cocotte.sudoku;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectActivity extends AppCompatActivity {

    Bundle extras;
    ListView selectView;
    Activity context;
    InputStream databaseInputStream;
    InputStreamReader inputReader;
    BufferedReader buffReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        extras = getIntent().getExtras();

        context = this;
        final Intent intent = new Intent(context, BoardActivity.class);
        final Bundle objectBundle = new Bundle();

        switch (extras.getString("level")){
            case "1":
                databaseInputStream = getResources().openRawResource(R.raw.level1);
                break;
            case "2":
                databaseInputStream = getResources().openRawResource(R.raw.level2);
                break;
            default:
                return;
        }
        inputReader = new InputStreamReader(databaseInputStream);
        buffReader = new BufferedReader(inputReader);

        final List<SelectModel> listItems = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            String line = "";
            try {
                line = buffReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Random r = new Random();
            int percent = r.nextInt(100 - 0);
            listItems.add(new SelectModel("GRID "+(i+1),"LEVEL"+extras.getString("level"),percent+"%", line));
        }

        selectView = findViewById(R.id.selectList);
        SelectAdapter selectAdapter = new SelectAdapter(listItems, this);
        selectView.setAdapter(selectAdapter);
        selectView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objectBundle.putString("grid", ""+listItems.get(i).getLine());
                intent.putExtras(objectBundle);
                context.startActivity(intent);
            }
        });


    }

}
