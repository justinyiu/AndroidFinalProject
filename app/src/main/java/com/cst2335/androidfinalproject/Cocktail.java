package com.cst2335.androidfinalproject;

import android.database.Cursor;
import java.util.ArrayList;

class Cocktail {
    long id;
    String name;
    String picture;
    String instructions;
    String ingredient1;
    String ingredient2;
    String ingredient3;


    public Cocktail(String name, String picture, String instructions,
                    String ingredient1, String ingredient2, String ingredient3) {
        super();
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.instructions = instructions;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
    }

    public long getId() { return this.id; }
    public String getName() {
        return this.name;
    }
    public String getPicture() { return this.picture; }
    public String getInstructions() {
        return this.instructions;
    }
    public String getIngredient1() {
        return this.ingredient1;
    }
    public String getIngredient2() {
        return this.ingredient2;
    }
    public String getIngredient3() {
        return this.ingredient3;
    }

    public String toString() {
        return (this.name);
    }

    public void printCursor(Cursor c, int version) {
        ArrayList<Cocktail> rowValue = new ArrayList<>();

        int idIndex = c.getColumnIndex(MyOpenHelper.COL_ID);
        int nameIndex = c.getColumnIndex(MyOpenHelper.COL_NAME);
        int picIndex = c.getColumnIndex(MyOpenHelper.COL_PICTURE);
        int insIndex = c.getColumnIndex(MyOpenHelper.COL_INSTRUCTIONS);
        int ing1Index = c.getColumnIndex(MyOpenHelper.COL_INGREDIENT1);
        int ing2Index = c.getColumnIndex(MyOpenHelper.COL_INGREDIENT2);
        int ing3Index = c.getColumnIndex(MyOpenHelper.COL_INGREDIENT3);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            long id = c.getInt(idIndex);
            String name = c.getString(nameIndex);
            String picture = c.getString(picIndex);
            String instructions = c.getString(insIndex);
            String ingredient1 = c.getString(ing1Index);
            String ingredient2 = c.getString(ing2Index);
            String ingredient3 = c.getString(ing3Index);
            rowValue.add(new Cocktail(name, picture, instructions, ingredient1, ingredient2, ingredient3));
            c.moveToNext();
        }

    }
}