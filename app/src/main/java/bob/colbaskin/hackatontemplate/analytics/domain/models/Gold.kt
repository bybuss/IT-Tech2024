package bob.colbaskin.hackatontemplate.analytics.domain.models

import com.google.gson.annotations.SerializedName

data class Golds(
    val gold: Map<String, GoldDateMap>
)

data class GoldDateMap(
    val price: Double,
    @SerializedName("last_7_day_diff_in_%") val last7DayDiff: String? = null,
    @SerializedName("next_7_day_diff_in_%") val next7DayDiff: String? = null
)

data class FullGold(
    val date: String,
    val price: Double,
    val last7DayDiff: String? = null,
    val next7DayDiff: String? = null
)

data class SimpleGold(
    val date: String,
    val price: Double
)
