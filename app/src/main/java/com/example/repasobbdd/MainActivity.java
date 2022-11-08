package com.example.repasobbdd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repasobbdd.model.Contacto;
import com.example.repasobbdd.model.DAOContacto;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etTelefono = findViewById(R.id.etTelefono);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        ListView lvContactos = findViewById(R.id.lvContactos);

        DAOContacto daoContacto = DAOContacto.getInstance();

        btnGuardar.setOnClickListener(view -> {
            Contacto contacto = new Contacto();
            contacto.setNombre(etNombre.getText().toString());
            contacto.setTelefono(etTelefono.getText().toString());
            daoContacto.insertUpdate(contacto);
        });

        lvContactos.setOnItemClickListener((parent, view, position, id) -> {
            Contacto contacto = (Contacto) parent.getItemAtPosition(position);
            daoContacto.selectContacto(contacto.getNombre());
        });

        lvContactos.setOnItemLongClickListener((parent, view, position, id) -> {
            Contacto contacto = (Contacto) parent.getItemAtPosition(position);
            daoContacto.delete(contacto);
            return true;
        });

        daoContacto.getListaContactos().observe(this, contactos -> {
            ArrayAdapter<Contacto> adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contactos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(contactos.get(position).getNombre());
                    text2.setText(contactos.get(position).getTelefono());

                    return view;
                }
            };
            lvContactos.setAdapter(adapter);
        });

        daoContacto.getContacto().observe(this, c -> {
            Toast.makeText(this, c.getSaludo(), Toast.LENGTH_SHORT).show();
        });

    }
}