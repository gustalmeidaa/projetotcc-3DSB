package com.projeto.projetotcc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificacoesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Configurando a notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(
                context,
                "1000"
        ).setContentTitle("Testando Notif")
                .setContentText("Funcionou!")
                .setSmallIcon(R.drawable.chef)
                /* .setAutoCancel(true)
                 .setContentIntent(pendente)*/
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat gerenciador = NotificationManagerCompat.from(context);
        gerenciador.notify(200, notificacao.build());
    }
}