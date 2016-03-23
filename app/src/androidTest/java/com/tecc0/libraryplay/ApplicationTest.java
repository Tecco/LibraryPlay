package com.tecc0.libraryplay;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

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
        // FABをクリック
        onView(ViewMatchers.withId(R.id.fab)).perform(click());

        //openDrawer();
        // Galleryをクリック
        //onView(ViewMatchers.withId(R.id.nav_gallery)).perform(click());

        //closeDrawer();
        // Galleryをクリック
        //onView(ViewMatchers.withId(R.id.nav_retrofit)).perform(click());
    }

    private void openDrawer () {
        //new Thread(() -> {
            ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).openDrawer(Gravity.LEFT);
        //}).start();

    }

    private void closeDrawer () {
        //new Thread(() -> {
            ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).closeDrawer(Gravity.LEFT);
        //}).start();
    }
}