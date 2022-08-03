package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

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

public class CocktailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);



        MyHttpRequest req = new MyHttpRequest();
        req.execute("https://www.cocktaildb.com/api/json/v1/1/search.php?s=martini"); // type 1

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