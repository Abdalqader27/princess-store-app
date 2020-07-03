package com.abdalqader27.princessstore.Views.Home;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdalqader27.princessstore.R;

public class Maintenance extends AppCompatActivity {
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance);
        initialization();
        setClicksItems();
    }

    private void initialization() {
        backImage = findViewById(R.id.back);
    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
    }
}