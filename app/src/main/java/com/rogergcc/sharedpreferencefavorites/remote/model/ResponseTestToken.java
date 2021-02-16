package com.rogergcc.sharedpreferencefavorites.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rogergcc on 20/05/2020.
 * Copyright Ⓒ 2020 . All rights reserved.
 */
public abstract class ResponseTestToken {

    @Expose
    @SerializedName("nuevoToken")
    private String nuevoToken;
    @Expose
    @SerializedName("mensaje")
    private String mensaje;
    @Expose
    @SerializedName("respuesta")
    private boolean respuesta;
    @Expose
    @SerializedName("isSuccess")
    private int isSuccess;

    public String getNuevoToken() {
        return nuevoToken;
    }

    public void setNuevoToken(String nuevoToken) {
        this.nuevoToken = nuevoToken;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }
}
