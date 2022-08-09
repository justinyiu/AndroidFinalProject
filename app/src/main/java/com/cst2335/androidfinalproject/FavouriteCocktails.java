package com.cst2335.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouriteCocktails extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> drinkName, drinkInstructions, drinkIng1, drinkIng2, drinkIng3;
    String idDrink;
    MyOpenHelper DB;
    MyAdapter adapter;
    boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_cocktails);
        DB = new MyOpenHelper(this);
        drinkName = new ArrayList<>();
        drinkInstructions = new ArrayList<>();
        drinkIng1 = new ArrayList<>();
        drinkIng2 = new ArrayList<>();
        drinkIng3 = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, drinkName, drinkInstructions, drinkIng1, drinkIng2, drinkIng3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

//        Button deleteButton = findViewById(R.id.deleteButton);
//
//
//        deleteButton.setOnClickListener( clk -> {
//            isSaved = DB.queryDrink(idDrink);
//            if (isSaved) {
//                DB.deleteDrink(idDrink);
//            }
//            isSaved = DB.queryDrink(idDrink);
//
//        });

    }

    private void displayData() {
        Cursor cursor = DB.getData();
        if (cursor.getCount()==0)
        {
            Toast.makeText(FavouriteCocktails.this, "No entries exist", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                drinkName.add(cursor.getString(1));
                drinkInstructions.add(cursor.getString(2));
                drinkIng1.add(cursor.getString(3));
                drinkIng2.add(cursor.getString(4));
                drinkIng3.add(cursor.getString(5));
            }
        }
    }
}