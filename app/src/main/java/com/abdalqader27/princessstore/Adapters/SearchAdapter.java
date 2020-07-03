package com.abdalqader27.princessstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Models.ItemsModels;
import com.abdalqader27.princessstore.R;
import com.abdalqader27.princessstore.Views.Home.DetailsItems;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

import static com.abdalqader27.princessstore.Contrroler.DataItems.itemsModels;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ExampleViewHolder> implements Filterable {
    private List<ItemsModels> searchList;
    private List<ItemsModels> searchListFull;
    private Context context;

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        LinearLayout linearLayoutPrice;

        ExampleViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nameCategories);
            price = view.findViewById(R.id.priceCategories);
            image = view.findViewById(R.id.imageCategories);
            linearLayoutPrice = view.findViewById(R.id.linearLayoutPrice);
        }
    }

    public SearchAdapter(List<ItemsModels> searchList, Context context) {
        this.searchList = searchList;
        searchListFull = new ArrayList<>(searchList);
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ItemsModels currentItem = searchList.get(position);

        holder.name.setText(currentItem.getName());
        loadImages(currentItem.getImage(), holder.image);
        if (currentItem.getPrice() == null || currentItem.getPrice().trim().length() == 0) {
            holder.linearLayoutPrice.setVisibility(View.GONE);
        } else {
            holder.price.setText(currentItem.getPrice());
        }
        holder.itemView.setOnClickListener(v -> {
            itemsModels = currentItem;
            Intent intent = new Intent(context, DetailsItems.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            v.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemsModels> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ItemsModels item : searchListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchList.clear();
            searchList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
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