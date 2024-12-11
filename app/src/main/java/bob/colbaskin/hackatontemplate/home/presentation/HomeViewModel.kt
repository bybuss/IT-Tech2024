package bob.colbaskin.hackatontemplate.home.presentation

import androidx.lifecycle.ViewModel
import bob.colbaskin.hackatontemplate.profile.domain.models.SimpleStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(){
    private val _strategies = MutableStateFlow<List<SimpleStrategy>>(emptyList())
    val strategies = _strategies.asStateFlow()

    init {
        setStrategies()
    }

    fun setStrategies() {
        _strategies.value = listOf(
            SimpleStrategy(
                id = UUID.randomUUID(),
                startDate = "01.01.2000",
                endDate = "01.01.20001",
                sum = 2000000
            ),
            SimpleStrategy(
                id = UUID.randomUUID(),
                startDate = "01.02.2000",
                endDate = "01.02.20001",
                sum = 2000000
            ),
            SimpleStrategy(
                id = UUID.randomUUID(),
                startDate = "01.03.2000",
                endDate = "01.03.20001",
                sum = 2000000
            ),
            SimpleStrategy(
                id = UUID.randomUUID(),
                startDate = "01.04.2000",
                endDate = "01.04.20001",
                sum = 2000000
            ),
            SimpleStrategy(
                id = UUID.randomUUID(),
                startDate = "01.05.2000",
                endDate = "01.05.20001",
                sum = 2000000
            ),
        )
    }
}