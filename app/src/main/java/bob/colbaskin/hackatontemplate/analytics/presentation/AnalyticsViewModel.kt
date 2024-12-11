package bob.colbaskin.hackatontemplate.analytics.presentation

import androidx.lifecycle.ViewModel
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
