package com.example.grucocinamindapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grucocinamindapp.Models.ResponseTrabajadores;
import com.example.grucocinamindapp.interfaces.BackendRequest;
import com.example.grucocinamindapp.util.ApiUrl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorkerList extends AppCompatActivity {


    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    TextView menu;




    int clickCounter=0;

    private ListView listView;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // you can set menu header with title icon etc
        menu.setHeaderTitle("");
        // add menu items
        menu.add(0, v.getId(), 0, "Agregar");
        menu.add(0, v.getId(), 0, "Salir");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Agregar") {

            Intent siguiente = new Intent(WorkerList.this,createWorker.class);
            startActivity(siguiente);
            finish();

        } else if (item.getTitle() == "Salir") {
            finish();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);
        this.listView=findViewById(R.id.lista);

        this.menu=(TextView) findViewById(R.id.textView3);




        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            String datos =(String) listView.getItemAtPosition(position);

            Intent intent = new Intent(this, editartrabajador.class);

            int idtrabajador =Integer.parseInt(datos.split(":")[0]);
            intent.putExtra("user_id", String.valueOf(idtrabajador));
            this.startActivity(intent);


        });
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);




        this.listView.setAdapter(this.adapter);
        String url = ApiUrl.url;
        Retrofit build = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        BackendRequest backendRequest = build.create(BackendRequest.class);

        Call<ResponseTrabajadores> data = backendRequest.listaTrabajadores();

        data.enqueue(new Callback<ResponseTrabajadores>() {
            @Override
            public void onResponse(Call<ResponseTrabajadores> call, Response<ResponseTrabajadores> response) {

                if(response.isSuccessful()){

                    ResponseTrabajadores body = response.body();
                    body.trabajadores.forEach(s -> {
                        adapter.add(s);
                    });
                    adapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onFailure(Call<ResponseTrabajadores> call, Throwable t) {

            }
        });



        registerForContextMenu(this.menu);

    }
    public void menu (View view){
        Intent menu  = new Intent(this, mainUser.class);
        startActivity(menu);
    }
    public void addItems(View v) {

        adapter.notifyDataSetChanged();
    }
}