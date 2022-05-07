package com.example.firebasefirsttry.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebasefirsttry.MainViewModel
import com.example.firebasefirsttry.MainViewModelFactory
import com.example.firebasefirsttry.navigation.NavRoute
import com.example.firebasefirsttry.ui.theme.FirebaseFirstTryTheme
import com.example.firebasefirsttry.utils.*
import com.example.firebasefirsttry.utils.Constants.Keys.FIREBASE_DATABASE
import com.example.firebasefirsttry.utils.Constants.Keys.LOG_IN
import com.example.firebasefirsttry.utils.Constants.Keys.ROOM_DATABASE
import com.example.firebasefirsttry.utils.Constants.Keys.WHAT_WILL_WE_USE
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

    val buttomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var login by remember {
        mutableStateOf(Constants.Keys.EMPTY)
    }
    var password by remember {
        mutableStateOf(Constants.Keys.EMPTY)
    }

    ModalBottomSheetLayout(
        sheetState = buttomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 32.dp)
                ) {
                    Text(
                        text = LOG_IN,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login,
                        onValueChange = {login = it},
                        label = { Text(text = Constants.Keys.LOGIN)},
                        isError = login.isEmpty()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text(text = Constants.Keys.PASSWORD)},
                        isError = password.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            LOGIN = login
                            PASSWORD = password
                            viewModel.initDatabase(TYPE_FIREBASE){
                                DB_TYPE = TYPE_FIREBASE
                                navController.navigate(NavRoute.Main.route)
                            }
                        },
                        enabled = login.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.SIGN_IN)
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = WHAT_WILL_WE_USE)
                Button(onClick = {
                    viewModel.initDatabase(TYPE_ROOM){
                        DB_TYPE = TYPE_ROOM
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = ROOM_DATABASE)
                }
                Button(onClick = {
                    coroutineScope.launch {
                        buttomSheetState.show()
                    }
                },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = FIREBASE_DATABASE)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevStartScreen(){
    FirebaseFirstTryTheme {
        val context = LocalContext.current
        val nViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        StartScreen(navController = rememberNavController(), viewModel = nViewModel)
    }
}