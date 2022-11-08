package com.example.repasobbdd.model;

import com.google.firebase.firestore.DocumentId;

public class Contacto {

    @DocumentId
    private String nombre;
    private String telefono;
    private String saludo;

    public Contacto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSaludo() {
        return saludo;
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

}
