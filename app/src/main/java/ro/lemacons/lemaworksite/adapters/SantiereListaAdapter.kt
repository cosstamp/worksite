package ro.lemacons.lemaworksite.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ro.lemacons.lemaworksite.R
import ro.lemacons.lemaworksite.data.Santiere
import java.text.NumberFormat

class SantiereListAdapter internal constructor(context: Context) : RecyclerView.Adapter<SantiereListAdapter.SantiereViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var santiere = emptyList<Santiere>() // Cached copy of words

    inner class SantiereViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val SantiereItemView_nume: TextView = itemView.findViewById(R.id.textView)
        val SantiereItemView_cod: TextView = itemView.findViewById(R.id.textView_cod)
        val SantiereItemView_data_fin: TextView = itemView.findViewById(R.id.textView_data)
        val SantiereItemView_buget: TextView = itemView.findViewById(R.id.textView_buget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SantiereViewHolder {
        val itemView = inflater.inflate(R.layout.content_recyclerview_item_main, parent, false)
        return SantiereViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SantiereViewHolder, position: Int) {
        val current = santiere[position]
        holder.SantiereItemView_nume.text = current.nume_santier
        holder.SantiereItemView_cod.text = String.format ("Cod:\n%s", current.cod_santier)
        holder.SantiereItemView_data_fin.text = String.format ("Data finalizare:\n%s", current.data_fin_santier)
        holder.SantiereItemView_buget.text = String.format ("Buget:\n%s lei",
            NumberFormat.getIntegerInstance().format(current.buget))

    }

    internal fun setSantiere(santiere: List<Santiere>) {
        this.santiere = santiere
        notifyDataSetChanged()
    }

    override fun getItemCount() = santiere.size
}