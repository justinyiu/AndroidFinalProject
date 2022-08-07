package com.cst2335.androidfinalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class CocktailActivity extends AppCompatActivity{

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
    Bitmap cocktailPic;
    Toolbar myToolbar;
    DetailFragment dFragment;
    public static final String COCKTAIL_NAME = "NAME";
    public static final String COCKTAIL_PICTURE = "PICTURE";
    public static final String COCKTAIL_INSTRUCTIONS = "INSTRUCTIONS";
    public static final String COCKTAIL_INGREDIENT1 = "INGREDIENT1";
    public static final String COCKTAIL_INGREDIENT2 = "INGREDIENT2";
    public static final String COCKTAIL_INGREDIENT3 = "INGREDIENT3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        searchButton = findViewById(R.id.search_button);
        cocktailText = findViewById(R.id.cocktailSearch);
        myList = findViewById(R.id.listView);

        myToolbar = (Toolbar)findViewById(R.id.my_toolbar);

        myListAdapter = new MyListAdapter();
        myList.setAdapter(myListAdapter);

//**************************************************************************************************

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

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CocktailActivity.this);
                //Message whatWasClicked = messageList.get(position);
                //whatWasClicked.getId()
                alertDialogBuilder.setTitle("Do you want to add this to favourites ？")
                        .setMessage("The selected row is:" + position + " " + "The database id is:" + id)
                        .setNegativeButton("No", (dialog, click1)->{})
                        .setPositiveButton("Yes", (dialog, click2)->{
                            cocktails.remove(position);
                            myListAdapter.notifyDataSetChanged();
                            getSupportFragmentManager().beginTransaction().remove(dFragment).commit();

                           ContentValues cv = new ContentValues();
                           cv.put(MyOpenHelper.COL_NAME, position);


                            myDatabase.delete(MyOpenHelper.TABLE_NAME,MyOpenHelper.COL_ID +" = ?", new String[]{Long.toString(id)});

                        })
                        .create().show();
                return true;
            }
        });

//**************************************************************************************************

        searchButton.setOnClickListener(v -> {
            drinkSearch = cocktailText.getText().toString();
            CocktailQuery cocktailQuery = new CocktailQuery();
            cocktailQuery.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkSearch);
            cocktailText.setText("");
            myListAdapter.notifyDataSetChanged();
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
                    String drink = objectFromArray.getString("strDrink");
                    String picture = objectFromArray.getString("strDrinkThumb");
                    String instructions = objectFromArray.getString("strInstructions");
                    String ingredient1 = objectFromArray.getString("strIngredient1");
                    String ingredient2 = objectFromArray.getString("strIngredient2");
                    String ingredient3 = objectFromArray.getString("strIngredient3");

                    //cocktailList.add(drinksArray);
                    ContentValues newRowValues = new ContentValues();
                    newRowValues.put(MyOpenHelper.COL_NAME, drink);
                    newRowValues.put(MyOpenHelper.COL_PICTURE, picture);
                    newRowValues.put(MyOpenHelper.COL_INSTRUCTIONS, instructions);
                    newRowValues.put(MyOpenHelper.COL_INGREDIENT1, ingredient1);
                    newRowValues.put(MyOpenHelper.COL_INGREDIENT2, ingredient2);
                    newRowValues.put(MyOpenHelper.COL_INGREDIENT3, ingredient3);

                    long newId = myDatabase.insert(MyOpenHelper.TABLE_NAME,null, newRowValues);

                    Cocktail newCocktail = new Cocktail(newId, drink, picture, instructions,
                            ingredient1, ingredient2,ingredient3);

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

        }

        public void onPostExecute(String fromDoInBackground) {
            Log.i(TAG, fromDoInBackground);
        }

    }

//**************************************************************************************************

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return cocktails.size();}

        public Cocktail getItem(int position) { return cocktails.get(position); }

        public long getItemId(int position) { return position; }

        public View getView(int position, View convertView, ViewGroup parent) {

            Cocktail thisRow = getItem(position);
            View newView = getLayoutInflater().inflate(R.layout.activity_cocktail_item, parent, false);
            TextView cocktailName = (TextView)newView.findViewById(R.id.search_result);
            cocktailName.setText(thisRow.getName());

            return newView;

            }
        }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            case R.id.cocktailMenu:
                message = "You clicked item 1";
                break;
            case R.id.cocktailIcon:
                message = "You clicked on the search";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

}




