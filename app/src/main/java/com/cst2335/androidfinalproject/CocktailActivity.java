

package com.cst2335.androidfinalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class CocktailActivity extends AppCompatActivity {

    /**
     * list of variables in the layout
     */
    MyListAdapter myListAdapter;
    ListView myList;
    ArrayList<Cocktail> cocktails = new ArrayList<>();
    EditText cocktailText;
    Button searchButton;
    MyOpenHelper myOpenHelper;
    SQLiteDatabase myDatabase;
    String drinkSearch;
    Toolbar myToolbar;
    ProgressBar progressBar;
    Bitmap cocktailPic;
    ImageView cocktailImage;
    DetailFragment dFragment;
    public static final String COCKTAIL_NAME = "NAME";
    public static final String COCKTAIL_PICTURE = "PICTURE";
    public static final String COCKTAIL_INSTRUCTIONS = "INSTRUCTIONS";
    public static final String COCKTAIL_INGREDIENT1 = "INGREDIENT1";
    public static final String COCKTAIL_INGREDIENT2 = "INGREDIENT2";
    public static final String COCKTAIL_INGREDIENT3 = "INGREDIENT3";

    /**
     * Instantiates functionality of the CocktailActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        searchButton = findViewById(R.id.search_button);
        cocktailText = findViewById(R.id.cocktailSearch);
        myList = findViewById(R.id.listView);
        myToolbar = findViewById(R.id.my_toolbarCocktail);
        cocktailImage = findViewById(R.id.image_result);

        progressBar = findViewById(R.id.progressBar);
        setSupportActionBar(myToolbar);
        myListAdapter = new MyListAdapter();
        myList.setAdapter(myListAdapter);

//**************************************************************************************************
        /**
         * This onItemClickListener allows the user to select a cocktail from the list
         * related to the keyword the typed in the search menu. When clicked
         * by the user a fragment is opened which shows further details of the cocktail
         * such as instructions to make it, a picture of the cocktail, and the first
         * three ingredients needed to make the cocktail.
         */
        myList.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            dataToPass.putString(COCKTAIL_NAME, cocktails.get(position).name);
            dataToPass.putString(COCKTAIL_PICTURE, cocktails.get(position).picture);
            dataToPass.putString(COCKTAIL_INSTRUCTIONS, cocktails.get(position).instructions);
            dataToPass.putString(COCKTAIL_INGREDIENT1, cocktails.get(position).ingredient1);
            dataToPass.putString(COCKTAIL_INGREDIENT2, cocktails.get(position).ingredient2);
            dataToPass.putString(COCKTAIL_INGREDIENT3, cocktails.get(position).ingredient3);

            Intent nextActivity = new Intent(CocktailActivity.this, EmptyActivity.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity);

        });

//**************************************************************************************************
        /**
         * This onClickListener waits for the user to type a keyword related to a cocktail
         * and once the click the search button the server will execute a query related to the
         * word that they type in. For example if apple was typed the server will gather the
         * information of all cocktails that contain the word apple within them. After all
         * the information is retrieved a listView will be populated with the names of
         * all the cocktails. A snackbar will also show up.
         */
        searchButton.setOnClickListener(v -> {
            drinkSearch = cocktailText.getText().toString();
            CocktailQuery cocktailQuery = new CocktailQuery();
            cocktailQuery.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkSearch);
            cocktailText.setText("");
            myListAdapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(searchButton, "Showing results related to " +
                    drinkSearch, Snackbar.LENGTH_SHORT);
            snackbar.show();
        });

//**************************************************************************************************

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

        history.close();


    }


//**************************************************************************************************

    /**
     * Queries the database to retrieve information based on keywords
     * related to cocktails within the database.
     */
    public class CocktailQuery extends AsyncTask<String, Integer, String> {

        public String doInBackground(String... args) {

            //clears the search
            cocktails.clear();
            try {
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String result = sb.toString();
                JSONObject jsObj = new JSONObject(result);
                JSONArray drinksArray = jsObj.getJSONArray("drinks");


                for (int i = 0; i < drinksArray.length(); i++) {
                    JSONObject objectFromArray = drinksArray.getJSONObject(i);
                    String name = objectFromArray.getString("strDrink");
                    String picture = objectFromArray.getString("strDrinkThumb");
                    String instructions = objectFromArray.getString("strInstructions");
                    String ingredient1 = objectFromArray.getString("strIngredient1");
                    String ingredient2 = objectFromArray.getString("strIngredient2");
                    String ingredient3 = objectFromArray.getString("strIngredient3");

                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(MyOpenHelper.COL_NAME, name);
                    newRowValues.put(MyOpenHelper.COL_PICTURE, picture);

                    newRowValues.put(MyOpenHelper.COL_INSTRUCTIONS, instructions);
                    newRowValues.put(MyOpenHelper.COL_INGREDIENT1, ingredient1);
                    newRowValues.put(MyOpenHelper.COL_INGREDIENT2, ingredient2);
                    newRowValues.put(MyOpenHelper.COL_INGREDIENT3, ingredient3);

                    //long newId = myDatabase.insert(MyOpenHelper.TABLE_NAME,null, newRowValues);

                    Cocktail newCocktail = new Cocktail(name, picture, instructions,
                            ingredient1, ingredient2, ingredient3);

                    cocktails.add(newCocktail);

                    int j = 0;
                    j++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "done";
        }

        public void onProgressUpdate(Integer... args) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);

        }

        public void onPostExecute(String fromDoInBackground) {
            progressBar.setVisibility(View.VISIBLE);
            Log.i(TAG, fromDoInBackground);
        }

    }

//**************************************************************************************************

    /**
     * Allows the listView to be inflated with the results
     * of the query to the cocktail database.
     */
    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return cocktails.size();
        }

        public Cocktail getItem(int position) {
            return cocktails.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View newView = getLayoutInflater().inflate(R.layout.activity_cocktail_item, parent, false);
            TextView cocktailName = newView.findViewById(R.id.search_result);
            cocktailName.setText(cocktails.get(position).name);

            return newView;

        }
    }

    /**
     * Allows functionality within the toolbar.
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cocktail, menu);

        return true;

    }

    /**
     * Gives specific tasks to each of the elements contained within
     * the toolbar.
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        Intent goToHome = new Intent(CocktailActivity.this, MainActivity.class);

        switch (item.getItemId()) {
            case R.id.home:
                message = "You click home, " + "\n" + "sending you to home page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                startActivity(goToHome);
                break;
            case R.id.homeIcon:
                message = "You clicked on the home icon, " + "\n " + " sending you to the home page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                startActivity(goToHome);
                break;
            case R.id.search:
            case R.id.searchIcon:
                message = "You're currently searching for cocktails";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.help:
            case R.id.helpIcon:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CocktailActivity.this);
                alertDialogBuilder.setTitle("Instructions")
                        .setMessage("Enter a word associated with a cocktail you would like to search." + "\n" +
                                "After you have entered the word click the 'SEARCH' button and the results will display" + "\n" +
                                "If you would like to view the image, instructions and ingredients of the drink" +
                                "click on one of the items from the list" + "\n\n" +
                                "For example entering the word apple will show all cocktails with apple in the name")
                        .setPositiveButton("Close", (dialog, click1) -> {
                        })
                        .create().show();
                break;
        }
        return true;
    }

}



