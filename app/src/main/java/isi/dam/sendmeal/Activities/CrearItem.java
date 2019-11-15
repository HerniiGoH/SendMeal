package isi.dam.sendmeal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.Plato;
import isi.dam.sendmeal.R;

public class CrearItem extends AppCompatActivity {
    private String filePath = "";
    String foto_path;
    ImageView imagePreview;
    Toolbar toolbar;
    TextInputLayout ingresoNombre, ingresoDescripcion, ingresoPrecio, ingresoCalorias;
    Button btnRegistrar;
    FloatingActionButton boton_camara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);

        toolbar = findViewById(R.id.toolbar_crear_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        boton_camara = findViewById(R.id.agregar_foto_flotante);
        boton_camara.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        File file = new File("mnt/sdcard/download/foto.pdf");
                        String decodedBase64 = null;

                        Uri outputFile = Uri.fromFile(file);

                        camara.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);

                        startActivityForResult(camara, 0);
                    }
                }

        );

        btnRegistrar = findViewById(R.id.buttonRegistrarPlato);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresoNombre = findViewById(R.id.ingresoNombrePlato);
                ingresoDescripcion = findViewById(R.id.ingresoDescripcionPlato);
                ingresoPrecio = findViewById(R.id.ingresoPrecioPlato);
                ingresoCalorias = findViewById(R.id.ingresoCaloriasPlato);
                if (verificarNombre(ingresoNombre) | verificarDescripcion(ingresoDescripcion) | verificarPrecio(ingresoPrecio) | verificarCalorias(ingresoCalorias)) {
                    Toast.makeText(getApplicationContext(), "Datos Inválidos", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Datos Registrados Exitosamente", Toast.LENGTH_SHORT).show();
                    Plato plato = new Plato(ingresoNombre.getEditText().getText().toString(), ingresoDescripcion.getEditText().getText().toString(), Double.valueOf(ingresoPrecio.getEditText().getText().toString()), Float.valueOf(ingresoCalorias.getEditText().getText().toString()));
                    Plato_repo.getInstance().crearPlato(plato, miHandler);
                    Toast.makeText(getApplicationContext(), plato.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verificarNombre(TextInputLayout ingresoNombre) {
        ingresoNombre.setError(null);
        try {
            String nombre = ingresoNombre.getEditText().getText().toString().trim();
            if (nombre.isEmpty()) {
                ingresoNombre.setError("Debe ingresar un nombre del plato.");
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean verificarDescripcion(TextInputLayout ingresoDescripcion) {
        ingresoDescripcion.setError(null);
        try {
            String desc = ingresoDescripcion.getEditText().getText().toString().trim();
            if (desc.isEmpty()) {
                ingresoDescripcion.setError("Debe ingresar una descripción del plato.");
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean verificarPrecio(TextInputLayout ingresoPrecio) {
        ingresoPrecio.setError(null);
        try {
            String precio = ingresoPrecio.getEditText().getText().toString().trim();
            if (precio.isEmpty()) {
                ingresoPrecio.setError("Debe ingresar el precio del plato.");
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean verificarCalorias(TextInputLayout ingresoCalorias) {
        ingresoCalorias.setError(null);
        try {
            String cal = ingresoCalorias.getEditText().getText().toString().trim();
            if (cal.isEmpty()) {
                ingresoCalorias.setError("Debe ingresar las calorías del plato.");
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    Handler miHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message m) {
            Log.d("APP_2", "VUELVE AL HANDLER" + m.arg1);
            switch (m.arg1) {
                case Plato_repo._ALTA_PLATO:
                    finish();
                    break;
                case Plato_repo._UPDATE_PLATO:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }





}
