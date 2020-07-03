package com.abdalqader27.princessstore.Views.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Adapters.CarouselAdapter;
import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.CarouselModels;
import com.github.islamkhsh.CardSliderViewPager;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Objects;

import static com.abdalqader27.princessstore.Contrroler.DataItems.CarouselArrayList;


public class Home extends Fragment {
    private  CardSliderViewPager cardSliderViewPager;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewGroup categoriesCard, locationCard, searchCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        initialization(view);
//        setCarouselList();
        //  setSwipeRefreshLayout(view);
        setClicksItems(view);
        readListCarousel();
        return view;
    }

    private void initialization(View view) {
        cardSliderViewPager = view.findViewById(R.id.viewPager);
        // swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        categoriesCard = view.findViewById(R.id.categoriesCard);
//        maintenanceCard = view.findViewById(R.id.maintenanceCard);
        locationCard = view.findViewById(R.id.locationCard);
        searchCard = view.findViewById(R.id.searchCard);
    }

    private void setClicksItems(View view) {
        categoriesCard.setOnClickListener(v -> startActivity(new Intent(view.getContext(), Products.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
        //maintenanceCard.setOnClickListener(v -> startActivity(new Intent(view.getContext(), Maintenance.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
        locationCard.setOnClickListener(v -> startActivity(new Intent(view.getContext(), Location.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
        searchCard.setOnClickListener(v -> startActivity(new Intent(view.getContext(), Search.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));

    }

    private void setCarouselList() {
        cardSliderViewPager.setAdapter(new CarouselAdapter(CarouselArrayList));
    }

    public void readListCarousel() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Slider");

        query.findInBackground((objects, e) -> {
            if (e == null) {
                DataItems.CarouselArrayList = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    DataItems.CarouselArrayList.add(new CarouselModels(
                            Objects.requireNonNull(objects.get(i).get("ID")).toString(),
                            Objects.requireNonNull(objects.get(i).get("TITLE")).toString(),
                            Objects.requireNonNull(objects.get(i).get("DECRYPTION")).toString(),
                            objects.get(i).getParseFile("IMAGE")

                    ));
                }
                setCarouselList();
            }
        });
    }


//    private void setSwipeRefreshLayout(View view) {
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            swipeRefreshLayout.setRefreshing(false);
//            Toast.makeText(view.getContext(), "تم التحديث بنجاح", Toast.LENGTH_SHORT).show();
//        });
//    }
}
