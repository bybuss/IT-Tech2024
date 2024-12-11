package bob.colbaskin.hackatontemplate.analytics.data.models

data class AssetData(
    val gold: Map<String, Map<String, Any>>,
    val shares: Map<String, Map<String, Any>>,
    val currencies: Map<String, Map<String, Any>>,
    val bounds: Map<String, Map<String, Any>>
)


