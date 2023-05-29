package com.example.primeraapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primeraapp.db.DbContactos;
import com.example.primeraapp.entidades.Contactos;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo;
    Button btnGuarda;
    boolean correcto = false;

    Contactos contacto;
    int id=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt( null);
            }else{
                id = extras.getInt("ID");
            }
        }else{

            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbContactos dbContactos = new DbContactos( EditarActivity.this);
        contacto = dbContactos.VerContacto(id);

        if(contacto != null){
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreo_electronico());

        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") ){
                    correcto= dbContactos.editarContacto(id, txtNombre.getText().toString(), txtTelefono.getText().toString(), txtCorreo.getText().toString() );

                    if(correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    }else{
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);

    }
}