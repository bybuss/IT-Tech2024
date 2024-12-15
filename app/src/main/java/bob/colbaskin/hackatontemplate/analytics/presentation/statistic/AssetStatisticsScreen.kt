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
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullBond
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullCurrency
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullGold
import bob.colbaskin.hackatontemplate.analytics.domain.models.FullShare
import bob.colbaskin.hackatontemplate.analytics.presentation.select.AssetSelectionViewModel
import bob.colbaskin.hackatontemplate.navigation.TopNavBar
import com.patrykandpatrick.vico.core.cartesian.CartesianChart

@Composable
fun AssetStatisticsScreen(
    assetType: String,
    onScaffoldBackClick: () -> Unit
) {
    //
}


