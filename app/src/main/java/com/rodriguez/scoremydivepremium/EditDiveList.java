package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

public class EditDiveList extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private TextView DODView, name;
    private Spinner diveCategory, diveStyle;
    private RadioButton radioStraight, radioPike, radioTuck, radioFree;
    private Button btnEnter;
    private int diverId, meetId, divePosition, diveType = 1, newDiveType = 0, diveNumber, diverSpinnerPosition;
    private double boardType, multiplier = 0.0;
    private ArrayList<DiveStyleSpinner> searchDives;
    private String stringId, ddString;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dive_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();
        diveCategory.setOnItemSelectedListener(this);
        diveStyle.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if (b!= null) {
            diverId = b.getInt("keyDiver");
            meetId = b.getInt("keyMeet");
            diverSpinnerPosition = b.getInt("keySpin");
            diveNumber = b.getInt("keyDiveNumber");
            boardType = b.getDouble("keyBoardType");
        }

        loadCategorySpinnerData();
        getMultiplier();
        checkRadios();
        addListenerOnButton();
    }

    private void addListenerOnButton(){
        final Context context = this;
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(multiplier != 0.0) {
                    updateJudgeScore();Intent intent = new Intent(context, EnterDiveList.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keySpin", diverSpinnerPosition);
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
    }

    private void updateJudgeScore(){
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

        TextView name = (TextView) findViewById(R.id.diveStyle);
        TextView id = (TextView) findViewById(R.id.diveId);
        String i = id.getText().toString();
        String diveTypeName = i + " - " + name.getText().toString();

        db.updateJudgeScoreTypes(meetId, diverId, diveCategory, diveTypeName, DivePosition,
                multiplier, diveNumber);
    }

    private void loadCategorySpinnerData(){
        if(boardType == 1.0 || boardType == 3.0) {
            GetSpringboardDiveName dives = new GetSpringboardDiveName();
            List<String> diveName = dives.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            dataAdapter.insert("  Choose a Dive Category", 0);
            diveCategory.setAdapter(dataAdapter);
        } else {
            GetPlatformDiveName divesP = new GetPlatformDiveName();
            List<String> diveName = divesP.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            dataAdapter.insert("  Choose a Dive Category", 0);
            diveCategory.setAdapter(dataAdapter);
        }
        if (diveCategory.getSelectedItemPosition() == 0){
            diveCategory.setSelection(1);
        }
        loadStyleSpinnerData();
    }

    private void loadStyleSpinnerData(){

        // DiveStyle Spinner
        newDiveType = diveType;
        DiveChooseController dive = new DiveChooseController();
        searchDives = dive.GetDiveCat(diveType, boardType, context);
        diveStyle.setAdapter(new SpinnerDiveStyleCustomBaseAdpater(this, searchDives));
    }

    private void getMultiplier(){
        //int diveId;
        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null && divePosition != 0) {
            stringId = name.getText().toString();

            DiveChooseController dive = new DiveChooseController();
            multiplier = dive.GetMultiplier(stringId, diveType, divePosition, boardType, context);

            ddString = "Dive DD: " + multiplier;
            DODView.setText(ddString);
        } else {
            ddString = "Dive DD: ";
            DODView.setText(ddString);
        }
    }

    private void checkRadios() {
        radioStraight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(false);
                radioStraight.setChecked(true);
                radioTuck.setChecked(false);
                divePosition = 1;
                getMultiplier();
            }
        });
        radioPike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(true);
                radioStraight.setChecked(false);
                radioTuck.setChecked(false);
                divePosition = 2;
                getMultiplier();
            }
        });
        radioTuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(false);
                radioStraight.setChecked(false);
                radioTuck.setChecked(true);
                divePosition = 3;
                getMultiplier();
            }
        });
        radioFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(true);
                radioPike.setChecked(false);
                radioStraight.setChecked(false);
                radioTuck.setChecked(false);
                divePosition = 4;
                getMultiplier();
            }
        });
    }

    private void DisableRadioButtons(){

        DiveChooseController dive = new DiveChooseController();
        ArrayList<Double> ddList = dive.GetTheDD(stringId, diveType, boardType, context);

        double testS = ddList.get(0);
        double testP = ddList.get(1);
        double testT = ddList.get(2);
        double testF = ddList.get(3);

        if (testS == 0.0){
            radioStraight.setEnabled(false);
            radioStraight.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            radioStraight.setEnabled(true);
            radioStraight.setTextColor(this.getResources().getColor(R.color.primary_text));
        }

        if (testP == 0.0){
            radioPike.setEnabled(false);
            radioPike.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            radioPike.setEnabled(true);
            radioPike.setTextColor(this.getResources().getColor(R.color.primary_text));
        }

        if (testT == 0.0){
            radioTuck.setEnabled(false);
            radioTuck.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            radioTuck.setEnabled(true);
            radioTuck.setTextColor(this.getResources().getColor(R.color.primary_text));
        }

        if (testF == 0.0){
            radioFree.setEnabled(false);
            radioFree.setTextColor(this.getResources().getColor(R.color.secondary_text));
        }else {
            radioFree.setEnabled(true);
            radioFree.setTextColor(this.getResources().getColor(R.color.primary_text));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.edit_dive_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpView(){
        DODView = (TextView)findViewById(R.id.DODView);
        diveCategory = (Spinner)findViewById(R.id.DiveCategory);
        diveStyle = (Spinner)findViewById(R.id.DiveStyle);
        btnEnter = (Button)findViewById(R.id.buttonScore);
        radioStraight = (RadioButton)findViewById(R.id.radioStraight);
        radioPike = (RadioButton)findViewById(R.id.radioPike);
        radioTuck = (RadioButton)findViewById(R.id.radioTuck);
        radioFree = (RadioButton)findViewById(R.id.radioFree);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        diveType = diveCategory.getSelectedItemPosition();

        if(diveType != newDiveType){
            loadStyleSpinnerData();
        }

        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null) {
            stringId = name.getText().toString();
        }
        DisableRadioButtons();
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
