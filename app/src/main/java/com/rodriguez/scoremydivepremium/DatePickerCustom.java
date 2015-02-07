package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;

public class DatePickerCustom extends ActionBarActivity {

    private DatePicker datePicker;
    private Button btnDone;
    private int judges, meetId;
    private String nameString, schoolString, cityString, stateString, dateString;
    private boolean fromMeetEnter;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_custom);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        datePicker = (DatePicker)findViewById(R.id.date_picker);
        btnDone = (Button)findViewById(R.id.buttonDate);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            nameString = b.getString("meetNameKey");
            schoolString = b.getString("schoolKey");
            cityString = b.getString("cityKey");
            stateString = b.getString("stateKey");
            judges = b.getInt("judgeKey");
            dateString = b.getString("dateKey");
            meetId = b.getInt("key");
            fromMeetEnter = b.getBoolean("meetEnterKey");
        }

        addListenerOnButton();
    }

    private void addListenerOnButton() {

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getTheDate();
                Bundle b = new Bundle();
                b.putString("meetNameKey", nameString);
                b.putString("schoolKey", schoolString);
                b.putString("cityKey", cityString);
                b.putString("stateKey", stateString);
                b.putInt("judgeKey", judges);
                b.putString("dateKey", dateString);
                b.putInt("key", meetId);

                if (fromMeetEnter) {
                    Intent intent = new Intent(context, EnterMeet.class);
                    intent.putExtras(b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, MeetEdit.class);
                    intent.putExtras(b);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private void getTheDate() {

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        dateString = (month + 1) + " - " + day + " - " + year;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_date_picker_custom, menu);
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
}
