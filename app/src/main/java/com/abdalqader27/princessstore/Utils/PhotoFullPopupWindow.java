package com.abdalqader27.princessstore.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.palette.graphics.Palette;

import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PhotoFullPopupWindow extends PopupWindow {

    private final Context mContext;
    private final ImageView photoView;
    private final ProgressBar loading;
    private final ViewGroup parent;
    private static PhotoFullPopupWindow instance = null;



    public PhotoFullPopupWindow(Context ctx, int layout, View v, final Bitmap bitmap, String Title) {
        super(((LayoutInflater) Objects.requireNonNull(ctx.getSystemService(LAYOUT_INFLATER_SERVICE)))
                        .inflate( layout, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        setElevation(5.0f);
        this.mContext = ctx;
        View view = getContentView();
        ImageButton closeButton = view.findViewById(R.id.ib_close);
        setOutsideTouchable(true);
        setBackgroundDrawable( new BitmapDrawable(view.getResources()));// it is most important peace of code

        setFocusable(true);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dismiss();
            }
        });
        //---------Begin customising this popup--------------------

        photoView = view.findViewById(R.id.imageFull);
        loading = view.findViewById(R.id.loading);
        TextView textView = view.findViewById(R.id.textNewsTitle);
        parent = (ViewGroup) photoView.getParent();
        textView.setText(Title);
        // ImageUtils.setZoomable(imageView);
        //----------------------------
        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            parent.setBackground(new BitmapDrawable(mContext.getResources(), Constants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));// ));
            photoView.setImageBitmap(bitmap);
        } else {
            loading.setIndeterminate(true);
            loading.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(DataItems.itemsModels.getImage().getUrl())
                    .error(R.mipmap.ic_launcher)
                   .into(new Target() {

                @Override
                public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from) {
                    /* Save the bitmap or do something with it here */

                    parent.setBackground(new BitmapDrawable(mContext.getResources(), Constants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));// ));


                    loading.setVisibility(View.GONE);

                    // Set it in the ImageView
                    photoView.setImageBitmap(bitmap);
                }

                       @Override
                       public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                           loading.setIndeterminate(false);
                           loading.setBackgroundColor(Color.LTGRAY);
                       }

                       @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {}

            });





        }
        showAtLocation(v, Gravity.CENTER, 50, 50);

        //------------------------------

    }

    private void onPalette(Palette palette) {
        if (null != palette) {
            ViewGroup parent = (ViewGroup) photoView.getParent();
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
        }
    }


}