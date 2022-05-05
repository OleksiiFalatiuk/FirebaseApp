package com.example.firebasefirsttry.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.firebasefirsttry.model.Note

@Dao
interface NoteRoomDao {

    @Query("SELECT * FROM notes_table")
    fun getAll(): LiveData<List<Note>>

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}