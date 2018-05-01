package com.example.cocotte.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends AppCompatActivity {

    private Button level1;
    private Button level2;
    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        context = this;
        level1 = findViewById(R.id.level1);
        level1.setLetterSpacing(0.1f);
        level2 = findViewById(R.id.level2);
        level2.setLetterSpacing(0.1f);
        final Intent intent = new Intent(context, SelectActivity.class);
        final Bundle objectBundle = new Bundle();

        level1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectBundle.putString("level", "1");
                intent.putExtras(objectBundle);
                context.startActivity(intent);
            }
        });
        level2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectBundle.putString("level", "2");
                intent.putExtras(objectBundle);
                context.startActivity(intent);
            }
        });
    }

}
