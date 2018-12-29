package com.axelfernandez.jay;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.axelfernandez.jay.Activitys.DescriptionActivity;
import com.axelfernandez.jay.Activitys.ProductsActivity;
import com.axelfernandez.jay.Models.ProductDescriptionModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    /**
     * ActivityMain Test
     * */
    @Test
    public void TestActivityMain(){
        //Right way
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent(appContext,Main.class);
        appContext.startActivity(i);
    }
    @Test

    public void TestListResult(){
        //Right way
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent(appContext,ProductsActivity.class);
        i.putExtra("search","q=Luces");
        i.putExtra("search", "Luces");
        appContext.startActivity(i);
    }
    @Test

    /**
     * Test ListResult
     * */
    public void TestListResultNotFound(){
        //Incorrect Result, don't crash, show that not be found.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent(appContext,ProductsActivity.class);
        i.putExtra("search","q=Lucesasdasdasdas");
        i.putExtra("searchName", "Lucesasdasdasd");
        appContext.startActivity(i);
    }

    /**
     * Test Description Item
     * */
    @Test
    public void DescriptionItemIncorrect() {
        //Test with item incorrecto or lost
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent(appContext,DescriptionActivity.class);
        i.putExtra("descriptionId","asd");
        appContext.startActivity(i);
    }
    @Test
    public void DescriptionItemCorrect(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent i = new Intent(appContext,DescriptionActivity.class);
        i.putExtra("descriptionId","MLA732501464");
        appContext.startActivity(i);

    }

}
