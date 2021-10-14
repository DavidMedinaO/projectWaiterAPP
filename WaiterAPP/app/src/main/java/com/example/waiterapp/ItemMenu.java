package com.example.waiterapp;

import java.io.Serializable;

public class ItemMenu implements Serializable {
    private int imgFoto;
    private String titulo;
    private String contenido;

    public ItemMenu(int imgFoto, String titulo, String contenido){
        this.imgFoto = imgFoto;
        this.titulo = titulo;
        this.contenido = contenido;


    }

    public int getImgFoto() {
        return imgFoto;
    }


    public String getTitulo() { return titulo; }

    public String getContenido() { return contenido; }


}
