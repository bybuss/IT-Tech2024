package bob.colbaskin.hackatontemplate.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home: Screen("home", "Главная", Icons.Default.Home)
    object Analytics: Screen("analytics", "Аналитика", Icons.Default.AutoGraph)
    object Profile: Screen("profile", "Профиль", Icons.Default.Person)
    object Welcome: DetailsScreen("welcome")
}

sealed class AuthScreen(val route: String) {
    object AuthenticationScreen: AuthScreen("web_browser")
}

sealed class DetailsScreen(val route: String) {
    object Home: DetailsScreen("home_details")
    object Profile: DetailsScreen("profile_details")
    object AssetSelection : DetailsScreen("asset_selection/{assetType}") {
        fun createRoute(assetType: String) = "asset_selection/$assetType"
    }
    object AssetStatistics : DetailsScreen("asset_statistics/{assetType}") {
        fun createRoute(assetType: String) = "asset_statistics/$assetType"
    }
}