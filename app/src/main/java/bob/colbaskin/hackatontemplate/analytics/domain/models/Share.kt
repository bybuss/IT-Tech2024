package bob.colbaskin.hackatontemplate.analytics.domain.models

import com.google.gson.annotations.SerializedName

data class Shares(
    val shares: Map<String, Map<String, ShareDateMap>>
)

data class ShareDateMap(
    val price: String,
    val sector: String? = null,
    val country: String? = null,
    val exchange: String? = null,
    @SerializedName("last_7_day_diff_in_%") val last7DayDiff: String? = null,
    @SerializedName("next_7_day_diff_in_%") val next7DayDiff: String? = null
)

data class FullShare(
    val date: String,
    val name: String,
    val price: String,
    val sector: String? = null,
    val country: String? = null,
    val exchange: String? = null,
    val last7DayDiff: String? = null,
    val next7DayDiff: String? = null
)

data class SimpleShare(
    val name: String,
    val price: Double
)
