package com.abdalqader27.princessstore.Views.Home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Adapters.ItemsAdapter;
import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.CategoriesModels;
import com.abdalqader27.princessstore.Models.ItemsModels;
import com.abdalqader27.princessstore.R;
import com.annimon.stream.Stream;

import java.util.List;

public class Items extends AppCompatActivity {
    TextView ItemsNamesText;
    ImageView backImage;
    RecyclerView itemsRecyclerViews;
    static CategoriesModels categoriesModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        initialization();
        setClicksItems();
        getFromAdapter();
        List<ItemsModels> ll = Stream.of(DataItems.itemsDataArrayList).filter(w -> w.getCategoriesID().equals(categoriesModels.getId())).toList();
        SetRecycler(ll);
    }

    private void initialization() {
        backImage = findViewById(R.id.back);
        itemsRecyclerViews = findViewById(R.id.itemsRecyclerView);
        ItemsNamesText = findViewById(R.id.itemsNamesText);


    }

    void getFromAdapter() {
//        Gson gson = new Gson();
        categoriesModels =DataItems.categoriesModels;
        ItemsNamesText.setText(categoriesModels.getName());
    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
    }


    private void SetRecycler(List<ItemsModels> list) {
        itemsRecyclerViews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        itemsRecyclerViews.setLayoutManager(layoutManager);
        ItemsAdapter categoriesAdapterd = new ItemsAdapter(list, getApplicationContext());
        itemsRecyclerViews.setAdapter(categoriesAdapterd);
    }
}
