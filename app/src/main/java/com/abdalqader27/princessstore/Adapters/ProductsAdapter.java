package com.abdalqader27.princessstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Models.ProductsModels;
import com.abdalqader27.princessstore.Views.Home.Categories;
import com.github.islamkhsh.CardSliderAdapter;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ProductsAdapter extends CardSliderAdapter<ProductsAdapter.ProductsViewHolder> {

    private List<ProductsModels> productsModelsList;
    Context context;
    private int lastPosition = -1;

    public ProductsAdapter(List<ProductsModels> productsModelsList, Context context) {
        this.productsModelsList = productsModelsList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return productsModelsList.size();
    }

    @NotNull
    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void bindVH(@NotNull ProductsViewHolder holder, int i) {
        ProductsModels current_item = productsModelsList.get(i);
        holder.name.setText(current_item.getName());
        loadImages(current_item.getImage(), holder.image);
        if (current_item.getPrice() == null) {
            holder.linearLayoutPrice.setVisibility(View.GONE);
        } else {
            holder.price.setText(current_item.getPrice());
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Categories.class);
            Bundle bundle=new Bundle();
            bundle.putString("productsId", current_item.getId());
            bundle.putString("productsName", current_item.getName());
//            Gson gson = new Gson();
//            String myJson = gson.toJson(current_item);
//            intent.putExtra("myDataProducts", (Serializable)current_item);
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
         v.getContext().startActivity(intent);

        });
        if (i > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.recycle_animation);
            holder.itemView.startAnimation(animation);
            lastPosition = i;
            lastPosition = -1;
        }
    }


    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        LinearLayout linearLayoutPrice;

        ProductsViewHolder(View view) {
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