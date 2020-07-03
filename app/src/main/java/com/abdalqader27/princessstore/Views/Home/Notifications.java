package com.abdalqader27.princessstore.Views.Home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Adapters.NotificationAdapter;
import com.abdalqader27.princessstore.Models.Notificons_models;
import com.abdalqader27.princessstore.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class Notifications extends AppCompatActivity {
    private ArrayList<Notificons_models> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);

        LinearLayout linearLayout = findViewById(R.id.noElements);
        SharedPreferences shared_bdges = getSharedPreferences("Save_badges", MODE_PRIVATE);
        SharedPreferences.Editor editor_badges = shared_bdges.edit();
        editor_badges.putInt("count2", 0);
        editor_badges.apply();
        getArrayList();
        if(itemsList.size()==0){
            linearLayout.setVisibility(View.VISIBLE);
        }
        else {
            linearLayout.setVisibility(View.GONE);
        }
        RecyclerView recyclerview = findViewById(R.id.list_noti);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(linearLayoutManager);
        Collections.reverse(itemsList);
        NotificationAdapter adapter = new NotificationAdapter(this, itemsList);
        recyclerview.setAdapter(adapter);

    }


    public void back_notification(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getArrayList() {
        SharedPreferences prefs = getSharedPreferences("save", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("task", null);
        Type type = new TypeToken<ArrayList<Notificons_models>>() {
        }.getType();
        itemsList = gson.fromJson(json, type);
        if (itemsList == null) {
            itemsList = new ArrayList<>();
        }
    }
}