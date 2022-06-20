package com.lm.hideapps.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lm.hideapps.R
import com.lm.hideapps.core.ResourcesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

interface Notifications {

    fun showForegroundNotification(): Notification

    fun showActionNotification(action: String)

    class Base @Inject constructor(
        private val resourcesManager: ResourcesManager,
        private val notificationBuilder: NotificationCompat.Builder,
        private val notificationManager: NotificationManagerCompat
    ) : Notifications {
        override fun showForegroundNotification(): Notification {
            foregroundServiceNotificationChannel
            return notificationBuilder
                .setOngoing(true)
                .setContentTitle(resourcesManager.string(R.string.name))
                .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
        }

        override fun showActionNotification(action: String) {
            scope.launch {
                action.cutAction.also { title ->
                    actionChannel(title, title)
                    notificationManager.notify(
                        1, notificationBuilder
                            .setOngoing(true)
                            .setContentTitle(title)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .build()
                    )
                    delayShow.invoke()
                    hideNotification()
                }
            }
        }

        private fun hideNotification() = notificationManager.cancel(1)

        private val scope by lazy { CoroutineScope(Dispatchers.IO) }
        private val delayShow by lazy { suspend { delay(2000L) } }
        private val String.cutAction
            get() =
                substringAfter(resourcesManager.string(R.string.substring))

        private val actionChannel: (String, String) -> Unit by lazy {
            { channel, name ->
                notificationManager
                    .createNotificationChannel(
                        NotificationChannel(channel, name, IMPORTANCE_DEFAULT)
                    )
            }
        }

        private val foregroundServiceNotificationChannel by lazy {
            resourcesManager.string(R.string.name).also { title ->
                NotificationChannel(
                    title, title, IMPORTANCE_DEFAULT
                ).apply {
                    lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                    setSound(null, null)
                    notificationManager.createNotificationChannel(this)
                }
            }
        }
    }
}
