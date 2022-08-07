package com.cst2335.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
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
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        Intent goToCocktail = new Intent(MainActivity.this, CocktailActivity.class);
        switch(item.getItemId())
        {
            case R.id.home:
            case R.id.homeIcon:
                message = "You're currently on the home page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.search:
                message = "You clicked search, " + "\n" + "sending you to search page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            case R.id.searchIcon:
                message = "You clicked on the search icon, " + "\n " + " sending you to search page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                startActivity(goToCocktail);
                break;
            case R.id.help:
            case R.id.helpIcon:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Instructions")
                        .setMessage("To search the cocktail database click on the cocktail icon or button.")
                        .setPositiveButton("Close", (dialog, click1) -> {})
                        .create().show();
                break;
        }
        return true;
    }

}