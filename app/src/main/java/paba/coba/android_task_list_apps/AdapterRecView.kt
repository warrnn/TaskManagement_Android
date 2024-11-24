package paba.coba.android_task_list_apps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecView(private val listTasks: ArrayList<Task>) :
    RecyclerView.Adapter<AdapterRecView.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask = itemView.findViewById<TextView>(R.id.tvNamaTask)
        var _tanggalTask = itemView.findViewById<TextView>(R.id.tvTanggalTask)
        var _deskripsiTask = itemView.findViewById<TextView>(R.id.tvDeskripsiTask)
    }

    // Membuat suatu tampilan dan mengembalikannya
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_recycler, parent, false)
        return ListViewHolder(view)
    }

    // Mengembalikan jumlah item yang tersedia untuk ditampilkan
    override fun getItemCount(): Int {
        return listTasks.size
    }

    // Menghubungkan data dengan view holder pada posisi yang ditentukan dalam RecyclerView
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var task = listTasks[position]

        holder._namaTask.setText(task.nama)
        holder._tanggalTask.setText(task.tanggal)
        holder._deskripsiTask.setText(task.deskripsi)
    }
}