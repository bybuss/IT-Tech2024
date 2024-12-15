package bob.colbaskin.hackatontemplate.analytics.domain.models

import com.google.gson.annotations.SerializedName

data class Bonds(
    val bonds: Map<String, Map<String, BondDateMap>>
)

data class BondDateMap(
    val price: String,
    val sector: String? = null,
    val country: String? = null,
    val exchange: String? = null,
    @SerializedName("last_7_day_diff_in_%") val last7DayDiff: String? = null,
    @SerializedName("next_7_day_diff_in_%") val next7DayDiff: String? = null
)

data class FullBond(
    val date: String,
    val name: String,
    val price: String,
    val sector: String? = null,
    val country: String? = null,
    val exchange: String? = null,
    val last7DayDiff: String? = null,
    val next7DayDiff: String? = null
)

data class SimpleBond(
    val name: String,
    val price: Double
)
