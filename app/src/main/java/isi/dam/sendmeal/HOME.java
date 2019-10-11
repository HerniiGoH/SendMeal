package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import isi.dam.sendmeal.Domain.Plato;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class HOME extends AppCompatActivity {

    Toolbar toolbar;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        myReceiver = new MyReceiver();

        registerReceiver(myReceiver, new IntentFilter("android.intent.action.myreceiver"));

        createNotificationChannel();

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal 1";
            String description = "Canal de notificacion-Testing";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("001", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            String titulo, descripcion;
            titulo = "NUEVA OFERTA";
            Plato plato = intent.getParcelableExtra("whatever");
            try {
                if (intent.getAction().equals("android.intent.action.myreceiver")) {
                    if(plato.getEnOferta()){
                        descripcion = "Un plato esta en oferta.";
                    }
                    else{
                        descripcion = "Un no plato esta en oferta.";
                    }

                    Toast.makeText(context, descripcion, Toast.LENGTH_LONG).show();

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "001")
                            .setSmallIcon(R.mipmap.ic_launcher_foreground)
                            .setContentTitle(titulo)
                            .setContentText(descripcion)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(plato.getId(), builder.build());

                }
            } catch (Exception e) {
                Toast.makeText(context, "Algo no llego.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
