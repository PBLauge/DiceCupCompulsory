package com.example.fars_pc.dicecup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fars_pc.dicecup.Model.DCLogic;
import com.example.fars_pc.dicecup.Model.IDCLogic;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.fars_pc.dicecup.R.id.btnRoll;
import static java.lang.System.runFinalizersOnExit;

public class MainActivity extends AppCompatActivity {

    private IDCLogic logic;
    private LinearLayout listHistory;
    private LinearLayout diceView;
    private ArrayList<Integer> valueList;
    private ArrayList<String> sumHistory;

    private int rolls;
    private int sum;
    private final String COUNT = "count";
    private final String VALUES = "values";
    private final String HISTORY = "history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logic = new DCLogic();
        listHistory = findViewById(R.id.listHistory);
        diceView = findViewById(R.id.diceView);
        valueList = new ArrayList<>();
        sumHistory = new ArrayList<>();

        findViewById(btnRoll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRoll();
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickClear();
            }
        });

        rolls = (savedInstanceState != null ? savedInstanceState.getInt(COUNT): 0);

        if(savedInstanceState != null)
        {
            valueList = savedInstanceState.getIntegerArrayList(VALUES);
            sumHistory = savedInstanceState.getStringArrayList(HISTORY);
        }
        else
        {
            valueList.add(logic.selectValueBetween1And6());
            valueList.add(logic.selectValueBetween1And6());
        }
        drawDices();
        drawHistory();
    }

    protected void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);
        state.putInt(COUNT, rolls);
        state.putIntegerArrayList(VALUES, valueList);
        state.putStringArrayList(HISTORY, sumHistory);
    }

    //NumberPicker
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_add) {
            addDice();
            return true;
        }

        if(id == R.id.action_remove) {
            removeDice();
            return true;
        }

        if(id == R.id.action_close) {
            finish();
            return true;
        }
        if(id == R.id.goToHistory) {
            onClickHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // setDice()
    private void addDice() {
        if(valueList.size() >= 6)
        {
            return;
        }
        valueList.add(1);
        drawDices();
    }

    private void removeDice() {
        if (valueList.size() <= 1)
        {
            return;
        }
        valueList.remove(valueList.size() - 1);
        drawDices();
    }

    private void onClickHistory(){
        Intent intent = new Intent();
        intent.setClass(this, HistoryActivity.class);
        intent.putExtra("Sum", this.sumHistory);
        startActivity(intent);
    }

    //Hist
    private void clearHistory() {
        listHistory.removeAllViews();
        sumHistory.clear();
        rolls = 0;
    }

    private void drawDices(){
        diceView.removeAllViews();
        for (int i = 0; i < valueList.size(); i++) {
            ImageView dice = new ImageView(this);
            setDiceImage(valueList.get(i), dice);
            sum = sum + valueList.get(i);
            diceView.addView(dice);
        }
    }

    //Hist
    private void drawHistory(){
        listHistory.removeAllViews();
        for (String h : sumHistory)
        {
            TextView history = new TextView(this);
            history.setText(h);
            listHistory.addView(history);
        }
    }

    private void onClickRoll() {
        rolls++;
        sum = 0;
        if(rolls >= 6)
        {
            clearHistory();
        }
        else {
            for (int i = 0; i < valueList.size(); i++) {
                valueList.remove(i);
                int newValue = logic.selectValueBetween1And6();
                valueList.add(newValue);
            }
            drawDices();
            String newHistory = "Sum: " + sum;
            sumHistory.add(newHistory);
            drawHistory();
        }
    }

    //Hist
    private void onClickClear() {
            clearHistory();
    }

    public void setDiceImage(int value, ImageView v){
        switch (value) {
            case 1: v.setImageResource(R.drawable.d1);
                break;
            case 2: v.setImageResource(R.drawable.d2);
                break;
            case 3: v.setImageResource(R.drawable.d3);
                break;
            case 4: v.setImageResource(R.drawable.d4);
                break;
            case 5: v.setImageResource(R.drawable.d5);
                break;
            case 6: v.setImageResource(R.drawable.d6);
                break;
        }
    }
}
