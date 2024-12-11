package bob.colbaskin.hackatontemplate.analytics.domain.models

data class OldGold(
    val date: String,
    val price: Double
)

data class NewGold(
    val date: String,
    val price: Double
)

data class SimpleGold(
    val date: String,
    val price: Double
)

data class FullGold(
    val date: String,
    val price: Double,
    val last7DayDiff: String,
    val next7DayDiff: String
)
