package com.example.noellesun.sakai;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class eachSite extends AppCompatActivity {
    public Button assignments;
    String userid;
    String siteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        Log.e("EachSite:", "got intent");
        String[] ids = b.getStringArray("IDS");

        userid = ids[0];
        siteid = ids[1];
        Log.e("EachSite:",userid);
        setContentView(R.layout.activity_each_site);
        //assignments = (Button)findViewById(R.id.Assignments);
        findViewById(R.id.Assignments).setOnClickListener(assignclick);
        findViewById(R.id.Gradebook).setOnClickListener(gradebookclick);
        findViewById(R.id.sitesbtn).setOnClickListener(sitesclick);
        findViewById(R.id.profilebtn).setOnClickListener(profilelclick);
    }
    final OnClickListener profilelclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toProfile = new Intent(eachSite.this, Profile.class);
            Bundle b = new Bundle();
            b.putString("USERID", userid);
            toProfile.putExtras(b);
            startActivity(toProfile);
        }
    };

    public final OnClickListener assignclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toAssignments = new Intent(eachSite.this, Assignment.class);
            startActivity(toAssignments);
        }
    };
    final OnClickListener sitesclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toSites = new Intent(eachSite.this, sites.class);
            toSites.putExtra("ID","eachSite");
            startActivity(toSites);
        }
    };
    final OnClickListener gradebookclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toGradebook = new Intent(eachSite.this, Gradebook.class);
            startActivity(toGradebook);
        }
    };

}
