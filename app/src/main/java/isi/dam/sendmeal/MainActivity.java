package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch esVendedor = findViewById(R.id.ButtonEsVendedor);

        esVendedor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    findViewById(R.id.LayoutDatosBancarios).setVisibility(View.VISIBLE);
                }
                else {
                    findViewById(R.id.LayoutDatosBancarios).setVisibility(View.GONE);
                }
            }

        });

    }

}
