package com.example.fars_pc.dicecup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RelativeLayout listHistory2;
    private ArrayList<String> sumHistory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listHistory2 = findViewById(R.id.listHistory2);

        Bundle extras = getIntent().getExtras();
        sumHistory2 = new ArrayList<>();
        String s = extras.getSerializable("Sum").toString();
        sumHistory2.add(s);
        for (String h : sumHistory2) {
            TextView history = new TextView(this);
            history.setText(h);
            listHistory2.addView(history);
        }

        findViewById(R.id.btnClear2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearHistory();
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    private void clearHistory() {
        listHistory2.removeAllViews();
    }

    private void back() {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
    }
}
