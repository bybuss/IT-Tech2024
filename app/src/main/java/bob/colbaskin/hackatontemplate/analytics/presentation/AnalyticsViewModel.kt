package bob.colbaskin.hackatontemplate.analytics.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticRepository
import bob.colbaskin.hackatontemplate.analytics.domain.models.Bonds
import bob.colbaskin.hackatontemplate.analytics.domain.models.Currencies
import bob.colbaskin.hackatontemplate.analytics.domain.models.Deposits
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullBond
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullCurrency
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullDeposit
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullGold
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullShare
import bob.colbaskin.hackatontemplate.analytics.domain.models.Golds
import bob.colbaskin.hackatontemplate.analytics.domain.models.Shares
import bob.colbaskin.hackatontemplate.analytics.utils.toDoublePrice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

sealed class Asset {
    data class Bond(val bond: FullBond): Asset()
    data class Currency(val currency: FullCurrency): Asset()
    data class Gold(val gold: FullGold): Asset()
    data class Share(val share: FullShare): Asset()
}

data class AssetsUiState (
    val bonds: List<Asset.Bond> = emptyList(),
    val currencies: List<Asset.Currency> = emptyList(),
    val golds: List<Asset.Gold> = emptyList(),
    val shares: List<Asset.Share> = emptyList(),
    val deposits: List<FullDeposit> = emptyList()
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticRepository: AnalyticRepository
) : ViewModel() {

    private val _assetsUiState = MutableStateFlow(AssetsUiState())
    val assetsUiState = _assetsUiState.asStateFlow()

    private val _assetsForToday = MutableStateFlow(AssetsUiState())
    val assetsForToday = _assetsForToday.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _budget = MutableStateFlow("")
    val budget = _budget.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private val currentDate = "10.12.2024"

    init {
        viewModelScope.launch {
            val json = analyticRepository.fetchJsonFromApi()?.trimIndent() ?: "null"
            loadAssetsData(json)
        }
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setBudget(budget: String) {
        _budget.value = budget
    }
    fun setDate(date: String) {
        _date.value = date
    }

    fun getAssetPoints(assetType: String, assetId: String):  Map<String, Map<LocalDate, Number>> {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val assetDates: List<LocalDate> = when (assetType) {
            "bonds" -> assetsUiState.value.bonds
                .filter { it.bond.name == assetId }
                .map { LocalDate.parse(it.bond.date, dateFormatter) }
            "currencies" -> assetsUiState.value.currencies
                .filter { it.currency.name == assetId }
                .map { LocalDate.parse(it.currency.date, dateFormatter) }
            "gold" -> assetsUiState.value.golds.map { LocalDate.parse(it.gold.date, dateFormatter) }
            else -> assetsUiState.value.shares
                .filter { it.share.name == assetId }
                .map { LocalDate.parse(it.share.date, dateFormatter) }
        }

        val assetNames: List<String> = when (assetType) {
            "bonds" -> assetsUiState.value.bonds
                .filter { it.bond.name == assetId }
                .map { it.bond.name }
            "currencies" -> assetsUiState.value.currencies
                .filter { it.currency.name == assetId }
                .map { it.currency.name }
            "gold" -> listOf("Золото")
            else -> assetsUiState.value.shares
                .filter { it.share.name == assetId }
                .map { it.share.name }
        }

        val assetPrices: List<Number> = when (assetType) {
            "bonds" -> assetsUiState.value.bonds
                .filter { it.bond.name == assetId }
                .map { it.bond.price.toDoublePrice() }
            "currencies" -> assetsUiState.value.currencies
                .filter { it.currency.name == assetId }
                .map { it.currency.price }
            "gold" -> assetsUiState.value.golds.map { it.gold.price }
            else -> assetsUiState.value.shares
                .filter { it.share.name == assetId }
                .map { it.share.price.toDoublePrice() }
        }

        val result = mutableMapOf<String, MutableMap<LocalDate, Number>>()
        for (i in assetNames.indices) {
            val name = assetNames[i]
            val date = assetDates[i]
            val price = assetPrices[i]

            result.computeIfAbsent(name) { mutableMapOf() } [date] = price
        }

        Log.d("Analytics", "Loaded assets points for $assetType: $result \n$assetDates")

        return result
    }

    private fun loadAssetsData(json: String) {
        getBonds(json)
        getCurrencies(json)
        getGold(json)
        getShares(json)
        getDeposits(json)
        Log.d("Analytics", "Loaded assets data: ${_assetsUiState.value}")
        Log.d("Analytics", "Loaded assets data for today: ${_assetsForToday.value}")
    }

    private fun getBonds(json: String) {
        val gson = Gson()
        val bond = gson.fromJson<Bonds>(json, object : TypeToken<Bonds>() {}.type)

        val newBonds: List<Asset.Bond> = bond.bonds.flatMap { (date, bondDateMap) ->
            bondDateMap.map { (name, bondDetails) ->
                Asset.Bond(
                    bond = FullBond(
                        date = date,
                        name = name,
                        price = bondDetails.price,
                        sector = bondDetails.sector,
                        country = bondDetails.country,
                        exchange = bondDetails.exchange,
                        last7DayDiff = bondDetails.last7DayDiff,
                        next7DayDiff = bondDetails.next7DayDiff
                    )
                )
            }
        }

        val bondsForTorToday: List<Asset.Bond> = newBonds.filter {
            it.bond.date == currentDate
        }

        _assetsUiState.value = _assetsUiState.value.copy (
            bonds = newBonds
        )

        _assetsForToday.value = _assetsForToday.value.copy (
            bonds = bondsForTorToday
        )
    }

    private fun getCurrencies(json: String) {
        val gson = Gson()
        val currency = gson.fromJson<Currencies>(json, object : TypeToken<Currencies>() {}.type)

        val newCurrencies: List<Asset.Currency> = currency.currencies.flatMap { (date, currencyDateMap) ->
            currencyDateMap.map { (name, currencyDetails) ->
                Asset.Currency(
                    currency = FullCurrency(
                        date = date,
                        name = name,
                        price = currencyDetails.price,
                        code = currencyDetails.code,
                        last7DayDiff = currencyDetails.last7DayDiff,
                        next7DayDiff = currencyDetails.next7DayDiff
                    )
                )
            }
        }

        val currenciesForToday: List<Asset.Currency> = newCurrencies.filter {
            it.currency.date == currentDate
        }

        _assetsForToday.value = _assetsForToday.value.copy (
            currencies = currenciesForToday
        )

        _assetsUiState.value = _assetsUiState.value.copy(
            currencies = newCurrencies
        )
    }

    private fun getGold(json: String) {
        val gson = Gson()
        val gold = gson.fromJson<Golds>(json, object : TypeToken<Golds>() {}.type)

        val newGold: List<Asset.Gold> = gold.gold.map { (date, goldDateMap) ->
            Asset.Gold(
                gold = FullGold(
                    date = date,
                    price = goldDateMap.price,
                    last7DayDiff = goldDateMap.last7DayDiff,
                    next7DayDiff = goldDateMap.next7DayDiff
                )
            )
        }

        val goldsForToday: List<Asset.Gold> = newGold.filter {
            it.gold.date == currentDate
        }

        _assetsForToday.value = _assetsForToday.value.copy (
            golds = goldsForToday
        )

        Log.d("Analytics", "Loaded today assets data: ${_assetsForToday.value}\n For current Date: $currentDate & asset date: ${goldsForToday.map { it.gold.date }}")

        _assetsUiState.value = _assetsUiState.value.copy(
            golds = newGold
        )
    }

    private fun getShares(json: String) {
        val gson = Gson()
        val shares = gson.fromJson<Shares>(json, object : TypeToken<Shares>() {}.type)

        val newShares: List<Asset.Share> = shares.shares.flatMap { (date, sharesDateMap) ->
            sharesDateMap.map { (name, sharesDetails) ->
                Asset.Share(
                    share = FullShare(
                        date = date,
                        name = name,
                        price = sharesDetails.price,
                        sector = sharesDetails.sector,
                        country = sharesDetails.country,
                        exchange = sharesDetails.exchange,
                        last7DayDiff = sharesDetails.last7DayDiff,
                        next7DayDiff = sharesDetails.next7DayDiff
                    )
                )

            }
        }

        val sharesForToday: List<Asset.Share> = newShares.filter {
            it.share.date == currentDate
        }

        _assetsForToday.value = _assetsForToday.value.copy (
            shares = sharesForToday
        )

        _assetsUiState.value = _assetsUiState.value.copy(
            shares = newShares
        )
    }

    private fun getDeposits(json: String) {
        val gson = Gson()
        val deposits = gson.fromJson<Deposits>(json, object : TypeToken<Deposits>() {}.type)

        val newDeposits: List<FullDeposit> = deposits.deposits.map { (name, depositDetails) ->
            FullDeposit(
                name = name,
                minValue = depositDetails.minValue,
                maxValue = depositDetails.maxValue,
                yearPercent = depositDetails.yearPercent
            )
        }

        _assetsUiState.value = _assetsUiState.value.copy(
            deposits = newDeposits
        )
    }
}
