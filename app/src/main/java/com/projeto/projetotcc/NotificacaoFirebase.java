package com.projeto.projetotcc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificacaoFirebase extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage mensagemNotificacao) {
        Log.e("Origem: ", mensagemNotificacao.getFrom());

        //Verificando se de fato há alguma notificação
        if (mensagemNotificacao.getNotification() != null) {
            Log.e( "Message Notification Body: ", mensagemNotificacao.getNotification().getBody());
        }
        criarCanalNotificacoes(mensagemNotificacao.getNotification().getBody());
    }

    private void criarCanalNotificacoes(String messageBody){
        //Configurando a notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(
                this, //Contexto
                "1000" //ID do canal de notificação (o mesmo usado no método que cria o canal)
        ).setContentTitle("Notificação")
                .setAutoCancel(true) //Remove a notificação após tocar/clicar
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.chef)
                ;

        //Verificando a versão do dispositivo (se ela é maior que Android 8.0)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Criando um nome pro canal de notificações
            CharSequence nomeCanal = "Canal do aplicativo TryOn";
            //Criando uma descrição
            String descricaoCanal = "Canal criado para notificar os usuários do app TryOn";

            //Cria o canal
            NotificationChannel canal = new NotificationChannel(
                    "1000", //É necessário colocar um ID que irá identificar o canal
                    nomeCanal,
                    NotificationManager.IMPORTANCE_DEFAULT //Nível de prioridade
            );

            //Passando a descrição pro canal através do .setDescription()
            canal.setDescription(descricaoCanal);

            //Transmitindo a configuração para o gerenciador de notificações
            NotificationManager gerenciador = getSystemService(NotificationManager.class);
            gerenciador.createNotificationChannel(canal);
            gerenciador.notify(1000, notificacao.build());
        }
    }

}
