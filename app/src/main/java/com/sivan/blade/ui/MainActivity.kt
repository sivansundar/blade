/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sivan.blade.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import com.sivan.blade.ui.graph.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint
import com.sivan.blade.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

sealed class BottomNavigationScreens(val route: String, val title: String, val icon: ImageVector) {
    object Contacts : BottomNavigationScreens("contacts", "Contacts", Icons.Rounded.AccountBox)
    object Settings : BottomNavigationScreens("settings", "Settings", Icons.Rounded.Settings)

    fun existsInGraph(title: String) {

    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionRequester.initialize(applicationContext)
        setupPermissions()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun setupPermissions() {
        val requester = PermissionRequester.instance()

        lifecycleScope.launch {
            requester.request(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
            ).collect { p ->
                when (p) {
                    is PermissionResult.Granted -> {
                        setContent {
                            val navController = rememberNavController()

                            MyApplicationTheme {
                                RootNavigationGraph(navController)
                            }
                        }
                    }

                    is PermissionResult.Denied -> Log.d(
                        "Permission",
                        "Data : ${p.permission} denied"
                    ) // denied, not interested in reason
                    is PermissionResult.Denied.NeedsRationale -> Log.d(
                        "Permission",
                        "Data : ${p.permission} needs rationale"
                    ) // show rationale
                    is PermissionResult.Denied.DeniedPermanently -> Log.d(
                        "Permission",
                        "Data : ${p.permission} denied for good"
                    ) // no go
                    is PermissionResult.Cancelled -> Log.d(
                        "Permission",
                        "Data : request cancelled"
                    ) // op canceled, repeat the request
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomNavigationItems: List<BottomNavigationScreens>
) {


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = bottomNavigationItems.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {

            bottomNavigationItems.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text("${screen.title}") },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }

        }
    }

}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val IMPORT = "import_graph"
}
