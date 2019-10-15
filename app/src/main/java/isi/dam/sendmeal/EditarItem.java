package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import isi.dam.sendmeal.Domain.Plato;

public class EditarItem extends AppCompatActivity {

    Toolbar toolbar;
    TextInputLayout ingresoNombre, ingresoDescripcion, ingresoPrecio, ingresoCalorias;
    Button btnRegistrar;
    TextView subtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);

        toolbar = findViewById(R.id.toolbar_crear_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        final Plato plato = Plato.platos.get(getIntent().getExtras().getInt("position"));

        ingresoNombre = findViewById(R.id.ingresoNombrePlato);
        ingresoNombre.getEditText().setText(plato.getNombre());
        ingresoDescripcion = findViewById(R.id.ingresoDescripcionPlato);
        ingresoDescripcion.getEditText().setText(plato.getDescripcion());
        ingresoPrecio = findViewById(R.id.ingresoPrecioPlato);
        ingresoPrecio.getEditText().setText(plato.getPrecio().toString());
        ingresoCalorias = findViewById(R.id.ingresoCaloriasPlato);
        ingresoCalorias.getEditText().setText(plato.getCalorias().toString());

        subtitulo = findViewById(R.id.SubtituloCrear);
        subtitulo.setText("Editar Plato");

        btnRegistrar = findViewById(R.id.buttonRegistrarPlato);

        if(getIntent().getExtras().getBoolean("Bool")){
            ingresoNombre.setEnabled(false);
            ingresoDescripcion.setEnabled(false);
            ingresoCalorias.setEnabled(false);
            ingresoPrecio.setEnabled(false);
            btnRegistrar.setVisibility(View.INVISIBLE);
        }
        else{
            ingresoNombre.setEnabled(true);
            ingresoDescripcion.setEnabled(true);
            ingresoCalorias.setEnabled(true);
            ingresoPrecio.setEnabled(true);
            btnRegistrar.setVisibility(View.VISIBLE);
        }

        btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(verificarNombre(ingresoNombre) | verificarDescripcion(ingresoDescripcion) | verificarPrecio(ingresoPrecio) | verificarCalorias(ingresoCalorias)){
                    Toast.makeText(getApplicationContext(), "Datos Inválidos", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Datos Registrados Exitosamente", Toast.LENGTH_SHORT).show();
                    plato.setNombre(ingresoNombre.getEditText().getText().toString());
                    plato.setDescripcion(ingresoDescripcion.getEditText().getText().toString());
                    plato.setPrecio(Float.valueOf(ingresoPrecio.getEditText().getText().toString()));
                    plato.setCalorias(Float.valueOf(ingresoCalorias.getEditText().getText().toString()));
                    Toast.makeText(getApplicationContext(), plato.toString(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private boolean verificarNombre(TextInputLayout ingresoNombre){
        ingresoNombre.setError(null);
        try{
            String nombre = ingresoNombre.getEditText().getText().toString().trim();
            if(nombre.isEmpty()){
                ingresoNombre.setError("Debe ingresar un nombre del plato.");
                return true;
            }
        }catch(Exception e){
            return true;
        }
        return false;
    }
    private boolean verificarDescripcion(TextInputLayout ingresoDescripcion){
        ingresoDescripcion.setError(null);
        try{
            String desc = ingresoDescripcion.getEditText().getText().toString().trim();
            if(desc.isEmpty()){
                ingresoDescripcion.setError("Debe ingresar una descripción del plato.");
                return true;
            }
        }catch(Exception e){
            return true;
        }
        return false;
    }
    private boolean verificarPrecio(TextInputLayout ingresoPrecio){
        ingresoPrecio.setError(null);
        try{
            String precio = ingresoPrecio.getEditText().getText().toString().trim();
            if(precio.isEmpty()){
                ingresoPrecio.setError("Debe ingresar el precio del plato.");
                return true;
            }
        }catch(Exception e){
            return true;
        }
        return false;
    }
    private boolean verificarCalorias(TextInputLayout ingresoCalorias){
        ingresoCalorias.setError(null);
        try{
            String cal = ingresoCalorias.getEditText().getText().toString().trim();
            if(cal.isEmpty()){
                ingresoCalorias.setError("Debe ingresar las calorías del plato.");
                return true;
            }
        }catch(Exception e){
            return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


}
