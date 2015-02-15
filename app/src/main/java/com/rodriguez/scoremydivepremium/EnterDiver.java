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
import android.widget.TextView;
import android.widget.Toast;

import com.info.sqlite.helper.DiverDatabase;

public class EnterDiver extends ActionBarActivity {
	
	Button btnEnterDiver;
	TextView name, age, grade, school;
	String nameString, ageString, gradeString, schoolString;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_diver);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        addListenerOnButton();
    }

	public void addListenerOnButton()
    {
    	final Context context = this;
    	btnEnterDiver = (Button)findViewById(R.id.buttonDiverEnter);
    	btnEnterDiver.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View arg0)
    		{
    			// write to database
    			// gets the data from the text boxes and converts
    	        name = (TextView)findViewById(R.id.editTextNameE);
    	        nameString = name.getText().toString().trim();
    	        age = (TextView)findViewById(R.id.editTextAgeN);
    	        ageString = age.getText().toString().trim();
    	        grade = (TextView)findViewById(R.id.editTextGradeN);
    	        gradeString = grade.getText().toString().trim();
    	        school = (TextView)findViewById(R.id.editTextSchoolN);
    	        schoolString = school.getText().toString().trim();
    	        if(nameString.isEmpty() || ageString.isEmpty()
    	        		|| gradeString.isEmpty() || schoolString.isEmpty())
    	        {
    	        	Toast.makeText(getApplicationContext(),
    	        			"Please make an entry in all fields", Toast.LENGTH_LONG).show();
    	        }
    	        else{
                    // calls a separate thread to write to the db
                    WriteNewDiver newdiver = new WriteNewDiver();
                    newdiver.doInBackground();

    	        	Toast.makeText(getApplicationContext(),
    	        			"Diver has been saved", Toast.LENGTH_SHORT).show();
    	        	Intent intent = new Intent(context, MeetsDivers.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(intent);
    	        } 
    		}
    	});
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.activity_enter_diver, menu);
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

    // Async database tasks
    private class WriteNewDiver extends AsyncTask<String, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.fillDiver(nameString, ageString, gradeString, schoolString);
            return null;
        }
    }

}
