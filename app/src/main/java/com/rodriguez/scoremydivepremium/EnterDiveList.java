package com.rodriguez.scoremydivepremium;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.info.sqlite.helper.DiveListDatabase;
import com.info.sqlite.helper.DiveTotalDatabase;
import com.info.sqlite.helper.DiverDatabase;
import com.info.sqlite.helper.JudgeScoreDatabase;
import com.info.sqlite.helper.MeetDatabase;
import com.info.sqlite.helper.TypeDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnterDiveList extends ActionBarActivity {

    private TextView diveInfo1, diveInfo2, diveInfo3, diveInfo4, diveInfo5, diveInfo6,
                    diveInfo7, diveInfo8, diveInfo9, diveInfo10, diveInfo11, diverName, meetName;
    private View view1, layoutScores, layout11;
    private TableRow row1, row2, row3, row4,row5, row6, row7, row8, row9, row10, row11 ;
    private Button btnScore, btnChooseDive, btnTypeNumber, btnSwitchDiver;
    private int diverId, meetId, diveTotal, diveNumber = 0;
    private double boardType;
    private boolean hasList, checkList;
    private String infoString1 = "", infoString2 = "", infoString3 = "", infoString4 = "", infoString5 = "",
            infoString6 = "", infoString7 = "", infoString8 = "", infoString9 = "", infoString10 = "", infoString11 = "";

    private final Context context = this;
    public boolean firstAlertEditDiveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_dive_list);
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
        if(b != null) {
            diverId = b.getInt("keyDiver");
            meetId = b.getInt("keyMeet");
        }

        getBoardType();
        getDiveTotals();
        fillNameText();
        getDiveNumber();
        fillDiveInfo();
        addListenerOnButton();
        setUpLongPress();

        checkDiveList();
        if(!checkList) {
            // first we need to write a divelist record
            DiveListDatabase dldb = new DiveListDatabase(getApplicationContext());
            dldb.createNewDiveList(meetId, diverId, 0, 0);
        }

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!firstAlertEditDiveList) {
            showAlert();
            savePreferences("firstAlertEditDiveList", true);
        }
    }

    // here we are creating an empty List record
    private void checkDiveList(){
        CheckDiveList check = new CheckDiveList();
        checkList = check.doInBackground();
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlertEditDiveList = sp.getBoolean("firstAlertEditDiveList",false);
    }

    private void savePreferences(String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void showAlert(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_dive_list);
        dialog.show();
    }

    private void showAlertForHowTo(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_dive_list);
        dialog.show();
    }

    @Override
    public void onBackPressed(){
        final Context context = this;
        Intent intent = new Intent(context, Choose.class);
        Bundle b = new Bundle();
        b.putInt("keyDiver", diverId);
        b.putInt("keyMeet", meetId);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void addListenerOnButton(){
        final Context context = this;

        btnTypeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasList = true;
                Intent intent = new Intent(context, DiveNumberEnter.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("diveNumber", diveNumber);
                b.putDouble("boardType", boardType);
                b.putBoolean("hasList", hasList);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnChooseDive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasList = true;
                Intent intent = new Intent(context, DiveChoose.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("diveNumber", diveNumber);
                b.putDouble("boardType", boardType);
                b.putBoolean("hasList", hasList);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseDivesFromList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnSwitchDiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context, SwitchDivers.class);
                Bundle b = new Bundle();
                b.putInt("keyMeet", meetId);
                b.putString("sendingClass", "EnterDiveList");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void setUpLongPress(){
        final Context context = this;
        diveInfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 1;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 2;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 3;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 4;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 5;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 6;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 7;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 8;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 9;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 10;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 11;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void fillDiveInfo(){
        if(diveNumber >= 1){
            updateListFilled(1);
            layoutScores.setVisibility(View.VISIBLE);
            row1.setVisibility(View.VISIBLE);
            infoString1 = getDiveInfoFromDB(1);
            diveInfo1.setText(infoString1);
        }

        if(diveNumber >= 2){
            row2.setVisibility(View.VISIBLE);
            infoString2 = getDiveInfoFromDB(2);
            diveInfo2.setText(infoString2);
        }

        if(diveNumber >= 3){
            row3.setVisibility(View.VISIBLE);
            infoString3 = getDiveInfoFromDB(3);
            diveInfo3.setText(infoString3);
        }

        if(diveNumber >= 4){
            row4.setVisibility(View.VISIBLE);
            infoString4 = getDiveInfoFromDB(4);
            diveInfo4.setText(infoString4);
        }

        if(diveNumber >= 5){
            row5.setVisibility(View.VISIBLE);
            infoString5 = getDiveInfoFromDB(5);
            diveInfo5.setText(infoString5);
        }

        if(diveNumber >= 6){
            row6.setVisibility(View.VISIBLE);
            infoString6 = getDiveInfoFromDB(6);
            diveInfo6.setText(infoString6);
        }

        if(diveNumber == 6 && diveTotal == 6){
            updateListFilled(2);
            Toast.makeText(getApplicationContext(),
                    "All six dives have been entered, " +
                            "Click on a dive to edit it",
                    Toast.LENGTH_SHORT).show();
            view1.setVisibility(View.VISIBLE);
            btnScore.setVisibility(View.VISIBLE);
            layout11.setVisibility(View.GONE);
            return;
        }

        if(diveNumber >= 7){
            row7.setVisibility(View.VISIBLE);
            infoString7 = getDiveInfoFromDB(7);
            diveInfo7.setText(infoString7);
        }

        if(diveNumber >= 8){
            row8.setVisibility(View.VISIBLE);
            infoString8 = getDiveInfoFromDB(8);
            diveInfo8.setText(infoString8);
        }

        if(diveNumber >= 9){
            row9.setVisibility(View.VISIBLE);
            infoString9 = getDiveInfoFromDB(9);
            diveInfo9.setText(infoString9);
        }

        if(diveNumber >= 10){
            row10.setVisibility(View.VISIBLE);
            infoString10 = getDiveInfoFromDB(10);
            diveInfo10.setText(infoString10);
        }

        if(diveNumber >= 11){
            row11.setVisibility(View.VISIBLE);
            infoString11 = getDiveInfoFromDB(11);
            diveInfo11.setText(infoString11);
        }

        if(diveNumber == 11 && diveTotal == 11){
            updateListFilled(2);
            Toast.makeText(getApplicationContext(),
                    "All eleven dives have been entered, " +
                            "Click on a dive to edit it",
                    Toast.LENGTH_SHORT).show();
            view1.setVisibility(View.VISIBLE);
            btnScore.setVisibility(View.VISIBLE);
            layout11.setVisibility(View.GONE);
        }
    }

    private void updateListFilled(int key){
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        db.updateListFilled(meetId, diverId, key);
    }

    private String getDiveInfoFromDB(int divenumber){
        int dash;
        String number, position, dd;
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        ArrayList<String> info;
        info = db.getCatAndName(meetId, diverId, divenumber);
        if(info.size() > 0){
            //cat = info.get(0);
            number = info.get(1);
            dash = number.indexOf("-");
            number = number.substring((0), (dash)).trim();
            position = info.get(2);
            dash = position.indexOf("-");
            position = position.substring((0), (dash)).trim();
            dd = info.get(3);

            return number + position + " - DD: " + dd;
        }
        return null;
    }

    private void getDiveTotals(){
        SearchDiveTotals total = new SearchDiveTotals();
        diveTotal = total.doInBackground();
    }

    private void fillNameText(){
        ArrayList<String> diverInfo;
        GetDiverInfo info = new GetDiverInfo();
        diverInfo = info.doInBackground();

        String nameString = diverInfo.get(0);
        diverName.setText(nameString);

        ArrayList<String> meetInfo;
        GetMeetInfo infoMeet = new GetMeetInfo();
        meetInfo = infoMeet.doInBackground();

        String meetNameString = meetInfo.get(0);
        meetName.setText(meetNameString);
    }

    private void getDiveNumber(){
        List<Integer> numbers;
        GetDiveNumber num = new GetDiveNumber();
        numbers = num.doInBackground();
        if(numbers.size() > 0) {
            diveNumber = Collections.max(numbers);
        }
    }

    private void getBoardType(){
        GetBoardType type = new GetBoardType();
        boardType = type.doInBackground();
    }

    private void setUpView(){
        diverName = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        diveInfo1 = (TextView)findViewById(R.id.diveInfo1);
        diveInfo2 = (TextView)findViewById(R.id.diveInfo2);
        diveInfo3 = (TextView)findViewById(R.id.diveInfo3);
        diveInfo4 = (TextView)findViewById(R.id.diveInfo4);
        diveInfo5 = (TextView)findViewById(R.id.diveInfo5);
        diveInfo6 = (TextView)findViewById(R.id.diveInfo6);
        diveInfo7 = (TextView)findViewById(R.id.diveInfo7);
        diveInfo8 = (TextView)findViewById(R.id.diveInfo8);
        diveInfo9 = (TextView)findViewById(R.id.diveInfo9);
        diveInfo10 = (TextView)findViewById(R.id.diveInfo10);
        diveInfo11 = (TextView)findViewById(R.id.diveInfo11);
        row1 = (TableRow)findViewById(R.id.tableRow1);
        row2 = (TableRow)findViewById(R.id.tableRow2);
        row3 = (TableRow)findViewById(R.id.tableRow3);
        row4 = (TableRow)findViewById(R.id.tableRow4);
        row5 = (TableRow)findViewById(R.id.tableRow5);
        row6 = (TableRow)findViewById(R.id.tableRow6);
        row7 = (TableRow)findViewById(R.id.tableRow7);
        row8 = (TableRow)findViewById(R.id.tableRow8);
        row9 = (TableRow)findViewById(R.id.tableRow9);
        row10 = (TableRow)findViewById(R.id.tableRow10);
        row11 = (TableRow)findViewById(R.id.tableRow11);
        btnScore = (Button)findViewById(R.id.buttonScore);
        btnChooseDive = (Button)findViewById(R.id.buttonChooseDives);
        btnTypeNumber = (Button)findViewById(R.id.buttonTypeNumber);
        view1 = findViewById(R.id.view1);
        layoutScores = findViewById(R.id.layoutScores);
        layout11 = findViewById(R.id.layout11);
        btnSwitchDiver = (Button)findViewById(R.id.buttonSwitchDiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter_dive_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            case R.id.menu_how_to:
                showAlertForHowTo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetBoardType extends AsyncTask<Double, Object, Object> {
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        Double type;

        @Override
        protected Double doInBackground(Double... params) {
            return type = db.getType(meetId, diverId);
        }
    }

    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object> {
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }

    private class GetDiverInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return diverinfo = db.getDiverInfo(diverId);
        }
    }

    private class GetMeetInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return meetinfo = db.getMeetInfo(meetId);
        }
    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        JudgeScoreDatabase db =  new JudgeScoreDatabase(getApplicationContext());
        List<Integer> number;

        @Override
        protected List<Integer> doInBackground(Integer... params) {
            return number = db.getDiveNumbers(meetId, diverId);
        }
    }

    private class CheckDiveList extends AsyncTask<Boolean, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        boolean check;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return check = db.checkList(meetId, diverId);
        }
    }
}
