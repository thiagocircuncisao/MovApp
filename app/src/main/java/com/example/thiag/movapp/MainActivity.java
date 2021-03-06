package com.example.thiag.movapp;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thiag.movapp.fragments.movieDetail;
import com.example.thiag.movapp.fragments.movieListBemAvaliados;
import com.example.thiag.movapp.fragments.movieListCartaz;
import com.example.thiag.movapp.fragments.movieListPopulares;
import com.example.thiag.movapp.fragments.movieListProcurar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, movieDetail.OnFragmentInteractionListener, movieListCartaz.OnFragmentInteractionListener, movieListBemAvaliados.OnFragmentInteractionListener, movieListPopulares.OnFragmentInteractionListener, movieListProcurar.OnFragmentInteractionListener{
    MenuItem menuItem;
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

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new movieListCartaz()).commit();
            navigationView.setCheckedItem(R.id.filmesCartaz);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if(menuItem != null)
                menuItem.setEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Chama o fragment de pesquisa
        if(id == R.id.action_search ){
            menuItem = item;
            item.setEnabled(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new movieListProcurar()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //Abaixo são as mudanças de fragment com base nos cliques no menu
        if (id == R.id.filmesCartaz) {
            if(menuItem != null)
                menuItem.setEnabled(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new movieListCartaz()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.filmesPopulares) {
            if(menuItem != null)
                menuItem.setEnabled(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new movieListPopulares()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.filmesAvaliados) {
            if(menuItem != null)
                menuItem.setEnabled(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new movieListBemAvaliados()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
