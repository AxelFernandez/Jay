package com.axelfernandez.jay.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axelfernandez.jay.Activitys.DescriptionActivity;
import com.axelfernandez.jay.Models.ProductListModel;
import com.axelfernandez.jay.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    List<ProductListModel> productListModels;
    Context context;
   public ProductsAdapter(List<ProductListModel> productListModels, Context context){
        this.context= context;
        this.productListModels= productListModels;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false);
        ProductsViewHolder pvh = new ProductsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, final int i) {
        if (productListModels.get(i).getImageUrl()!=null){
            productsViewHolder.title.setText(productListModels.get(i).getName());
            productsViewHolder.price.setText(productListModels.get(i).getPrice());
            if (productListModels.get(i).getImageUrl().isEmpty()) {
                productsViewHolder.image.setImageResource(R.drawable.cuppycry);
            } else{
            String bestImage = productListModels.get(i).getImageUrl().replace("-I.jpg","-O.jpg");
            Picasso.get().load(bestImage).into(productsViewHolder.image);
            }
            productsViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DescriptionActivity.class);
                    intent.putExtra("descriptionId",productListModels.get(i).getIdProduct());
                    context.startActivity(intent);

                }
            });
        }else{
            productsViewHolder.title.setText(productListModels.get(i).getName());
            productsViewHolder.image.setImageResource(R.drawable.cuppynotfound);

        }

    }

    @Override
    public int getItemCount() {
        return productListModels.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView title;
        TextView price;
        ImageView image;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.product_item_cv);
            title = itemView.findViewById(R.id.product_item_name);
            price= itemView.findViewById(R.id.product_item_price);
            image = itemView.findViewById(R.id.product_item_image);
        }
    }
}
