package com.cst2335.androidfinalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    private Bundle dataFromActivity;
    private Bundle savedState = null;
    private long id;
    private AppCompatActivity parentActivity;
    private int someStateValue;
    private final String value = "someValue";


    /**
     * storing the
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(CocktailActivity.ACTIVITY_SERVICE);

        View results = inflater.inflate(R.layout.activity_cocktail, container, false);

        if (savedInstanceState !=null && savedState == null) {
            someStateValue = savedInstanceState.getInt(value);
        }
        EditText drink = results.findViewById(R.id.cocktailSearch);
        drink.setText(dataFromActivity.getString(CocktailActivity.SEARCH_SERVICE));
        return results;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        parentActivity = (AppCompatActivity)context;
    }

}
