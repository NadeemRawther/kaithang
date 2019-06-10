package com.example.greeshma.bloodbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);

        textView = (TextView)headerLayout.findViewById(R.id.website);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.kaithang.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        Fragment fragment = null;
        fragment = new DonorsPage();
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.res_for_actionbaricon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.instaicon) {
            Uri uri = Uri.parse("https://instagram.com/kaithang_tvm?utm_source=ig_profile_share&igshid=3v0j0p43m63v");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            //intent.setPackage("com.instagram.android");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_donors) {
            fragment = new DonorsPage();
        } else if (id == R.id.nav_register) {
            fragment = new RegisterFragment();
        } else if (id == R.id.nav_signIn) {
            fragment = new SignIn_Page();
        } else if (id == R.id.nav_contactus) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Contact us");
            alert.setMessage("Email: kaithangbloodbank@gmail.com\nPhone: 9207663879");
            alert.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String ph = "9207663879";
                    //Toast.makeText(getApplicationContext(), ph, Toast.LENGTH_LONG).show();
                    //  ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
                    startActivity(intent);
                }
            });
            alert.setNegativeButton("Cancel",null);
            alert.show();
        } else if (id == R.id.nav_about) {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("About");
            alert.setMessage("This is an android based application which will play an important role in saving the life of human beings and which is also its main aim developed an android application will include all the relevant features to provide a means of communication between blood seekers,blood donors and blood banks. This application will help the users fast and easily in such a way that users can locate different volunteer blood donors in short time at emergency situation ");
            alert.setPositiveButton("OK",null);
            alert.show();
        }
         else if (id == R.id.helpline){
            String ph = "9207663879";
            //Toast.makeText(getApplicationContext(), ph, Toast.LENGTH_LONG).show();
            //  ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.CALL_PHONE}, 1);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
            startActivity(intent);
        }
        else if(id == R.id.helpline2){

            String ph = "9946863342";
            //Toast.makeText(getApplicationContext(), ph, Toast.LENGTH_LONG).show();
            //  ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.CALL_PHONE}, 1);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
            startActivity(intent);

        }else if (id == R.id.helpline3){
            String ph = "9495469354";
            //Toast.makeText(getApplicationContext(), ph, Toast.LENGTH_LONG).show();
            //  ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.CALL_PHONE}, 1);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
            startActivity(intent);


        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
