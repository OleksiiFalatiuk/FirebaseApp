package com.example.firebasefirsttry.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebasefirsttry.MainViewModel
import com.example.firebasefirsttry.MainViewModelFactory
import com.example.firebasefirsttry.navigation.NavRoute
import com.example.firebasefirsttry.ui.theme.FirebaseFirstTryTheme
import com.example.firebasefirsttry.utils.TYPE_FIREBASE
import com.example.firebasefirsttry.utils.TYPE_ROOM


@Composable
fun StartScreen(navController: NavHostController) {

    val context = LocalContext.current
    val nViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
      Column(
          modifier = Modifier.fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
      ) {
          Text(text = "What will we use?")
          Button(onClick = {
              nViewModel.initDatabase(TYPE_ROOM){
                  navController.navigate(route = NavRoute.Main.route)
              }
          },
          modifier = Modifier
              .width(200.dp)
              .padding(vertical = 8.dp)
          ) {
              Text(text = "Room database")
          }
          Button(onClick = {
              nViewModel.initDatabase(TYPE_FIREBASE){
                  navController.navigate(route = NavRoute.Main.route)
                           }

          },
          modifier = Modifier
              .width(200.dp)
              .padding(vertical = 8.dp)
          ) {
              Text(text = "Firebase database")
          }
      }
    }
}

@Preview(showBackground = true)
@Composable
fun prevStartScreen(){
    FirebaseFirstTryTheme {
        StartScreen(navController = rememberNavController())
    }
}