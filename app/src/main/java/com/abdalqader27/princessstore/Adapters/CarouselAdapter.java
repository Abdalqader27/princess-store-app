package com.abdalqader27.princessstore.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Models.CarouselModels;
import com.github.islamkhsh.CardSliderAdapter;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CarouselAdapter extends CardSliderAdapter<CarouselAdapter.CaroselViewHolder> {

    private ArrayList<CarouselModels> carouselList;

    public CarouselAdapter(ArrayList<CarouselModels> movies) {
        this.carouselList = movies;
    }

    @Override
    public int getItemCount() {
        return carouselList.size();
    }

    @NotNull
    @Override
    public CaroselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CaroselViewHolder(view);
    }

    @Override
    public void bindVH(@NotNull CaroselViewHolder holder, int i) {
        CarouselModels current_item = carouselList.get(i);
        holder.title.setText(current_item.getTitle());
        holder.subTitle.setText(current_item.getDescription());
        loadImages(current_item.getImage(), holder.image);
    }


    static class CaroselViewHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle;
        ImageView image;

        CaroselViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleCarouesl);
            subTitle = view.findViewById(R.id.txtSubTitle);
            image = view.findViewById(R.id.imageCarousel);
        }
    }

    private void loadImages(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground((data, e) -> {
                if (e == null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    img.setImageBitmap(bmp);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                }
            });
        } else {
            img.setImageResource(R.drawable.ic_undraw_chore_list_iof3);
        }
    }
}