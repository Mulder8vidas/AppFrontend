package com.example.grucocinamindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.grucocinamindapp.Models.RequestLoginModel;
import com.example.grucocinamindapp.Models.ResponseModel;
import com.example.grucocinamindapp.interfaces.BackendRequest;
import com.example.grucocinamindapp.util.ApiUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText user, password;



    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        btn=findViewById(R.id.btn);



       btn.setOnClickListener(v -> {
           this.onClick(v,this);
       });
    }
    public void Login(View view){
      /*  Intent siguiente = new Intent(this,mainUser.class);
        startActivity(siguiente);
        finish();*/

    }

    private void onClick(View v,LoginActivity actividad) {
        String usuario = user.getText().toString();
        String password = this.password.getText().toString();
        String url = ApiUrl.url;
        Log.d("consola", url);
        Retrofit build = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        BackendRequest backendRequest = build.create(BackendRequest.class);

        RequestLoginModel peticionLogin = new RequestLoginModel();
        peticionLogin.setUsername(usuario);
        peticionLogin.setPassword(password);


        try {
            Call<ResponseModel> data = backendRequest.getData(peticionLogin);
            data.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        ResponseModel body = response.body();
                        Log.d("consola", body.getRespuesta());

                        if (body.getRespuesta().equalsIgnoreCase(usuario)) {
                            SharedPreferences datos = getSharedPreferences("datos", MODE_PRIVATE);
                            SharedPreferences.Editor edit = datos.edit();
                            edit.putString("usuario", usuario);
                            edit.putString("foto",body.getFoto());
                            edit.putString("mensaje", "Bienvenido : "+body.getNombre()+ " "+body.getApellido());
                            edit.putString("rol",body.getRol());
                            edit.putInt("id",body.getId());
                            edit.apply();

                            if(body.getRol().equalsIgnoreCase("ADMIN")){
                                Intent siguiente = new Intent(actividad,WorkerList.class);
                                startActivity(siguiente);
                                finish();
                            }



                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario o clave incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Log.d("consola", "entre3");
                }
            });

        } catch (Exception ex) {
            Log.d("consola", ex.getMessage());
        }


    }
}