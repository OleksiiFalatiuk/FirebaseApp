package com.example.firebasefirsttry

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasefirsttry.database.firebase.FirebaseRepository
import com.example.firebasefirsttry.database.room.AppRoomDatabase
import com.example.firebasefirsttry.database.room.repository.RoomRepository
import com.example.firebasefirsttry.model.Note
import com.example.firebasefirsttry.utils.Constants.Keys.EMPTY
import com.example.firebasefirsttry.utils.DB_TYPE
import com.example.firebasefirsttry.utils.REPOSITORY
import com.example.firebasefirsttry.utils.TYPE_FIREBASE
import com.example.firebasefirsttry.utils.TYPE_ROOM
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    fun initDatabase(type: String, onSuccess: ()-> Unit) {
        Log.d("checkData", "MainViewModel initDatabase with type: $type")
        when(type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = FirebaseRepository()
                REPOSITORY.connectToDatabase(
                    { onSuccess() },
                    { Log.d("checkData", "Error: ${it}")}
                )
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun deleteNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun signOut(onSuccess: () -> Unit){
        when(DB_TYPE.value){
            TYPE_FIREBASE,
                TYPE_ROOM -> {
                    REPOSITORY.signOut()
                DB_TYPE.value = EMPTY
                onSuccess()
                }
            else -> { Log.d("checkData,", "ELSE: ${DB_TYPE}") }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll
}

class MainViewModelFactory(private val application: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}