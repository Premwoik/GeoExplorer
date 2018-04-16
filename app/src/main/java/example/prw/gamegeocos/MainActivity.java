package example.prw.gamegeocos;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;

import java.util.Objects;

import example.prw.gamegeocos.api.ApiUtils;
import example.prw.gamegeocos.api.model.SecurityToken;
import example.prw.gamegeocos.api.model.UserCredentials;
import example.prw.gamegeocos.api.model.UserRegistrationForm;
import example.prw.gamegeocos.api.service.UserService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String CURRENT_TAG = null;
    Handler mHandler = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        ImageView view;
        Mapbox.getInstance(this, getString(R.string.token_mb));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerlayout = navigationView.getHeaderView(0);
        headerlayout.findViewById(R.id.nav_header_image).
                setOnClickListener(listener -> {
                    loadFragment(new UserProfileFragment(), UserProfileFragment.TAG);
                    drawer.closeDrawer(GravityCompat.START);
                });

        loadFragment(new MapFragment(), MapFragment.TAG);

    }

    //    @OnClick(R.id.imageView)
//    public void clikced(ImageView view){
//        Toast.makeText(this, "Klikam obrazek :)", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        switch (id) {
            case R.id.nav_map:
                loadFragment(new MapFragment(), MapFragment.TAG);
                break;
            case R.id.nav_ranking:
                loadFragment(new RankingFragment(), RankingFragment.TAG);
                break;
            case R.id.nav_logout:
                ApiUtils
                        .getService(UserService.class)
                        .register(new UserRegistrationForm("abc@gm.pl", "galgan69", "alarmoasda"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn(x-> {x.printStackTrace(); return null;})
                        .subscribe(x-> Toast.makeText(getApplicationContext(), String.valueOf(x.code()), Toast.LENGTH_SHORT).show());
//                ApiUtils
//                        .getService(UserService.class)
//                        .login(new UserCredentials("test", "test"))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .onErrorReturn(x -> new SecurityToken("ERROR"))
//                        .subscribe(x ->
//                        {
//                            Toast.makeText(getApplicationContext(), x.getToken(), Toast.LENGTH_SHORT).show();
//                            ApiUtils.getService(UserService.class)
//                                    .getUsers(x.getToken())
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(y -> Log.i("MAIN", y.string()));
//                        });
                break;
            default:
                Toast.makeText(getApplicationContext(), "No action yet! " + id, Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(final Fragment fragment, final String tag) {
        if (Objects.equals(CURRENT_TAG, tag)) return;

        Runnable pendingRunnable = () -> {
            // update the main content by replacing fragments
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
//                fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.replace(R.id.frame, fragment, tag);
            fragmentTransaction.commit();
        };
        mHandler.postDelayed(pendingRunnable, 250);
        CURRENT_TAG = tag;
    }

}
