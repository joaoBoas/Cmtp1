package ipvc.estg.cmtp1.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.cmtp1.EditNoteActivity
import ipvc.estg.cmtp1.R
import ipvc.estg.cmtp1.entities.Note

const val TITLE = "TITLE"
const val DESC = "DESC"
const val ID = "ID"

class LineAdapter (context: Context, private val interID: SendInfo): RecyclerView.Adapter<LineAdapter.NoteViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    interface SendInfo {
        fun catchingID(id: Int?)
    }

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val noteDate: TextView = itemView.findViewById(R.id.date_content)
        val noteTitle: TextView = itemView.findViewById(R.id.title_content)
        val noteDescription: TextView = itemView.findViewById(R.id.description_content)
        val noteEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
        val noteRemove: ImageButton = itemView.findViewById(R.id.btn_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_notes_line, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]

        holder.noteDate.text = current.date
        holder.noteTitle.text = current.title
        holder.noteDescription.text = current.description
        val id = current.id

        holder.noteRemove.setOnClickListener {
            interID.catchingID(id)
        }

        holder.noteEdit.setOnClickListener {
            val context = holder.noteTitle.context
            val title = holder.noteTitle.text.toString()
            val desc = holder.noteDescription.text.toString()

            val intent = Intent(context, EditNoteActivity::class.java).apply {
                putExtra(TITLE, title)
                putExtra(DESC, desc)
                putExtra(ID, id)
            }
            context.startActivity(intent)
        }
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}