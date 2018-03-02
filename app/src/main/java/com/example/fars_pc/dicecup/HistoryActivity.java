package com.example.fars_pc.dicecup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class HistoryActivity extends AppCompatActivity {

    private LinearLayout listHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listHistory = findViewById(R.id.listHistory);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        String[] fromMain = b.getStringArrayList("list").toArray(new String[0]);
        listHistory.removeAllViews();
        for (String h : fromMain)
        {
            TextView history = new TextView(this);
            history.setText(h);
            listHistory.addView(history);
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
        listHistory.removeAllViews();
    }

    private void back() {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
    }
}
