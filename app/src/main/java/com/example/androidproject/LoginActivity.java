package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs= getApplicationContext()
                .getSharedPreferences("myprefs",MODE_PRIVATE);
        if (prefs.getBoolean(Utils.PREF_AUTOL,false)){
            //CheckBox cb= findViewById(R.id.autoLogin);
            //cb.isChecked(true);
            authenticate(prefs.getString(Utils.PREF_LOGIN,""),prefs.getString(Utils.PREF_PASSW,""));
        }
    }

    public void monClick(View view) {
        EditText txtLogin=findViewById(R.id.txtLogin);
        EditText txtPasswd=findViewById(R.id.txtPasswd);
        authenticate(txtLogin.getText().toString(),txtPasswd.getText().toString());

    }
    private void authenticate(String login, String pass){
        String params="?login="+login+"&passwd="+pass;
        JsonObjectRequest req =new JsonObjectRequest(Request.Method.GET, Utils.HTTP_BASE + Utils.HTTP_WS_LOGIN+params, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                if (json.has("error")){

                }else{
                    CheckBox cb= findViewById(R.id.autoLogin);
                    if (cb.isChecked()){
                       SharedPreferences prefs= getApplicationContext()
                               .getSharedPreferences("myprefs",MODE_PRIVATE);
                       SharedPreferences.Editor ed=prefs.edit();
                       ed.putString(Utils.PREF_LOGIN,login);
                       ed.putString(Utils.PREF_PASSW,pass);
                       ed.putBoolean(Utils.PREF_AUTOL,true);
                       ed.apply();
                    }
                    Intent i=new Intent(getApplicationContext(),Accueil.class);
                    startActivity(i);
                    LoginActivity.this.finish();
                }

                Log.d(LoginActivity.class.getSimpleName(),json.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LoginActivity.class.getSimpleName(),error.getMessage());
            }
        });

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }
}