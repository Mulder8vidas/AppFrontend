package com.example.grucocinamindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grucocinamindapp.Models.RequestCreateWorkerModel;
import com.example.grucocinamindapp.Models.ResponseModel;
import com.example.grucocinamindapp.interfaces.BackendRequest;
import com.example.grucocinamindapp.util.ApiUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class createWorker extends AppCompatActivity {

    Spinner spinner;

    EditText nombre,apellido,dni;

    TextView menu;

    private String datoseleccionado;



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        menu.setHeaderTitle("");
        // add menu items
        menu.add(0, v.getId(), 0, "Grabar");
        menu.add(0, v.getId(), 0, "Volver");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Grabar") {


            String url = ApiUrl.url;
            Retrofit build = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            BackendRequest backendRequest = build.create(BackendRequest.class);

            String nombre=this.nombre.getText().toString();
            String apellido = this.apellido.getText().toString();
            String dni=this.dni.getText().toString();


            RequestCreateWorkerModel requestCreateWorkerModel = new RequestCreateWorkerModel();
            requestCreateWorkerModel.setNombre(nombre);
            requestCreateWorkerModel.setApellido(apellido);
            requestCreateWorkerModel.setDni(dni);
            requestCreateWorkerModel.setArea(this.datoseleccionado);

            try {
                Call<ResponseModel> data = backendRequest.guardarTrabajador(requestCreateWorkerModel);
                data.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            ResponseModel body = response.body();
                            Log.d("consola", body.getRespuesta());

                            String newuser=nombre.substring(0,2)+apellido.substring(0,2);
                            Toast.makeText(createWorker.this, "Trabajador registrado "+ newuser , Toast.LENGTH_SHORT).show();
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

        } else if (item.getTitle() == "Volver") {
            Intent siguiente = new Intent(createWorker.this,WorkerList.class);
            startActivity(siguiente);
            finish();
        }

        return true;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_worker);

        this.nombre=findViewById(R.id.nombre);
        this.apellido=findViewById(R.id.apellido);
        this.dni=findViewById(R.id.dni);
        this.menu=findViewById(R.id.textView4);

        registerForContextMenu(this.menu);



        this.spinner=findViewById(R.id.spinner4);
        final String[] categorias = {"ADMINISTRADOR", "USUARIO", "Sede Centro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = categorias[position];
                datoseleccionado=selectedCategory;
                /*Toast.makeText(getApplicationContext(), "Categoría seleccionada: " + selectedCategory, Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }
    public void menu (View view){
        Intent menu  = new Intent(this, mainUser.class);
        startActivity(menu);
    }
}