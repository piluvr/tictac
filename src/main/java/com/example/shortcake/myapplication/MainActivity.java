package com.example.shortcake.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context = this;
    MediaPlayer c1;
    MediaPlayer c2;
    MediaPlayer r;

    private final int size = 3; // lets have the default size be 3, and then user can change via settings
    private Button[][] buttons = new Button[size][size];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    private boolean soundOn = true;

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

        for (int r = 0; r < size; r++)
        {
            for (int c = 0; c < size; c++) //this is where the magic happens
            {
                String buttonID = "button_" + r + c; //this will be gone
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName()); // this will be gone
                buttons[r][c] = findViewById(resID); //we can add the buttons programmatically rather than finding existing ones
                buttons[r][c].setOnClickListener(this);
            }
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