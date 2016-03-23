package com.tecc0.libraryplay;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity activity;

    public ApplicationTest(){
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception{
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        this.activity = getActivity();
    }

    @After
    public void tearDown() throws Exception{
        super.tearDown();
    }

    @Test
    public void checkPrecondition() {
        // HomeのTextViewのタイトルがあっているか
        onView(ViewMatchers.withId(R.id.home_title)).check(ViewAssertions.matches(ViewMatchers.withText("Study Libraries")));
        // FABが表示されているか
        onView(ViewMatchers.withId(R.id.fab)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}