package com.axelfernandez.jay.Utilities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.axelfernandez.jay.Adapters.CategoryAdapter;
import com.axelfernandez.jay.Adapters.ProductsAdapter;
import com.axelfernandez.jay.Interfaces.CategoryCallBack;
import com.axelfernandez.jay.Interfaces.ProductDescriptionCallback;
import com.axelfernandez.jay.Interfaces.ProductDescriptionCallbackTextPlain;
import com.axelfernandez.jay.Interfaces.ProductsCallback;
import com.axelfernandez.jay.Main;
import com.axelfernandez.jay.Models.CategoryModel;
import com.axelfernandez.jay.Models.ProductDescriptionModel;
import com.axelfernandez.jay.Models.ProductListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class Connect to Mercadolibre's Api
 * Use Volley and this class not need to open a new tread because volley do that
 */
public class ConextionApi {


    List<ProductListModel> productListModels;
    List <CategoryModel> categoryList;
    ProductDescriptionModel productDescriptionModel;
    String description;
    
    /**
     * Search all products from MercadoLibre Argentina with a filter
     *
     * @param host    send a String from String.xml Constant
     * @param search  a filter whit will search

     *
     */
    public JsonObjectRequest getProducts(String host, String search, ProductsCallback callback) {
        String urlContext = "/sites/MLA/search?" + search;
        productListModels = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                host + urlContext,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int total = Integer.valueOf(response.getJSONObject(StringUtility.LABEL_PAGING).getString(StringUtility.LABEL_TOTAL));
                            if (total != 0) {
                                JSONArray results = response.getJSONArray(StringUtility.LABEL_RESULTS);
                                for (int i = 0; i < results.length(); i++) {
                                    ProductListModel product = new ProductListModel();
                                    JSONObject JsonProduct = results.getJSONObject(i);
                                    product.setName(JsonProduct.getString(StringUtility.LABEL_TITLE));
                                    product.setImageUrl(JsonProduct.getString(StringUtility.LABEL_THUMBNAIL));
                                    product.setIdProduct(JsonProduct.getString(StringUtility.LABEL_ID));
                                    product.setPrice("$" + JsonProduct.getString(StringUtility.LABEL_PRICE));
                                    productListModels.add(product);
                                }
                            }else{
                                ProductListModel notfound = new ProductListModel();
                                notfound.setName(StringUtility.JAY_NOT_FOUND);
                                notfound.setImageUrl(null);
                                notfound.setIdProduct(null);
                                notfound.setPrice(null);
                                productListModels.add(notfound);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ProductListModel notfound = new ProductListModel();
                        notfound.setName(StringUtility.JAY_NOT_FOUND);
                        notfound.setImageUrl(null);
                        notfound.setIdProduct(null);
                        notfound.setPrice(null);
                        productListModels.add(notfound);

                        Log.e("MELIError", "Error in request Json: " + error.getMessage());

                    }
                }
        );
        callback.response(productListModels);
        return jsonObjectRequest;
    }

    /**
    Idem for getProducts, just don't need  a Search
     */

    public JsonArrayRequest getCategory(String host, CategoryCallBack callBack) {
        String urlContext = "/sites/MLA/categories";
        categoryList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                host + urlContext,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() != 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    CategoryModel categoryModel = new CategoryModel();
                                    categoryModel.setId(response.getJSONObject(i).getString(StringUtility.LABEL_ID));
                                    categoryModel.setName(response.getJSONObject(i).getString(StringUtility.LABEL_NAME));
                                    categoryList.add(categoryModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("MELIJsonException", e.getMessage());
                                }
                            }
                        } else {
                            CategoryModel notfound = new CategoryModel();
                            notfound.setName(StringUtility.JAY_PROBLEM);
                            notfound.setId(null);
                            categoryList.add(notfound);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CategoryModel notfound = new CategoryModel();
                notfound.setName(StringUtility.JAY_PROBLEM);
                notfound.setId(null);
                categoryList.add(notfound);
                Log.e("MELIError", "Error in request Json: " + error.getMessage());
            }
        });
        callBack.response(categoryList);
        return jsonArrayRequest;

    }


    /**Get one item to display in a view using a itemID*/
    public JsonObjectRequest getItem(String host, String urlContext, ProductDescriptionCallback callback){
        productDescriptionModel = new ProductDescriptionModel();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                host + urlContext,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            productDescriptionModel.setId(response.getString(StringUtility.LABEL_ID));
                            productDescriptionModel.setTitle(response.getString(StringUtility.LABEL_TITLE));
                            productDescriptionModel.setPrice(response.getString(StringUtility.LABEL_PRICE));
                            for (int i = 0; i < response.getJSONArray(StringUtility.LABEL_PICTURES).length(); i++) {
                                String urlPictures = response.getJSONArray(StringUtility.LABEL_PICTURES).getJSONObject(i).getString(StringUtility.LABEL_URL);
                                productDescriptionModel.setPicturesUrl(urlPictures);
                            }
                            for (int j = 0; j < response.getJSONArray(StringUtility.LABEL_ATTRIBUTE).length(); j++) {
                                String key = response.getJSONArray(StringUtility.LABEL_ATTRIBUTE).getJSONObject(j).getString("name");
                                String value = response.getJSONArray(StringUtility.LABEL_ATTRIBUTE).getJSONObject(j).getString(StringUtility.LABEL_VALUE_NAME);
                                productDescriptionModel.setAttributesKey(key);
                                productDescriptionModel.setAttributeValue(value);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            productDescriptionModel.setId(null);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MELIError", "Error in request Json: " + error.getMessage());

            }
        }
        );
        callback.response(productDescriptionModel);
        return jsonObjectRequest;
    }


    /**
     * Get the description from a item using a itemID*/
    public JsonObjectRequest getItemdesc(String host, String urlContextdesc, final ProductDescriptionCallbackTextPlain callbackdesc) {
        JsonObjectRequest jsonObjectRequestDesc = new JsonObjectRequest(
                Request.Method.GET,
                host + urlContextdesc,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callbackdesc.response(response.getString(StringUtility.LABEL_PLAIN_TEXT));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callbackdesc.response(null);
                Log.e("MELIError", "Error in request Json: " + error.getMessage());
            }
        });
        return jsonObjectRequestDesc;
    }

}





