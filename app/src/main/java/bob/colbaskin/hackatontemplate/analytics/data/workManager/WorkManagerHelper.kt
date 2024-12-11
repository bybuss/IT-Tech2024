package bob.colbaskin.hackatontemplate.analytics.data.workManager

import android.content.Context
import androidx.work.*
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class WorkManagerHelper {

    fun scheduleDailyFetchWork(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<FetchDataWorker>()
            .setInitialDelay(calculateDelayUntilMidnight(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }

    private fun calculateDelayUntilMidnight(): Long {
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Moscow"))
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val midnightTime = calendar.timeInMillis

        return if (midnightTime > now) {
            midnightTime - now
        } else {
            midnightTime + TimeUnit.DAYS.toMillis(1) - now
        }
    }
}
