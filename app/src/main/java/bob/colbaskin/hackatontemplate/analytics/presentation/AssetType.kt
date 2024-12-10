package bob.colbaskin.hackatontemplate.analytics.presentation

import bob.colbaskin.hackatontemplate.R

sealed class AssetType(val displayName: String, val icon: Int) {
    object Shares : AssetType("Акции", R.drawable.splash_icon)
    object Gold : AssetType("Золото", R.drawable.splash_icon)
    object Currencies : AssetType("Валюта", R.drawable.splash_icon)
    object Bonds : AssetType("Облигации", R.drawable.splash_icon)

    companion object {
        fun fromType(type: String): AssetType {
            return when (type) {
                "shares" -> Shares
                "gold" -> Gold
                "currencies" -> Currencies
                "bonds" -> Bonds
                else -> throw IllegalArgumentException("Unknown asset type: $type")
            }
        }
    }
}
