package bob.colbaskin.hackatontemplate.analytics.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AssetStatisticsScreen(
    assetType: String
) {
    when (assetType) {
        "shares" -> {
            Text("Stock Price History")
        }
        "gold" -> {
            Text("Gold Price History")
        }
        "bounds" -> {
            Text("Bonds Price History")
        }
        "currencies" -> {
            Text("Currency Price History")
        }
        else -> {
            Text("Unknown asset type")
        }
    }
}
