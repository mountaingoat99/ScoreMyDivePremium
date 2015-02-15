package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.info.sqlite.helper.DiveListDatabase;
import com.info.sqlite.helper.DiveNumberDatabase;
import com.info.sqlite.helper.DiveTotalDatabase;
import com.info.sqlite.helper.JudgeScoreDatabase;
import com.info.sqlite.helper.ResultDatabase;



public class EnterFinalDiveScore extends ActionBarActivity {
    private EditText score1;
    private TextView diveNameView, diveDDView;
    private int diverId, meetId, diveType, diveNumber, diveTotal, divePosition, diveId;
    private double boardType = 0.0;
    private double  sc1 = 0.0, multiplier = 0.0, total = 0.0;
    private String failedDive = "P", className = "nonList", diveNameString = "",
            diveTypeName, divePosString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_final_dive_score);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_button);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setUpView();

        Bundle b = getIntent().getExtras();
        if(b != null) {
            diverId = b.getInt("keyDiver");
            meetId = b.getInt("keyMeet");
            diveNumber = b.getInt("diveNumber");
            boardType = b.getDouble("boardType");
            multiplier = b.getDouble("multiplier");
            diveType = b.getInt("diveType");
            diveTypeName = b.getString("diveTypeString");
            diveId = b.getInt("diveID");
            diveNameString = b.getString("diveName");
            divePosition = b.getInt("positionNum");
            divePosString = b.getString("postionString");
        }

        setTitle();
        getDiveTotal();
        addListenerOnButton();
    }

    @Override
    public void onBackPressed(){
        final Context context = this;
        Intent intent = new Intent(context, ChooseSummary.class);
        Bundle b = new Bundle();
        b.putInt("keyDiver", diverId);
        b.putInt("keyMeet", meetId);
        intent.putExtras(b);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void getDiveTotal(){
        SearchDiveTotals search = new SearchDiveTotals();
        diveTotal = search.doInBackground();
    }

    private void setTitle(){
        diveNameView = (TextView)findViewById(R.id.diveNameString);
        diveDDView = (TextView)findViewById(R.id.diveDDString);
        String fullName = Integer.toString(diveId) + divePosString + " - "
                + diveNameString;
        diveDDView.setText("Dive DD: " + Double.toString(multiplier));
        diveNameView.setText(fullName);

    }

    public void addListenerOnButton()
    {
        final Context context = this;
        Button btnTotal = (Button) findViewById(R.id.buttonScore);

        btnTotal.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if(multiplier != 0.0) {
                    getScoreText();
                    if(sc1 != 0.0) {
                        incrementDiveNumber();
                        calcScores();
                        updateJudgeScores();
                        Bundle b = new Bundle();
                        b.putInt("keyDiver", diverId);
                        b.putInt("keyMeet", meetId);
                        Intent intent = new Intent(context, ChooseSummary.class);
                        intent.putExtras(b);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Please enter a score or use the menu to fail the dive",
                                Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void incrementDiveNumber(){
        IncrementDiveNumber divenumber = new IncrementDiveNumber();
        if (diveNumber != diveTotal) {
            diveNumber++;
            divenumber.doInBackground();
        }
    }

    private void calcScores() {
        GetTotalScore tScore = new GetTotalScore();
        total = tScore.doInBackground();
        total = total + sc1;

        ResultDatabase db = new ResultDatabase(getApplicationContext());
        int resultIndex;
        if(diveNumber == 1){
            resultIndex = 3;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            updateDiveListToYes();
            return;
        }
        if(diveNumber == 2){
            resultIndex = 4;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 3){
            resultIndex = 5;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 4){
            resultIndex = 6;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 5){
            resultIndex = 7;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 6){
            resultIndex = 8;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 7){
            resultIndex = 9;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 8){
            resultIndex = 10;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 9){
            resultIndex = 11;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 10){
            resultIndex = 12;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 11){
            resultIndex = 13;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
        }
    }

    private void updateJudgeScores(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        String diveCategory = null;
        switch (diveType){
            case 1:
                diveCategory = "Forward Dive";
                break;
            case 2:
                diveCategory = "Back Dive";
                break;
            case 3:
                diveCategory = "Reverse Dive";
                break;
            case 4:
                diveCategory = "Inward Dive";
                break;
            case 5:
                diveCategory = "Twist Dive";
                break;
            case 6:
                diveCategory = "Front Platform Dives";
                break;
            case 7:
                diveCategory = "Back Platform Dives";
                break;
            case 8:
                diveCategory = "Reverse Platform Dives";
                break;
            case 9:
                diveCategory = "Inward Platform Dives";
                break;
            case 10:
                diveCategory = "Twist Platform Dives";
                break;
            case 11:
                diveCategory = "Armstand Platform Dives";
                break;
        }

        String DivePosition = null;
        switch (divePosition){
            case 1:
                DivePosition = "A - Straight";
                break;
            case 2:
                DivePosition = "B - Pike";
                break;
            case 3:
                DivePosition = "C - Tuck";
                break;
            case 4:
                DivePosition = "D - Free";
                break;
        }

        String diveNameForDB = Integer.toString(diveId) + " - " + diveNameString;

        // we are setting the judge scores values to zero since we are just adding in the total
        // we still want to keep track of the other stats
        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveNameForDB, DivePosition,
                failedDive, total, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, multiplier);
    }

    private void getScoreText(){
        String testNumber = score1.getText().toString();
        if(testNumber.equals("")){
            testNumber = "0.0";
        }
        sc1 = Double.parseDouble(testNumber);
    }

    private void updateDiveListToYes(){
        UpdateDiveListToYes yes = new UpdateDiveListToYes();
        yes.doInBackground();
    }

    private void setUpView(){

        score1 = (EditText)findViewById(R.id.editScore1);
        diveNameView = (TextView)findViewById(R.id.diveNameString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_enter_final_dive_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_failed_dive:
                if (multiplier != 0.0) {
                    Intent intent = new Intent(context, FailedDive.class);
                    Bundle b = new Bundle();
                    b.putString("className", className);
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveId", diveId);
                    b.putInt("keyDiveType", diveType);
                    b.putString("keyDiveTypeName", diveNameString);
                    b.putInt("keyDivePosition", divePosition);
                    b.putDouble("boardType", boardType);
                    b.putDouble("multiplier", multiplier);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_LONG).show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private class UpdateDiveListToYes extends AsyncTask<Object, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params){
            db.setNoList(meetId, diverId);
            return null;
        }
    }

    private class GetTotalScore extends AsyncTask<Double, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double total;

        @Override
        protected Double doInBackground(Double... params) {
            return total = db.getTotalScore(meetId, diverId);
        }
    }

    private class IncrementDiveNumber extends AsyncTask<Object, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateDiveNumber(meetId, diverId, diveNumber);
            return null;
        }
    }

    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object>{
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }
}
