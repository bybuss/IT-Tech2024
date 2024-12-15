package bob.colbaskin.hackatontemplate.analytics.presentation.statistic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticRepository
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullBond
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullCurrency
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullGold
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullShare
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetStatisticsViewModel @Inject constructor(
    private val analyticRepository: AnalyticRepository,
    private val authRepository: AuthRepository
): ViewModel(){
    private val _fullAsset = MutableStateFlow<Any?>(null)
    val fullAsset= _fullAsset.asStateFlow()

    fun loadFullAsset(assetType: String, assetName: String) {
//        viewModelScope.launch {
//            _fullAsset.value = when (assetType) {
//                "shares" -> FullShare(
//                    name = assetName,
//                    price =   51.0,
//                    sector = "Транспорт",
//                    country = "Россия",
//                    exchange = "Московская биржа",
//                    date = "date",
//                    last7DayDiff = "15%",
//                    next7DayDiff = "15%"
//                )
//                "gold" -> FullGold(
//                    date = "10.12.2024",
//                    price = 8426.19,
//                    last7DayDiff = "15%",
//                    next7DayDiff = "15%"
//                )
//                "currencies" -> FullCurrency(
//                    name = assetName,
//                    price = 99.3,
//                    code = "USD",
//                    date = "date",
//                    last7DayDiff = "15%",
//                    next7DayDiff = "15%"
//                )
//                "bounds" -> FullBond(
//                    name = assetName,
//                    price = 500.0,
//                    sector = "Потребительские услуги",
//                    country = "Россия",
//                    exchange = "Московская биржа",
//                    date = "date",
//                    last7DayDiff = "15%",
//                    next7DayDiff = "15%"
//                )
//                else -> null
//            }
//        }
    }
}