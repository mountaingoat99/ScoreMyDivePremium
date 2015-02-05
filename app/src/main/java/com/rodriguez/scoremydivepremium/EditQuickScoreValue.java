package com.rodriguez.scoremydivepremium;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.info.sqlite.helper.QuickScoreDatabase;

public class EditQuickScoreValue extends ActionBarActivity {

    private TextView header;
    private View view1, view2;
    private EditText sheetName, score;
    private Button btnUpdate, btnReturn;
    private int sheetId, diveNumber;
    private String updateValue, Name = "";
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quick_score_value);ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnReturn = (Button)findViewById(R.id.buttonReturn);
        header = (TextView)findViewById(R.id.Header);
        sheetName = (EditText)findViewById(R.id.sheetName);
        score = (EditText)findViewById(R.id.score);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);

        sheetName.requestFocus();
        score.requestFocus();

        Bundle b = getIntent().getExtras();
        if (b != null){
            sheetId = b.getInt("keySheet");
            diveNumber = b.getInt("diveNumber");
            Name = b.getString("sheetName");
        }

        setEditValue();
        addListenerOnButton();
    }

    private void setEditValue(){

        if(diveNumber == 0){
            header.setText("Update Name");
            sheetName.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            sheetName.setHint(Name);
            sheetName.setHintTextColor(getResources().getColor(R.color.secondary_text));
            score.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.INVISIBLE);
        }else{
            header.setText("Dive " + diveNumber);
            sheetName.setVisibility(View.INVISIBLE);
            view1.setVisibility(View.INVISIBLE);
            score.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            score.setHint(Name);
            score.setHintTextColor(getResources().getColor(R.color.secondary_text));
        }
    }

    private void addListenerOnButton(){


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diveNumber == 0){
                   updateValue = sheetName.getText().toString().trim();
                   if (updateValue.equals("")){
                       updateValue = Name.trim();
                   }
                }else{
                    updateValue = score.getText().toString().trim();
                    if(updateValue.equals("")){
                        updateValue = Name.trim();
                    }
                }
                QuickScoreDatabase db = new QuickScoreDatabase(getApplicationContext());
                db.UpdateAQuickScore(sheetId, diveNumber, updateValue );
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                Intent intent = new Intent(context, QuickScore.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                Intent intent = new Intent(context, QuickScore.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_edit_quick_score_value, menu);
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
