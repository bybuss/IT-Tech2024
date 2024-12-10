package bob.colbaskin.hackatontemplate.analytics.domain.models

data class SimpleShare(
    val name: String,
    val price: Double
)

data class FullShare(
    val name: String,
    val price: Double,
    val sector: String,
    val country: String,
    val exchange: String
)

