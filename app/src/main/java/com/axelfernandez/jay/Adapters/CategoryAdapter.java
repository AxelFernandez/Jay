package com.axelfernandez.jay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axelfernandez.jay.Activitys.ProductsActivity;
import com.axelfernandez.jay.Models.CategoryModel;
import com.axelfernandez.jay.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<CategoryModel> categoryModels;
    private Context context;
    private final int WELCOME_MESSAGE = 0;
    private final int CATEGORY_ITEM =1;


    public CategoryAdapter (List<CategoryModel> categoryModels, Context context){
        this.categoryModels= categoryModels;
        this.context= context;

    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case WELCOME_MESSAGE:
                return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_welcome_message, parent, false));
            case CATEGORY_ITEM:
                return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false));
            default:
                return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        if (position!=WELCOME_MESSAGE){
            if ( categoryModels.get(position).getId()!=null){
                holder.title.setText(categoryModels.get(position).getName());
                holder.cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, ProductsActivity.class);
                        i.putExtra("search","category="+categoryModels.get(position).getId());
                        i.putExtra("searchName",categoryModels.get(position).getName());
                        context.startActivity(i);

                    }
                });
            }else{
                holder.title.setText(categoryModels.get(position).getName());
            }

        }
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return WELCOME_MESSAGE;
        } else {
            return CATEGORY_ITEM;
        }
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

            TextView title;
        CardView cv;


        public CategoryViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category_item);
            cv = itemView.findViewById(R.id.category_cv);
        }
    }
}


