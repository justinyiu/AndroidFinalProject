package com.cst2335.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailFragment extends Fragment {

    private ImageView imageView;
    private TextView infoTextView;
    private TextView first_ingredient_textView;
    private TextView second_ingredient_textView;
    private TextView third_ingredient_textView;
    private EditText edit_text;
    private Button saveEditBtn;
    private Button saveDatabaseBtn;
    private Button deleteDatabaseBtn;

    private long id;
    private String info;
    private String imageUrl;
    private String firstIngredient;
    private String secondIngredient;
    private String thirdIngredient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container);
        imageView = rootView.findViewById(R.id.image_view);
        infoTextView = rootView.findViewById(R.id.info);
        first_ingredient_textView = rootView.findViewById(R.id.first_ingredient);
        second_ingredient_textView = rootView.findViewById(R.id.second_ingredient);
        third_ingredient_textView = rootView.findViewById(R.id.third_ingredient);
        edit_text = rootView.findViewById(R.id.edit_text);
        saveEditBtn = rootView.findViewById(R.id.save_desc);
        saveDatabaseBtn = rootView.findViewById(R.id.save_to_database);
        deleteDatabaseBtn = rootView.findViewById(R.id.delete_from_database);

        // load image
        new ImageTask().execute();

        // load info
        infoTextView.setText(info);

        // load ingredient3
        first_ingredient_textView.setText(firstIngredient);
        second_ingredient_textView.setText(secondIngredient);
        third_ingredient_textView.setText(thirdIngredient);

        // load desc from sp
        final SharedPreferences sp = getActivity()
                .getSharedPreferences("detail", Context.MODE_PRIVATE);
        edit_text.setText(sp.getString("desc", null));

        // set edit onclick
        saveEditBtn.setOnClickListener(v -> {
            sp.edit().putString("desc", edit_text.getText().toString()).apply();
        });

        // get save status

        // set save database onclick
        saveDatabaseBtn.setOnClickListener(v -> {

        });

        // delete from database onclick
        deleteDatabaseBtn.setOnClickListener(v -> {

        });

        return rootView;
    }

    public void setIntent(Intent intent) {
        id = intent.getLongExtra("id", 0);
        info = intent.getStringExtra("info");
        imageUrl = intent.getStringExtra("strDrinkThumb");
        firstIngredient = intent.getStringExtra("strIngredient1");
        secondIngredient = intent.getStringExtra("strIngredient2");
        thirdIngredient = intent.getStringExtra("strIngredient3");
    }

    private class ImageTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL myFileURL;
            Bitmap bitmap = null;
            try {
                myFileURL = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
                conn.setConnectTimeout(6000);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
                Bitmap finalBitmap = bitmap;
                getActivity().runOnUiThread(() -> {
                    imageView.setImageBitmap(finalBitmap);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void showAlertDialog(int resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(resId);
        builder.setTitle("");
        builder.setPositiveButton(R.string.detail_alert_confirm, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }
}
