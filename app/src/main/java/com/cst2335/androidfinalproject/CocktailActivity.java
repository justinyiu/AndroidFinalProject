package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class CocktailActivity extends AppCompatActivity {


    /**
     * list of variables in the layout
     */
    private ListAdapter myAdapter;
    private ListView theList; //id is theList
    private ArrayList<Cocktail> cocktailList = new ArrayList<>();
    //private MyListAdapter myAdapter;
    private EditText userText;
    private Button search;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase myDatabase;
    private String drinkSearch;

    ArrayList<String> source = new ArrayList<>(Arrays.asList("One", "Two", "Three", "Four"));



    //TODO: make a progress bar
    private ProgressBar cocktailProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        // find the id's
        search = findViewById(R.id.search_button);
        userText = findViewById(R.id.cocktailSearch);
        theList = findViewById(R.id.theList);
        cocktailProgressBar = findViewById(R.id.progressBar);



        cocktailProgressBar.setVisibility(View.VISIBLE);




        myOpenHelper = new MyOpenHelper(this);
        myDatabase = myOpenHelper.getWritableDatabase();
        Cursor history = myDatabase.rawQuery("select * from " + MyOpenHelper.TABLE_NAME + ";", null);


        int idIndex = history.getColumnIndex((MyOpenHelper.COL_ID));
        int picIndex = history.getColumnIndex((MyOpenHelper.COL_PICTURE));
        int instructionsIndex = history.getColumnIndex((MyOpenHelper.COL_INSTRUCTIONS));
        int ingredient1Index = history.getColumnIndex((MyOpenHelper.COL_INGREDIENT1));
        int ingredient2Index = history.getColumnIndex((MyOpenHelper.COL_INGREDIENT2));
        int ingredient3Index = history.getColumnIndex((MyOpenHelper.COL_INGREDIENT3));

        while (history.moveToNext()) {
            long id = history.getInt(idIndex);
            String picture = history.getString(picIndex);
            String instructions = history.getString(instructionsIndex);
            String ingredient1 = history.getString(ingredient1Index);
            String ingredient2 = history.getString(ingredient2Index);
            String ingredient3 = history.getString((ingredient3Index));

        }

        //printCursor(history, 1);
        history.close();



//**************************************************************************************************
        //TODO: add and initialize the database
        // inialize the database
        MyOpenHelper myOpener = new MyOpenHelper(this);
        myDatabase = myOpener.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);


        userText = findViewById(R.id.cocktailSearch);
        //EditText editText2 = findViewById(R.id.editText2);
        search = findViewById(R.id.search_button);




/**
 * When the user clicks the search button, it will get the user's text and execute the search query
 *Then it will launch into the DetailFragment class
 * TODO: Add error handling: make sure the user only enters TEXT
 */

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkSearch = userText.getText().toString();
                //drinkSearch = drinkSearch.replace(" ", "%20");
                MyHttpRequest req = new MyHttpRequest();
                req.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkSearch); // type 1
                cocktailProgressBar.setVisibility(View.VISIBLE);
                userText.setText("");


            }
        });

        //theList.setAdapter(myAdapter = new MyListAdapter());
        /**
         * add the results from the search result to the listView
         */



    }



    /**
     * establishes the HTTP request
     */

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

    public class Cocktail {
        String instructions;
        String ingredients;
        long id;

        public Cocktail(String instructions, String ingredients, long id) {
            super();
            this.instructions = instructions;
            this.ingredients = ingredients;
            this.id = id;
        }

        public String getInstructions() {
            return this.instructions;
        }

        public String getIngredients() {
            return this.ingredients;
        }

        public long getId() { return this.id;}

        public String toString(){
            return("message:" + this.instructions + " isSent:" + this.ingredients + " id:" + this.id);
        }
    }

}