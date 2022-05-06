package com.example.firebasefirsttry.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.firebasefirsttry.model.Note
import com.example.firebasefirsttry.ui.theme.FirebaseFirstTryTheme
import com.example.firebasefirsttry.utils.Constants
import com.example.firebasefirsttry.utils.Constants.Keys.DELETE
import com.example.firebasefirsttry.utils.Constants.Keys.NAV_BACK
import com.example.firebasefirsttry.utils.Constants.Keys.NONE
import com.example.firebasefirsttry.utils.Constants.Keys.SUBTITLE
import com.example.firebasefirsttry.utils.Constants.Keys.TITLE
import com.example.firebasefirsttry.utils.Constants.Keys.UPDATE


@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = notes.firstOrNull{ it.id == noteId?.toInt()} ?: Note(title = NONE, subtitle = NONE)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = note.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    Text(
                        text = note.subtitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                /*TODO*/
                }) {
                    Text(text = UPDATE)
                }
                Button(onClick = {
                    /*TODO*/
                }) {
                    Text(text = DELETE)
                }}
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onClick = {
                    /*TODO*/
                }) {
                    Text(text = NAV_BACK)
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevNoteScreen(){
    FirebaseFirstTryTheme() {
        val context = LocalContext.current
        val nViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        NoteScreen(
            navController = rememberNavController(),
            viewModel = nViewModel,
            noteId = "1"
        )
    }
}