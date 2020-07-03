package com.abdalqader27.princessstore.Views.Home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.CategoriesModels;
import com.abdalqader27.princessstore.Models.ItemsModels;
import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Adapters.SearchAdapter;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    ImageView backImage;
    private SearchAdapter adapter;
    private List<ItemsModels> itemsList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        initialization();
        setClicksItems();
        fillSearchList();
        setUpRecyclerView();
    }

    private void fillSearchList() {
        itemsList = new ArrayList<>();
        for (int i = 0; i < DataItems.itemsDataArrayList.size(); ++i) {
            itemsList.add(new ItemsModels(
                    DataItems.itemsDataArrayList.get(i).getId(),
                    DataItems.itemsDataArrayList.get(i).getName(),
                    DataItems.itemsDataArrayList.get(i).getPrice(),
                    DataItems.itemsDataArrayList.get(i).getCategoriesID(),
                    DataItems.itemsDataArrayList.get(i).getImage()

            ));

        }
        List<CategoriesModels> l = Stream.of(DataItems.categoriesDataArrayList).filter(w -> w.getPrice() != null && w.getPrice().trim().length() != 0 && !w.getPrice().equals("0")).toList();
        for (int i = 0; i < l.size(); ++i) {
            ItemsModels itemsModels = new ItemsModels(l.get(i).getName(), l.get(i).getPrice(), l.get(i).getImage());
            itemsList.add(itemsModels);

        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new SearchAdapter(itemsList, getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.isSubmitButtonEnabled();
        searchView.setIconifiedByDefault(false);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void initialization() {
        backImage = findViewById(R.id.back);
    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
    }
}