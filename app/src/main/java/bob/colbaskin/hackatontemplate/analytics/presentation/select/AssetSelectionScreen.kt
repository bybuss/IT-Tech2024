package bob.colbaskin.hackatontemplate.analytics.presentation.select

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

@Composable
fun AssetSelectionScreen(
    assetType: String,
    onStatisticClicked: (String) -> Unit,
    onAssetSelected: (String) -> Unit,
    viewModel: AssetSelectionViewModel = hiltViewModel()
) {
    val assetData by viewModel.assetData.collectAsState()

    val assets = when (assetType) {
        "bounds" -> assetData?.bounds?.map { it.name } ?: emptyList()
        "currencies" -> assetData?.currencies?.map { it.name } ?: emptyList()
        "gold" -> assetData?.gold?.map { it.date } ?: emptyList()
        "shares" -> assetData?.shares?.map { it.name } ?: emptyList()
        else -> emptyList()
    }

    val previousScreen = when (assetType) {
        "bounds" -> "currencies"
        "currencies" -> "gold"
        "gold" -> "shares"
        "shares" -> "bounds"
        else -> null
    }

    val nextScreen = when (assetType) {
        "bounds" -> "shares"
        "currencies" -> "bounds"
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
            items(assets) { assetName ->
                AssetItem(
                    name = assetName,
                    onClick = { onStatisticClicked(assetType) }
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
                "bounds" -> "Облигации"
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
fun AssetItem(name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = onClick) {
            Text("Подробнее")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetItemPreview() {
    AssetItem(name = "Test", onClick = {})
}

@Preview(showBackground = true)
@Composable
fun AssetSelectionHeaderPreview() {
    AssetSelectionHeader(
        assetType = "bounds",
        onPrevious = {},
        onNext = {},
    )
}

@Preview(showBackground = true)
@Composable
fun AssetSelectionScreenPreview() {
    AssetSelectionScreen(
        assetType = "bounds",
        onStatisticClicked = {},
        onAssetSelected = {}
    )
}