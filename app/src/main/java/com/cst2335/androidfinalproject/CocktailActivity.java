package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.cst2335.androidfinalproject.databinding.ActivityMainBinding;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;

public class CocktailActivity extends AppCompatActivity {


    private EditText userText;
    private Button search;

    /**
     * database variables
     */
    private MyOpenHelper myOpener;
    private SQLiteDatabase myDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        //TODO: add and initialize the database
        // inialize the database
        MyOpenHelper myOpener = new MyOpenHelper(this);


        userText = findViewById(R.id.cocktailSearch);
        //EditText editText2 = findViewById(R.id.editText2);


        search = findViewById(R.id.search_button);



        MyHttpRequest req = new MyHttpRequest();
        String drink = userText.getText().toString();
        req.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drink);

/**
 * When the user clicks the search button, it will get the user's text and execute the search query
 *
 */

//TODO: Add error handling
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // type 1

                Intent goToFragment = new Intent(CocktailActivity.this, DetailFragment.class );
                startActivity(goToFragment);
            }
        });
    }




    private class MyHttpRequest extends AsyncTask <String, Integer, String> {

        public String doInBackground(String ... args) {

            try {
                // create a URL object of what server to contact
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:
                //build the string response
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String result = sb.toString(); // result in the whole string

                //convert String to JSON
                JSONObject jsObj = new JSONObject(result);
                JSONArray drinksArray = jsObj.getJSONArray("drinks");
                for (int i = 0; i < drinksArray.length(); i++) {
                    JSONObject objectFromArray = drinksArray.getJSONObject(i);

                    String picture = objectFromArray.getString("strDrinkThumb");

                    String instructions = objectFromArray.getString("strInstructions");

                    String ingredient1 = objectFromArray.getString("strIngredient1");

                    String ingredient2 = objectFromArray.getString("strIngredient2");

                    String ingredient3 = objectFromArray.getString("strIngredient3");
                    int j=0; j++;
                }

            }
            catch (Exception e) {

            }
            return "done";
        }

        //type 2
        public void onProgressUpdate(Integer ... args) {

        }

        //type 3
        public void onPostExecute(String fromDoInBackground) {
            Log.i("HTTP", fromDoInBackground);
        }
    }

}