package com.axelfernandez.jay.Utilities;

import android.content.Context;
import android.content.Intent;

import com.axelfernandez.jay.Activitys.ProductsActivity;

public class SeacrhviewUtils {

    /**
     * This class just start a search in ProductsActivity
     * @param context send a context for the intent
     * @param query this is a word will search.
     *              The prefix "q=" is for search item by word.
     *              Categoty Adapter send a prefix "category="
     * */
    public static Intent search(final Context context, String query){
        final Intent i = new Intent(context,ProductsActivity.class);
        i.putExtra("search","q="+query);
        i.putExtra("searchName",query);
        return i;


    }
}
