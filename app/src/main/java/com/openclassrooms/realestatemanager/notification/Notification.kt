package com.openclassrooms.realestatemanager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.property_list.PropertyListActivity

object Notification {

    private const val NOTIFICATION_ID = 2007
    private const val NOTIFICATION_TAG = "Real Estate Manager"

    fun sendNotification(context: Context) {

        // 1 - Create an Intent that will be shown when user will click on the Notification
        val intent = Intent(context, PropertyListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // 2 - Create a Style for the Notification
        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle(context.getString(R.string.new_property))
        inboxStyle.addLine(context.getString(R.string.the_new_property_has_been_successfully_created))

        // 3 - Create a Channel (Android 8)
        val channelId = context.getString(R.string.default_notification_channel_id)

        // 4 - Build a Notification object
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_check)
                .setContentTitle(context.getString(R.string.new_property))
                .setContentText(context.getString(R.string.the_new_property_has_been_successfully_created))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)

        // 5 - Add the Notification to the Notification Manager and show it.
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Real Estate Manager Application"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        // 7 - Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build())
    }
}