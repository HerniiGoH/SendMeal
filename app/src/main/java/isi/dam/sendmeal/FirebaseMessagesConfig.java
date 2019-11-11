package isi.dam.sendmeal;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseMessagesConfig extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        mostrarNotificacion(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
    }

    @Override
    public void onNewToken(String token) {
        Log.d("TOKEN : ", "Refreshed token: " + token);

    }

    public void mostrarNotificacion (String titulo, String cuerpo){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Notificacion_Pedido")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(titulo)
                .setContentText(cuerpo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(999,builder.build());


    }
}
