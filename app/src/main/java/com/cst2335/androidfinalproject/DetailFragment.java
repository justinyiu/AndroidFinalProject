package com.cst2335.androidfinalproject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    boolean isSaved;
    MyOpenHelper DB;
    String idDrink;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Bundle dataFromActivity;
    private String drinkName;
    private String thumbnail;
    private String instructions;
    private String ingredient1;
    private String ingredient2;
    private String ingredient3;
    private AppCompatActivity parentActivity;

    /**
     * Public constructor is empty
     */
    public DetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */

    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * onCreateView receives data from the CocktailActivity to populate
     * the fragment which contains the picture, instructions and ingredients
     * of a seclected cocktail.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        //drinkName = dataFromActivity.getString(CocktailActivity.ITEM_SELECTED);

        MyOpenHelper db = new MyOpenHelper(getActivity());

        View result = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView name = (TextView)result.findViewById(R.id.name);
        name.setText(dataFromActivity.getString(CocktailActivity.COCKTAIL_NAME));

        ImageView picture = (ImageView)result.findViewById(R.id.picture);
        //picture.setImageBitmap(dataFromActivity.get(CocktailActivity.COCKTAIL_PICTURE));

        TextView instructions = (TextView)result.findViewById(R.id.instructions);
        instructions.setText(dataFromActivity.getString(CocktailActivity.COCKTAIL_INSTRUCTIONS));

        TextView ing1 = (TextView)result.findViewById(R.id.ingredient1);
        ing1.setText("Ingredient 1: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT1));
        TextView ing2 = (TextView)result.findViewById(R.id.ingredient2);
        ing2.setText("Ingredient 3: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT2));
        TextView ing3 = (TextView)result.findViewById(R.id.ingredient3);
        ing3.setText("Ingredient 3: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT3));

//
//        Button hideButton = (Button) result.findViewById(R.id.hide);
//        hideButton.setOnClickListener( clk -> {
//
//                isSaved = DB.queryDrink(idDrink);
//                if (isSaved) {
//                    DB.deleteDrink(idDrink);
//                }
//                isSaved = DB.queryDrink(idDrink);
//       });

        Button saveButton = (Button)result.findViewById(R.id.saveDrink);
        saveButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This method saves the cocktails descriptions
             * to be used for offline viewing within a favourites page.
             * @param v
             */
            @Override
            public void onClick(View v) {
                String drinkName = name.getText().toString();
                String drinkIns = instructions.getText().toString();
                String drinkIng1 = ing1.getText().toString();
                String drinkIng2 = ing2.getText().toString();
                String drinkIng3 = ing3.getText().toString();

                Boolean checkInsertData = db.addData(drinkName, drinkIns, drinkIng1, drinkIng2, drinkIng3);
                if (checkInsertData==true)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "New entry inserted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "No entry inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button favButton = (Button) result.findViewById(R.id.goToFav);
        favButton.setOnClickListener( clk -> {
            Intent goToFavourite = new Intent(getActivity().getApplicationContext(), FavouriteCocktails.class);
            startActivity(goToFavourite);
        });

        return result;
    }
}