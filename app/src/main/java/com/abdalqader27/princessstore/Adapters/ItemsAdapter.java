package com.abdalqader27.princessstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Contrroler.DataItems;
import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Models.ItemsModels;
import com.abdalqader27.princessstore.Views.Home.DetailsItems;
import com.github.islamkhsh.CardSliderAdapter;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ItemsAdapter extends CardSliderAdapter<ItemsAdapter.itemsViewHolder> {

    private List<ItemsModels> itemsModelsList;
    Context context;

    public ItemsAdapter(List<ItemsModels> itemsModelsList, Context context) {
        this.itemsModelsList = itemsModelsList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemsModelsList.size();
    }

    @NotNull
    @Override
    public itemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new itemsViewHolder(view);
    }

    @Override
    public void bindVH(@NotNull itemsViewHolder holder, int i) {
        ItemsModels current_item = itemsModelsList.get(i);
        holder.name.setText(current_item.getName());
        loadImages(current_item.getImage(), holder.image);
        if (current_item.getPrice() == null || current_item.getPrice().trim().length() == 0) {
            holder.linearLayoutPrice.setVisibility(View.GONE);
        } else {
            holder.price.setText("السعر : "+current_item.getPrice());
        }
        holder.itemView.setOnClickListener(v -> {
            DataItems.itemsModels = current_item;
            Intent intent = new Intent(context, DetailsItems.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            v.getContext().startActivity(intent);

        });
    }


    static class itemsViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        LinearLayout linearLayoutPrice;

        itemsViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameCategories);
            price = view.findViewById(R.id.priceCategories);
            image = view.findViewById(R.id.imageCategories);
            linearLayoutPrice = view.findViewById(R.id.linearLayoutPrice);
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
            img.setImageResource(R.mipmap.ic_launcher);
        }
    }

}