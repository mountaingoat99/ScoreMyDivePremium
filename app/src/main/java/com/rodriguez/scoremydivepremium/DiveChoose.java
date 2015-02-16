package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.info.Helpers.DiveStyleSpinner;
import com.info.controls.DiveChooseController;
import com.info.controls.SpinnerDiveStyleCustomBaseAdpater;
import com.info.sqlite.helper.DivesDatabase;
import com.info.sqlite.helper.JudgeScoreDatabase;
import com.info.sqlite.helper.PlatformDivesDatabase;

import java.util.ArrayList;
import java.util.List;


public class DiveChoose extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerDiveCat, spinnerDiveType;
    private RadioButton rdStraight, rdPike, rdTuck, rdFree;
    private TextView diveddView, name;
    private View layoutScore, layoutList;
    private Button btnJudgeScore, btnTotalScore, btnAddToList;
    private int diverId, meetId, diveType, diveNumber, divePosition;
    private double boardType, multiplier = 0.0;
    private String stringId, divePosString;
    private boolean hasList;
    private ArrayList<DiveStyleSpinner> searchDives;
    final Context context = this;
    private static final String KEY_TEXT_VALUE = "textValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_choose);
        if (savedInstanceState != null) {
            stringId = savedInstanceState.getString(KEY_TEXT_VALUE);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();
        spinnerDiveCat.setOnItemSelectedListener(this);
        spinnerDiveType.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            diverId = b.getInt("keyDiver");
            meetId = b.getInt("keyMeet");
            diveNumber = b.getInt("diveNumber");
            boardType = b.getDouble("boardType");
            hasList = b.getBoolean("hasList");
        }

        loadCatSpinnerData();
        addListenerOnButton();
        checkRadios();
        hideButtons();
    }

    private void hideButtons(){
        if(hasList){
            layoutScore.setVisibility(View.GONE);
            layoutList.setVisibility(View.VISIBLE);
        } else {
            layoutScore.setVisibility(View.VISIBLE);
            layoutList.setVisibility(View.GONE);
        }
    }

    private void addListenerOnButton() {

        btnJudgeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (multiplier != 0.0) {

                    // divetype in string
                    String diveTypeName = spinnerDiveCat.getSelectedItem().toString();
                    //dive Name in string
                    TextView name = (TextView) findViewById(R.id.diveStyle);
                    String diveName = name.getText().toString();
                    // diveID
                    TextView id = (TextView) findViewById(R.id.diveId);
                    int diveId = Integer.parseInt(id.getText().toString());

                    Intent intent = new Intent(context, Dives.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveNumber", diveNumber);
                    // BoardSize
                    b.putDouble("boardType", boardType);
                    // DD
                    b.putDouble("multiplier", multiplier);
                    // diveType Int
                    b.putInt("diveType", diveType);
                    // diveType String
                    b.putString("diveTypeString", diveTypeName);
                    // diveID
                    b.putInt("diveID", diveId);
                    // dive name
                    b.putString("diveName", diveName);
                    // position int
                    b.putInt("positionNum", divePosition);
                    // position String
                    b.putString("postionString", divePosString);
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTotalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (multiplier != 0.0) {

                    // divetype in string
                    String diveTypeName = spinnerDiveCat.getSelectedItem().toString();
                    //dive Name in string
                    TextView name = (TextView) findViewById(R.id.diveStyle);
                    String diveName = name.getText().toString();
                    // diveID
                    TextView id = (TextView) findViewById(R.id.diveId);
                    int diveId = Integer.parseInt(id.getText().toString());

                    Intent intent = new Intent(context, EnterFinalDiveScore.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveNumber", diveNumber);
                    // BoardSize
                    b.putDouble("boardType", boardType);
                    // DD
                    b.putDouble("multiplier", multiplier);
                    // diveType Int
                    b.putInt("diveType", diveType);
                    // diveType String
                    b.putString("diveTypeString", diveTypeName);
                    // diveID
                    b.putInt("diveID", diveId);
                    // dive name
                    b.putString("diveName", diveName);
                    // position int
                    b.putInt("positionNum", divePosition);
                    // position String
                    b.putString("postionString", divePosString);
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (multiplier != 0.0){

                    // divetype in string
                    String diveTypeName = spinnerDiveCat.getSelectedItem().toString();
                    //dive Name in string
                    TextView name = (TextView) findViewById(R.id.diveStyle);
                    String diveName = name.getText().toString();
                    // diveID
                    TextView id = (TextView) findViewById(R.id.diveId);
                    String diveId = id.getText().toString();
                    String diveNameForDB = diveId + " - " +  diveName;
                    String divePositionForDB = divePositionCheck();

                    int divenum = diveNumber += 1;

                    JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
                    db.fillNewJudgeScores(meetId, diverId, divenum, diveTypeName, diveNameForDB, divePositionForDB,
                        "", 0.0,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, multiplier);

                    Intent intent = new Intent(context, EnterDiveList.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String divePositionCheck() {

        String DivePosition = null;
        switch (divePosition) {
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
        return DivePosition;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        // Dive Category Spinner
        if(spinner.getId() == R.id.spinnerDiveCatC){

            DiveChooseController dive = new DiveChooseController();
            diveType = dive.SetDiveTypeNumber(position, boardType);
            if (diveType > 0 ) {
                loadTypeSpinnerData();
            }

        // Dive Type Spinner
        } else {
            name = (TextView) findViewById(R.id.diveStyle);
            if(name != null) {
                stringId = name.getText().toString();
            }
            DisableRadioButtons();
            getMultiplier();
        }
    }

    // here we need to send the spinner in fromm the dialog and then fill them
    private void loadCatSpinnerData(){

        if(boardType == 1.0 || boardType == 3.0) {

            GetSpringboardDiveName dives = new GetSpringboardDiveName();
            List<String> diveName = dives.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            dataAdapter.insert("  Choose a Dive Category", 0);
            spinnerDiveCat.setAdapter(dataAdapter);

        } else {

            GetPlatformDiveName divesP = new GetPlatformDiveName();
            List<String> diveName = divesP.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            dataAdapter.insert("  Choose a Dive Category", 0);
            spinnerDiveCat.setAdapter(dataAdapter);
        }
    }

    private void loadTypeSpinnerData() {

        DiveChooseController dive = new DiveChooseController();
        searchDives = dive.GetDiveCat(diveType, boardType, context);
        spinnerDiveType.setAdapter(new SpinnerDiveStyleCustomBaseAdpater(this, searchDives));
    }

    private void DisableRadioButtons(){

        DiveChooseController dive = new DiveChooseController();
        ArrayList<Double> ddList = dive.GetTheDD(stringId, diveType, boardType, context);

        double testS = ddList.get(0);
        double testP = ddList.get(1);
        double testT = ddList.get(2);
        double testF = ddList.get(3);

        if (testS == 0.0){
            rdStraight.setEnabled(false);
            rdStraight.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            rdStraight.setEnabled(true);
            rdStraight.setTextColor(this.getResources().getColor(R.color.primary_text));
        }

        if (testP == 0.0){
            rdPike.setEnabled(false);
            rdPike.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            rdPike.setEnabled(true);
            rdPike.setTextColor(this.getResources().getColor(R.color.primary_text));
        }

        if (testT == 0.0){
            rdTuck.setEnabled(false);
            rdTuck.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            rdTuck.setEnabled(true);
            rdTuck.setTextColor(this.getResources().getColor(R.color.primary_text));
        }

        if (testF == 0.0){
            rdFree.setEnabled(false);
            rdFree.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            rdFree.setEnabled(true);
            rdFree.setTextColor(this.getResources().getColor(R.color.primary_text));
        }
    }

    private void checkRadios() {
        rdStraight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdFree.setChecked(false);
                rdPike.setChecked(false);
                rdStraight.setChecked(true);
                rdTuck.setChecked(false);
                divePosition = 1;
                divePosString = "A";
                getMultiplier();
            }
        });
        rdPike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdFree.setChecked(false);
                rdPike.setChecked(true);
                rdStraight.setChecked(false);
                rdTuck.setChecked(false);
                divePosition = 2;
                divePosString = "B";
                getMultiplier();
            }
        });
        rdTuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdFree.setChecked(false);
                rdPike.setChecked(false);
                rdStraight.setChecked(false);
                rdTuck.setChecked(true);
                divePosition = 3;
                divePosString = "C";
                getMultiplier();
            }
        });
        rdFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdFree.setChecked(true);
                rdPike.setChecked(false);
                rdStraight.setChecked(false);
                rdTuck.setChecked(false);
                divePosition = 4;
                divePosString = "D";
                getMultiplier();
            }
        });
    }

    private void getMultiplier(){
        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null && divePosition != 0) {
            stringId = name.getText().toString();

            DiveChooseController dive = new DiveChooseController();
            multiplier = dive.GetMultiplier(stringId, diveType, divePosition, boardType, context);

            String ddString = "Dive DD: " + multiplier;
            diveddView.setText(ddString);
        } else {
            String ddString = "Dive DD: ";
            diveddView.setText(ddString);
        }
    }

    private void setUpView() {

        spinnerDiveCat = (Spinner)findViewById(R.id.spinnerDiveCatC);
        spinnerDiveType = (Spinner)findViewById(R.id.spinnerDiveType);
        rdStraight = (RadioButton)findViewById(R.id.radioStraight);
        rdPike = (RadioButton)findViewById(R.id.radioPike);
        rdTuck = (RadioButton)findViewById(R.id.radioTuck);
        rdFree = (RadioButton)findViewById(R.id.radioFree);
        diveddView = (TextView)findViewById(R.id.showDD);
        btnJudgeScore = (Button)findViewById(R.id.buttonJudgeScore);
        btnTotalScore = (Button)findViewById(R.id.buttonTotalScore);
        btnAddToList = (Button)findViewById(R.id.buttonAddToList);
        layoutList = findViewById(R.id.layoutList);
        layoutScore = findViewById(R.id.layoutScore);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT_VALUE, stringId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dive_choose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetSpringboardDiveName extends AsyncTask<List<String>, Object, Object> {
        DivesDatabase db = new DivesDatabase(getApplicationContext());
        List<String> dives;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return dives = db.getDiveNames();
        }
    }

    private class GetPlatformDiveName extends AsyncTask<List<String>, Object, Object>{
        PlatformDivesDatabase db = new PlatformDivesDatabase(getApplicationContext());
        List<String> dives;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return dives = db.getPlatformDiveNames();
        }
    }
}

