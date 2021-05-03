package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Accueil extends AppCompatActivity {

    private static final String HTTP_BASE = "https://belatar.name/";
    private static final String HTTP_WS_STD = "rest/profile.php?login=test&passwd=test&id=9998";
    private Etudiant etd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Accueil.class.getSimpleName(),"on est dans OnCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnResume");
        JsonObjectRequest req =new JsonObjectRequest(Request.Method.GET, HTTP_BASE + HTTP_WS_STD, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {

                try {
                    etd = new Etudiant(json.getInt("id"),json.getString("nom"),
                            json.getString("prenom"),json.getString("classe"),json.getString("phone"),null);
                    VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(HTTP_BASE+"/images/"+json.getString("photo"),
                            new ImageLoader.ImageListener() {
                                @Override
                                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                    Log.d(Accueil.class.getSimpleName(),"Image telechargee");
                                    etd.setPhoto(response.getBitmap());
                                    EditText txtNom=findViewById(R.id.txtNom);
                                    EditText txtPrenom=findViewById(R.id.txtPrenom);
                                    EditText txtClasse=findViewById(R.id.txtClasse);
                                    ImageView img=findViewById(R.id.imgProfile);
                                    img.setImageBitmap(etd.getPhoto());
                                    txtNom.setText(etd.getNom());
                                    txtPrenom.setText(etd.getPrenom());
                                    txtClasse.setText(etd.getClasse());
                                }

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(Accueil.class.getSimpleName(),error.getMessage());
                                }
                            });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(Accueil.class.getSimpleName(),json.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Accueil.class.getSimpleName(),error.getMessage());
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnRestart");
    }

    public void monClick(View view) {
        Toast.makeText(this,"on a cliqué",Toast.LENGTH_LONG).show();
    }
}