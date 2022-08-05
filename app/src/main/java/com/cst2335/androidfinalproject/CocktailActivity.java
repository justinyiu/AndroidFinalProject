package com.cst2335.androidfinalproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CocktailActivity extends AppCompatActivity{

    /**
     * list of variables in the layout
     */
    MyListAdapter myListAdapter;
    ListView myList;
    ArrayList<Cocktail> cocktailList = new ArrayList<>();
    EditText cocktailText;
    Button searchButton;
    MyOpenHelper myOpenHelper;
    SQLiteDatabase myDatabase;
    String drinkSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        searchButton = findViewById(R.id.search_button);
        cocktailText = findViewById(R.id.cocktailSearch);
        myList = findViewById(R.id.listView);
        myListAdapter = new MyListAdapter();
        myList.setAdapter(myListAdapter);

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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkSearch = cocktailText.getText().toString();
                CocktailQuery cocktailQuery = new CocktailQuery();
                cocktailQuery.execute("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkSearch);
                cocktailText.setText("");
                Log.i(TAG, "Name of drink: " + drinkSearch);
            }
        });
    }
//**************************************************************************************************


//**************************************************************************************************

    public class CocktailQuery extends AsyncTask<String, Integer, String> {

        public String doInBackground(String... args) {

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

        public int getCount() {
            return cocktailList.size();
        }

        public Cocktail getItem(int position) {
            return cocktailList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Cocktail cocktail = getItem(position);
            View newView;
            TextView textView;
            LayoutInflater inflater = getLayoutInflater();

            newView = inflater.inflate(R.layout.activity_cocktail_item, parent, false);
            textView = newView.findViewById(R.id.search_result);

            textView.setText(cocktailList.get(position).getName());
            return newView;
            }
        }
    }

//**************************************************************************************************

     class Cocktail {
        String instructions;
        String ingredients;
        String name;
        long id;

        public Cocktail(String instructions, String ingredients, long id, String name) {
            super();
            this.instructions = instructions;
            this.ingredients = ingredients;
            this.name = name;
            this.id = id;
        }

        public String getInstructions() {
            return this.instructions;
        }

        public String getIngredients() {
            return this.ingredients;
        }

        public long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return ("instructions:" + this.instructions + " ingredients:" + this.ingredients + " id:" + this.id + "name:" + this.name);
        }

    public void printCursor(Cursor c, int version) {
        ArrayList<Cocktail> rowValue = new ArrayList<>();

        int nameIndex = c.getColumnIndex(MyOpenHelper.COL_NAME);
        int idIndex = c.getColumnIndex(MyOpenHelper.COL_ID);
        int insIndex = c.getColumnIndex(MyOpenHelper.COL_INSTRUCTIONS);
        int ing1Index = c.getColumnIndex(MyOpenHelper.COL_INGREDIENT1);
        int ing2Index = c.getColumnIndex(MyOpenHelper.COL_INGREDIENT2);
        int ing3Index = c.getColumnIndex(MyOpenHelper.COL_INGREDIENT3);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            long id = c.getInt(idIndex);
            String name = c.getString(nameIndex);
            String instructions = c.getString(insIndex);
            String ingredients1 = c.getString(ing1Index);
            String ingredients2 = c.getString(ing2Index);
            String ingredients3 = c.getString(ing3Index);
            rowValue.add(new Cocktail(instructions, ingredients1, id, name));
            c.moveToNext();
        }

    }
}

//**************************************************************************************************

