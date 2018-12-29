package com.axelfernandez.jay.Interfaces;

import com.axelfernandez.jay.Models.CategoryModel;
import com.axelfernandez.jay.Models.ProductListModel;

import java.util.List;

public interface ProductsCallback {

    void response(List<ProductListModel> productListModels);

}
