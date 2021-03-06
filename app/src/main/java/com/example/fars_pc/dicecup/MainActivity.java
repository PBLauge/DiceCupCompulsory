package com.example.fars_pc.dicecup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fars_pc.dicecup.Model.DCLogic;
import com.example.fars_pc.dicecup.Model.IDCLogic;

import java.util.ArrayList;

import static com.example.fars_pc.dicecup.R.id.btnRoll;

public class MainActivity extends AppCompatActivity {

    private IDCLogic logic;
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
        diceView = findViewById(R.id.diceView);
        valueList = new ArrayList<>();
        sumHistory = new ArrayList<>();

        findViewById(btnRoll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRoll();
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
        intent.setClass(MainActivity.this, HistoryActivity.class);
        Bundle b = new Bundle();
        b.putStringArrayList("list", sumHistory);
        intent.putExtras(b);
        startActivity(intent);

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

    private void onClickRoll() {
        rolls++;
        sum = 0;
            for (int i = 0; i < valueList.size(); i++) {
                valueList.remove(i);
                int newValue = logic.selectValueBetween1And6();
                valueList.add(newValue);
            }
            drawDices();
            String newHistory = "Sum: " + sum;
            sumHistory.add(newHistory);
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
