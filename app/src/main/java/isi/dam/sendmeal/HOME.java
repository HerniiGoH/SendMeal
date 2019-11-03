package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.DAO.rest.PlatoRest;
import isi.dam.sendmeal.Domain.Plato;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class HOME extends AppCompatActivity {

    Toolbar toolbar;
    private MyReceiver myReceiver;

    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage (Message m){
            Log.d("APP_2", "VUELVE AL HANDLER"+ m.arg1);
            switch ( m.arg1){
                case Plato_repo._ALTA_PLATO:
                    break;
                case Plato_repo._CONSULTA_PLATO:
                    List<Plato> platos = Plato_repo.getInstance().getListaPlatos();
                    Log.d("ALGO", ""+platos.size());
                    if(platos.size()!=0){
                        Plato.setIdSeq(platos.get(platos.size()-1).getId()+1);
                    }
                    else{
                        Plato.setIdSeq(1);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Plato_repo.getInstance().listarPlato(miHandler);

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

            case R.id.op_04:
                Intent i4 = new Intent(this, BusquedaItem.class);
                startActivity(i4);
                return true;

            case R.id.op_05:
                Intent i5 = new Intent (this,ListaItemsPedido.class);
                startActivity(i5);
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
            try {
                if(intent.getAction().equals("android.intent.action.myreceiver")){
                    if (intent.getBooleanExtra("boolean", true)) {
                        Plato plato = intent.getParcelableExtra("whatever");
                        titulo = "NUEVA OFERTA";
                        if(plato.getEnOferta()){
                            descripcion = "Un plato esta en oferta.";
                        }
                        else{
                            descripcion = "Un no plato esta en oferta.";
                        }

                        Toast.makeText(context, descripcion, Toast.LENGTH_LONG).show();

                        Intent intent_1 = new Intent(context,EditarItem.class);
                        intent_1.putExtra("Bool", true);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent_1,0);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "001")
                                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                                .setContentTitle(titulo)
                                .setContentText(descripcion)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(plato.getId(), builder.build());

                    }
                    else{
                        titulo = "PLATO ELIMINADO";
                        descripcion = "Plato "+intent.getStringExtra("nombrePlato")+" ha sido borrado.";

                        Toast.makeText(context, descripcion, Toast.LENGTH_LONG).show();

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "001")
                                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                                .setContentTitle(titulo)
                                .setContentText(descripcion)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(intent.getIntExtra("idPlato", 0), builder.build());
                    }
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
