package com.tecc0.libraryplay;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

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
    public void testFirstDisplay() {
        // HomeのTextViewのタイトルがあっているか
        onView(withId(R.id.home_title)).check(matches(withText("Study Libraries")));
        // FABが表示されて、クリックできるか
        onView(withId(R.id.fab)).check(matches(isDisplayed())).perform(click());
        // SnackBarのテキストがあっているか
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("I am Android!"))).check(matches(isDisplayed()));
    }

    @Test
    public void testDrawerOpenClose() {
        openDrawer();
        closeDrawer();
    }

    @Test
    public void testShowGallery() {
        openDrawer();
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_gallery));
        onView(withId(R.id.gallery_listview)).check(matches(isDisplayed()));
        //onData(allOf(startsWith("[1]"), is(instanceOf(String.class)))).perform(click());
        closeDrawer();
    }

    @Test
    public void testShowRetrofit() {
        openDrawer();
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_retrofit));
        onView(withId(R.id.retrofit_listview)).check(matches(isDisplayed()));
        // FIXME: ListViewのItemの中身を拾う方法がわからんぼ
        //onData(hasToString(startsWith("max"))).inAdapterView(withId(R.id.weather_item_max_textView)).atPosition(0).perform(click());
        SystemClock.sleep(2000);
        // ListViewでやっとクリックできたんご！！
        onData(anything()).inAdapterView(withId(R.id.retrofit_listview)).atPosition(0).perform(click());
        //onView(withId(R.id.toolbar)).check(matches(withText("Tokyo : JP")));
        //onData(withId(R.id.retrofit_listview)).atPosition(0).perform(click());

        closeDrawer();
    }

    @Test
    public void testShowTour() {
        openDrawer();
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_tour));
        onView(withId(R.id.introViewPager)).check(matches(isDisplayed()));
        // skipボタンが表示か
        onView(withId(R.id.skipIntroButton)).check(matches(isDisplayed()));
        // 左右にスワイプできるか
        onView(withId(R.id.introViewPager)).perform(swipeLeft()).perform(swipeRight());
        // 4ページ目に進めるか
        onView(withId(R.id.introViewPager)).perform(swipeLeft()).perform(swipeLeft()).perform(swipeLeft());
        // skipボタンが非表示か
        onView(withId(R.id.skipIntroButton)).check(matches(not(isDisplayed())));
        // doneボタンが表示され、クリックできるか
        onView(withId(R.id.doneSlideButton)).check(matches(isDisplayed())).perform(click());
        // TourActivityがfinish()してMainActivityに遷移するか
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowCoodinator() {
        openDrawer();
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_coordinator));
        onView(withId(R.id.coordinator_fab)).check(matches(isDisplayed()));
        // swipeするとfabが消える
        onView(withId(R.id.app_bar)).perform(swipeUp());
        onView(withId(R.id.coordinator_fab)).check(matches(not(isDisplayed())));
        closeDrawer();
    }

    @Test
    public void testShowAbout() {
        openDrawer();
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_about));
        // AboutのTextが表示されていて、内容があっているか
        onView(withId(R.id.about_textview)).check(matches(isDisplayed()))
                .check(matches(withText(getActivity().getString(R.string.about_fragment_title))));
        closeDrawer();
    }

    /*
     * 以下、メソッド
     */

    private void openDrawer () {
        getActivity().runOnUiThread(() -> ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).openDrawer(Gravity.LEFT));
        SystemClock.sleep(1000);
    }

    private void closeDrawer() {
        getActivity().runOnUiThread(() -> ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout)).closeDrawer(Gravity.LEFT));
        SystemClock.sleep(1000);
    }

}