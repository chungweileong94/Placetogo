package com.example.chungwei.placetogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String fragment_nav_backstack_tag = "main_nav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigate to home fragment
        navigateFragment(HomeFragment.newInstance(), false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
                return;
            }

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_nav_item:
                navigateFragment(HomeFragment.newInstance(), true);
                break;

            case R.id.mission_nav_item:
                Intent intent = new Intent(this, ChallengeActivity.class);
                startActivity(intent);
                break;

            case R.id.near_by_nav_item:
                navigateFragment(NearByFragment.newInstance(), true);
                break;

            case R.id.achievement_nav_item:
                navigateFragment(AchievementFragment.newInstance(), true);
                break;

            case R.id.settings_nav_item:
                break;
        }

        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);

        return true;
    }

    //used for navigate fragment
    private void navigateFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation,
                R.anim.fade_in_animation, R.anim.fade_out_animation);
        transaction.replace(R.id.content_frameLayout, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(fragment_nav_backstack_tag);
        }

        transaction.commit();
    }
}
