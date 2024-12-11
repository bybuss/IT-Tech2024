package bob.colbaskin.hackatontemplate.analytics.domain

import okhttp3.ResponseBody
import retrofit2.http.GET

interface AnalyticApiService {

    @GET("investments")
    suspend fun getAllInvestments(): ResponseBody?
}