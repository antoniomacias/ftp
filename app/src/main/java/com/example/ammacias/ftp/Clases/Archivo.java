package com.example.ammacias.ftp.Clases;

/**
 * Created by ammacias on 16/12/2016.
 */

public class Archivo {
    String nombre;
    //0 = file
    //1 = folder
    int icono;
    Long peso;
    String ruta;

    public Archivo() {
    }

    public Archivo(String nombre, int icono, Long peso, String ruta) {
        this.nombre = nombre;
        this.icono = icono;
        this.peso = peso;
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Archivo{" +
                "nombre='" + nombre + '\'' +
                ", icono=" + icono +
                ", peso=" + peso +
                ", ruta='" + ruta + '\'' +
                '}';
    }
}
