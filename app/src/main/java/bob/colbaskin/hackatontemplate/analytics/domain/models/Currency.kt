package bob.colbaskin.hackatontemplate.analytics.domain.models

data class SimpleCurrency(
    val name: String,
    val price: Double
)

data class FullCurrency(
    val name: String,
    val price: Double,
    val code: String
)

