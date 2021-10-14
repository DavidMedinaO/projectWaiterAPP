package com.example.waiterapp;

public class ItemMenu {
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
