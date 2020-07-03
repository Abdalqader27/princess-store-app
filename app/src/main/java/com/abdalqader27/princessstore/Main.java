package com.abdalqader27.princessstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.abdalqader27.princessstore.Views.Home.Notifications;
import com.abdalqader27.princessstore.Views.Home.Offers;
import com.abdalqader27.princessstore.Views.Menu.About;
import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.CategoriesModels;
import com.abdalqader27.princessstore.Models.ItemsModels;
import com.abdalqader27.princessstore.Models.ProductsModels;
import com.abdalqader27.princessstore.Views.Home.Home;
import com.andremion.counterfab.CounterFab;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.instabug.library.Instabug;
import com.instabug.library.ui.onboarding.WelcomeMessage;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import stream.customalert.CustomAlertDialogue;


public class Main extends AppCompatActivity {
    protected AdvanceDrawerLayout drawer;
    protected Toolbar toolbar;
    protected ViewGroup shareCard, about, sendProblem;
    protected ImageView menuImage;
    private SharedPreferences Shared_bdges;

    //    protected SmoothBottomBar smoothBottomBar;
    private CounterFab counterFab;
    static protected Fragment SelectFragment = new Home();
    static protected Fragment home = new Home();
    static protected Fragment offers = new Offers();
    ViewGroup activity_with_view_pager, loading;
    boolean t1, t2, t3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setInstabug();
        initialization();
        setSmoothBottomBar();
        setClickElements();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("MpVxwHNjc7WLxcIzcktH06UkvYWI906toJzU7lLT")
                .clientKey("3QueHj9WdrQYXSUmXBzgt0MSllUpS3i9qs4GZZH1")
                .server("https://parseapi.back4app.com")
                .build()
        );
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", "1086072371688");
        installation.saveInBackground();
        readListProducts();
        readListItems();
        readListCategories();


    }

    protected void initialization() {
        drawer = findViewById(R.id.drawer_layout);
        menuImage = findViewById(R.id.img_menu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shareCard = findViewById(R.id.share_card);
        about = findViewById(R.id.about);
        sendProblem = findViewById(R.id.sendProblem);
        activity_with_view_pager = findViewById(R.id.activity_with_view_pager);
        loading = findViewById(R.id.loading);
        counterFab = findViewById(R.id.counter_fab);
        Shared_bdges = getSharedPreferences("Save_badges", Context.MODE_PRIVATE);

//        smoothBottomBar = findViewById(R.id.bottomBar);
        t1 = t2 = t3 = false;

    }

    protected void setClickElements() {


        menuImage.setOnClickListener(v -> drawer.openDrawer(GravityCompat.END));

        shareCard.setOnClickListener(v -> sendApplication(Objects.requireNonNull(this)));

        about.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), About.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        sendProblem.setOnClickListener(v -> {
            Instabug.enable();
            Instabug.show();
        });
        counterFab.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Notifications.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
    }

    @Override
    public void onResume() {
        counterFab.setCount(Shared_bdges.getInt("count2", 0));


        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            try {

                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(Main.this)
                        .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                        .setCancelable(false)
                        .setTitle("تنبيه!!  ")
                        .setMessage("هل حقا تريد  الخروج ؟")
                        .setPositiveText("نعم ")
                        .setPositiveColor(R.color.negative)
                        .setOnPositiveClicked((view12, dialog) -> {
                            finishAffinity();
                            dialog.dismiss();
                        })
                        .setNegativeText("لا ")
                        .setNegativeColor(R.color.positive)
                        .setOnNegativeClicked((view1, dialog) -> dialog.dismiss())
                        .setDecorView(Main.this.getWindow().getDecorView())
                        .build();
                alert.show();

            } catch (Exception e) {
                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(Main.this)
                        .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                        .setCancelable(false)
                        .setTitle("تنبيه!!  ")
                        .setMessage("هل حقا تريد  الخروج ؟")
                        .setPositiveText("نعم ")
                        .setPositiveColor(R.color.negative)
                        .setOnPositiveClicked((view12, dialog) -> {
                            super.onBackPressed();

                            dialog.dismiss();
                        })
                        .setNegativeText("لا ")
                        .setNegativeColor(R.color.positive)
                        .setOnNegativeClicked((view1, dialog) -> dialog.dismiss())
                        .setDecorView(Main.this.getWindow().getDecorView())
                        .build();
                alert.show();
            }
        }
    }

    protected void sendApplication(Activity activity) {
        ApplicationInfo app = activity.getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);

        try {
            //Make new directory in new location
            File tempFile = new File(activity.getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory()) {
                if (!tempFile.mkdirs()) {
                    return;
                }
            }
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + activity.getString(app.labelRes).replace(" ", "").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

            //Open share dialog
            Context context = this;

            Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(context), activity.getPackageName(), tempFile);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            activity.grantUriPermission(activity.getPackageManager().toString(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setSmoothBottomBar() {
//        smoothBottomBar.setActiveItem(1);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.container_main, SelectFragment).
                commit();

//        smoothBottomBar.setOnItemSelected(integer -> {
//            switch (integer) {
//                case 0:
//                    SelectFragment = offers;
//                    break;
//                case 1:
//                    SelectFragment = home;
//                    break;
//            }
//            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                    .replace(R.id.container_main, SelectFragment).
//                    commit();
//            return null;
//        });
    }

    protected void setInstabug() {
        try {
            new Instabug.Builder(getApplication(), "348f181f387aa9dd9939182cdb7bdeb6")
                    .build();
            Instabug.setWelcomeMessageState(WelcomeMessage.State.DISABLED);
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void readListProducts() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("s1");

        query.findInBackground((objects, e) -> {
            if (e == null) {
                DataItems.productsModelsArrayList = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    DataItems.productsModelsArrayList.add(new ProductsModels(
                            Objects.requireNonNull(objects.get(i).get("ID")).toString(),
                            Objects.requireNonNull(objects.get(i).get("NAME")).toString(),
                            null,
                            objects.get(i).getParseFile("Image")
                    ));
                }
                t1 = true;
                if (t2 && t3) {
                    activity_with_view_pager.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }


    public void readListItems() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("s3");

        query.findInBackground((objects, e) -> {
            if (e == null) {
                DataItems.itemsDataArrayList = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    DataItems.itemsDataArrayList.add(new ItemsModels(
                            Objects.requireNonNull(objects.get(i).get("ID")).toString(),
                            Objects.requireNonNull(objects.get(i).get("NAME")).toString(),
                            Objects.requireNonNull(objects.get(i).get("PRICE")).toString(),
                            Objects.requireNonNull(objects.get(i).get("CategoriesID")).toString(),
                            objects.get(i).getParseFile("IMAGE")
                    ));
                }
                t3 = true;
                if (t1 && t2) {
                    activity_with_view_pager.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    public void readListCategories() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("s2");

        query.findInBackground((objects, e) -> {
            if (e == null) {
                DataItems.categoriesDataArrayList = new ArrayList<>();
                for (int i = 0; i < objects.size(); i++) {
                    DataItems.categoriesDataArrayList.add(new CategoriesModels(
                            Objects.requireNonNull(objects.get(i).get("ID")).toString(),
                            Objects.requireNonNull(objects.get(i).get("NAME")).toString(),
                            objects.get(i).get("PRICE") + "",
                            Objects.requireNonNull(objects.get(i).get("ProductsID")).toString(),
                            objects.get(i).getParseFile("IMAGE")

                    ));
                }
                t2 = true;
                if (t1 && t3) {
                    activity_with_view_pager.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }


}
