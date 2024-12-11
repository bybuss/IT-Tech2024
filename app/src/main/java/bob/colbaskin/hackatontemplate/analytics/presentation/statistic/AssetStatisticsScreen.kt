package bob.colbaskin.hackatontemplate.analytics.presentation.statistic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullBound
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullCurrency
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullGold
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullShare
import bob.colbaskin.hackatontemplate.analytics.presentation.select.AssetSelectionViewModel
import bob.colbaskin.hackatontemplate.navigation.TopNavBar

@Composable
fun AssetStatisticsScreen(
    assetType: String,
    onScaffoldBackClick: () -> Unit,
    viewModel: AssetStatisticsViewModel = hiltViewModel(),
    assetSelectionViewModel: AssetSelectionViewModel = hiltViewModel()
) {
    val assetData by assetSelectionViewModel.assetData.collectAsState()

    LaunchedEffect(assetType) {

        when (assetType) {
            "shares" -> {
                assetData?.shares?.map {
                    viewModel.loadFullAsset(assetType, it.name)
                }
            }
            "gold" -> {
                assetData?.gold?.map {
                    viewModel.loadFullAsset(assetType, it.date)
                }
            }
            "currencies" -> {
                assetData?.currencies?.map {
                    viewModel.loadFullAsset(assetType, it.name)
                }
            }
            "bounds" -> {
                assetData?.bounds?.map {
                    viewModel.loadFullAsset(assetType, it.name)
                }
            }
            else -> {
            }
        }
    }

    val fullAsset by viewModel.fullAsset.collectAsState()

    Scaffold(
        topBar = {
            TopNavBar(
                onClick = onScaffoldBackClick,
                title = when (assetType) {
                    "shares" -> "Акции"
                    "gold" -> "Золото"
                    "currencies" -> "Валюта"
                    "bounds" -> "Облигации"
                    else -> "Неизвестный экран"
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (fullAsset) {
                is FullShare -> {
                    val share = fullAsset as FullShare
                    Text("Акция: ${share.name}\nЦена: ${share.price}\nСектор: ${share.sector}")
                }
                is FullGold -> {
                    val gold = fullAsset as FullGold
                    Text("Дата: ${gold.date}\nЦена: ${gold.price}")
                }
                is FullCurrency -> {
                    val currency = fullAsset as FullCurrency
                    Text("Валюта: ${currency.name}\nКурс: ${currency.price}\nКод: ${currency.code}")
                }
                is FullBound -> {
                    val bound = fullAsset as FullBound
                    Text("Облигация: ${bound.name}\nЦена: ${bound.price}\nСектор: ${bound.sector}")
                }
                else -> {
                    Text("Данные загружаются...")
                }
            }
        }
    }
}
