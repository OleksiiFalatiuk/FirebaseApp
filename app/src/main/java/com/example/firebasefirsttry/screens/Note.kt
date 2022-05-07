package com.example.firebasefirsttry.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.firebasefirsttry.navigation.NavRoute
import com.example.firebasefirsttry.ui.theme.FirebaseFirstTryTheme
import com.example.firebasefirsttry.utils.Constants
import com.example.firebasefirsttry.utils.Constants.Keys.DELETE
import com.example.firebasefirsttry.utils.Constants.Keys.EDIT_NOTE
import com.example.firebasefirsttry.utils.Constants.Keys.EMPTY
import com.example.firebasefirsttry.utils.Constants.Keys.NAV_BACK
import com.example.firebasefirsttry.utils.Constants.Keys.NONE
import com.example.firebasefirsttry.utils.Constants.Keys.SUBTITLE
import com.example.firebasefirsttry.utils.Constants.Keys.TITLE
import com.example.firebasefirsttry.utils.Constants.Keys.UPDATE
import com.example.firebasefirsttry.utils.DB_TYPE
import com.example.firebasefirsttry.utils.TYPE_FIREBASE
import com.example.firebasefirsttry.utils.TYPE_ROOM
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = when(DB_TYPE) {
        TYPE_ROOM -> {
            notes.firstOrNull { it.id == noteId?.toInt() } ?: Note()
        }
        TYPE_FIREBASE -> {
            notes.firstOrNull { it.firebaseId == noteId } ?: Note()
        }
        else -> Note()
    }
    val buttonSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var title by remember {
        mutableStateOf(EMPTY)
    }
    var subtitle by remember {
        mutableStateOf(EMPTY)
    }

    ModalBottomSheetLayout(
        sheetState = buttonSheetState,
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
                        text = EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = {title = it},
                        label = { Text(text = TITLE)},
                        isError = title.isEmpty()
                    )
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = {subtitle = it},
                        label = { Text(text = SUBTITLE)},
                        isError = subtitle.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            viewModel.updateNote(note =
                            Note(id = note.id, title = title, subtitle = subtitle, firebaseId = note.firebaseId)
                            ){
                                navController.navigate(NavRoute.Main.route)
                            }
                        }
                    ) {
                        Text(text = UPDATE)
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
                        coroutineScope.launch {
                            title = note.title
                            subtitle = note.subtitle
                            buttonSheetState.show()
                        }
                    }) {
                        Text(text = UPDATE)
                    }
                    Button(onClick = {
                        viewModel.deleteNote(note = note){
                            navController.navigate(NavRoute.Main.route)
                        }
                    }) {
                        Text(text = DELETE)
                    }}
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate(NavRoute.Main.route)
                    }) {
                    Text(text = NAV_BACK)
                }
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