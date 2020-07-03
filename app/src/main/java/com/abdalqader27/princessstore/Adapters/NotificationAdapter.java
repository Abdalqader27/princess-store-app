package com.abdalqader27.princessstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdalqader27.princessstore.Models.Notificons_models;
import com.abdalqader27.princessstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CustomViewHolder> {
    private final List<Notificons_models> list;
    private final Context context;
    private int lastPosition = -1;
    public NotificationAdapter(Context context, List<Notificons_models> list) {
        this.list = list;
        this.context = context;
    }
    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_notification, viewGroup, false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        Notificons_models s = list.get(i);
        customViewHolder.title.setText(s.getTitle());
        customViewHolder.message.setText(s.getMessage());
        setAnimation(customViewHolder.itemView, i);
    }
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(500);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public void onViewDetachedFromWindow(@NonNull CustomViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }
    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView title;
        final TextView message;

        CustomViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_notif);
            this.message = itemView.findViewById(R.id.discrip_notif);
            this.itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (v == itemView) {
            }
        }
    }
}
