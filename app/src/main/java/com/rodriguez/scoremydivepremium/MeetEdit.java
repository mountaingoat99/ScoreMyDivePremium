package com.rodriguez.scoremydivepremium;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.info.sqlite.helper.MeetDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class MeetEdit extends ActionBarActivity implements
        OnClickListener{

    private RadioGroup radioJudgesGroup;
    private RadioButton judge2, judge3, judge5, judge7;
	private EditText name, school, city, state, date;
	private String nameString, schoolString, cityString, stateString, dateString, nameEdit,
            schoolEdit, cityEdit, stateEdit, dateEdit, judgeString;
	private int meetId, judges, judgeChecked;
    private final Context context = this;
    public int idMeet;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_edit);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setupView();
        date.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("meetNameKey") != null) {
                name.setText(b.getString("meetNameKey"));
            }
            if (b.getString("schoolKey") != null) {
                school.setText(b.getString("schoolKey"));
            }
            if (b.getString("cityKey") != null) {
                city.setText(b.getString("cityKey"));
            }
            if (b.getString("stateKey") != null) {
                state.setText(b.getString("stateKey"));
            }
            judges = b.getInt("judgeKey");
            setJudgesChecked();
            date.setText(b.getString("dateKey"));
            nameString = b.getString("nameStr");
            schoolString = b.getString("schoolStr");
            cityString = b.getString("cityStr");
            stateString = b.getString("stateStr");
            dateString = b.getString("dateStr");
            judgeString = b.getString("judgeStr");
            // if we are getting the date we will send 0 as the key and get it back,
            // then we will only fill the text from the db on entry to the page,
            // not re-load after setting date dialog
            if (b.getInt("key") > 0) {
                meetId = b.getInt("key");
                fillEditText();
            }
        }

        addListenerOnButton();
        savePreferences("idMeet", meetId);
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idMeet = sp.getInt("idMeet", 0);
    }

    private void savePreferences(String key, int value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void setupView() {
        name = (EditText)findViewById(R.id.editTextNameEM);
        school = (EditText)findViewById(R.id.editTextSchoolEM);
        city = (EditText)findViewById(R.id.editTextCityEM);
        state = (EditText)findViewById(R.id.editTextStateEM);
        date = (EditText)findViewById(R.id.editTextDateEM);
        radioJudgesGroup = (RadioGroup)findViewById(R.id.radioGroupMeet);
        judge2 = (RadioButton)findViewById(R.id.radio2J);
        judge3 = (RadioButton)findViewById(R.id.radio3J);
        judge5 = (RadioButton)findViewById(R.id.radio5J);
        judge7 = (RadioButton)findViewById(R.id.radio7J);
    }

    public void fillEditText(){
		ArrayList<String> meetInfo;
        GetInfoForMeet meetinfo = new GetInfoForMeet();
        meetInfo = meetinfo.doInBackground();

		if(!meetInfo.isEmpty()){
			nameString = meetInfo.get(0);
			schoolString = meetInfo.get(1);
			cityString = meetInfo.get(2);
			stateString = meetInfo.get(3);
			dateString = meetInfo.get(4);
            judgeString = meetInfo.get(5);
		
			name.setText(nameString);
			school.setText(schoolString);
			city.setText(cityString);
			state.setText(stateString);
			date.setText(dateString);

            if(judgeString.equals("2")){
                judge2.setChecked(true);
                judge3.setChecked(false);
                judge5.setChecked(false);
                judge7.setChecked(false);
            }

            if(judgeString.equals("3")){
                judge2.setChecked(false);
                judge3.setChecked(true);
                judge5.setChecked(false);
                judge7.setChecked(false);
            }
            if(judgeString.equals("5")){
                judge2.setChecked(false);
                judge5.setChecked(true);
                judge3.setChecked(false);
                judge7.setChecked(false);
            }
            if (judgeString.equals("7")) {
                judge2.setChecked(false);
                judge7.setChecked(true);
                judge3.setChecked(false);
                judge5.setChecked(false);
            }
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Meet is corrupted, please delete and add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}

	public void addListenerOnButton()
    {
		final Context context = this;

        loadSavedPreferences();

        Button btnEditMeet = (Button) findViewById(R.id.buttonMeetE);
    	btnEditMeet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nameEdit = name.getText().toString().trim();
                schoolEdit = school.getText().toString().trim();
                cityEdit = city.getText().toString().trim();
                stateEdit = state.getText().toString().trim();
                dateEdit = date.getText().toString().trim();
                judgeChecked = radioJudgesGroup.getCheckedRadioButtonId();
                if (judgeChecked == R.id.radio2J)
                    judges = 2;
                if (judgeChecked == R.id.radio3J)
                    judges = 3;
                if (judgeChecked == R.id.radio5J)
                    judges = 5;
                if (judgeChecked == R.id.radio7J)
                    judges = 7;

                if (nameEdit.isEmpty() || schoolEdit.isEmpty()
                        || cityEdit.isEmpty() || stateEdit.isEmpty()
                        || dateEdit.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please make an entry in all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!nameEdit.equals(nameString)) {
                    EditNameinDB eName = new EditNameinDB();
                    eName.doInBackground();
                }
                if (!schoolEdit.equals(schoolString)) {
                    EditSchoolinDB eSchool = new EditSchoolinDB();
                    eSchool.doInBackground();
                }
                if (!cityEdit.equals(cityString)) {
                    EditCityinDB eCity = new EditCityinDB();
                    eCity.doInBackground();
                }
                if (!stateEdit.equals(stateString)) {
                    EditStateinDB eState = new EditStateinDB();
                    eState.doInBackground();
                }
                if (!dateEdit.equals(dateString)) {
                    EditDateinDB eDate = new EditDateinDB();
                    eDate.doInBackground();
                }
                EditJudgeinDB eJudge = new EditJudgeinDB();
                eJudge.doInBackground();
                Toast.makeText(getApplicationContext(),
                        "Diver has been edited to " + nameEdit + ", "
                                + schoolEdit + ", " + cityEdit + ", "
                                + stateEdit + ", " + dateEdit + ", Judges: "
                                + judges, Toast.LENGTH_LONG
                ).show();
                Intent intent = new Intent(context, MeetsDivers.class);
                startActivity(intent);
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.activity_meet_edit, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        final Context context = this;
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_how_to:
                Intent intent3 = new Intent(context, HowTo.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == date) {

            checkRadios();

            // works, but testing out pure dialog here
            boolean fromMeetEnter = true;
            Intent intent = new Intent(context, DatePickerCustom.class);
            Bundle b = new Bundle();
            if (name.getText() != null) {
                b.putString("meetNameKey", name.getText().toString().trim());
            }
            if (school.getText() != null) {
                b.putString("schoolKey", school.getText().toString().trim());
            }
            if (city.getText() != null) {
                b.putString("cityKey", city.getText().toString().trim());
            }
            if (state.getText() != null) {
                b.putString("stateKey", state.getText().toString().trim());
            }
            b.putString("nameStr", nameString);
            b.putString("schoolStr", schoolString);
            b.putString("cityStr", cityString);
            b.putString("stateStr", stateString);
            b.putString("dateStr", dateString);
            b.putString("judgeStr", judgeString);
            b.putInt("judgeKey", judges);
            b.putInt("key", 0);
            b.putBoolean("meetEnterKey", false);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    private void checkRadios() {

        judgeChecked = radioJudgesGroup.getCheckedRadioButtonId();
        if (judgeChecked == R.id.radio2J)
            judges = 2;
        if (judgeChecked == R.id.radio3J)
            judges = 3;
        if (judgeChecked == R.id.radio5J)
            judges = 5;
        if (judgeChecked == R.id.radio7J)
            judges = 7;
    }

    private void setJudgesChecked() {

        switch (judges){
            case 2:
                judge2.setChecked(true);
                judge3.setChecked(false);
                judge5.setChecked(false);
                judge7.setChecked(false);
                break;
            case 3:
                judge2.setChecked(false);
                judge3.setChecked(true);
                judge5.setChecked(false);
                judge7.setChecked(false);
                break;
            case 5:
                judge2.setChecked(false);
                judge3.setChecked(false);
                judge5.setChecked(true);
                judge7.setChecked(false);
                break;
            case 7:
                judge2.setChecked(false);
                judge3.setChecked(false);
                judge5.setChecked(false);
                judge7.setChecked(true);
                break;
        }
    }

    private class EditNameinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetName(idMeet, nameEdit);
            return null;
        }
    }

    private class EditSchoolinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetSchool(idMeet, schoolEdit);
            return null;
        }
    }

    private class EditCityinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetCity(idMeet, cityEdit);
            return null;
        }
    }

    private class EditStateinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetState(idMeet, stateEdit);
            return null;
        }
    }

    private class EditDateinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetDate(idMeet, dateEdit);
            return null;
        }
    }

    private class EditJudgeinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetJudges(idMeet, judges);
            return null;
        }
    }

    private class GetInfoForMeet extends AsyncTask<ArrayList<String>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getMeetInfo(meetId);
        }
    }
}
