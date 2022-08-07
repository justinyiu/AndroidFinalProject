package com.cst2335.androidfinalproject;

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
        //picture.setImageBitmap(dataFromActivity.get(CocktailActivity.COCKTAIL_PICTURE));

        TextView instructions = (TextView)result.findViewById(R.id.instructions);
        instructions.setText(dataFromActivity.getString(CocktailActivity.COCKTAIL_INSTRUCTIONS));

        TextView ing1 = (TextView)result.findViewById(R.id.ingredient1);
        ing1.setText("Ingredient 1: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT1));
        TextView ing2 = (TextView)result.findViewById(R.id.ingredient2);
        ing2.setText("Ingredient 3: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT2));
        TextView ing3 = (TextView)result.findViewById(R.id.ingredient3);
        ing3.setText("Ingredient 3: " + dataFromActivity.get(CocktailActivity.COCKTAIL_INGREDIENT3));


        Button hideButton = (Button) result.findViewById(R.id.hide);
        hideButton.setOnClickListener( clk -> {

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

        });

        return result;
    }
}