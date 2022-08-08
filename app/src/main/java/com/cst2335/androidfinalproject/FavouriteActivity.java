package com.cst2335.androidfinalproject;

import static com.cst2335.androidfinalproject.MyOpenHelper.COL_DRINK_ID;
import static com.cst2335.androidfinalproject.MyOpenHelper.COL_INGREDIENT1;
import static com.cst2335.androidfinalproject.MyOpenHelper.COL_INGREDIENT2;
import static com.cst2335.androidfinalproject.MyOpenHelper.COL_INGREDIENT3;
import static com.cst2335.androidfinalproject.MyOpenHelper.COL_INSTRUCTIONS;
import static com.cst2335.androidfinalproject.MyOpenHelper.COL_PICTURE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * list things that being added from DrinkDetailActivity
 */
public class FavouriteActivity extends AppCompatActivity {

    private ListView listView;
    private List<Map<String, Object>> dataList = new ArrayList<>();
    private MyOpenHelper myOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        listView = findViewById(R.id.listView);

        // load my favourite from database
        myOpenHelper = new MyOpenHelper(this);
        List<Map<String, Object>> tempList = myOpenHelper.queryAll();
        if (tempList != null){
            dataList.addAll(tempList);
        }
        listView.setAdapter(new FavouriteAdapter());

        progressBar.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail_author:
                showAlertDialog(R.string.detail_menu_author_content);
                break;
            case R.id.detail_desc:
                showAlertDialog(R.string.favourite_menu_description_content);
                break;
            case R.id.detail_version:
                showAlertDialog(R.string.detail_menu_version_content);
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(@StringRes int resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(resId);
        builder.setTitle("");
        builder.setPositiveButton(R.string.detail_alert_confirm, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    private class FavouriteAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Map<String, Object> item = dataList.get(position);

            convertView = LayoutInflater.from(FavouriteActivity.this).inflate(R.layout.list_item_favourite, null);
            ImageView imageView = convertView.findViewById(R.id.image_view);
            TextView info = convertView.findViewById(R.id.info);
            TextView first_ingredient_textView = convertView.findViewById(R.id.first_ingredient_textView);
            TextView second_ingredient_textView = convertView.findViewById(R.id.second_ingredient_textView);
            TextView third_ingredient_textView = convertView.findViewById(R.id.third_ingredient_textView);

            // load image url
            String picture = (String) item.get(COL_PICTURE);
            if (!TextUtils.isEmpty(picture)) {
                new ImageTask(imageView, picture).execute();
            }

            // load info
            String instructions = (String) item.get(COL_INSTRUCTIONS);
            info.setText(instructions);

            // load ingredient
            first_ingredient_textView.setText((String) item.get(COL_INGREDIENT1));
            second_ingredient_textView.setText((String) item.get(COL_INGREDIENT2));
            third_ingredient_textView.setText((String) item.get(COL_INGREDIENT3));

            convertView.setOnLongClickListener(v -> {
                String idDrink = (String) item.get(COL_DRINK_ID);
                if (TextUtils.isEmpty(idDrink)){
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteActivity.this);
                builder.setMessage(R.string.detail_delete_database_confirm);
                builder.setTitle("");
                builder.setPositiveButton(R.string.detail_alert_confirm, (dialogInterface, i) -> {
                    myOpenHelper.deleteCock(idDrink);
                    dataList.clear();
                    List<Map<String, Object>> tempList = myOpenHelper.queryAll();
                    if (tempList != null){
                        dataList.addAll(tempList);
                    }
                    notifyDataSetChanged();
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton(R.string.detail_alert_cancel, (dialog, which) -> dialog.dismiss());
                builder.create().show();
                return true;
            });

            return convertView;
        }
    }

    private class ImageTask extends AsyncTask<String, String, String> {

        private ImageView imageView;
        private String imageUrl;

        public ImageTask(ImageView imageView, String imageUrl){
            this.imageView = imageView;
            this.imageUrl = imageUrl;
        }

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
                runOnUiThread(() -> {
                    imageView.setImageBitmap(finalBitmap);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
