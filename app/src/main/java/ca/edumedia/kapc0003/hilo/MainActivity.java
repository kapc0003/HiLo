/**
 * Purpose/Description of your app
 *  Android App: To implement, test and deploy a guessing game that allows users to guess a number from 1 to 1000
 *
 * @author Alison Kapcala (kapc0003@algonquinlive.com)
 * @version 1.0
 */

package ca.edumedia.kapc0003.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    public int tries = 0; //tries: set to 0, users can begin without hitting reset when launching the app

    public int theNumber = (int) (Math.random() * (1000 - 1)) + 1; //theNumber: generates a number between the range of 1 and 1000

    private EditText userGuessText;

    //Dialog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userGuessText = (EditText) findViewById(R.id.GuessText); //Getting input from GuessText field
        final TextView triesText = (TextView) findViewById(R.id.triesText); //Update triesText after every attempt

        //Initiating the Guess and Reset buttons
        Button GuessButton = (Button) findViewById(R.id.GuessButton);
        Button ResetButton = (Button) findViewById(R.id.ResetButton);

        triesText.setText("Tries: " + tries + "\\10"); //Creates triesText when the app launches

        //Guess Button - Game Play
        GuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userGuess = userGuessText.getText().toString();

                //Check if the user input is empty, equal to 0 or more than 1000
                if (userGuess.isEmpty() || Integer.parseInt(userGuess) > 1000 || Integer.parseInt(userGuess) == 0) {
                    userGuessText.setError("Please enter a number between 1-1000");
                    userGuessText.requestFocus();
                    return;
                }

                if (tries < 10) { //Check if the user has less than 10 tries

                    if (theNumber == Integer.parseInt(userGuess)) { //Check if the user is equal to theNumber

                        if (tries <= 5) { //User has 5 or less tries
                            Toast.makeText(getApplicationContext(), "Superior win!", Toast.LENGTH_LONG).show();
                            return;

                        } else { //User has more than 5 tries (6-10)
                            Toast.makeText(getApplicationContext(), "Excellent win!", Toast.LENGTH_LONG).show();
                            return;
                        }

                    } else {
                        if (theNumber > Integer.parseInt(userGuess)) { //Input number is less than theNumber
                            Toast.makeText(getApplicationContext(), "Your guess is too low!", Toast.LENGTH_SHORT).show();

                        } else { //Input number is higher than theNumber
                            Toast.makeText(getApplicationContext(), "Your guess is too high!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    tries++; //Adds an attempt when the user fails at guessing theNumber
                    triesText.setText("Tries: " + tries + "\\10"); //Updates the triesText

                } else { //User runs out of tries, must hit the Reset Button to continue
                    Toast.makeText(getApplicationContext(), "Please Reset!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Reset Button - onClick: reset game, randomly pick a new number (theNumber), re-set the number of guesses back to 0
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theNumber = (int) (Math.random() * (1000 - 1)) + 1;
                tries = 0;
                triesText.setText("Tries: " + tries + "\\10");
                userGuessText.setText("");
            }
        });

        //Reset Button - onLongClick: Toast message (Long) to show theNumber
        ResetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "The number is " + theNumber + "!", Toast.LENGTH_LONG).show();
                userGuessText.setText("");
                return false;
            }
        });
    }
}