package com.sivan.blade.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sivan.blade.ui.Graph
import com.sivan.blade.ui.import.FinishScreen
import com.sivan.blade.ui.contacts.details.DetailsScreen

fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController
) {
    // Use nav controller here for all internal navigation
    navigation(
        route = Graph.IMPORT,
        startDestination = DetailsScreen.Details.route
    ) {
        composable(route = DetailsScreen.Details.route) {

            val contactId =
                it.arguments?.getString(DetailsScreen.Details.CONTACT_ID) ?: ""

            DetailsScreen(
                contactId = contactId,
                onFinishClick = {
                    navController.navigate(DetailsScreen.Finish.route)
                }
            )

        }
        composable(route = DetailsScreen.Finish.route) {
            FinishScreen()

        }
    }
}

sealed class DetailsScreen(val route: String, val title : String) {
    object Details : DetailsScreen(route = "details/{contact_id}", title = "Import") {
        val CONTACT_ID = "contact_id"
        fun create(id: String) = "details/$id"
    }
    object Finish : DetailsScreen(route = "finish", title = "Finish")

    // Edit notes as a feature maybe
}