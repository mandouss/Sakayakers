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


public class sites extends AppCompatActivity {
    public void rowClick(View view) {
        switch(view.getId()) {
            case R.id.ece550:
                Intent toSites1 = new Intent(sites.this, eachSite.class);
                startActivity(toSites1);
                break;

            case R.id.ece590:
                Intent toSites2 = new Intent(sites.this, eachSite.class);
                startActivity(toSites2);
                break;

            case R.id.ece651:
                Intent toSites3 = new Intent(sites.this, eachSite.class);
                startActivity(toSites3);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);
        findViewById(R.id.sites).setOnClickListener(sitesclick);
    }
    final OnClickListener sitesclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toSites = new Intent(sites.this, sites.class);
            startActivity(toSites);
        }
    };
}
