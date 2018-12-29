package com.axelfernandez.jay.Activitys;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.axelfernandez.jay.Adapters.ImageDescriptionAdapter;
import com.axelfernandez.jay.Interfaces.ProductDescriptionCallback;
import com.axelfernandez.jay.Interfaces.ProductDescriptionCallbackTextPlain;
import com.axelfernandez.jay.Utilities.ConextionApi;
import com.axelfernandez.jay.Models.ProductDescriptionModel;
import com.axelfernandez.jay.R;
import com.axelfernandez.jay.Utilities.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class DescriptionActivity extends AppCompatActivity {
    ProductDescriptionModel productDescriptionModel =  new ProductDescriptionModel();
    Toolbar toolbar;
    ProgressDialog progressDialog;
    TextView title;
    TextView price;
    TextView attributes;
    TextView description;
    RecyclerView images;
    ConextionApi conextionApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.jay_is_searching_item));
        progressDialog.setCancelable(false);
        progressDialog.show();

        title = findViewById(R.id.description_title);
        price = findViewById(R.id.description_price);
        attributes = findViewById(R.id.description_attributes);
        description = findViewById(R.id.description_description);
        images= findViewById(R.id.rv_images);

        String host = getString(R.string.baseUrlApi);
        String idProduct = getIntent().getExtras().getString(getString(R.string.parameter_descriptionid));
        conextionApi = new ConextionApi();

        //Url with the both web services will consume
        String urlContext = "/items/" + idProduct;
        String urlContextdesc = "/items/" + idProduct + "/description";


        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        images.setLayoutManager(llm);



        VolleySingleton.getInstance(this).addToRequestQueue(conextionApi.getItem(host, urlContext, new ProductDescriptionCallback() {
            @Override
            public void response(ProductDescriptionModel productDescriptionModels) {
                productDescriptionModel= productDescriptionModels;

            }

        }));
        VolleySingleton.getInstance(this).getRequestQueue().add(conextionApi.getItemdesc(host,urlContextdesc, new ProductDescriptionCallbackTextPlain() {
            @Override
            public void response(String response) {
                productDescriptionModel.setDescription(response);
            }
        }));

        VolleySingleton.getInstance(this).getRequestQueue().addRequestFinishedListener(new RequestQueue.RequestFinishedListener<ProductDescriptionModel>() {
            @Override
            public void onRequestFinished(Request<ProductDescriptionModel> request) {
                toolbar.setTitle(productDescriptionModel.getTitle());
                BlindView();
                images.setAdapter(new ImageDescriptionAdapter(productDescriptionModel.getPicturesUrl()));
                progressDialog.dismiss();
            }
        });

    }

    /**
     * Set all the object in a view only when volley's done*/

    public void BlindView() {
        if (productDescriptionModel.getId()!=null){
            if (productDescriptionModel.getTitle() != null) {
                title.setText(productDescriptionModel.getTitle());
            }
            if (productDescriptionModel.getPrice() != null) {
                price.setText("$"+productDescriptionModel.getPrice());
            }

            if (productDescriptionModel.getAttributesKey() != null && productDescriptionModel.getAttributesValue()!=null) {
                String attr = "";
                for (int i = 0; i<productDescriptionModel.getAttributesValue().size();i++){
                    String key= productDescriptionModel.getAttributesKey().get(i);
                    String value= productDescriptionModel.getAttributesValue().get(i);
                    attr = attr + "- "+key + " : " + value + "\n";
                }

                attributes.setText(attr);
            }
            if (productDescriptionModel.getDescription()!=null){
                description.setText(productDescriptionModel.getDescription());
            }
        }else{
            productDescriptionModel.setTitle("Opps!");
            title.setText("No pudimos encontrar el producto, vuelve a intentarlo mas tarde");

        }


    }


}


