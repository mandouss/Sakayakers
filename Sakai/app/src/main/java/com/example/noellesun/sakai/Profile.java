package com.example.noellesun.sakai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    String userid;
    String fixurl = "https://sakai.duke.edu/direct/profile/";
    private String TAG = Login.class.getSimpleName();
    String cookiestr = "";
    private ProgressDialog pDialog;
    String jsonname;
    String jsonemail;
    String jsonnickname;
    String jsondegree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final CookieManager cookieManager = CookieManager.getInstance();
        cookiestr = cookieManager.getCookie("https://sakai.duke.edu/portal");
        Log.e(TAG,cookiestr);
        Bundle b = getIntent().getExtras();
        userid = b.getString("USERID");
        Log.e("PROFILE", userid);
        new GetProfile().execute();

        findViewById(R.id.sitesbtn).setOnClickListener(sitesclick);

    }

    final View.OnClickListener sitesclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toSites = new Intent(Profile.this, sites.class);
            toSites.putExtra("ID","eachSite");
            startActivity(toSites);
        }
    };
    void loadText(){
        TextView name = (TextView) findViewById(R.id.nameview);
        name.setText(jsonname);
        TextView email = (TextView) findViewById(R.id.emailview);
        email.setText(jsonemail);
        TextView nickname = (TextView) findViewById(R.id.nicknameview);
        nickname.setText(jsonnickname);
        TextView degree = (TextView) findViewById(R.id.degreeview);
        degree.setText(jsondegree);
    }
    private class GetProfile extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Profile.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = fixurl + userid.toString() + ".json";
            Log.e("jsonurl!",url);
            Log.e("profileCookiestr",cookiestr);
            String jsonStr = sh.makeServiceCall(url, cookiestr);

            Log.e("profilejson", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    jsonname = jsonObj.getString("displayName");
                    jsonemail = jsonObj.getString("email");
                    jsonnickname = jsonObj.getString("nickname");
                    jsondegree = jsonObj.getString("course");
                    loadText();
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.e("postexe", "prepare to list");//not execute this!!???
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
}
