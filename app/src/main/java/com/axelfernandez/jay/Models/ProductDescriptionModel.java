package com.axelfernandez.jay.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDescriptionModel {
    private String id;
    private String title;
    private String price;
    private List<String> picturesUrl;
    private List<String> attributesKey;
    private List<String> attributesValue;
    private String description;



    public ProductDescriptionModel(){
        this.attributesKey = new ArrayList<>();
        this.picturesUrl= new ArrayList<>();
        this.attributesValue = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public List<String> getPicturesUrl() {
        return picturesUrl;
    }

    public void setPicturesUrl(String picturesUrl) {
        this.picturesUrl.add(picturesUrl);
    }

    public void setId(String id) {this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAttributesKey() {
        return attributesKey;
    }

    public void setAttributesKey(String key) {
        this.attributesKey.add(key);
    }

    public List<String> getAttributesValue() {
        return attributesValue;
    }

    public void setAttributeValue(String value) {
        this.attributesValue.add(value);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
