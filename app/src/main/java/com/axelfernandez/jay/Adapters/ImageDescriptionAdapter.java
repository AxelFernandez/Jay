package com.axelfernandez.jay.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.axelfernandez.jay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageDescriptionAdapter extends RecyclerView.Adapter<ImageDescriptionAdapter.ImageDescriptionViewHolder> {
    List<String> urlImage;

    public ImageDescriptionAdapter(List<String> urlImage){
        this.urlImage= urlImage;
    }

    @Override
    public ImageDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageDescriptionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.description_image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageDescriptionViewHolder holder, int position) {
        Picasso.get().load(urlImage.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return urlImage.size();
    }


    static class ImageDescriptionViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ImageDescriptionViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_description_image);
        }
    }
}
