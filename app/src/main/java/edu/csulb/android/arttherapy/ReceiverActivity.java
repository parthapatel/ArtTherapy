package edu.csulb.android.arttherapy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by parth on 3/15/17.
 */

public class ReceiverActivity extends BroadcastReceiver {

    Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.ctx = context;
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

            callPush();

        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
        }
    }

    private void callPush()
    {
        try{
            Intent intent = new Intent(ctx, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder b = new NotificationCompat.Builder(ctx);

            b.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("ArtTherapy")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Make a drawing!")
                    .setContentText("Draw something to release your stress!")
                    .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                    .setContentIntent(contentIntent)
                    .setContentInfo("Info");


            NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, b.build());
        }
        catch (Exception e)
        {
            System.out.print(e.toString());
        }
    }
}
