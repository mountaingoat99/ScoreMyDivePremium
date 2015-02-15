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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.info.sqlite.helper.DiveListDatabase;
import com.info.sqlite.helper.DiveNumberDatabase;
import com.info.sqlite.helper.JudgeScoreDatabase;
import com.info.sqlite.helper.MeetDatabase;
import com.info.sqlite.helper.ResultDatabase;
import com.info.sqlite.helper.ScoresDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dives extends ActionBarActivity {

    private TextView view3, view4, view5, view6, view7, diveNameView, diveDDView;
    private Spinner score1, score2, score3, score4, score5, score6, score7;
    private int judges, diverId, meetId, diveType, diveNumber, divePosition, diveId;
    private double boardType = 0.0;
    double diveTotal;
    private double sc1, sc2, sc3, sc4, sc5, sc6, sc7, diveScoreTotal = 0.0, multiplier = 0.0;
    private ArrayList<Double> Scores = new ArrayList<>();
    private boolean ifZeroTotal = true;
    private String failedDive = "P", diveNameString = "", className = "nonList",
                    diveTypeName, divePosString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dives);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_button);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        Bundle b = getIntent().getExtras();
        if (b != null) {
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

        loadScoreSpinners();
        setTitle();
        getJudges();
        showScores();
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

    //loads the spinners for the scores
    private void loadScoreSpinners(){
        int spinnerPosition = 10;
        GetScoreNames snames = new GetScoreNames();
        List<String> scoreNames = snames.doInBackground();

        ArrayAdapter<String> da = new ArrayAdapter<>(this,
                R.layout.spinner_item, scoreNames);
        da.setDropDownViewResource(R.layout.spinner_layout);
        score1.setAdapter(da);
        score1.setSelection(spinnerPosition);
        score2.setAdapter(da);
        score2.setSelection(spinnerPosition);
        score3.setAdapter(da);
        score3.setSelection(spinnerPosition);
        score4.setAdapter(da);
        score4.setSelection(spinnerPosition);
        score5.setAdapter(da);
        score5.setSelection(spinnerPosition);
        score6.setAdapter(da);
        score6.setSelection(spinnerPosition);
        score7.setAdapter(da);
        score7.setSelection(spinnerPosition);
    }
    
    public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnTotal = (Button) findViewById(R.id.buttonScore);

    	btnTotal.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if(multiplier != 0.0) {
                    getScoreText();
                    calcScores();
                    if(ifZeroTotal) {
                        updateJudgeScores();
                        Bundle b = new Bundle();
                        b.putInt("keyDiver", diverId);
                        b.putInt("keyMeet", meetId);
                        Intent intent = new Intent(context, ChooseSummary.class);
                        intent.putExtras(b);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Scores entered are not valid. Please enter an accurate score,",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void incrementDiveNumber(){
        IncrementDiveNumber divenumber = new IncrementDiveNumber();
        diveNumber ++;
        divenumber.doInBackground();
    }

    private void updateDiveListToYes(){
        UpdateDiveListToYes yes = new UpdateDiveListToYes();
        yes.doInBackground();
    }

    private void calcScores() {
        ifZeroTotal = true;
        GetTotalScore tScore = new GetTotalScore();

        double total = tScore.doInBackground();
        // Converts and sorts the ArrayList for processing
        Double[] theScores = new Double[ Scores.size()];
        Scores.toArray(theScores);
        Arrays.sort(theScores);

        if(judges == 3 || judges == 2){
            if(sc1 == 0.0 || sc2 == 0.0 || sc3 == 0.0){
                ifZeroTotal = false;
                return;
            }else {
                diveScoreTotal = sc1 + sc2 + sc3;
            }
        }else if (judges == 5){
            // converts the sorted array to a list and removes the smallest and largest scores
            List<Double> list = new ArrayList<>(Arrays.asList(theScores));
            list.remove(0);
            list.remove(3);
            // iterates through to get the sum of the three scores
            for (int i = 0; i < list.size(); i++){
                diveScoreTotal = diveScoreTotal + list.get(i);
            }
        } else if (judges == 7){
            // converts the sorted array a list and removes the smallest and largest scores
            List<Double> list = new ArrayList<>(Arrays.asList(theScores));
            list.remove(0);
            list.remove(0);
            list.remove(4);
            list.remove(3);
            // iterates through to get the sum of the three scores
            for(int i = 0; i < list.size(); i++){
                diveScoreTotal = diveScoreTotal + list.get(i);
            }
        }
        if(multiplier != 0.0) {
            diveTotal = diveScoreTotal * multiplier;
        }else{
            diveTotal = diveScoreTotal;
        }

        //roundedDiveTotal = .5 * Math.round(diveTotal * 2);

        total = total + diveTotal;

        if(diveTotal < .5){
            ifZeroTotal = false;
            return;
        }

        incrementDiveNumber();
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        int resultIndex;
        if(diveNumber == 1){
            resultIndex = 3;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            updateDiveListToYes();
            return;
        }
        if(diveNumber == 2){
            resultIndex = 4;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 3){
            resultIndex = 5;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 4){
            resultIndex = 6;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 5){
            resultIndex = 7;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 6){
            resultIndex = 8;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 7){
            resultIndex = 9;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 8){
            resultIndex = 10;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 9){
            resultIndex = 11;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 10){
            resultIndex = 12;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 11){
            resultIndex = 13;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
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


        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveNameForDB, DivePosition,
                             failedDive, diveTotal,  sc1, sc2, sc3, sc4, sc5, sc6, sc7, multiplier);
    }

    private void getScoreText(){
        Scores.clear();

        if(judges == 2) {
            double roundedScore;
            sc1 = Double.parseDouble(score1.getSelectedItem().toString().trim());
            sc2 = Double.parseDouble(score2.getSelectedItem().toString().trim());
            roundedScore = (sc1 + sc2) / 2;
            sc3 = roundedScore;
            sc4 = 0.0;
            sc5 = 0.0;
            sc6 = 0.0;
            sc7 = 0.0;
        }

        if(judges == 3) {
            sc1 = Double.parseDouble(score1.getSelectedItem().toString().trim());
            Scores.add(sc1);
            sc2 = Double.parseDouble(score2.getSelectedItem().toString().trim());
            Scores.add(sc2);
            sc3 = Double.parseDouble(score3.getSelectedItem().toString().trim());
            Scores.add(sc3);
            sc4 = 0.0;
            sc5 = 0.0;
            sc6 = 0.0;
            sc7 = 0.0;
        }

        if(judges == 5){
            sc1 = Double.parseDouble(score1.getSelectedItem().toString().trim());
            Scores.add(sc1);
            sc2 = Double.parseDouble(score2.getSelectedItem().toString().trim());
            Scores.add(sc2);
            sc3 = Double.parseDouble(score3.getSelectedItem().toString().trim());
            Scores.add(sc3);
            sc4 = Double.parseDouble(score4.getSelectedItem().toString().trim());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString().trim());
            Scores.add(sc5);
            sc6 = 0.0;
            sc7 = 0.0;

        }
        if(judges == 7){
            sc1 = Double.parseDouble(score1.getSelectedItem().toString().trim());
            Scores.add(sc1);
            sc2 = Double.parseDouble(score2.getSelectedItem().toString().trim());
            Scores.add(sc2);
            sc3 = Double.parseDouble(score3.getSelectedItem().toString().trim());
            Scores.add(sc3);
            sc4 = Double.parseDouble(score4.getSelectedItem().toString().trim());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString().trim());
            Scores.add(sc5);
            sc6 = Double.parseDouble(score6.getSelectedItem().toString().trim());
            Scores.add(sc6);
            sc7 = Double.parseDouble(score7.getSelectedItem().toString().trim());
            Scores.add(sc7);
        }
    }

    private void setTitle(){
        diveNameView = (TextView)findViewById(R.id.diveNameString);
        diveDDView = (TextView)findViewById(R.id.diveDDString);
        String fullName = Integer.toString(diveId) + divePosString + " - "
                + diveNameString;
        diveDDView.setText("Dive DD: " + Double.toString(multiplier));
        diveNameView.setText(fullName);

    }

    private void showScores(){
        if(judges == 2){
            view3.setVisibility(View.INVISIBLE);
            score3.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
            score4.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
            score5.setVisibility(View.INVISIBLE);
            view5.setVisibility(View.INVISIBLE);
            score6.setVisibility(View.INVISIBLE);
            view6.setVisibility(View.INVISIBLE);
            score7.setVisibility(View.INVISIBLE);
            view7.setVisibility(View.INVISIBLE);
        } else if(judges == 3){
            score4.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
            score5.setVisibility(View.INVISIBLE);
            view5.setVisibility(View.INVISIBLE);
            score6.setVisibility(View.INVISIBLE);
            view6.setVisibility(View.INVISIBLE);
            score7.setVisibility(View.INVISIBLE);
            view7.setVisibility(View.INVISIBLE);
        } else if(judges == 5){
            score6.setVisibility(View.INVISIBLE);
            view6.setVisibility(View.INVISIBLE);
            score7.setVisibility(View.INVISIBLE);
            view7.setVisibility(View.INVISIBLE);
        }
    }

    private void getJudges(){
        GetJudgeTotal jt = new GetJudgeTotal();
        judges = jt.doInBackground();
    }

    private void setUpView(){

        score1 = (Spinner)findViewById(R.id.editScore1);
        score2 = (Spinner)findViewById(R.id.editScore2);
        score3 = (Spinner)findViewById(R.id.editScore3);
        score4 = (Spinner)findViewById(R.id.editScore4);
        score5 = (Spinner)findViewById(R.id.editScore5);
        score6 = (Spinner)findViewById(R.id.editScore6);
        score7 = (Spinner)findViewById(R.id.editScore7);
        view3 =  (TextView)findViewById(R.id.score3);
        view4 =  (TextView)findViewById(R.id.score4);
        view5 =  (TextView)findViewById(R.id.score5);
        view6 =  (TextView)findViewById(R.id.score6);
        view7 =  (TextView)findViewById(R.id.score7);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dives, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
                            Toast.LENGTH_SHORT).show();
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

    private class GetScoreNames extends AsyncTask<List<String>, Object, Object>{
        ScoresDatabase db = new ScoresDatabase(getApplicationContext());
        List<String> score;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return score = db.getScores();
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

    private class GetTotalScore extends AsyncTask<Double, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double total;

        @Override
        protected Double doInBackground(Double... params) {
            return total = db.getTotalScore(meetId, diverId);
        }
    }

    private class GetJudgeTotal extends AsyncTask<Integer, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        int num;

        @Override
        protected Integer doInBackground(Integer... params) {
            return num = db.getJudges(meetId);
        }
    }

}
