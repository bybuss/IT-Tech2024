package bob.colbaskin.hackatontemplate.analytics.domain.models

data class AssetData(
    val bounds: List<SimpleBound>,
    val currencies: List<SimpleCurrency>,
    val gold: List<SimpleGold>,
    val shares: List<SimpleShare>
)
