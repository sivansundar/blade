package com.sivan.blade.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sivan.blade.ui.BottomNavigationScreens
import com.sivan.blade.ui.Graph
import com.sivan.blade.ui.import.FinishScreen
import com.sivan.blade.ui.import.ImportScreen

fun NavGraphBuilder.importNavGraph(
    navController: NavHostController
) {
    // Use nav controller here for all internal navigation
    navigation(
        route = Graph.IMPORT,
        startDestination = ImportScreen.Import.route
    ) {
        composable(route = ImportScreen.Import.route) {

            val contactId =
                it.arguments?.getString(ImportScreen.Import.CONTACT_ID) ?: ""

            ImportScreen(
                contactId = contactId,
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

sealed class ImportScreen(val route: String, val title : String) {
    object Import : ImportScreen(route = "import/{contact_id}", title = "Import") {
        val CONTACT_ID = "contact_id"
        fun create(id: String) = "import/$id"
    }
    object Finish : ImportScreen(route = "finish", title = "Finish")
}