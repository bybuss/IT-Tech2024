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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticRepository: AnalyticRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _budget = MutableStateFlow("")
    val budget = _budget.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    init {
        viewModelScope.launch {
            val json = analyticRepository.fetchJsonFromApi()?.trimIndent() ?: "null"
            getBonds(json)
            getCurrencies(json)
            getGold(json)
            getShares(json)
            getDeposits(json)
        }
    }

    fun getBonds(json: String) {
        val gson = Gson()
        val bond = gson.fromJson<Bonds>(json, object : TypeToken<Bonds>() {}.type)

        bond.bonds.forEach { (date, bondDateMap) ->
            bondDateMap.forEach { (name, bondDetails) ->
                val fullBonds = FullBond(
                    date = date,
                    name = name,
                    price = bondDetails.price,
                    sector = bondDetails.sector,
                    country = bondDetails.country,
                    exchange = bondDetails.exchange,
                    last7DayDiff = bondDetails.last7DayDiff,
                    next7DayDiff = bondDetails.next7DayDiff,
                )

                Log.d("AnalyticsRep", fullBonds.toString())
            }
        }
    }

    fun getCurrencies(json: String) {
        val gson = Gson()
        val currency = gson.fromJson<Currencies>(json, object : TypeToken<Currencies>() {}.type)

        currency.currencies.forEach { (date, currencyDateMap) ->
            currencyDateMap.forEach { (name, currencyDetails) ->
                val fullCurrencies = FullCurrency(
                    date = date,
                    name = name,
                    price = currencyDetails.price,
                    code = currencyDetails.code,
                    last7DayDiff = currencyDetails.last7DayDiff,
                    next7DayDiff = currencyDetails.next7DayDiff
                )

                Log.d("AnalyticsRep", fullCurrencies.toString())
            }
        }
    }

    fun getGold(json: String) {
        val gson = Gson()
        val gold = gson.fromJson<Golds>(json, object : TypeToken<Golds>() {}.type)


        gold.gold.forEach { (date, goldDateMap) ->
            val fullGolds = FullGold(
                date = date,
                price = goldDateMap.price,
                last7DayDiff = goldDateMap.last7DayDiff,
                next7DayDiff = goldDateMap.next7DayDiff
            )

            Log.d("AnalyticsRep", fullGolds.toString())
        }
    }

    fun getShares(json: String) {
        val gson = Gson()
        val shares = gson.fromJson<Shares>(json, object : TypeToken<Shares>() {}.type)

        shares.shares.forEach { (date, sharesDateMap) ->
            sharesDateMap.forEach { (name, sharesDetails) ->
                val fullShare = FullShare(
                    date = date,
                    name = name,
                    price = sharesDetails.price,
                    sector = sharesDetails.sector,
                    country = sharesDetails.country,
                    exchange = sharesDetails.exchange,
                    last7DayDiff = sharesDetails.last7DayDiff,
                    next7DayDiff = sharesDetails.next7DayDiff
                )

                Log.d("AnalyticsRep", fullShare.toString())
            }
        }
    }

    fun getDeposits(json: String) {
        val gson = Gson()
        val deposits = gson.fromJson<Deposits>(json, object : TypeToken<Deposits>() {}.type)

        deposits.deposits.forEach{ (name, depositDetails) ->
            val fullDeposit = FullDeposit(
                name = name,
                minValue = depositDetails.minValue,
                maxValue = depositDetails.maxValue,
                yearPercent = depositDetails.yearPercent
            )

            Log.d("AnalyticsRep", fullDeposit.toString())
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
}
