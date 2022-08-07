package com.cst2335.androidfinalproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
    MyOpenHelper db;
    private SQLiteDatabase myDatabase;


    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        db =  new MyOpenHelper(getActivity().getApplicationContext());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        //drinkName = dataFromActivity.getString(CocktailActivity.ITEM_SELECTED);


        View result = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView name = (TextView)result.findViewById(R.id.name);
        name.setText(dataFromActivity.getString(CocktailActivity.COCKTAIL_NAME));

        ImageView picture = (ImageView)result.findViewById(R.id.picture);
        //picture.setImageBitmap((Bitmap) dataFromActivity.get(CocktailActivity.COCKTAIL_PICTURE));
        //picture.setImageBitmap(dataFromActivity.getByteArray(CocktailActivity.COCKTAIL_PICTURE));

        TextView instructions = (TextView)result.findViewById(R.id.instructions);
        instructions.setText("Instructions: " + dataFromActivity.getString(CocktailActivity.COCKTAIL_INSTRUCTIONS));

        TextView ing1 = (TextView)result.findViewById(R.id.ingredient1);
        ing1.setText("Ingredient 1: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT1));
        TextView ing2 = (TextView)result.findViewById(R.id.ingredient2);
        ing2.setText("Ingredient 2: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT2));
        TextView ing3 = (TextView)result.findViewById(R.id.ingredient3);
        ing3.setText("Ingredient 3: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT3));


        Button hideButton = (Button) result.findViewById(R.id.hide);
        hideButton.setOnClickListener( clk -> {

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        });

        // when user presses save the contents of the fragment will be stored in the DB
        Button saveButton = (Button) result.findViewById(R.id.saveDrink);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drinkName = name.getText().toString();
                String drinkPic = picture.toString();
                String drinkIns = instructions.getText().toString();
                String ingred1 = ing1.getText().toString();
                String ingred2 = ing2.getText().toString();
                String ingred3 = ing3.getText().toString();


                ContentValues cv = new ContentValues();
                cv.put(MyOpenHelper.COL_NAME, drinkName);

                long id = myDatabase.insert(MyOpenHelper.TABLE_NAME, null, cv);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),R.string.Saved, Toast.LENGTH_LONG);
                toast.show();



            }
        });


        return result;
    }
}