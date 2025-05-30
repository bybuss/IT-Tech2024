package bob.colbaskin.hackatontemplate.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import bob.colbaskin.hackatontemplate.analytics.presentation.AssetSelectionScreen
import bob.colbaskin.hackatontemplate.analytics.presentation.AssetStatisticsScreen
import bob.colbaskin.hackatontemplate.home.presentation.HomeScreenDetailed
import bob.colbaskin.hackatontemplate.navigation.DetailsScreen
import bob.colbaskin.hackatontemplate.profile.presentation.ProfileScreenDetailed

fun NavGraphBuilder.detailsNavGraph (navController: NavHostController) {
    navigation(
        startDestination = DetailsScreen.Home.route,
        route = Graph.DETAILS
    ) {
        composable(DetailsScreen.Home.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            HomeScreenDetailed (
                id = id,
                onClick = {
                    navController.navigateUp()
                }
            )
        }
        composable(DetailsScreen.Profile.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ProfileScreenDetailed (
                id = id,
                onClick = {
                    navController.navigateUp()
                }
            )
        }
        composable(DetailsScreen.AssetSelection.route) { backStackEntry ->
            val assetType = backStackEntry.arguments?.getString("assetType") ?: "shares"
            AssetSelectionScreen(
                assetType = assetType,
                onAssetSelected = { selectedAssetType ->
                    navController.navigate(DetailsScreen.AssetSelection.createRoute(selectedAssetType))
                },
                onStatisticClicked = { assetId ->
                    navController.navigate(
                        DetailsScreen.AssetStatistics.createRoute(assetType, assetId)
                    )
                }
            )
        }

        composable(DetailsScreen.AssetStatistics.route) { backStackEntry ->
            val assetType = backStackEntry.arguments?.getString("assetType") ?: "shares"
            val assetId = backStackEntry.arguments?.getString("assetId") ?: "id"
            AssetStatisticsScreen(
                assetType = assetType,
                assetId = assetId,
                onScaffoldBackClick = {
                    navController.navigateUp()
                },
            )
        }
    }
}