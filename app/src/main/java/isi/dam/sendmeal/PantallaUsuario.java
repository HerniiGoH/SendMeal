package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import isi.dam.sendmeal.Domain.Usuario;

public class PantallaUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_usuario);
        Button btn = findViewById(R.id.button);

        Usuario user = getIntent().getParcelableExtra("user");

        Toast.makeText(this, user.getNombre(), Toast.LENGTH_SHORT).show();

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}
