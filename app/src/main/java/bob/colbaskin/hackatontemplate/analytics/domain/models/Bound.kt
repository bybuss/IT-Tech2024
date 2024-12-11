package bob.colbaskin.hackatontemplate.analytics.domain.models

data class OldBound(
    val date: String,
    val name: String,
    val price: Double
)

data class NewBound(
    val date: String,
    val name: String,
    val price: Double
)

data class SimpleBound(
    val name: String,
    val price: Double
)

data class FullBound(
    val date: String,
    val name: String,
    val price: Double,
    val sector: String,
    val country: String,
    val exchange: String,
    val last7DayDiff: String,
    val next7DayDiff: String
)
