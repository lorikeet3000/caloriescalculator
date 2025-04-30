package ru.caloriescalculator.calories.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val screenItems = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.History
    )
    BottomNavigation(
        backgroundColor = Color.White,
        windowInsets = BottomNavigationDefaults.windowInsets
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        screenItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                icon =  {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}