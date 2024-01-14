package com.example.grucocinamindapp.interfaces;

import com.example.grucocinamindapp.Models.RequestCreateWorkerModel;
import com.example.grucocinamindapp.Models.RequestLoginModel;
import com.example.grucocinamindapp.Models.ResponseListTrabajadores;
import com.example.grucocinamindapp.Models.ResponseModel;
import com.example.grucocinamindapp.Models.ResponseTareas;
import com.example.grucocinamindapp.Models.ResponseTrabajadores;
import com.example.grucocinamindapp.Models.TareaDetalleDO;
import com.example.grucocinamindapp.Models.TareaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BackendRequest {

    @POST("login")
    Call<ResponseModel> getData(@Body RequestLoginModel payload);

    @POST("trabajador")
    Call<ResponseModel> guardarTrabajador(@Body RequestCreateWorkerModel payload);

    @GET("trabajador")
    Call<ResponseTrabajadores> listaTrabajadores();

    @POST("tarea")
    Call<ResponseModel> guardarTarea(@Body TareaModel model);

    @GET("tarea/{id}")
    Call<TareaDetalleDO> detalleTarea(@Path("id") int id);

    @GET("tarea/persona/{id}")
    Call<List<ResponseTareas>> tareasPersona(@Path("id") int id);

    @GET("tarea")
    Call<List<ResponseTareas>> tareaslista();

    @GET("tarea/{id}/{estado}")
    Call<ResponseModel> editarEstado(@Path("id") int id,@Path("estado") String estado);

    @PUT("trabajador/{id}")
    Call<ResponseModel> actualizarTrabajador(@Path("id") int id, @Body RequestCreateWorkerModel trabajadro);


    @DELETE("trabajador/{id}")
    Call<ResponseModel> borrarTrabajador(@Path("id") int id);

    @GET("trabajador/{id}")
    Call<ResponseListTrabajadores> buscar(@Path("id") int id);


}
