package com.example.listacontatos.Data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.listacontatos.Data.BEAN.NotesBean

@Dao
interface userDAO {
    @Query("SELECT * FROM notes")
    fun getAll(): MutableList<NotesBean>

    @Query("SELECT * FROM notes WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): MutableList<NotesBean>

    @Query("SELECT * FROM notes WHERE uid = :id")
    fun findbyId(id: Long): NotesBean

    @Insert
    fun insertAll(vararg users: NotesBean)

    @Delete
    fun delete(user: NotesBean)

    @Update
    fun update(user: NotesBean)
}