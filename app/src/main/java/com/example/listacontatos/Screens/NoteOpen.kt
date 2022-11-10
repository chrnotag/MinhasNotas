package com.example.listacontatos.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.listacontatos.Data.BEAN.NotesBean
import com.example.listacontatos.Data.DAO.userDAO
import com.example.listacontatos.Data.DBAcess.DBAcess
import com.example.listacontatos.R
import com.example.listacontatos.databinding.ActivityNoteOpenBinding

class NoteOpen : AppCompatActivity() {

    private val binding by lazy {
        ActivityNoteOpenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /** Configurações da Toolbar**/
        val toolbar = binding.noteToolbar
        toolbar.title = "Title"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val db = Room.databaseBuilder(
            this,
            DBAcess::class.java,
            "notesDB"
        ).allowMainThreadQueries().build()
        val userDao = db.userDao()


        /**métodos da CRUD**/
        binding.saveNota.setOnClickListener {
            SaveNote(
                binding.titleNote.text.toString(),
                binding.areaNota.text.toString(),
                userDao
            )
            onBackPressed()
        }
        //Resgatando a UID enviada pelo Adapter do RecyclerView
        val uidResgatada = intent?.getStringExtra("uid")
        //Verificar se a UID está vazia
        //caso esteja vazia quer dizer que é uma nova nota sendo escrita
        //caso contrario irá carregar a nota pertencente ao uid carregado
        if (uidResgatada != null) {
            val data = LoadNote(userDao, uidResgatada.toLong())
            binding.titleNote.setText(data.title)
            binding.areaNota.setText(data.text)
            binding.noteToolbar.title = data.title
        }
    }

    override fun onBackPressed() {
        if (verificarDados())
            finish()
        else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun SaveNote(title: String, note: String, bd: userDAO) {
        bd.insertAll(NotesBean(title = title, text = note))
    }

    private fun LoadNote(bd: userDAO, id: Long): NotesBean {
        return bd.findbyId(id)
    }

    private fun verificarDados(): Boolean {
        return intent.getStringExtra("uid") != null
    }
}