package com.projeto.projetotcc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.projeto.projetotcc.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        trocarFragment(new TelaInicial());


        try{
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.HOUR_OF_DAY, 18);
            calendario.set(Calendar.SECOND, 00);
            calendario.set(Calendar.MINUTE, 01);
            criarCanalNotificacoes();
            AlarmManager notificacao = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, NotificacoesReceiver.class);
            PendingIntent telaInicial = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            notificacao.setRepeating(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(), notificacao.INTERVAL_DAY, telaInicial);
            } catch (Exception ex){
            ex.printStackTrace();
        }



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.receitas:
                    trocarFragment(new Receitas());
                    break;
                case R.id.telaInicial:
                    trocarFragment(new TelaInicial());
                    break;
                case R.id.autenticacao:
                    trocarFragment(new Autenticacao());
                    break;
                case R.id.informacoes:
                    trocarFragment(new TelaInformacoes());
                    break;
            }
            return true;
        });
    }

    private void trocarFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void criarCanalNotificacoes(){
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

        }
    }
}