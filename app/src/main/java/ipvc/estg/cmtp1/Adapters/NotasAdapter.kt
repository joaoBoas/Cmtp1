package ipvc.estg.cmtp1.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.cmtp1.R
import ipvc.estg.cmtp1.entities.Notas
import kotlinx.android.synthetic.main.note_items.view.*


class NotasAdapter(val notas:List<Notas>, val recyclerClick: RecyclerClick) : RecyclerView.Adapter<NotasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.note_items,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notas.get(position)
        holder.txtTitle?.text = note.title
        holder.txtDescription?.text = note.description

    }


    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var txtTitle: TextView? = null
        var txtDescription: TextView? = null


        init {
            row.setOnClickListener{
                recyclerClick.onItemClick(adapterPosition)
            }
            this.txtTitle = row.findViewById(R.id.txtTitle)
            this.txtDescription =row.findViewById(R.id.txtDescription)

        }


    }

    interface RecyclerClick{
        fun onItemClick(position: Int)
    }

    fun getNoteAt(position: Int): Notas{
        return notas.get(position)
    }

    }
