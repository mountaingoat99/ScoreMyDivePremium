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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.info.sqlite.helper.DiverDatabase;
import com.info.sqlite.helper.JudgeScoreDatabase;
import com.info.sqlite.helper.MeetDatabase;


public class Home extends ActionBarActivity {

    private Button btnQuick, btnDetailed, btnMeetDivers, btnReports;
    private boolean diverCheck = false, meetCheck = false;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btnQuick = (Button)findViewById(R.id.buttonQuickScore);
        btnDetailed = (Button)findViewById(R.id.buttonDetailScore);
        btnMeetDivers = (Button)findViewById(R.id.buttonMeetsDivers);
        btnReports = (Button)findViewById(R.id.buttonReports);

        addListenerOnButton();
    }

    // do not allow any back presses on the Welcome screen to other activities
    // just exit the app
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void addListenerOnButton(){
        btnQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuickScore.class);
                startActivity(intent);
            }
        });

        btnDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckDiver diver = new CheckDiver();
                diverCheck = diver.doInBackground();
                CheckMeet meet = new CheckMeet();
                meetCheck = meet.doInBackground();

                if(diverCheck && meetCheck) {
                    Intent intent = new Intent(context, Choose.class);
                    startActivity(intent);
                } else {
                    if (!diverCheck && meetCheck) {
                        Toast.makeText(getApplicationContext(),
                                "Please add a diver and a meet",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!diverCheck){
                        Toast.makeText(getApplicationContext(),
                                "Please add a diver before starting a meet",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(getApplicationContext(),
                            "There have been no meets added yet",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMeetDivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MeetsDivers.class);
                startActivity(intent);
            }
        });

        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check db on a separate thread
                ValidMeet val = new ValidMeet();
                boolean validMeet = val.doInBackground();
                if (validMeet) {
                    Intent intent = new Intent(context, Reports.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "No scores have been entered, so no reports can be generated",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
                Intent intent3 = new Intent(context, HowTo.class);
                startActivity(intent3);
                break;
            case R.id.menu_about:
                Intent intent2 = new Intent(context, About.class);
                startActivity(intent2);
                break;
            case R.id.menu_rankings:
                Intent intent1 = new Intent(context, Rankings.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ValidMeet extends AsyncTask<Boolean, Object, Object> {
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        Boolean validmeet;
        @Override
        protected Boolean doInBackground(Boolean... params) {
            return validmeet = db.checkJudgesScores();
        }
    }

    private class CheckDiver extends AsyncTask<Boolean, Object, Object> {
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        Boolean diver;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return diver = db.checkDiver();
        }
    }

    private class CheckMeet extends AsyncTask<Boolean, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        Boolean meet;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return meet = db.checkMeet();
        }
    }
}
