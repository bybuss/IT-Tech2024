package bob.colbaskin.hackatontemplate.analytics.data.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import bob.colbaskin.hackatontemplate.analytics.data.FileManager
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticRepository

class FetchDataWorker(
    private val context: Context,
    private val analyticRepository: AnalyticRepository,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val json = analyticRepository.fetchJsonFromApi()

            if (json != null) {
                val fileManager = FileManager(context)
                fileManager.saveJsonToFile("investments.json", json)
                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}