package fi.kaiyu.listology2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



/**
 * This is the main screen of the app which allows the user to navigate to the tasks page
 */
public class MainActivity extends AppCompatActivity {

    /**
     * the main activity page
     * @param savedInstanceState -a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
