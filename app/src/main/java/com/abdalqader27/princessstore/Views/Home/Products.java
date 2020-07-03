package com.abdalqader27.princessstore.Views.Home;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Adapters.ProductsAdapter;
import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.ProductsModels;
import com.abdalqader27.princessstore.R;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Products extends AppCompatActivity {
    ImageView backImage;
    RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        initialization();
        setClicksItems();
        try {
            Collections.sort(DataItems.productsModelsArrayList, (o1, o2) -> Collator.getInstance(new Locale("ar")).compare(o1.getName(), o2.getName()));

        }catch (Exception e){}
        SetRecycler(DataItems.productsModelsArrayList);
    }

    private void initialization() {
        backImage = findViewById(R.id.back);
        productsRecyclerView = findViewById(R.id.categoriesRecyclerView);

    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
    }


    private void SetRecycler(List<ProductsModels> list) {
        productsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        productsRecyclerView.setLayoutManager(layoutManager);
        ProductsAdapter productsAdapterd = new ProductsAdapter(list, getApplicationContext());
        productsRecyclerView.setAdapter(productsAdapterd);
    }


}
