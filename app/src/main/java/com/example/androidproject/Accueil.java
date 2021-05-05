package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Accueil extends AppCompatActivity {

    public static final String EXTRA_NOTE ="newNote" ;
    private static final String HTTP_BASE = "https://belatar.name/";
    private static final String HTTP_WS_STD = "rest/profile.php?login=test&passwd=test&id=9998&notes=true";
    public static final String EXTRA_STD_NAME ="stdName";
    private static final int ACT_NOTE=101 ;
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
        ListView l=findViewById(R.id.listNotes);
        String noteParam=(l==null?"":"&notes=true");
        Log.d(Accueil.class.getSimpleName(),"on est dans OnResume");
        JsonObjectRequest req =new JsonObjectRequest(Request.Method.GET, HTTP_BASE + HTTP_WS_STD, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {

                try {
                    etd = new Etudiant(json.getInt("id"),json.getString("nom"),
                            json.getString("prenom"),json.getString("classe"),json.getString("phone"),null);
                    if(json.has("notes")){
                        JSONArray ja=json.getJSONArray("notes");
                        for(int i=0;i<ja.length();i++){
                            JSONObject j=ja.getJSONObject(i);
                            etd.addNote(new Note(j.getString("label"),j.getDouble("score")));
                        }
                    }
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
                                    if(l!=null) {
                                        l.setAdapter(new NoteAdapter(Accueil.this,etd.getNotes()));
                                    }
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
        if(etd==null)
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Accueil.class.getSimpleName(),"on est dans OnRestart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(Accueil.class.getSimpleName(),"on esr dans onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }


    public void monClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                Toast.makeText(this, "Button cliquee", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnCall:
                Intent iCall=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+etd.getPhone()));
                startActivity(iCall);
                break;
            case R.id.btnAddNote:
                Intent iNote=new Intent(getApplicationContext(),NoteActivity.class);
                iNote.putExtra(EXTRA_STD_NAME,etd.getNom()+" "+etd.getPrenom());
                startActivityForResult(iNote,ACT_NOTE);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ACT_NOTE && resultCode==RESULT_OK && data!=null){
            Note n = (Note) data.getSerializableExtra(EXTRA_NOTE);
            ListView l=findViewById(R.id.listNotes);
            ((NoteAdapter)l.getAdapter()).add(n);
        }


    }







}