/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sharedpreferencefavorites;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.rogergcc.sharedpreferencefavorites.fragments.FavoriteFragment;
import com.rogergcc.sharedpreferencefavorites.fragments.RickAndMortyFragment;
import com.rogergcc.sharedpreferencefavorites.model.RickMorty;

public class NavigationActivity extends AppCompatActivity
        implements
//        NavigationView.OnNavigationItemSelectedListener,
        FavoriteFragment.OnListFragmentInteractionListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //agregarToolbar();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        navigationView.setNavigationItemSelectedListener(this);


        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        drawer.setVerticalFadingEdgeEnabled(true);

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawer.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_home:
                fragmentoGenerico = new RickAndMortyFragment();
                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_favorites:

                fragmentoGenerico = new FavoriteFragment();

                break;
            case R.id.nav_tools:
//                fragmentoGenerico = new FragmentoServicios();


                break;
            case R.id.nav_share:
                //startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
            case R.id.nav_send:
                //startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
        }


        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.principal_container, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
//            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
//            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    private void initToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        final ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            // Poner ícono del drawer toggle
//            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
//
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        Fragment genericFragment = null;
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//
//        switch (item.getItemId()) {
//            case R.id.nav_home:
//                genericFragment = new RickAndMortyFragment();
//                break;
//            case R.id.nav_gallery:
//
//                break;
//            case R.id.nav_favorites:
//
//                genericFragment = new FavoriteCharactersFragment();
//
//                break;
//            case R.id.nav_tools:
////                fragmentoGenerico = new FragmentoServicios();
//
//
//                break;
//            case R.id.nav_share:
//                //startActivity(new Intent(this, ActividadConfiguracion.class));
//                break;
//            case R.id.nav_send:
//                //startActivity(new Intent(this, ActividadConfiguracion.class));
//                break;
//        }
//
//
//        if (genericFragment != null) {
//            fragmentManager
//                    .beginTransaction()
//                    .replace(R.id.principal_container, genericFragment)
//                    .commit();
//        }
//
//
//        setTitle(item.getTitle());
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


    @Override
    public void onListFragmentInteraction(RickMorty item) {

    }
}
