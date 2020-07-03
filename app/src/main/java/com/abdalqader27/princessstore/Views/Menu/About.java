package com.abdalqader27.princessstore.Views.Menu;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdalqader27.princessstore.R;

public class About extends AppCompatActivity {
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
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
