package com.axelfernandez.jay;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.axelfernandez.jay.Adapters.CategoryAdapter;
import com.axelfernandez.jay.Adapters.ProductsAdapter;
import com.axelfernandez.jay.Interfaces.CategoryCallBack;
import com.axelfernandez.jay.Models.CategoryModel;
import com.axelfernandez.jay.Utilities.ConextionApi;
import com.axelfernandez.jay.Utilities.SeacrhviewUtils;
import com.axelfernandez.jay.Utilities.VolleySingleton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {
    MaterialSearchView searchView;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    List<CategoryModel> categoryModelsList;
    RecyclerView rv;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =  findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search_view);
        rv = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.jay_is_searching));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ConextionApi conextionApi = new ConextionApi();

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        VolleySingleton.getInstance(this).addToRequestQueue(conextionApi.getCategory(getString(R.string.baseUrlApi), new CategoryCallBack() {
            @Override
            public void response(List<CategoryModel> categoryModels) {
                categoryModelsList= categoryModels;
            }
        }));
        adapter = new CategoryAdapter(categoryModelsList,this);


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
