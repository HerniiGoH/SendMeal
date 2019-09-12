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
        Button btn2 = findViewById(R.id.Ver);

        final Usuario user = getIntent().getParcelableExtra("user");

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user == null){
                    Toast.makeText(getApplicationContext(), "Error cargando Usuario", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}
