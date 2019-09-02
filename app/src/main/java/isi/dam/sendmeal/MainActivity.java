package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.material.textfield.TextInputLayout;
import com.santalu.maskedittext.MaskEditText;
import java.text.DecimalFormat;
import java.util.Calendar;
import isi.dam.sendmeal.Domain.CuentaBancaria;
import isi.dam.sendmeal.Domain.TarjetaCredito;
import isi.dam.sendmeal.Domain.TipoCuenta;
import isi.dam.sendmeal.Domain.Usuario;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout ingresoNombre, ingresoClave, ingresoClave2, ingresoCorreo, ingresoNumTarjeta, ingresoCCV, ingresoVencimTarjeta, ingresoAliasCBU, ingresoCBU;

    private SeekBar credito;

    private Switch esVendedor;

    private RadioGroup grupo;

    private Button buttonRegistrar;

    private ToggleButton buttonNotif;

    private CheckBox terminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        esVendedor = findViewById(R.id.ButtonEsVendedor);
        credito = findViewById(R.id.IngresoCredito);
        grupo = findViewById(R.id.GrupoBotones);
        terminos = findViewById(R.id.ButtonTerminosYCondiciones);
        buttonRegistrar = findViewById(R.id.ButtonRegistrar);

        final DecimalFormat formatoMonto = new DecimalFormat("$0.00");
        final TextView montoCredito = findViewById(R.id.MontoCredito);
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

        terminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    buttonRegistrar.setEnabled(true);
                }
                else{
                    buttonRegistrar.setEnabled(false);
                }
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresoNombre = findViewById(R.id.IngresoNombre);
                ingresoClave = findViewById(R.id.IngresoClave);
                ingresoClave2 = findViewById(R.id.IngresoClave2);
                ingresoCorreo = findViewById(R.id.IngresoCorreo);
                ingresoNumTarjeta = findViewById(R.id.IngresoNumeroTarjeta);
                ingresoCCV = findViewById(R.id.IngresoCCVTarjeta);
                ingresoVencimTarjeta = findViewById(R.id.IngresoFechaTarjeta);
                buttonNotif = findViewById(R.id.ButtonNotificaciones);

                if(esVendedor.isChecked()){
                    ingresoAliasCBU = findViewById(R.id.IngresoAliasCBU);
                    ingresoCBU = findViewById(R.id.IngresoCBU);
                    if(verificarVendedor(ingresoNombre, ingresoClave, ingresoClave2, ingresoCorreo, ingresoNumTarjeta, ingresoCCV, ingresoVencimTarjeta, ingresoAliasCBU, ingresoCBU, grupo)){
                        CuentaBancaria cuentaBancaria = new CuentaBancaria(ingresoAliasCBU.getEditText().getText().toString().trim(), ingresoCBU.getEditText().getText().toString().trim());
                        Calendar date = Calendar.getInstance();
                        date.set(Calendar.MONTH, Integer.valueOf(((MaskEditText)findViewById(R.id.IngresoFecha)).getRawText().toString().trim().substring(0,2)));
                        date.set(Calendar.YEAR, Integer.valueOf(((MaskEditText)findViewById(R.id.IngresoFecha)).getRawText().toString().trim().substring(2,4))+2000);
                        TarjetaCredito tarjetaCredito = new TarjetaCredito(Long.valueOf(((MaskEditText)findViewById(R.id.IngresoNumero)).getRawText().toString().trim()), Integer.valueOf(((MaskEditText)findViewById(R.id.IngresoCCV)).getRawText().toString().trim()), date);
                        Usuario usuario = new Usuario(ingresoNombre.getEditText().getText().toString().trim(), ingresoCorreo.getEditText().getText().toString().trim(), ingresoClave.getEditText().getText().toString().trim(), buttonNotif.isChecked(), Double.valueOf(credito.getProgress()), cuentaBancaria, tarjetaCredito, TipoCuenta.Vendedor);
                        Toast.makeText(getApplicationContext(), "Datos Registrados Exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Datos Inválidos.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(verificarComprador(ingresoNombre, ingresoClave, ingresoClave2, ingresoCorreo, ingresoNumTarjeta, ingresoCCV, ingresoVencimTarjeta, grupo)){
                        Calendar date = Calendar.getInstance();
                        date.set(Calendar.MONTH, Integer.valueOf(((MaskEditText)findViewById(R.id.IngresoFecha)).getRawText().toString().trim().substring(0,2)));
                        date.set(Calendar.YEAR, Integer.valueOf(((MaskEditText)findViewById(R.id.IngresoFecha)).getRawText().toString().trim().substring(2,4))+2000);
                        TarjetaCredito tarjetaCredito = new TarjetaCredito(Long.valueOf(((MaskEditText)findViewById(R.id.IngresoNumero)).getRawText().toString().trim()), Integer.valueOf(((MaskEditText)findViewById(R.id.IngresoCCV)).getRawText().toString().trim()), date);
                        Usuario usuario = new Usuario(ingresoNombre.getEditText().getText().toString().trim(), ingresoCorreo.getEditText().getText().toString().trim(), ingresoClave.getEditText().getText().toString().trim(), buttonNotif.isChecked(), Double.valueOf(credito.getProgress()), null, tarjetaCredito, TipoCuenta.Comprador);
                        Toast.makeText(getApplicationContext(), "Datos Registrados Exitosamente.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Datos Inválidos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean verificarVendedor(TextInputLayout ingresoNombre, TextInputLayout ingresoClave, TextInputLayout ingresoClave2, TextInputLayout ingresoCorreo, TextInputLayout ingresoNumTarjeta, TextInputLayout ingresoCCVTarjeta, TextInputLayout ingresoFechaTarjeta, TextInputLayout ingresoAliasCBU, TextInputLayout ingresoCBU, RadioGroup grupo){
        if(verificarNombe(ingresoNombre) | verificarClave(ingresoClave, ingresoClave2) | verificarCorreo(ingresoCorreo) | verificarNumTarjeta(ingresoNumTarjeta) | verificarCCVTarjeta(ingresoCCVTarjeta) | verificarFechaTarjeta(ingresoFechaTarjeta) | verificarAliasCBU(ingresoAliasCBU) | verificarCBU(ingresoCBU) | verificarGrupo(grupo)){
            return false;
        }
        return true;
    }

    private boolean verificarComprador(TextInputLayout ingresoNombre, TextInputLayout ingresoClave, TextInputLayout ingresoClave2, TextInputLayout ingresoCorreo, TextInputLayout ingresoNumTarjeta, TextInputLayout ingresoCCVTarjeta, TextInputLayout ingresoFechaTarjeta, RadioGroup grupo){
        if(verificarNombe(ingresoNombre) | verificarClave(ingresoClave, ingresoClave2) | verificarCorreo(ingresoCorreo) | verificarNumTarjeta(ingresoNumTarjeta) | verificarCCVTarjeta(ingresoCCVTarjeta) | verificarFechaTarjeta(ingresoFechaTarjeta) | verificarGrupo(grupo)){
            return false;
        }
        return true;
    }

    private boolean verificarNombe(TextInputLayout ingresoNombre){
        ingresoNombre.setError(null);
        String nombre = ingresoNombre.getEditText().getText().toString().trim();
        if(nombre.isEmpty()){
            ingresoNombre.setError("Campo 'Nombre' no puede ser vacío.");
            return true;
        }

        return false;
    }

    private boolean verificarClave(TextInputLayout ingresoClave, TextInputLayout ingresoClave2){
        ingresoClave.setError(null);
        ingresoClave2.setError(null);
        String clave = ingresoClave.getEditText().getText().toString().trim();
        String clave2 = ingresoClave2.getEditText().getText().toString().trim();
        if(clave.isEmpty()){
            ingresoClave.setError("Campo 'Clave' no debe ser vacío.");
            return true;
        }
        else{
            if(!clave.equals(clave2)){
                ingresoClave2.setError("Las claves no coinciden.");
                return true;
            }
        }

        return false;
    }

    private boolean verificarCorreo(TextInputLayout ingresoCorreo){
        ingresoCorreo.setError(null);

        String correo = ingresoCorreo.getEditText().getText().toString().trim();
        if(correo.isEmpty()){
            ingresoCorreo.setError("Campo 'Correo Electrónico' no debe ser vacío.");
            return true;
        }
        else{
            int indice = correo.indexOf("@");
            if(indice==-1|| correo.length()-indice<3){
                ingresoCorreo.setError("Debe haber al menos tres caracteres luego del '@'.");
                return true;
            }
        }


        return false;
    }

    private boolean verificarNumTarjeta(TextInputLayout ingresoNumTarjeta){
        ingresoNumTarjeta.setError(null);
        MaskEditText ingresoNum = findViewById(R.id.IngresoNumero);

        try{
            String numTarjeta = ingresoNum.getRawText().toString().trim();
            if(numTarjeta.length()!=16) {
                ingresoNumTarjeta.setError("El campo 'Número de Tarjeta' debe tener 16 caracteres.");
                return true;
            }
        }catch(Exception e){
            ingresoNumTarjeta.setError("El campo 'Número de Tarjeta' debe tener 16 caracteres.");
            return true;
        }

        return false;
    }

    private boolean verificarCCVTarjeta(TextInputLayout ingresoCCVTarjeta){
        ingresoCCVTarjeta.setError(null);
        MaskEditText ingresoCCV = findViewById(R.id.IngresoCCV);

        try{
            String CCVTarjeta = ingresoCCV.getRawText().toString().trim();
            if(CCVTarjeta.length()!=3) {
                ingresoCCVTarjeta.setError("El campo 'CCV' debe tener 3 caracteres.");
                return true;
            }
        }catch(Exception e){
            ingresoCCVTarjeta.setError("El campo 'CCV' debe tener 3 caracteres.");
            return true;
        }

        return false;
    }

    private boolean verificarFechaTarjeta(TextInputLayout ingresoFechaTarjeta){
        ingresoFechaTarjeta.setError(null);
        MaskEditText ingresoFecha = findViewById(R.id.IngresoFecha);

        try{
            String fechaTarjeta = ingresoFecha.getRawText().toString().trim();
            int mes = Integer.valueOf(fechaTarjeta.substring(0,2));
            int anio = Integer.valueOf(fechaTarjeta.substring(2,4))+2000;
            Calendar hoy = Calendar.getInstance();
            int anioActual = hoy.get(Calendar.YEAR);
            int mesActual = hoy.get(Calendar.MONTH)+1;
            if(mes<1 || mes>12 || anio<anioActual || anio-anioActual>10) {
                ingresoFechaTarjeta.setError("Fecha Inválida.");
                return true;
            }
            else{
                if(anio==anioActual && mes-mesActual<3){
                    ingresoFechaTarjeta.setError("Vencimiento debe ser mayor a 3 meses.");
                    return true;
                }
                else{
                    if(anio-anioActual==1 && (mes+12)-mesActual<3){
                        ingresoFechaTarjeta.setError("Vencimiento debe ser mayor a 3 meses.");
                        return true;
                    }
                }
            }
        }catch(Exception e){
            ingresoFechaTarjeta.setError("Debe ingresar una fecha de vencimiento.");
            return true;
        }
        return false;
    }

    private boolean verificarAliasCBU(TextInputLayout ingresoAliasCBU){
        ingresoAliasCBU.setError(null);

        String aliasCBU = ingresoAliasCBU.getEditText().getText().toString().trim();

        if(aliasCBU.isEmpty()){
            ingresoAliasCBU.setError("El campo 'Alias CBU' no debe ser vacío.");
            return true;
        }

        return false;
    }

    private boolean verificarCBU(TextInputLayout ingresoCBU){
        ingresoCBU.setError(null);

        String CBU = ingresoCBU.getEditText().getText().toString().trim();

        if(CBU.isEmpty()){
            ingresoCBU.setError("El campo 'CBU' no debe ser vacío.");
            return true;
        }

        return false;
    }

    private boolean verificarGrupo(RadioGroup grupo){
        ((RadioButton)grupo.getChildAt(2)).setError(null);
        if(grupo.getCheckedRadioButtonId()==-1){
            ((RadioButton)grupo.getChildAt(2)).setError("Debe seleccionar tipo de cuenta.");
            return true;
        }
        return false;
    }

}
