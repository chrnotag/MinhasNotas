package com.example.listacontatos.Data.DBAcess

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.listacontatos.Data.BEAN.NotesBean
import com.example.listacontatos.Data.DAO.userDAO

@Database(entities = [NotesBean::class], version = 1)
abstract class DBAcess : RoomDatabase() {
    abstract fun userDao(): userDAO
}