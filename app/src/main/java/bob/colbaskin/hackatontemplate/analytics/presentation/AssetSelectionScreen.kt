package bob.colbaskin.hackatontemplate.analytics.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullGold

@Composable
fun AssetSelectionScreen(
    assetType: String,
    onStatisticClicked: (String) -> Unit,
    onAssetSelected: (String) -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val assetsUiState by viewModel.assetsForToday.collectAsState()

    val assets: List<Asset> = when (assetType) {
        "bonds" -> assetsUiState.bonds
        "currencies" -> assetsUiState.currencies
        "gold" -> assetsUiState.golds
        "shares" -> assetsUiState.shares
        else -> emptyList()
    }

    val previousScreen = when (assetType) {
        "bonds" -> "currencies"
        "currencies" -> "gold"
        "gold" -> "shares"
        "shares" -> "bonds"
        else -> null
    }

    val nextScreen = when (assetType) {
        "bonds" -> "shares"
        "currencies" -> "bonds"
        "gold" -> "currencies"
        "shares" -> "gold"
        else -> null
    }

    Column {

        AssetSelectionHeader(
            assetType = assetType,
            onPrevious = { previousScreen?.let { onAssetSelected(it) } },
            onNext = { nextScreen?.let { onAssetSelected(it) } }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(assets) { asset ->
                AssetItem(
                    asset = asset,
                    onClick = { assetId ->
                        onStatisticClicked(assetId)
                    }
                )
            }
        }
    }
}

@Composable
fun AssetSelectionHeader(
    assetType: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPrevious) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = when (assetType) {
                "shares" -> "Акции"
                "gold" -> "Золото"
                "currencies" -> "Валюта"
                "bonds" -> "Облигации"
                else -> "Неизвестный экран"
            },
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        IconButton(onClick = onNext) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Дальше",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AssetItem(asset: Asset,  onClick: (String) -> Unit) {

    val assetId = when(asset) {
        is Asset.Bond -> asset.bond.name
        is Asset.Currency -> asset.currency.name
        is Asset.Gold -> asset.gold.date
        is Asset.Share -> asset.share.name
    }

    val assetDate = when(asset) {
        is Asset.Bond -> asset.bond.date
        is Asset.Currency -> asset.currency.date
        is Asset.Gold -> asset.gold.date
        is Asset.Share -> asset.share.date
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = assetId,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = { onClick(assetId) }) {
            Text("Подробнее")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetItemPreview() {
    AssetItem(
        asset = Asset.Gold(
            gold = FullGold(
                date = "2024-01-01",
                price = 1850.50,
                last7DayDiff = "2%",
                next7DayDiff = "-1%"
            )
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AssetSelectionHeaderPreview() {
    AssetSelectionHeader(
        assetType = "bonds",
        onPrevious = {},
        onNext = {},
    )
}

@Preview(showBackground = true)
@Composable
fun AssetSelectionScreenPreview() {
    AssetSelectionScreen(
        assetType = "bonds",
        onStatisticClicked = {},
        onAssetSelected = {}
    )
}