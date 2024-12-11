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
import com.patrykandpatrick.vico.core.cartesian.CartesianChart

@Composable
fun AssetScreen(
    assetType: String, // Тип актива, например, "shares", "gold", "currencies", "bounds"
    fullAsset: Any, // Данные актива
    onScaffoldBackClick: () -> Unit // Функция для кнопки назад
) {
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
            // Отображаем информацию о выбранном активе
            when (fullAsset) {
                is FullShare -> {
                    val share = fullAsset as FullShare
                    Text("Акция: ${share.name}\nЦена: ${share.price}\nСектор: ${share.sector}")
                    AssetGraph(fullAsset) // Строим график для акций
                }
                is FullGold -> {
                    val gold = fullAsset as FullGold
                    Text("Дата: ${gold.date}\nЦена: ${gold.price}")
                    AssetGraph(fullAsset) // Строим график для золота
                }
                is FullCurrency -> {
                    val currency = fullAsset as FullCurrency
                    Text("Валюта: ${currency.name}\nКурс: ${currency.price}\nКод: ${currency.code}")
                    AssetGraph(fullAsset) // Строим график для валюты
                }
                is FullBound -> {
                    val bound = fullAsset as FullBound
                    Text("Облигация: ${bound.name}\nЦена: ${bound.price}\nСектор: ${bound.sector}")
                    AssetGraph(fullAsset) // Строим график для облигаций
                }
                else -> {
                    Text("Данные загружаются...")
                }
            }
        }
    }
}

@Composable
fun AssetGraph(fullAsset: Any) {
    // Данные для графика по Y (ценам) и X (дням)
    val xValues = listOf(1f, 2f, 3f, 4f) // Дни или индексы
    val yValues = when (fullAsset) {
        is FullShare -> {
            listOf(fullAsset.price, fullAsset.price + 5f, fullAsset.price - 3f, fullAsset.price + 2f) // Стоимость акций
        }
        is FullGold -> {
            listOf(fullAsset.price, fullAsset.price + 10f, fullAsset.price - 5f, fullAsset.price + 3f) // Цена золота
        }
        is FullCurrency -> {
            listOf(fullAsset.price, fullAsset.price * 1.1f, fullAsset.price * 0.95f, fullAsset.price * 1.05f) // Курс валюты
        }
        is FullBound -> {
            listOf(fullAsset.price, fullAsset.price + 2f, fullAsset.price - 1f, fullAsset.price + 1f) // Цена облигаций
        }
        else -> {
            listOf(0f, 0f, 0f, 0f) // По умолчанию
        }
    }

    // Здесь будет построение графика с двумя линиями
    LineChart(
        data1 = yValues,
        data2 = yValues.map { it * 1.05f }, // Прогноз (фиолетовая линия)
        labels = xValues.map { "День $it" } // Можно заменить на более осмысленные даты или дни
    )
}

@Composable
fun LineChart(data1: List<Float>, data2: List<Float>, labels: List<String>) {
    // График с двумя линиями: синяя для текущих данных и фиолетовая для прогноза
    CartesianChart(
        modifier = Modifier.fillMaxSize()
    ) {
        lineSeries {
            series(data1) // Первая линия (синяя)
            lineStyle {
                color = Color.Blue
            }
        }
        lineSeries {
            series(data2) // Вторая линия (фиолетовая)
            lineStyle {
                color = Color.Purple
            }
        }

        xAxis {
            labels = labels
        }

        yAxis {
            // Здесь можно настроить значения оси Y, например, цены
        }
    }
}

@Composable
fun TopNavBar(onClick: () -> Unit, title: String) {
    // Простой пример компонента навигационной панели
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

