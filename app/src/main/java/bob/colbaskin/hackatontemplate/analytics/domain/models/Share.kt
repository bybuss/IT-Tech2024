package bob.colbaskin.hackatontemplate.analytics.domain.models

data class OldShare(
    val date: String,
    val name: String,
    val price: Double
)

data class NewShare(
    val date: String,
    val name: String,
    val price: Double
)

data class SimpleShare(
    val name: String,
    val price: Double
)

data class FullShare(
    val date: String,
    val name: String,
    val price: Double,
    val sector: String,
    val country: String,
    val exchange: String,
    val last7DayDiff: String,
    val next7DayDiff: String
)
