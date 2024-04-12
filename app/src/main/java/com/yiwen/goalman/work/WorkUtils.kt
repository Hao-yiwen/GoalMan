package com.yiwen.goalman.work

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.yiwen.goalman.CHANNEL_ID
import com.yiwen.goalman.GOAL_SETTING_CHANNEL_DESCRIPTION
import com.yiwen.goalman.GOAL_SETTING_WORKER_NAME
import com.yiwen.goalman.MainActivity
import com.yiwen.goalman.NOTIFICATION_ID
import com.yiwen.goalman.R
import com.yiwen.goalman.REQUEST_CODE

fun makePlantReminderNotification(message: String, context: Context) {
    /**
     * @description 这段代码是用来创建通知渠道的，通知渠道是 Android 8.0（API 级别 26）引入的功能。
     * 通知渠道是一种将通知分组的方式，用户可以根据自己的需求对通知进行管理。
     */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importtance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            CHANNEL_ID,
            GOAL_SETTING_WORKER_NAME,
            importtance
        )
        channel.description = GOAL_SETTING_CHANNEL_DESCRIPTION

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    val pendingIntent: PendingIntent = createPendingIntent(context)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(GOAL_SETTING_WORKER_NAME)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

fun createPendingIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    var flags = PendingIntent.FLAG_UPDATE_CURRENT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        flags = flags or PendingIntent.FLAG_IMMUTABLE
    }

    return PendingIntent.getActivity(context, REQUEST_CODE, intent, flags)
}

fun requestPermissons(context: Context, activityContext: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activityContext as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        } else {
            Log.d("GoalMan", "Permission granted")
        }
    }
}