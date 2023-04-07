package com.sivan.blade.ui.graph

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sivan.blade.ui.BottomBar
import com.sivan.blade.ui.BottomNavigationScreens
import com.sivan.blade.ui.contacts.ContactsScreen
import com.sivan.blade.ui.Graph
import com.sivan.blade.ui.SettingsScreen
import com.sivan.blade.ui.graph.ImportScreen.Import.CONTACT_ID


@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        composable(route = Graph.HOME) {
            RootScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        content = {
            RootNavHost(navController = navController, modifier = Modifier.padding(it))
        }
    )
}

@Composable
fun RootNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavigationScreens.Contacts.route
    ) {
        composable(route = BottomNavigationScreens.Contacts.route) {
            ContactsScreen(navigateToDetails = {
                navController.navigate(
                    ImportScreen.Import.create(
                        it.contactId.toString()
                    )
                )
            })
        }
        composable(route = BottomNavigationScreens.Settings.route) {
            SettingsScreen()
        }

        importNavGraph(navController = navController)
    }
}

