package com.example.listacontatos.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.Room
import com.example.listacontatos.Data.BEAN.NotesBean
import com.example.listacontatos.Data.DBAcess.DBAcess
import com.example.listacontatos.R
import com.example.listacontatos.Screens.NoteOpen

class RecyclerContatoAdapter(private val dados: MutableList<NotesBean>) :
    RecyclerView.Adapter<RecyclerContatoAdapter.viewHolder>() {
    inner class viewHolder(itemView: View) : ViewHolder(itemView) {
        var titlenote: TextView
        var note: TextView
        var delete: ImageView

        init {
            titlenote = itemView.findViewById(R.id.title_note)
            note = itemView.findViewById(R.id.note)
            delete = itemView.findViewById(R.id.delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_layout, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val db = Room.databaseBuilder(
            holder.itemView.context,
            DBAcess::class.java,
            "notesDB"
        ).allowMainThreadQueries().build()
        val userDao = db.userDao()

        /**Preencher os itens do Recyclerview com os dados do DB**/
        holder.titlenote.text = dados[position].title
        holder.note.text = dados[position].text

        /**Abrir a tela de editar notas para a nota selecionada**/
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NoteOpen::class.java)
            intent.putExtra("uid", "${dados[position].uid}")
            intent.putExtra("title", dados[position].title)
            intent.putExtra("text", dados[position].text)
            holder.itemView.context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            userDao.delete(dados[position])
            dados.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = dados.size
}