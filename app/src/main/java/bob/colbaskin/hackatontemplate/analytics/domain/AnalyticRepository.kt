package bob.colbaskin.hackatontemplate.analytics.domain

interface AnalyticRepository {

    suspend fun fetchJsonFromApi(): String?
}