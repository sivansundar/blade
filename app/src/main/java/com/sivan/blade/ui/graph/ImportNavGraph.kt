package com.sivan.blade.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sivan.blade.ui.Graph
import com.sivan.blade.ui.import.FinishScreen
import com.sivan.blade.ui.import.ImportScreen

fun NavGraphBuilder.importNavGraph(navController: NavHostController) {
    // Use nav controller here for all internal navigation
    navigation(
        route = Graph.IMPORT,
        startDestination = ImportScreen.Import.route
    ) {
        composable(route = ImportScreen.Import.route) {
            ImportScreen(
                onFinishClick = {
                    navController.navigate(ImportScreen.Finish.route)
                }
            )
        }
        composable(route = ImportScreen.Finish.route) {
            FinishScreen()
        }
    }
}

sealed class ImportScreen(val route: String) {
    object Import : ImportScreen(route = "Import")
    object Finish : ImportScreen(route = "finish")
}