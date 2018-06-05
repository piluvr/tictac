import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context = this;
    MediaPlayer c1;
    MediaPlayer c2;
    MediaPlayer r;


    public static int size = 4; //can change in settings
    private Button[][] buttons = new Button[size][size];
    public static boolean player1Turn = true; //can change in settings

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    public static boolean computerOn = false;

    public static boolean soundOn = true; //can change in settings


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        c1 = MediaPlayer.create(context, R.raw.click1);
        c2 = MediaPlayer.create(context, R.raw.click2);
        r = MediaPlayer.create(context, R.raw.puff);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        LinearLayout layout = (LinearLayout)findViewById(R.id.wrapper);

        for(int i = 0; i < size; i++)
        {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));


            for(int j = 0; j < size; j++)
            {
                Button btn = new Button(this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                // btn.setWidth(0);
                // btn.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
                String id = i + "" + j;
                btn.setId(Integer.parseInt(id));
                buttons[i][j] = btn;
                buttons[i][j].setOnClickListener(this);
                row.addView(btn);

            }
            layout.addView(row);
        }


        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                if (soundOn)
                {
                    try
                    {
                        if (r.isPlaying())
                        {
                            r.stop();
                            r.release();
                            r = MediaPlayer.create(context, R.raw.puff);
                        }
                        r.start();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button buttonSettings = findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener((new View.OnClickListener() {

            Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
            @Override
            public void onClick(View v) {
                {

                    startActivity(intent);
                }


            }
        }));

    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;

        }

        if (player1Turn) {
            ((Button) v).setText("X");
            if(soundOn)
            {
                try
                {
                    if (c1.isPlaying())
                    {
                        c1.stop();
                        c1.release();
                        c1 = MediaPlayer.create(context, R.raw.click1);
                    }
                    c1.start();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        } else {
            ((Button) v).setText("O");
            if(soundOn)
            {
                try
                {
                    if (c2.isPlaying())
                    {
                        c2.stop();
                        c2.release();
                        c2 = MediaPlayer.create(context, R.raw.click2);
                    }
                    c2.start();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        roundCount++;

        if (checkForWin())
        {
            if (player1Turn)
            {
                player1Wins();
            }
            else
            {
                player2Wins();
            }
        }
        else if (roundCount == (size * size))
        {
            draw();
        }
        else
        {
            player1Turn = !player1Turn;
        }


    }

    public boolean checkForWin()
    {
        String[][] field = new String[size][size];

        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                field[r][c] = buttons[r][c].getText().toString();
            }
        }

        //CHECK FOR WINS
        for(int r = 0; r < size; r++)   //horizontal check works
        {
            int c;
            for(c = 1; c < size; c++)
            {
                if((field[r][c-1].equals("")) || !(field[r][0].equals(field[r][size - c])))
                {
                    break;
                }
            }

            if(c == size)
                return true;
        }

        for(int r = 0; r < size; r++)   //vertical check works
        {
            int c;
            for(c = 1; c < size; c++)
            {
                if((field[c - 1][r].equals("")) || !(field[0][r].equals(field[size - c][r])))
                {
                    break;
                }
            }

            if(c == size)
                return true;
        }

        for(int r = 0; r < size; r++)   //diagonal check 1 works
        {
            if((field[r][(size - 1) - r].equals("")) || (!(field[0][size - 1].equals(field[(size - 1) - r][r]))))
            {
                break;
            }

            if(r == size - 1)
            {
                return true;
            }
        }

        for(int r = 0; r < size; r++) //diagonal check 2 works
        {
            if((field[r][r].equals("")) || (!(field[0][0].equals(field[r][r]))))
            {
                break;
            }

            if(r == size - 1)
            {
                return true;
            }

        }

        return false;

    }

    public void player1Wins()
    {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show(); //we could do a text view rather than toast
        updatePointsText();
        resetBoard();
    }

    public void player2Wins()
    {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    public void draw()
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    public static void setSize(int input){
        MainActivity.size = input;
    }
    public static void setComputer(boolean input) {
    MainActivity.computerOn =  input;
    }
    public static void setSound(boolean input){
        MainActivity.soundOn= input;
    }
    public static void setTurn(boolean input){
        MainActivity.player1Turn= input;
    }

    public void updatePointsText()
    {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    public void resetBoard()
    {
        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++)
            {
                buttons[r][c].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
}
