package com.rodriguez.scoremydivepremium;

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
import android.widget.ListView;
import android.widget.Toast;

import com.info.sqlite.helper.DiveListDatabase;
import com.info.sqlite.helper.DiverDatabase;

import java.util.ArrayList;


public class SwitchDivers extends ActionBarActivity {

    private ListView myList;
    private int diverId, meetId;
    private String stringId, sendingClass;
    private boolean noList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_divers);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myList = (ListView)findViewById(R.id.list);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            meetId = b.getInt("keyMeet");
            sendingClass = b.getString("sendingClass");
        }

        populateListViewFromDB();
    }

    public void populateListViewFromDB(){
        ArrayList<String> diverInfo;
        GetDiverHistory his = new GetDiverHistory();
        diverInfo = his.doInBackground();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_item, diverInfo);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                stringId = myList.getItemAtPosition(position).toString();
                GetDiverId diveid = new GetDiverId();
                diverId = diveid.doInBackground();

                // list checking
                // here we first see if they have a noList record
                CheckForYesList noListCheck = new CheckForYesList();
                noList = noListCheck.doInBackground();

                // then we will check and see if they have any list records
                CheckDiveListComplete check = new CheckDiveListComplete();
                int isComplete = check.doInBackground();

                // if no list go right to ChooseSummary
                if(noList) {
                    Intent intent = new Intent(getBaseContext(), ChooseSummary.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return;
                }
                // if List go right to EnterDiveList
                if(isComplete == 1) {
                    Intent intent = new Intent(getBaseContext(), EnterDiveList.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return;
                }
                if(isComplete == 2){
                    Intent intent = new Intent(getBaseContext(), ChooseDivesFromList.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return;
                }
                // if none but attached to a meet we send them to the Choose Class
                Intent intent = new Intent(getBaseContext(), Choose.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                intent.putExtras(b);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_switch_divers, menu);
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

    private class GetDiverHistory extends AsyncTask<ArrayList<String>, Object, Object> {
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getDiverHistory(meetId);
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

    private class CheckForYesList extends AsyncTask<Boolean, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return db.checkForNoList(meetId, diverId);
        }
    }

    private class CheckDiveListComplete extends AsyncTask<Integer, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        int check;

        @Override
        protected Integer doInBackground(Integer... params) {
            return check = db.isListFinished(meetId, diverId);
        }
    }
}
