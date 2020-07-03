package com.abdalqader27.princessstore.Views.Home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Adapters.CategoriesAdapter;
import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.CategoriesModels;
import com.annimon.stream.Stream;
import com.google.gson.Gson;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Categories extends AppCompatActivity {
    TextView categoriesNamesText;
    ImageView backImage;
    RecyclerView categoriesRecyclerViews;
    String productsModelsId, productsModelsName;
    //    static ProductsModels productsModels;
    static List<CategoriesModels> ll;
    Locale local = new Locale("ar");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initialization();
        setClicksItems();
        getFromAdapter();
        ll = Stream.of(DataItems.categoriesDataArrayList).filter(w -> w.getProductsId().equals(productsModelsId)).toList();
        Collections.sort(ll, (o1, o2) -> Collator.getInstance(local).compare(o1.getName(), o2.getName()));
        SetRecycler(ll);
    }

    private void initialization() {
        backImage = findViewById(R.id.back);
        categoriesRecyclerViews = findViewById(R.id.categoriesRecyclerView);
        categoriesNamesText = findViewById(R.id.categoriesNamesText);


    }

    void getFromAdapter() {
        Gson gson = new Gson();
        try {
            Bundle bundle = getIntent().getExtras();

            productsModelsId = bundle.getString("productsId");
            productsModelsName = bundle.getString("productsName");
//            productsModels = gson.fromJson(getIntent().getStringExtra("myDataProducts"), ProductsModels.class);
            categoriesNamesText.setText(productsModelsName);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
    }


    private void SetRecycler(List<CategoriesModels> list) {

        categoriesRecyclerViews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        categoriesRecyclerViews.setLayoutManager(layoutManager);

        CategoriesAdapter categoriesAdapterd = new CategoriesAdapter(list, getApplicationContext());
        categoriesRecyclerViews.setAdapter(categoriesAdapterd);
    }

}

