package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.info.sqlite.helper.DiverDatabase;

import java.util.ArrayList;

public class DiverDelete extends ActionBarActivity {

    private TextView name, age, grade, school;
	private String nameString;
    private int diverId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_delete);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name = (TextView)findViewById(R.id.deleteName);
        age = (TextView)findViewById(R.id.deleteAge);
        grade = (TextView)findViewById(R.id.deleteGrade);
        school = (TextView)findViewById(R.id.deleteSchool);
        
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");

        fillEditText();
        addListenerOnButton();
    }
	
	public void fillEditText(){
		ArrayList<String> diverInfo;
        GetDiverInfo info = new GetDiverInfo();
		diverInfo = info.doInBackground();
						
		if(!diverInfo.isEmpty()){
			nameString = diverInfo.get(0);
            String ageString = diverInfo.get(1);
            String gradeString = diverInfo.get(2);
            String schoolString = diverInfo.get(3);
		
			name.setText(nameString);		
			age.setText(ageString);		
			grade.setText(gradeString);		
			school.setText(schoolString);	
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Diver if is corrupted, please edit or add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnDelete = (Button) findViewById(R.id.buttonDiverDeleteE);
        Button btnCancel = (Button) findViewById(R.id.buttonDiverCancel);
    	btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DeleteDiverinDB delete = new DeleteDiverinDB();
                delete.doInBackground();

                Toast.makeText(getApplicationContext(),
                        "Diver " + nameString + " has been deleted",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MeetsDivers.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.activity_diver_delete, menu);
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


    private class DeleteDiverinDB extends AsyncTask<Integer, Object, Object> {
        DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Integer... params) {
            db.deleteDiver(diverId);
            return null;
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
}
