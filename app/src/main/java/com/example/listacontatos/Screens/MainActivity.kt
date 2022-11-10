package com.example.listacontatos.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.listacontatos.Adapters.RecyclerContatoAdapter
import com.example.listacontatos.Data.BEAN.NotesBean
import com.example.listacontatos.Data.DAO.userDAO
import com.example.listacontatos.Data.DBAcess.DBAcess
import com.example.listacontatos.R
import com.example.listacontatos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /**Instancia da vinculação de visualizações**/
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var listaDados: MutableList<NotesBean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /**Configurar o acesso ao DB**/
        listaDados = listaDados(acessoDB()) as MutableList<NotesBean>

        /**Configuração do RecyclerView**/
        val adapter = RecyclerContatoAdapter(listaDados)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager

        /**Ação do Floating Action Button para chamar a tela de editar/criar nova nota**/
        binding.add.setOnClickListener {
            val intent = Intent(this, NoteOpen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun acessoDB(): userDAO {
        val db = Room.databaseBuilder(
            this,
            DBAcess::class.java,
            "notesDB"
        ).allowMainThreadQueries().build()
        return db.userDao()
    }

    private fun listaDados(db: userDAO): List<NotesBean> {
        return db.getAll()
    }

    override fun onResume() {
        super.onResume()
        listaDados = listaDados(acessoDB()) as MutableList<NotesBean>

    }
}