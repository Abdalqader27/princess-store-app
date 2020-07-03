package com.abdalqader27.princessstore.Views.Home;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abdalqader27.princessstore.R;

public class Location extends AppCompatActivity {
    ImageView backImage, whatsapp, faceBook;
    //    private MapView mapView;
    TextView phoneText;
    ImageView imageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        initialization(savedInstanceState);
        setClicksItems();
    }

    private void initialization(Bundle savedInstanceState) {
        backImage = findViewById(R.id.back);
        whatsapp = findViewById(R.id.whatsapp);
        faceBook = findViewById(R.id.facebook);
//        mapView = findViewById(R.id.map_header);
//        mapView.onCreate(savedInstanceState);
//        mapView.setClickable(false);
//        mapView.getMapAsync(this);
        imageMap = findViewById(R.id.imageMap);
        phoneText = findViewById(R.id.phoneText);

    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
        faceBook.setOnClickListener(v -> {
            try {
                String url = "https://www.facebook.com/PrincessForTechnology/";
                startActivity(newFacebookIntent(getPackageManager(), url));
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "No FaceBook App", Toast.LENGTH_SHORT).show();
            }
        });
        whatsapp.setOnClickListener(v -> {
            String contact = "+963 996830651"; // use country code with your phone number
            String url = "https://api.whatsapp.com/send?phone=" + contact;
            try {
                PackageManager pm = v.getContext().getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(v.getContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
        phoneText.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0996830651"));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        });
        imageMap.setOnClickListener(v -> {
//            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 36.2221388, 37.125732);
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/jsTsbVNNuDnSNa436"));
////            intent.setPackage("com.google.android.apps.maps");
//            startActivity(intent);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("www.google.com")
                    .appendPath("maps")
                    .appendPath("dir")
                    .appendPath("")
                    .appendQueryParameter("api", "1")
                    .appendQueryParameter("destination", 36.2221388 + "," + 37.125732);
            String url = builder.build().toString();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
//            String uri = "http://maps.google.com/maps?saddr=" + 36.2221388 + "," + 37.125732 ;
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//            intent.setPackage("com.google.android.apps.maps");
//            startActivity(intent);
        });
    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(36.2221388, 37.125732)));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.2221388, 37.125732), 15));
//
//
//        mapView.onResume();
//
//    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }
}