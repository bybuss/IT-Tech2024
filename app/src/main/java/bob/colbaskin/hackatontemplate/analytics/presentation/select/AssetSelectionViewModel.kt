package bob.colbaskin.hackatontemplate.analytics.presentation.select

import androidx.lifecycle.ViewModel
import bob.colbaskin.hackatontemplate.analytics.domain.models.AssetData
import bob.colbaskin.hackatontemplate.analytics.domain.models.SimpleBound
import bob.colbaskin.hackatontemplate.analytics.domain.models.SimpleCurrency
import bob.colbaskin.hackatontemplate.analytics.domain.models.SimpleGold
import bob.colbaskin.hackatontemplate.analytics.domain.models.SimpleShare
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AssetSelectionViewModel @Inject constructor(): ViewModel() {
    private val _assetData = MutableStateFlow<AssetData?>(null)
    val assetData = _assetData.asStateFlow()

    init {
        loadAssets()
    }

    private fun loadAssets() {
        _assetData.value = AssetData(
            bounds = listOf(
                SimpleBound("Облигация 1", 500.0),
                SimpleBound("Облигация 2", 700.0)
            ), currencies = listOf(
                SimpleCurrency("USD", 99.3),
                SimpleCurrency("EUR", 105.1)
            ), gold = listOf(
                SimpleGold("10.12.2024", 8426.19),
                SimpleGold("09.12.2024", 8439.17)
            ), shares = listOf(
                SimpleShare("Акция 1", 51.0),
                SimpleShare("Акция 2", 1100.0)
            )
        )
    }
}
