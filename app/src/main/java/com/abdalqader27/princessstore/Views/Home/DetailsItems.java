package com.abdalqader27.princessstore.Views.Home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.Models.ItemsModels;
import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Utils.PhotoFullPopupWindow;
import com.parse.ParseFile;

public class DetailsItems extends AppCompatActivity {


    TextView ItemsNamesText, price;
    ImageView backImage, ViewImage;
    Button sendItems;
    static ItemsModels itemsModels;
    private Bitmap _bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_items);
        initialization();
        setClicksItems();
        getFromAdapter();

    }

    private void initialization() {
        backImage = findViewById(R.id.back);
        ItemsNamesText = findViewById(R.id.detailsName);
        price = findViewById(R.id.priceDeails);
        ViewImage = findViewById(R.id.imageDetials);
        sendItems = findViewById(R.id.sendItems);

    }

    void getFromAdapter() {
        itemsModels = DataItems.itemsModels;
        ItemsNamesText.setText(itemsModels.getName());
        String tempPrice = itemsModels.getPrice();
        price.setText(tempPrice);
        loadImages(itemsModels.getImage(), ViewImage);
    }

    private void setClicksItems() {
        backImage.setOnClickListener(v -> super.onBackPressed());
        sendItems.setOnClickListener(v -> {
            String phoneNumberWithCountryCode = "+963996830651";
            String message = "نريد  طلب المنتج   : ";
            message += itemsModels.getName();

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(
                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                    phoneNumberWithCountryCode, message))));
        });
        ViewImage.setOnClickListener(v -> {
            new PhotoFullPopupWindow(getApplicationContext(),
                    R.layout.popup_photo_full, v
                    , _bitmap, DataItems.itemsModels.getName()).dismiss();


            new PhotoFullPopupWindow(getApplicationContext(),
                    R.layout.popup_photo_full, v
                    , _bitmap, DataItems.itemsModels.getName());
        });
    }

    private void loadImages(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground((data, e) -> {
                if (e == null) {

                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    img.setImageBitmap(bmp);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);

                }
            });
        } else {
            img.setImageResource(R.mipmap.ic_launcher);
        }
    }


    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


}
