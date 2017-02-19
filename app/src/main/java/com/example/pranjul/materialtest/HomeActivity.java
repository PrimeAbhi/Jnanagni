package com.example.pranjul.materialtest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static HomeActivity currObject;

    String title = "Home";
    long lastPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currObject = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Create Drawer Layout
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //Setup Drawer View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
            setFragment(new HomeFragment(), title);
        } else {
            title = savedInstanceState.getString("title");
            setTitle(title);
        }
        //On Header Icon Touch
        View headerView = navigationView.getHeaderView(0);
        ImageView headerImage = (ImageView) headerView.findViewById(R.id.logo);
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastPress > 3000) {
                    Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_LONG).show();
                    lastPress = currentTime;
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        title = item.getTitle().toString();
        if (id == R.id.nav_login) {
            setFragment(new RegisterFragment(), title);
        } else if (id == R.id.nav_home) {
            setFragment(new HomeFragment(), title);
        } else if (id == R.id.nav_events) {
            startActivity(new Intent(HomeActivity.currObject, Main3Activity.class));
        } else if (id == R.id.nav_location) {
            setFragment(new LocationFragment(), title);
        } else if (id == R.id.nav_share) {
            setFragment(new FeedbackFragment(), title);
        } else if (id == R.id.nav_send) {
            setFragment(new FeedbackFragment(), title);
        } else if (id == R.id.nav_contact) {
            setFragment(new ContactFragment(), title);
        }
        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(final Fragment fragment, final String title) {
        //Set Title
        setTitle(title);
        //Set Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
