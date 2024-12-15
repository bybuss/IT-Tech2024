package bob.colbaskin.hackatontemplate.analytics.data

import android.content.Context
import android.util.Log
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticApiService
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticRepository
import javax.inject.Inject

class AnalyticRepositoryImpl @Inject constructor(
    private val analyticApiService: AnalyticApiService,
    context: Context
): AnalyticRepository {

    private val fileManager = FileManager(context)

    override suspend fun fetchJsonFromApi(): String? {
        val cachedJson = fileManager.readJsonFromFile("investments.json")
        if (cachedJson != null) {
            Log.d("AnalyticsRep", "Returning cached JSON: $cachedJson")
            return cachedJson
        }

        val responseBody = analyticApiService.getAllInvestments()
        val json = responseBody?.string() ?: "null"

        fileManager.saveJsonToFile("investments.json", json)

        Log.d("AnalyticsRep", json)
        return json
    }
} 