package schmidt.mc.tic_tac_toe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;


public class ChangeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_change);
        SharedPreferences.Editor editor = MainActivity.sharedpreferences.edit();
        Switch soundSwitch = findViewById(R.id.switch2);
        Switch turnSwitch =  findViewById(R.id.switch1);
        Switch computerSwitch =  findViewById(R.id.switch3);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setSelection(0);
        String text = spinner.getSelectedItem().toString();
        text = text.substring(0,1);
        prefs.edit().putBoolean("locked", true).commit();
        MainActivity.soundOn = soundSwitch.isChecked();
        MainActivity.computerOn = computerSwitch.isChecked();
        MainActivity.size = Integer.parseInt(text);

    }

}



