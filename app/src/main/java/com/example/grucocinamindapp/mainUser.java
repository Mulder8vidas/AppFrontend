package com.example.grucocinamindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class mainUser extends AppCompatActivity {

    Button btncreauser,button,button3,buttonTareas;

    TextView textView2;

    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        this.textView2=findViewById(R.id.textView2);
        this.btncreauser=findViewById(R.id.btncreauser);
        this.button=findViewById(R.id.actividades);
        this.imageView=findViewById(R.id.imageView);
        this.btncreauser.setOnClickListener(v -> {
            Intent siguiente = new Intent(this,createWorker.class);
            startActivity(siguiente);
            finish();
        });
        this.button.setOnClickListener(v -> {
            Intent siguiente = new Intent(this,WorkerList.class);
            startActivity(siguiente);
            finish();
        });





        SharedPreferences datos = getSharedPreferences("datos", MODE_PRIVATE);
        String usuario = datos.getString("mensaje", "");
        this.textView2.setText(usuario);

        String imageUrl=datos.getString("foto","https://png.pngtree.com/png-vector/20220817/ourlarge/pngtree-manager-icon-he-teacher-profile-vector-png-image_19531546.jpg");

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);


    }
    public void cerrarS (View view){
        Intent menu  = new Intent(this, LoginActivity.class);
        startActivity(menu);

    }

}