package com.axelfernandez.jay.Activitys;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.axelfernandez.jay.Adapters.ProductsAdapter;
import com.axelfernandez.jay.Interfaces.ProductsCallback;
import com.axelfernandez.jay.Models.ProductListModel;
import com.axelfernandez.jay.Utilities.ConextionApi;
import com.axelfernandez.jay.R;
import com.axelfernandez.jay.Utilities.SeacrhviewUtils;
import com.axelfernandez.jay.Utilities.VolleySingleton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    MaterialSearchView searchView;
    List<ProductListModel> productListModel;
    RecyclerView rv;
    ProductsAdapter adapter;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        String search = getIntent().getExtras().getString(getString(R.string.parameter_search));
        String searchName = getIntent().getExtras().getString(getString(R.string.parameter_search_title));

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.result_of)+searchName);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.jay_is_searching));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ConextionApi conextionApi = new ConextionApi();

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(conextionApi.getProducts(getString(R.string.baseUrlApi), search, new ProductsCallback() {
            @Override
            public void response(List<ProductListModel> productListModels) {
                productListModel= productListModels;
            }
        }));

        adapter =  new ProductsAdapter(productListModel,this);

        VolleySingleton.getInstance(this).getRequestQueue().addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                rv.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(SeacrhviewUtils.search(getApplicationContext(),query));
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seachmenu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


}
