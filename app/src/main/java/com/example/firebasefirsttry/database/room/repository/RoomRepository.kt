package com.example.firebasefirsttry.database.room.repository

import androidx.lifecycle.LiveData
import com.example.firebasefirsttry.database.DatabaseRepository
import com.example.firebasefirsttry.database.room.dao.NoteRoomDao
import com.example.firebasefirsttry.model.Note

class RoomRepository(private val noteRoomDao: NoteRoomDao): DatabaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAll()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note = note)
        onSuccess()
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note = note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note = note)
        onSuccess()
    }
}