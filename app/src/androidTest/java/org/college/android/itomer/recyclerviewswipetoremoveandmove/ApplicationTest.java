package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        RecyclerAdapterTest t = new RecyclerAdapterTest();
        try {
            t.testGetItemCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}