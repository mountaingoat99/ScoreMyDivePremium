package com.rodriguez.scoremydivepremium;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.info.sqlite.helper.DiverDatabase;
import com.info.sqlite.helper.MeetDatabase;

import java.util.ArrayList;
import java.util.List;


public class MeetsDivers extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerName, spinnerMeet;
    private Button diverHistory, diverEdit, diverDelete, meetResult, meetEdit, meetDelete,
            btnNewDiver, btnNewMeet;
    private View layout2, layout3;
    private int diveCount = 0, meetCount = 0, diverId = 0, meetId = 0;
    private String stringId = "";
    private final Context context = this;
    public boolean firstAlertWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meets_divers);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setUpView();
        addListenerOnButton();
        loadSpinnerName();
        loadSpinnerMeet();
        spinnerName.setOnItemSelectedListener(this);
        spinnerMeet.setOnItemSelectedListener(this);

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!firstAlertWelcome) {
            showAlert();
        }
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlertWelcome = sp.getBoolean("firstAlertWelcome",false);
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
        dialog.setContentView(R.layout.dialog_welcome_info);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("firstAlertWelcome", true);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void showMenuHowToAlert(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_welcome_info);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void loadSpinnerName(){

        GetDiverInfo diver = new GetDiverInfo();
        List<String> diverName = diver.doInBackground();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diverName);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        dataAdapter.insert("  Choose a Diver", 0);
        spinnerName.setAdapter(dataAdapter);
    }

    private void loadSpinnerMeet(){

        GetMeetInfo meet = new GetMeetInfo();
        List<String> meetName = meet.doInBackground();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, meetName);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        dataAdapter.insert("  Choose a Meet", 0);
        spinnerMeet.setAdapter(dataAdapter);
    }

    // Checks all the buttons and makes sure the proper data is in place before the user can
    // start a meet, get reports, ect, then calls the correct Intent
    public void addListenerOnButton() {

        btnNewDiver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(context, EnterDiver.class);
                startActivity(intent);
            }
        });

        btnNewMeet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(context, EnterMeet.class);
                startActivity(intent);
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, final int position,
                               long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerDiverNameW && position >= 1){

            String nameSpinner = "name";
            diverId = getDiverId(nameSpinner);
            if(diveCount == 0){
                // counter used to keep track if the spinner has been hit and only adds the buttons once
                diveCount ++;
                layout2.setVisibility(View.VISIBLE);

                diverHistory.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ArrayList<String> meetInfo;

                        // calling a separate thread for the db select
                        MeetHistoryThread his = new MeetHistoryThread();
                        meetInfo = his.doInBackground(diverId);
                        if(!meetInfo.isEmpty()) {
                            Intent intent = new Intent(getBaseContext(), DiverHistory.class);
                            Bundle b = new Bundle();
                            b.putInt("key", diverId);
                            intent.putExtras(b);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Diver has not been in any meets yet",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                diverEdit.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), DiverEdit.class);
                        Bundle b = new Bundle();
                        b.putInt("key", diverId);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });

                diverDelete.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), DiverDelete.class);
                        Bundle b = new Bundle();
                        b.putInt("key", diverId);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }
        }

        if(spinner.getId() == R.id.spinnerMeetNameW && position >= 1){

            String meetSpinner = "meet";
            meetId = getDiverId(meetSpinner);

            if(meetCount == 0){
                // counter used to keep track if the spinner has been hit and only adds the buttons once
                meetCount ++;
                layout3.setVisibility(View.VISIBLE);

                meetResult.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ArrayList<String> diverInfo;

                        // calls a separate thread to get the database info
                        DiverHistoryThread his = new DiverHistoryThread();
                        diverInfo = his.doInBackground(meetId);
                        if(!diverInfo.isEmpty()) {
                            Intent intent = new Intent(getBaseContext(), MeetResults.class);
                            Bundle b = new Bundle();
                            b.putInt("key", meetId);
                            intent.putExtras(b);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Meet has no divers associated with it",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                meetEdit.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), MeetEdit.class);
                        Bundle b = new Bundle();
                        b.putInt("key", meetId);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });

                meetDelete.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), MeetDelete.class);
                        Bundle b = new Bundle();
                        b.putInt("key", meetId);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getDiverId(String spinner){
        int id;
        if(spinner.equals("name"))
        {
            stringId = spinnerName.getSelectedItem().toString().trim();
            GetDiverId ID = new GetDiverId();
            id = ID.doInBackground(stringId);
        }
        else
        {
            stringId = spinnerMeet.getSelectedItem().toString().trim();
            GetMeetId ID = new GetMeetId();
            id = ID.doInBackground(stringId);
        }
        return id;
    }

    private void setUpView(){
        spinnerName = (Spinner)findViewById(R.id.spinnerDiverNameW);
        spinnerMeet = (Spinner)findViewById(R.id.spinnerMeetNameW);
        btnNewDiver = (Button) findViewById(R.id.buttonNewDiver);
        btnNewMeet = (Button) findViewById(R.id.buttonNewMeet);
        diverHistory = (Button) findViewById(R.id.buttonDiverHistory);
        diverEdit = (Button) findViewById(R.id.buttonDiverEdit);
        diverDelete = (Button) findViewById(R.id.buttonDiverDelete);
        meetResult = (Button) findViewById(R.id.buttonMeetHistory);
        meetEdit = (Button) findViewById(R.id.buttonMeetEdit);
        meetDelete = (Button) findViewById(R.id.buttonMeetDelete);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_how_to:
                showMenuHowToAlert();
                break;
            case R.id.menu_rankings:
                Intent intent1 = new Intent(context, Rankings.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Async database tasks
    // These are private classes used to make the database calls in separate threads
    // so as to not tie up the UI thread. Otherwise the app will drop frames
    // and that never looks good.
    private class GetDiverInfo extends AsyncTask<List<String>, List<String>, List<String>> {
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        List<String> diverName;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return diverName = db.getDiverNames();
        }
    }

    private class GetMeetInfo extends AsyncTask<List<String>, List<String>, List<String>>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        List<String> meetName;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return meetName = db.getMeetNames();
        }
    }

    private class MeetHistoryThread extends AsyncTask<Integer, ArrayList<String>, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetinfo;

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            return meetinfo = db.getMeetHistory(diverId);
        }
    }

    private class DiverHistoryThread extends AsyncTask<Integer, ArrayList<String>, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverinfo;

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            return diverinfo = db.getDiverHistory(meetId);
        }
    }

    private class GetDiverId extends AsyncTask<String, Integer, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        int ids;

        @Override
        protected Integer doInBackground(String... params) {
            return ids = db.getId(stringId);
        }
    }

    private class GetMeetId extends AsyncTask<String, Integer, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        int ids;

        @Override
        protected Integer doInBackground(String... params) {
            return ids = db.getId(stringId);
        }
    }

}
