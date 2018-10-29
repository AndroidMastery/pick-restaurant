package android.kaushik.com.pickrestaurant.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.kaushik.com.pickrestaurant.Constants;
import android.kaushik.com.pickrestaurant.MainActivity;
import android.kaushik.com.pickrestaurant.R;
import android.support.v4.app.NotificationCompat;

public class MyNotificationManager {

    private Context context;

    private static MyNotificationManager notificationManager;

    private MyNotificationManager(Context context)
    {
        this.context = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context)
    {
        if(notificationManager == null)
        {
            notificationManager = new MyNotificationManager(context);
        }

        return notificationManager;
    }

    public void displayNotification(String notificationTitle, String notificationMessage)
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(manager != null)
        {
            manager.notify(1, notificationBuilder.build());
        }


    }


}
