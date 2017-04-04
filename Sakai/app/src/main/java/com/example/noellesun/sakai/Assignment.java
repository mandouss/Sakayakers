package com.example.noellesun.sakai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Assignment extends AppCompatActivity {

    private String TAG = sites.class.getSimpleName();
    static ArrayList<HashMap<String, String>> asnList = new ArrayList<>();
    private ProgressDialog pDialog;
    String fixurl = "https://sakai.duke.edu/direct/assignment/site/";
    String cookiestr;
    String siteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        siteid = getIntent().getExtras().getString("SiteID");
        Log.i("ASSIGNiteid:",siteid);
        final CookieManager cookieManager = CookieManager.getInstance();
        cookiestr = cookieManager.getCookie("https://sakai.duke.edu/portal");
        new Assignment.GetAssign().execute();
        //findViewById(R.id.btn8).setOnClickListener(siteClickEvent);
        //findViewById(R.id.sites).setOnClickListener(sitesclick);
    }
    final OnClickListener siteClickEvent = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(),"sites clicked",Toast.LENGTH_SHORT).show();
        }
    };
    final OnClickListener sitesclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toSites = new Intent(Assignment.this, sites.class);
            startActivity(toSites);
        }
    };

    private class GetAssign extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Assignment.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = fixurl + siteid + ".json";
            String jsonStr = sh.makeServiceCall(url, cookiestr);
            Log.e(TAG, "ASSIGNJSON: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray assignments = jsonObj.getJSONArray("assignment_collection");
                    for (int i = 0; i < assignments.length(); i++) {
                        JSONObject c = assignments.getJSONObject(i);
                        String itemName = c.getString("gradebookItemName");
                        String dueTime = c.getString("dueTimeString");
                        String startTime = c.getString("openTimeString");
                        Log.e("ASSINITEMNAME", itemName);

                        HashMap<String, String> eachAssign = new HashMap<>();
                        eachAssign.put("itemName", itemName);
                        eachAssign.put("dueTime", dueTime);
                        eachAssign.put("startTime", startTime);
                        asnList.add(eachAssign);
                        Log.i("ASNLIST",asnList.toString());
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            Log.e("background","done!");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }
}


