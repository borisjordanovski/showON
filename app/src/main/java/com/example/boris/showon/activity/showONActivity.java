package com.example.boris.showon.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boris.showon.R;
import com.example.boris.showon.fragment.BaseFragment;
import com.example.boris.showon.fragment.LoginFragment;
import com.example.boris.showon.fragment.MainFragment;
import com.example.boris.showon.fragment.MyShowsFragment;
import com.example.boris.showon.fragment.ProfileFragment;
import com.example.boris.showon.fragment.SearchFragment;
import com.example.boris.showon.model.User;
import com.example.boris.showon.util.UIHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showONActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.OnFragmentInteractionListener {

    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView tvNavName;
    private TextView tvNavEmail;
    private AppBarLayout appBarLayout;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;

    public static final int MULTIPLE_PERMISSION_REQUEST = 123;


    private void initVar() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_on);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initVar();

        if (firebaseAuth.getCurrentUser() == null) {
            UIHelper.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, LoginFragment.newInstance(), false, 0, 0);
        } else {

            UIHelper.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, MainFragment.newInstance(), false, 0, 0);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        requestAppPermissions();
    }

    private void requestAppPermissions()    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission
                        .READ_EXTERNAL_STORAGE}, MULTIPLE_PERMISSION_REQUEST);
            }
        }
    }

    public void updateNavDrawerInfo(String userID) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String imageBase64 = sharedPref.getString("profile", "");

        final View header = navigationView.getHeaderView(0);
        final TextView tvNavHeaderNameSurname = (TextView) header.findViewById(R.id.tvNavHeaderNameSurname);
        final TextView tvNavHeaderEmail = (TextView) header.findViewById(R.id.tvNavHeaderEmail);
        final ImageView ivNavHeader = (ImageView) header.findViewById(R.id.imageView);
        if(!imageBase64.equals("")) {
            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivNavHeader.setImageBitmap(decodedByte);
        }
        myRef.child("user").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                for (int i = 0; i < dataSnapshot.getChildrenCount() ; i++) {
                    user.setName(dataSnapshot.getValue(User.class).getName());
                    user.setLastName(dataSnapshot.getValue(User.class).getLastName());
                    user.setEmail(dataSnapshot.getValue(User.class).getEmail());
                }
                tvNavHeaderNameSurname.setText(user.getName() + " "  + user.getLastName());
                tvNavHeaderEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (baseFragment instanceof MainFragment) {
            navigationView.getMenu().getItem(0).setChecked(true);
            setTitle("showON");
        } else if (baseFragment instanceof MyShowsFragment) {
            navigationView.getMenu().getItem(1).setChecked(true);
            setTitle("My Shows");
        } else if (baseFragment instanceof SearchFragment) {
            navigationView.getMenu().getItem(2).setChecked(true);
            setTitle("showON");
        } else if (baseFragment instanceof ProfileFragment) {
            navigationView.getMenu().getItem(3).setChecked(true);
            setTitle("Profile");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.show_on, menu);
        return false;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void hideActionBar(Boolean hide) {
        if (getSupportActionBar() != null) {
            if (hide) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().show();
            }
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            UIHelper.clearBackstack(getSupportFragmentManager());
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName());
            toolbar.setTitle(fragment.getTitle());
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (id == R.id.nav_my_shows) {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(!(fragment instanceof MyShowsFragment))  {
                UIHelper.clearBackstack(getSupportFragmentManager());
                UIHelper.addFragment(getSupportFragmentManager(), R.id.fragmentContainer, MyShowsFragment.newInstance(), true, 0, 0);
            }
        } else if (id == R.id.nav_search) {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(!(fragment instanceof SearchFragment))  {
                UIHelper.clearBackstack(getSupportFragmentManager());
                UIHelper.addFragment(getSupportFragmentManager(), R.id.fragmentContainer, SearchFragment.newInstance(), true, 0, 0);
            }
        } else if (id == R.id.nav_profile) {
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if(!(fragment instanceof ProfileFragment))  {
                UIHelper.clearBackstack(getSupportFragmentManager());
                UIHelper.addFragment(getSupportFragmentManager(), R.id.fragmentContainer, ProfileFragment.newInstance(), true, 0, 0);
            }
        } else if (id == R.id.nav_logout) {
            UIHelper.clearBackstack(getSupportFragmentManager());
            UIHelper.replaceFragment(getSupportFragmentManager(), R.id.fragmentContainer, LoginFragment.newInstance(), false, 0, 0);
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profile", "");
            editor.commit();
            firebaseAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(BaseFragment source, Bundle message, Class destination) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MULTIPLE_PERMISSION_REQUEST:
                if(grantResults.length > 0) {
                    boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(!readExternalStorage)  {
                        Toast.makeText(this, "The application won't work properly without these permissions", Toast.LENGTH_SHORT).show();
                        requestAppPermissions();
                    }
                }
                break;


            default:
                break;
        }
    }
}
