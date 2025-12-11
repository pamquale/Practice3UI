package com.example.practice3ui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> {
    public interface OnImageClick { void onClick(int pos); }
    public interface OnImageLongClick { void onLongClick(int pos); }

    private final Context context;
    private final List<Uri> data;
    private final OnImageClick click;
    private final OnImageLongClick longClick;

    public ImageAdapter(Context context, List<Uri> data, OnImageClick click, OnImageLongClick longClick) {
        this.context = context;
        this.data = data;
        this.click = click;
        this.longClick = longClick;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.img.setImageURI(data.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (click != null) click.onClick(holder.getAdapterPosition());
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (longClick != null) longClick.onLongClick(holder.getAdapterPosition());
            return true;
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}