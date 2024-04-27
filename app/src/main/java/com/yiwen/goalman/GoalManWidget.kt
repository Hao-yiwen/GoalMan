package com.yiwen.goalman

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.yiwen.goalman.data.GoalDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Implementation of App Widget functionality.
 */
class GoalManWidget : AppWidgetProvider() {
    private val widgetScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            widgetScope.launch {
                try {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                } catch (e: Exception) {
                    Log.e("Widget Update", "Error updating widget", e)
                }
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("Goalman widget", "onEnabled")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d("Goalman widget", "onDisabled")
    }
}

internal suspend fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.goal_man_widget)
    val database = GoalDatabase.getDatabase(context).completionRecordsDao()
    val positiveDays = database.getTotalPositiveCompletionRecords() ?: 0
    val widgetText = context.getString(R.string.appwidget_text, positiveDays.toString())
    // 设置小部件文本
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setTextColor(
        R.id.appwidget_text, context.getColor(R.color.white)
    )
    // 点击小部件跳转到主界面
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = android.app.PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.goalman_widget_container, pendingIntent);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}