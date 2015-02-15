package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.info.sqlite.helper.MeetDatabase;

public class EnterMeet extends ActionBarActivity implements
OnClickListener {

    private RadioGroup radioJudgesGroup;
    private RadioButton j2, j3, j5, j7;
    private TextView name, school, city, state;
	private EditText txtDate;
    private Button btnEnterMeet;
    private int judges, judgeChecked;
	private String nameString, schoolString, cityString, stateString, dateString;
    private final Context context = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_meet);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_button);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setUpViews();
        txtDate.setOnClickListener(this);

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
            txtDate.setText(b.getString("dateKey"));
        }

        addListenerOnButton();
    }

	@Override
    public void onClick(View v) {
 
        if (v == txtDate) {

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
            b.putInt("judgeKey", judges);
            b.putBoolean("meetEnterKey", fromMeetEnter);
            intent.putExtras(b);
            startActivity(intent);

         }
    }
	
	public void addListenerOnButton()
    {
    	final Context context = this;

    	btnEnterMeet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                nameString = name.getText().toString().trim();
                schoolString = school.getText().toString().trim();
                cityString = city.getText().toString().trim();
                stateString = state.getText().toString().trim();
                dateString = txtDate.getText().toString().trim();
                checkRadios();

                if (nameString.isEmpty() || schoolString.isEmpty()
                        || stateString.isEmpty() || dateString.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please make an entry in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    WriteNewMeet newmeet = new WriteNewMeet();
                    newmeet.doInBackground();
                    Toast.makeText(getApplicationContext(),
                            "Meet has been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MeetsDivers.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
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
                j2.setChecked(true);
                j3.setChecked(false);
                j5.setChecked(false);
                j7.setChecked(false);
                break;
            case 3:
                j2.setChecked(false);
                j3.setChecked(true);
                j5.setChecked(false);
                j7.setChecked(false);
                break;
            case 5:
                j2.setChecked(false);
                j3.setChecked(false);
                j5.setChecked(true);
                j7.setChecked(false);
                break;
            case 7:
                j2.setChecked(false);
                j3.setChecked(false);
                j5.setChecked(false);
                j7.setChecked(true);
                break;
        }
    }

    private void setUpViews() {

        txtDate = (EditText)findViewById(R.id.editTextDate);
        name = (TextView) findViewById(R.id.editTextName);
        school = (TextView) findViewById(R.id.editTextSchool);
        city = (TextView) findViewById(R.id.editTextCity);
        state = (TextView) findViewById(R.id.editTextState);
        radioJudgesGroup = (RadioGroup)findViewById(R.id.radioGroupMeet);
        j2 = (RadioButton)findViewById(R.id.radio2J);
        j3 = (RadioButton)findViewById(R.id.radio3J);
        j5 = (RadioButton)findViewById(R.id.radio5J);
        j7 = (RadioButton)findViewById(R.id.radio7J);
        btnEnterMeet = (Button) findViewById(R.id.buttonMeet);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.activity_enter_meet, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Async database tasks
    private class WriteNewMeet extends AsyncTask<String, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.fillMeet(nameString, schoolString, cityString, stateString, dateString, judges);
            return null;
        }
    }

}
