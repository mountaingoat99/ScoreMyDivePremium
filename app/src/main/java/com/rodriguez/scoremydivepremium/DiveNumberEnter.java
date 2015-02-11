package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.info.controls.DiveNumberEnterController;
import com.info.sqlite.helper.AllSpringboardDatabase;
import com.info.sqlite.helper.DiveTotalDatabase;

import org.apache.http.cookie.CookieIdentityComparator;


public class DiveNumberEnter extends ActionBarActivity {

    private EditText editDiveNum, editDivePos;
    private TextView diveddView;
    private Button btnJudgeScore, btnTotalScore;
    private int diverId, meetId, diveTotal, diveType, diveNumber, divePosition;
    private double boardType, multiplier = 0.0;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_number_enter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        editDiveNum = (EditText)findViewById(R.id.editDiveNum);
        editDivePos = (EditText)findViewById(R.id.editDivePos);
        diveddView = (TextView)findViewById(R.id.showDD);
        btnJudgeScore = (Button)findViewById(R.id.buttonJudgeScore);
        btnTotalScore = (Button)findViewById(R.id.buttonTotalScore);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            diverId = b.getInt("keyDiver");
            meetId = b.getInt("keyMeet");
            diveNumber = b.getInt("diveNumber");
            boardType = b.getDouble("boardType");
        }

        diveddView.setText("Dive DD: ");
        getDiveTotals();
        addListenerOnButton();
        doATextWatcher();
    }

    private void doATextWatcher() {

        editDiveNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editDiveNum.getText().length() > 2 && editDivePos.getText().length() == 1) {
                    DiveNumberEnterController num = new DiveNumberEnterController();
                    int id = Integer.parseInt(editDiveNum.getText().toString().trim());
                    int pos = ConvertDivePosition(editDivePos.getText().toString().trim());
                    multiplier = num.GetMultiplier(id, pos, boardType, context);
                    doesDiveExist();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editDivePos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editDiveNum.getText().length() > 2 && editDivePos.getText().length() == 1) {
                    DiveNumberEnterController num = new DiveNumberEnterController();
                    int id = Integer.parseInt(editDiveNum.getText().toString().trim());
                    int pos = ConvertDivePosition(editDivePos.getText().toString().trim());
                    multiplier = num.GetMultiplier(id, pos, boardType, context);
                    doesDiveExist();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void doesDiveExist() {

        if (multiplier > 0.0){
            diveddView.setText(String.valueOf(multiplier));
            diveddView.setText("Dive DD: " + String.valueOf(multiplier));
            btnJudgeScore.setEnabled(true);
            btnTotalScore.setEnabled(true);
        } else {
            diveddView.setText("Dive does not exist");
            btnTotalScore.setEnabled(false);
            btnJudgeScore.setEnabled(false);
        }
    }

    private int ConvertDivePosition(String pos) {
        int position = 0;
        if (pos.equals("A") || pos.equals("a")){
            position = 1;
        }
        if (pos.equals("B") || pos.equals("b")){
            position = 2;
        }
        if (pos.equals("C") || pos.equals("c")){
            position = 3;
        }
        if (pos.equals("D") || pos.equals("d")){
            position = 4;
        }

        return position;
    }

    private void addListenerOnButton() {

        btnJudgeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (multiplier != 0.0) {

                    DiveNumberEnterController db = new DiveNumberEnterController();
                    String diveTypeName = db.GetDiveType(Integer.parseInt(editDiveNum.getText().toString().trim()), boardType);
                    String diveName = db.GetDiveName(Integer.parseInt(editDiveNum.getText().toString().trim()), boardType, context);
                    String diveNameToSend = diveTypeName + " - " + diveName;

                    Intent intent = new Intent(context, Dives.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveType", diveType);
                    b.putDouble("boardType", boardType);
                    b.putDouble("multiplier", multiplier);
                    b.putString("diveName", diveNameToSend);
                    //TODO add position and diveName String
                    intent.putExtras(b);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTotalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (multiplier != 0.0) {

                    DiveNumberEnterController db = new DiveNumberEnterController();
                    String diveTypeName = db.GetDiveType(Integer.parseInt(editDiveNum.getText().toString().trim()), boardType);
                    String diveName = db.GetDiveName(Integer.parseInt(editDiveNum.getText().toString().trim()), boardType, context);
                    String diveNameToSend = diveTypeName + " - " + diveName;

                    Intent intent = new Intent(context, EnterFinalDiveScore.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveType", diveType);
                    b.putDouble("boardType", boardType);
                    b.putDouble("multiplier", multiplier);
                    b.putString("diveName", diveNameToSend);
                    //TODO add position and diveName String
                    intent.putExtras(b);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getDiveTotals(){
        SearchDiveTotals total = new SearchDiveTotals();
        diveTotal = total.doInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dive_number_enter, menu);
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

    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object> {
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }
}
