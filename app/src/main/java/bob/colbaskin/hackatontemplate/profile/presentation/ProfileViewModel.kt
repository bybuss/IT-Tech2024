package bob.colbaskin.hackatontemplate.profile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.profile.domain.models.SimpleStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authDataStoreRepository: AuthDataStoreRepository
): ViewModel() {

    init {
        getTasks()
        getInventory()
        loadAchievements()
    }

    private val _strategies = MutableStateFlow<List<SimpleStrategy>>(emptyList())
    val strategies = _strategies.asStateFlow()

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

    fun accountExit() {
        viewModelScope.launch{
            authDataStoreRepository.clearToken()
            Log.d("Profile", "accountExit -> token cleared")
        }
    }

    fun getTasks() {
        Log.d("ProfileViewModel", "getTasks")
    }

    fun getInventory() {
        Log.d("ProfileViewModel", "getInventory")
    }

    fun loadAchievements() {
       Log.d("ProfileViewModel", "loadAchievements")
    }
}