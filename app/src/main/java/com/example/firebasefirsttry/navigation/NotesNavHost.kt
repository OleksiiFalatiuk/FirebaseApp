package com.example.firebasefirsttry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasefirsttry.MainViewModel
import com.example.firebasefirsttry.screens.*
import com.example.firebasefirsttry.utils.Constants.Keys.ID
import com.example.firebasefirsttry.utils.Constants.Screens.ADD_SCREEN
import com.example.firebasefirsttry.utils.Constants.Screens.MAIN_SCREEN
import com.example.firebasefirsttry.utils.Constants.Screens.NOTE_SCREEN
import com.example.firebasefirsttry.utils.Constants.Screens.START_SCREEN

sealed class NavRoute(val route: String){
    object Start: NavRoute(START_SCREEN)
    object Main: NavRoute(MAIN_SCREEN)
    object Add: NavRoute(ADD_SCREEN)
    object Note: NavRoute(NOTE_SCREEN)
}

@Composable
fun NotesNavHost(nViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route){
        composable(NavRoute.Start.route){ StartScreen(navController = navController, viewModel = nViewModel) }
        composable(NavRoute.Main.route){ MainScreen(navController = navController, viewModel = nViewModel) }
        composable(NavRoute.Add.route){ AddScreen(navController = navController, viewModel = nViewModel) }
        composable(NavRoute.Note.route + "/{${ID}}"){ backStackEntry ->
            NoteScreen(navController = navController, viewModel = nViewModel, noteId =  backStackEntry.arguments?.getString(ID))
        }
    }
}