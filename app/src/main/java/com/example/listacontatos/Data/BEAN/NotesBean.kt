package com.example.listacontatos.Data.BEAN

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NotesBean(
    @PrimaryKey(autoGenerate = true) var uid: Long=0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "text") var text: String,
)