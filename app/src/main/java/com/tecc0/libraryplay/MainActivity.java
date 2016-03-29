package com.tecc0.libraryplay;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tecc0.libraryplay.fragment.AboutFragment;
import com.tecc0.libraryplay.fragment.GalleryFragment;
import com.tecc0.libraryplay.fragment.HomeFragment;
import com.tecc0.libraryplay.fragment.RetrofitFragment;
import com.tecc0.libraryplay.fragment.ScrollingFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.ButterKnife.findById;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initToolbar();
        initFragment();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                replaceFragment(HomeFragment.create());
                break;
            case R.id.nav_gallery:
                replaceFragment(GalleryFragment.create());
                break;
            case R.id.nav_retrofit:
                replaceFragment(RetrofitFragment.create());
                break;
            case R.id.nav_recycler:
                break;
            case R.id.nav_tour:
                startActivity(new Intent(this, TourActivity.class));
                break;
            case R.id.nav_coordinator:
                replaceFragment(ScrollingFragment.create());
                break;
            case R.id.nav_share:
                shareAction();
                break;
            case R.id.nav_about:
                replaceFragment(AboutFragment.create());
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fab)
    void fabClicked (View view) {
        Snackbar.make(view, "I am Android!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void initToolbar () {
        Toolbar toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findById(this, R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void shareAction() {
        String subject = "Hello share!!";
        String url = "https://tecc0.com";
        String hashTag = "#tecco";

        String textDefault = String.format("/%s /%s", subject, url);
        String textTwitter = String.format("/%s /%s /%s", subject, url, hashTag);
        String chooserTitle = "Share";

        Intent baseIntent = new Intent(Intent.ACTION_SEND);
        baseIntent.setType("text/plain");
        baseIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        baseIntent.putExtra(Intent.EXTRA_TEXT, textDefault);

        PackageManager pm = this.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(baseIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfoList.isEmpty()) {
            this.startActivity(Intent.createChooser(baseIntent, chooserTitle));
        } else {
            List<LabeledIntent> shareIntentList = new ArrayList<>();
            Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(this.getPackageManager()));
            for (ResolveInfo resolveInfo : resolveInfoList) {
                Intent shareIntent = new Intent(baseIntent);

                String packageName = resolveInfo.activityInfo.packageName;
                switch (packageName) {
                    // Facebook official app (only url
                    case "com.facebook.katana":
                        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                        break;
                    // Twitter official app (with hashtag
                    case "com.twitter.android":
                        shareIntent.putExtra(Intent.EXTRA_TEXT, textTwitter);
                        break;
                    // add others
                }

                shareIntent.setClassName(packageName, resolveInfo.activityInfo.name);
                shareIntentList.add(new LabeledIntent(shareIntent, packageName, resolveInfo.loadLabel(pm), resolveInfo.icon));
            }

            Intent chooserIntent = Intent.createChooser(shareIntentList.remove(shareIntentList.size() - 1), chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new LabeledIntent[shareIntentList.size()]));
            this.startActivity(chooserIntent);
        }
    }

    private void initFragment() {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_view, HomeFragment.create(), HomeFragment.create().getClass().getSimpleName());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void replaceFragment(Fragment fragment) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit);
        ft.replace(R.id.content_view, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack(null);
        ft.commit();
    }
}
