package com.example.repasobbdd.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;

public class DAOContacto {

    private static DAOContacto daoContacto;
    private static MutableLiveData<Contacto> contacto = new MutableLiveData<>();
    private static MutableLiveData<LinkedList<Contacto>> listaContactos = new MutableLiveData<>();
    private static final CollectionReference db = FirebaseFirestore.getInstance().collection("contactos");

    private DAOContacto() {
    }

    public static DAOContacto getInstance() {
        if (daoContacto == null) {
            daoContacto = new DAOContacto();
        }
        db.addSnapshotListener((queryDocumentSnapshots, e) -> {
            LinkedList<Contacto> contactos = new LinkedList<>(queryDocumentSnapshots.toObjects(Contacto.class));
            listaContactos.setValue(contactos);
        });
        return daoContacto;
    }

    public LiveData<Contacto> getContacto() {
        return contacto;
    }

    public LiveData<LinkedList<Contacto>> getListaContactos() {
        return listaContactos;
    }

    public void selectContacto(String nombre){
        db.document(nombre).get().addOnSuccessListener(documentSnapshot -> {
            Contacto c = documentSnapshot.toObject(Contacto.class);
            contacto.setValue(c);
        });
    }

//    public void selectContactos(){
//        db.get().addOnSuccessListener(queryDocumentSnapshots -> {
//            LinkedList<Contacto> lista = new LinkedList<>(queryDocumentSnapshots.toObjects(Contacto.class));
//            listaContactos.setValue(lista);
//        });
//    }

    public void insertUpdate(Contacto contacto) {
        db.document(contacto.getNombre()).set(contacto);
    }

    public void delete(Contacto contacto) {
        db.document(contacto.getNombre()).delete();
    }

}