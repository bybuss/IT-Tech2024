package bob.colbaskin.hackatontemplate.analytics.domain.models

data class SimpleBond(
    val name: String,
    val price: Double
)

data class FullBond(
    val name: String,
    val price: Double,
    val sector: String,
    val country: String,
    val exchange: String
)

