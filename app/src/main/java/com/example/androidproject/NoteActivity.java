package com.example.androidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        this.setTitle(this.getIntent().getStringExtra(Accueil.EXTRA_STD_NAME));
    }

    public void monClick(View view) {
        EditText tMat=findViewById(R.id.txtNewMat);
        EditText tScr=findViewById(R.id.txtNewScr);
        Note n=new Note(tMat.getText().toString(),Double.parseDouble(tScr.getText().toString()));

        Intent iResult=new Intent();
        iResult.putExtra(Accueil.EXTRA_NOTE,n);
        setResult(RESULT_OK,iResult);
        this.finish();
    }


}