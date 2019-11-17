package isi.dam.sendmeal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.Plato;
import isi.dam.sendmeal.R;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class CrearItem extends AppCompatActivity {
    String photoConvert;
    ImageView imagePreview;
    Toolbar toolbar;
    TextInputLayout ingresoNombre, ingresoDescripcion, ingresoPrecio, ingresoCalorias;
    Button btnRegistrar;
    FloatingActionButton boton_camara;
    String pathToFile;
    Bitmap fotoPlato;


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

        if(Build.VERSION.SDK_INT>=23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        imagePreview = findViewById(R.id.preview_plato);

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                fotoPlato = BitmapFactory.decodeResource(getResources(),R.drawable.plato_01);
                fotoPlato.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] b = byteArrayOutputStream.toByteArray();
                photoConvert = Base64.encodeToString(b, Base64.DEFAULT);
                return photoConvert;
            }
            @Override
            protected void onPostExecute(String s){
                imagePreview.setImageBitmap(fotoPlato);
                Log.d("Foto debug", s);
            }
        }.execute();


        boton_camara = findViewById(R.id.agregar_foto_flotante);
        boton_camara.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        if(camara.resolveActivity(getPackageManager())!= null){
                            File photoFile = null;
                            try{
                                String name = "lalala";
                                File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                                File image = File.createTempFile(name, ".jpg", storageDir);
                                photoFile = image;
                                String path = photoFile.getAbsolutePath();
                                if(path != null){
                                    pathToFile = photoFile.getAbsolutePath();
                                    Uri photoUri = FileProvider.getUriForFile(CrearItem.this, "asd", photoFile);
                                    camara.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(camara, 1);
                                }
                            }catch (Exception e){
                                Log.d("Foto debug", e.toString());
                            }
                        }
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
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                fotoPlato = BitmapFactory.decodeFile(pathToFile);
                imagePreview.setImageBitmap(fotoPlato);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                fotoPlato.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] b = byteArrayOutputStream.toByteArray();

                photoConvert = Base64.encodeToString(b, Base64.DEFAULT);

                Log.d("Foto debug", photoConvert);

            }
        }

    }





}
