package com.rogergcc.sharedpreferencefavorites.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rogergcc on 20/05/2020.
 * Copyright Ⓒ 2020 . All rights reserved.
 */
public abstract class ResponseRestaurant {

    @Expose
    @SerializedName("mensaje")
    private String mensaje;
    @Expose
    @SerializedName("respuesta")
    private boolean respuesta;
    @Expose
    @SerializedName("estado")
    private String estado;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
