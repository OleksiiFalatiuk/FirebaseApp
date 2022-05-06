package com.example.firebasefirsttry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasefirsttry.MainViewModel
import com.example.firebasefirsttry.screens.*

sealed class NavRoute(val route: String){
    object Start: NavRoute("start_screen")
    object Main: NavRoute("main_screen")
    object Add: NavRoute("add_screen")
    object Note: NavRoute("note_screen")
}

@Composable
fun NotesNavHost(nViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route){
        composable(NavRoute.Start.route){ StartScreen(navController = navController, viewModel = nViewModel) }
        composable(NavRoute.Main.route){ MainScreen(navController = navController, viewModel = nViewModel) }
        composable(NavRoute.Add.route){ AddScreen(navController = navController, viewModel = nViewModel) }
        composable(NavRoute.Note.route){ NoteScreen(navController = navController, viewModel = nViewModel) }
    }
}