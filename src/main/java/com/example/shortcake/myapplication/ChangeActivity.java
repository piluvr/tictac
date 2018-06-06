package schmidt.mc.tic_tac_toe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


public class ChangeActivity extends AppCompatActivity {
    private Switch soundSwitch;
    private Switch turnSwitch;
    private Switch computerSwitch;
    private Spinner spinner;
    private String text;
    private Button buttonApply;
    public static int newSize = MainActivity.size;
    public EditText editNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_change);

        soundSwitch = (Switch) findViewById(R.id.switch2);
        turnSwitch = findViewById(R.id.switch1);
        computerSwitch = findViewById(R.id.switch3);
        editNum = findViewById(R.id.edit_num);
        editNum.setText(newSize + "");




        soundSwitch.setChecked(MainActivity.soundOn);
        turnSwitch.setChecked(MainActivity.player1Turn);
        computerSwitch.setChecked(MainActivity.computerOn);




    }




            public void applyChanges(View v)
            {
                MainActivity.setSound(soundSwitch.isChecked());
                MainActivity.setComputer(computerSwitch.isChecked());
                newSize = Integer.parseInt(editNum.getText().toString());
                    MainActivity.setSize(newSize);

                MainActivity.setTurn(turnSwitch.isChecked());
                Toast.makeText(this, (newSize + ""), Toast.LENGTH_SHORT).show();

            }




    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}