package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DecimalFormat formatoMonto = new DecimalFormat("$0.00");

        final SeekBar credito = findViewById(R.id.IngresoCredito);
        Switch esVendedor = findViewById(R.id.ButtonEsVendedor);
        final TextView montoCredito = findViewById(R.id.MontoCredito);
        RadioGroup grupo = findViewById(R.id.GrupoBotones);

        montoCredito.setText(formatoMonto.format(0));

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

        credito.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                int val = progress;
                montoCredito.setText(formatoMonto.format(val));
                montoCredito.setX(seekBar.getThumb().getBounds().left - val/15);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.ButtonBase: credito.setProgress(100); break;
                    case R.id.ButtonPremium: credito.setProgress(250); break;
                    case R.id.ButtonFull: credito.setProgress(500); break;
                }
            }
        });

    }

}
