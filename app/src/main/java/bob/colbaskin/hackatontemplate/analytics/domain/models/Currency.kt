package bob.colbaskin.hackatontemplate.analytics.domain.models

import com.google.gson.annotations.SerializedName

data class Currencies(
    val currencies: Map<String, Map<String, CurrencyDateMap>>
)

data class CurrencyDateMap(
    val price: Double,
    val code: String? = null,
    @SerializedName("last_7_day_diff_in_%") val last7DayDiff: String? = null,
    @SerializedName("next_7_day_diff_in_%") val next7DayDiff: String? = null
)

data class FullCurrency(
    val date: String,
    val name: String,
    val price: Double,
    val code: String? = null,
    val last7DayDiff: String? = null,
    val next7DayDiff: String? = null
)

data class SimpleCurrency(
    val name: String,
    val price: Double
)
