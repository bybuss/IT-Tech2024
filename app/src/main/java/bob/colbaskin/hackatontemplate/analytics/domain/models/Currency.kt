package bob.colbaskin.hackatontemplate.analytics.domain.models

data class OldCurrency(
    val date: String,
    val name: String,
    val price: Double
)

data class NewCurrency(
    val date: String,
    val name: String,
    val price: Double
)

data class SimpleCurrency(
    val name: String,
    val price: Double
)

data class FullCurrency(
    val date: String,
    val name: String,
    val price: Double,
    val code: String,
    val last7DayDiff: String,
    val next7DayDiff: String
)
