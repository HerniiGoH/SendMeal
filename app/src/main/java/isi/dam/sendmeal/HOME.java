package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class HOME extends AppCompatActivity {

    Toolbar toolbar;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        myReceiver = new MyReceiver();

        registerReceiver(myReceiver, new IntentFilter("android.intent.action.myreceiver"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar_01);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.op_01:

                Intent i1 = new Intent(this, MainActivity.class);
                startActivity(i1);
                return true;

            case R.id.op_02:
                Intent i2 = new Intent(this, CrearItem.class);
                startActivity(i2);
                return true;

            case R.id.op_03:
                Intent i3 = new Intent(this, ListaItems.class);
                startActivity(i3);
                return true;

            default: return false;
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            try {
                if (intent.getAction().equals("android.intent.action.myreceiver")) {
                    if(intent.getExtras().getBoolean("whatever")){

                        Toast.makeText(context, "Esta en oferta", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "No esta en oferta", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(context, "algonollego", Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
