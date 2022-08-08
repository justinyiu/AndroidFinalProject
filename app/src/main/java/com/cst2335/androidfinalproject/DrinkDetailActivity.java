package com.cst2335.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class DrinkDetailActivity extends AppCompatActivity {

    private DrinkDetailFragment detailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProgressBar progressBar = findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        if (intent == null){
            Toast.makeText(this, R.string.detail_has_no_intent, Toast.LENGTH_SHORT).show();
            return;
        }
        String idDrink = intent.getStringExtra("idDrink");
        if (TextUtils.isEmpty(idDrink)){
            Snackbar.make(getWindow().getDecorView(), R.string.detail_has_no_intent, Snackbar.LENGTH_SHORT).show();
            return;
        }
        detailFragment = new DrinkDetailFragment();
        detailFragment.setIntent(intent);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, detailFragment).commit();

        progressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (detailFragment != null){
            detailFragment.onReloadButtonStatus();
        }
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
                showAlertDialog(R.string.detail_menu_description_content);
                break;
            case R.id.detail_version:
                showAlertDialog(R.string.detail_menu_version_content);
                break;
            case R.id.detail_favourite:
                startActivity(new Intent(DrinkDetailActivity.this, FavouriteActivity.class));
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
}
