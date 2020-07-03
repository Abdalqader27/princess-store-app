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
import com.abdalqader27.princessstore.Models.CategoriesModels;
import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Views.Home.DetailsItems;
import com.abdalqader27.princessstore.Views.Home.Items;
import com.github.islamkhsh.CardSliderAdapter;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;



public class CategoriesAdapter extends CardSliderAdapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<CategoriesModels> categoriesList;
    Context context;

    public CategoriesAdapter(List<CategoriesModels> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    @NotNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void bindVH(@NotNull CategoriesViewHolder holder, int i) {
        CategoriesModels current_item = categoriesList.get(i);
        holder.name.setText(current_item.getName());
        loadImages(current_item.getImage(), holder.image);
        if (current_item.getPrice() == null || current_item.getPrice().trim().length() == 0 || current_item.getPrice().equals("0")) {
            holder.linearLayoutPrice.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, Items.class);
                DataItems.categoriesModels.setId(current_item.getId());
                DataItems.categoriesModels.setName(current_item.getName());
                DataItems.categoriesModels.setImage(current_item.getImage());
                DataItems.categoriesModels.setProductsId(current_item.getProductsId());
                DataItems.categoriesModels.setPrice(current_item.getPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                v.getContext().startActivity(intent);

            });
        } else {
            holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.price.setText("السعر : "+current_item.getPrice());
            holder.itemView.setOnClickListener(v -> {
               DataItems. itemsModels.setName(current_item.getName());
                DataItems.  itemsModels.setPrice(current_item.getPrice());
                DataItems.     itemsModels.setImage(current_item.getImage());
                Intent intent = new Intent(context, DetailsItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                v.getContext().startActivity(intent);
            });
        }


    }


    static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        LinearLayout linearLayoutPrice;

        CategoriesViewHolder(View view) {
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
                } else {
                }
            });
        } else {
            img.setPadding(40, 40, 40, 40);
            img.setImageResource(R.drawable.ic_offer);
        }
    }

}