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

import com.info.sqlite.helper.MeetDatabase;

import java.util.ArrayList;

public class MeetDelete extends ActionBarActivity {

    private TextView name, school, city, state, date;
	private String nameString;
    private int meetId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_delete);ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name = (TextView)findViewById(R.id.deleteMName);
        school = (TextView)findViewById(R.id.deleteSchool);
        city = (TextView)findViewById(R.id.deleteCity);
        state = (TextView)findViewById(R.id.deleteState);
        date = (TextView)findViewById(R.id.deleteDate);
        
        Bundle b = getIntent().getExtras();
        meetId = b.getInt("key");

        fillEditText();
        addListenerOnButton();
    }
	
	public void fillEditText(){
		ArrayList<String> meetInfo;
        GetMeetInfo info = new GetMeetInfo();
		meetInfo = info.doInBackground();
		
		if(!meetInfo.isEmpty()){
			nameString = meetInfo.get(0);
            String schoolString = meetInfo.get(1);
            String cityString = meetInfo.get(2);
            String stateString = meetInfo.get(3);
            String dateString = meetInfo.get(4);
		
			name.setText(nameString);
			school.setText(schoolString);
			city.setText(cityString);
			state.setText(stateString);
			date.setText(dateString);		
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Meet if is corrupted, please edit or add again",
        			Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getBaseContext(), MeetsDivers.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
	
	public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnDelete = (Button) findViewById(R.id.buttonMeetDelete);
        Button btnCancel = (Button) findViewById(R.id.buttonMeetCancel);
    	btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                DeleteMeetinDB delete = new DeleteMeetinDB();
                delete.doInBackground();

                Toast.makeText(getApplicationContext(),
                        "Meet " + nameString + " has been deleted",
                        Toast.LENGTH_SHORT).show();
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
        //getMenuInflater().inflate(R.menu.activity_meet_delete, menu);
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

    private class DeleteMeetinDB extends AsyncTask<Integer, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Integer... params) {
            db.deleteMeet(meetId);
            return null;
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
}
