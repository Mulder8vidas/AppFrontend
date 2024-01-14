package com.example.grucocinamindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grucocinamindapp.Models.RequestCreateWorkerModel;
import com.example.grucocinamindapp.Models.ResponseListTrabajadores;
import com.example.grucocinamindapp.Models.ResponseModel;
import com.example.grucocinamindapp.Models.ResponseTrabajadores;
import com.example.grucocinamindapp.Models.Trabajador;
import com.example.grucocinamindapp.interfaces.BackendRequest;
import com.example.grucocinamindapp.util.ApiUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class editartrabajador extends AppCompatActivity {

    Button btn;




    TextView menu;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("");
        menu.add(0, v.getId(), 0, "Grabar");
        menu.add(0, v.getId(), 0, "Volver");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Grabar") {
            Bundle extras = getIntent().getExtras();
            Log.d("consola", extras.getString("user_id"));
            final int idTrabajador=Integer.parseInt(extras.getString("user_id"));
            String url = ApiUrl.url;
            Retrofit build = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            BackendRequest backendRequest = build.create(BackendRequest.class);

            EditText nombre =(EditText) findViewById(R.id.nombre);
            EditText apellido = (EditText) findViewById(R.id.apellido);
            EditText dni = (EditText) findViewById(R.id.dni);


            RequestCreateWorkerModel requestCreateWorkerModel = new RequestCreateWorkerModel();
            requestCreateWorkerModel.setArea("");
            requestCreateWorkerModel.setDni(dni.getText().toString());
            requestCreateWorkerModel.setApellido(apellido.getText().toString());
            requestCreateWorkerModel.setNombre(nombre.getText().toString());

            Call<ResponseModel> actualizarTrabajador = backendRequest.actualizarTrabajador(idTrabajador,requestCreateWorkerModel);

            actualizarTrabajador.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(editartrabajador.this, "Trabajador Actualizado", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });

        } else if (item.getTitle() == "Volver") {
            Intent siguiente = new Intent(editartrabajador.this,WorkerList.class);
            startActivity(siguiente);
            finish();
        }

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editartrabajador);
        Bundle extras = getIntent().getExtras();
        Log.d("consola", extras.getString("user_id"));
        final int idTrabajador=Integer.parseInt(extras.getString("user_id"));
        String url = ApiUrl.url;
        Retrofit build = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        BackendRequest backendRequest = build.create(BackendRequest.class);
        this.menu=findViewById(R.id.textView9);

        registerForContextMenu(this.menu);




        Call<ResponseListTrabajadores> buscar = backendRequest.buscar(idTrabajador);
        buscar.enqueue(new Callback<ResponseListTrabajadores>() {
            @Override
            public void onResponse(Call<ResponseListTrabajadores> call, Response<ResponseListTrabajadores> response) {
                if(response.isSuccessful()){

                    ResponseListTrabajadores body = response.body();
                    EditText nombre =(EditText) findViewById(R.id.nombre);
                    EditText apellido = (EditText) findViewById(R.id.apellido);
                    EditText dni = (EditText) findViewById(R.id.dni);
                    Trabajador trabajador = body.getTrabajadores().get(0);
                    nombre.setText(trabajador.getNombre());
                    apellido.setText(trabajador.getApellido());
                    dni.setText(trabajador.getDni());



                }
            }

            @Override
            public void onFailure(Call<ResponseListTrabajadores> call, Throwable t) {

            }
        });
    }
    public void menu (View view){
        Intent menu  = new Intent(this, mainUser.class);
        startActivity(menu);
    }
}