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
        RadioGroup grupo = findViewById(R.id.GrupoBotones);
        final EditText date = findViewById(R.id.IngresoFechaTarjeta);

        final DecimalFormat formatoMonto = new DecimalFormat("$0.00");

        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "MMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 0; i <= cl && i < 4; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 6){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int mon  = Integer.parseInt(clean.substring(0,2));
                        int year = Integer.parseInt(clean.substring(2,6));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<2018)?2018:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012
                        clean = String.format("%02d%02d", mon, year);
                    }

                    clean = String.format("%s/%s", clean.substring(0, 2),
                            clean.substring(2, 6));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }}

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        date.addTextChangedListener(tw);

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
