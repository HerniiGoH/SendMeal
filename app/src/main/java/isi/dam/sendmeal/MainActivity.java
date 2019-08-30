package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar credito = findViewById(R.id.IngresoCredito);
        Switch esVendedor = findViewById(R.id.ButtonEsVendedor);
        final TextView montoCredito = findViewById(R.id.MontoCredito);
        final RadioGroup grupo = findViewById(R.id.GrupoBotones);

        final DecimalFormat formatoMonto = new DecimalFormat("$0.00");

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
                switch (grupo.getCheckedRadioButtonId()){
                    case R.id.ButtonBase: if(progress<100) progress=100; break;
                    case R.id.ButtonPremium: if(progress<250) progress=250; break;
                    case R.id.ButtonFull: if(progress<500) progress=500; break;
                    default: break;
                }
                credito.setProgress(progress);
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
                int progress = credito.getProgress();
                switch (checkedId){
                    case R.id.ButtonBase: if(progress<100) credito.setProgress(100); break;
                    case R.id.ButtonPremium: if(progress<250) credito.setProgress(250); break;
                    case R.id.ButtonFull: if(progress<500) credito.setProgress(500); break;
                }
            }
        });

    }

}
