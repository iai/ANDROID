package dbvtech.com.br.servicosimples.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Paulo on 21/07/2015.
 */
public class NotificacaoUtil {
    public static void Create(Context context,
                              CharSequence ticker,
                              CharSequence titulo,
                              CharSequence mensagem,
                              int icone,
                              int id,
                              Intent intent){

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification = null;

        int numeroApi = Build.VERSION.SDK_INT;

        if(numeroApi >= 11){

            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(titulo)
                    .setContentText(mensagem)
                    .setSmallIcon(icone)
                    .setContentIntent(pendingIntent);

            if(numeroApi >= 17){
                notification = builder.build();
            }
            else{
                notification = builder.getNotification();
            }
        }
        else{
            notification = new Notification(icone, ticker, System.currentTimeMillis());
            notification.setLatestEventInfo(context, titulo, mensagem, pendingIntent);
        }

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, notification);

    }

    public static void Cancel(Context context, int id){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
    }
}
