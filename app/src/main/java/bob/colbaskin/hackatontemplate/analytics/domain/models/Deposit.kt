package bob.colbaskin.hackatontemplate.analytics.domain.models

import com.google.gson.annotations.SerializedName

data class Deposits(
    val deposits: Map<String, DepositMap>
)

data class DepositMap (
    @SerializedName("min_value") val minValue: Int,
    @SerializedName("max_value") val maxValue: Int,
    @SerializedName("year_percent") val yearPercent: Int
)

data class FullDeposit(
    val name: String,
    val minValue: Int,
    val maxValue: Int,
    val yearPercent: Int
)
