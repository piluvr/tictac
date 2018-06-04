package schmidt.mc.tic_tac_toe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class ChangeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_change);
    }
    Switch soundSwitch = (Switch) findViewById(R.id.switch2);
    Switch turnSwitch = (Switch) findViewById(R.id.switch1);
    Switch computerSwitch = (Switch) findViewById(R.id.switch3);
    public void changeTurn(View view) {
     MainActivity.player1Turn =turnSwitch.isChecked();
    }
    public void toggleSound(View view) {
        MainActivity.soundOn = soundSwitch.isChecked();
    }
    public void toggleComputer(View view){
        MainActivity.computerOn = computerSwitch.isChecked();
    }
    }



