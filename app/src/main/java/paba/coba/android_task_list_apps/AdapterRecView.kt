package paba.coba.android_task_list_apps

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecView(private val listTasks: MutableList<Task>) :
    RecyclerView.Adapter<AdapterRecView.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onEditClicked(data: Task)
        fun onDeleteClicked(position: Int)
        fun onWorkClicked(position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask = itemView.findViewById<TextView>(R.id.tvNamaTask)
        var _tanggalTask = itemView.findViewById<TextView>(R.id.tvTanggalTask)
        var _deskripsiTask = itemView.findViewById<TextView>(R.id.tvDeskripsiTask)
        var _deleteBtn = itemView.findViewById<Button>(R.id.deleteBtn)
        var _editBtn = itemView.findViewById<Button>(R.id.editBtn)
        var _workBtn = itemView.findViewById<Button>(R.id.workBtn)
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

        // Looping display data
        holder._namaTask.setText(task.nama)
        holder._tanggalTask.setText(task.tanggal)
        holder._deskripsiTask.setText(task.deskripsi)
        when {
            task.isStart && !task.isDone -> {
                holder._editBtn.visibility = View.GONE
                holder._workBtn.text = "Finish"
                holder._workBtn.setTextColor(Color.BLACK)
                holder._workBtn.setBackgroundColor(Color.YELLOW)
            }

            task.isStart && task.isDone -> {
                holder._tanggalTask.text = "Task Complete"
                holder._tanggalTask.setTextColor(Color.GREEN)
                holder._editBtn.visibility = View.GONE
                holder._workBtn.visibility = View.GONE
            }

            else -> {
                holder._tanggalTask.setTextColor(Color.RED)
                holder._editBtn.isEnabled = true
                holder._editBtn.setBackgroundColor(Color.parseColor("#1976D2"))
                holder._editBtn.visibility = View.VISIBLE
                holder._workBtn.text = "Start"
                holder._workBtn.setTextColor(Color.WHITE)
                holder._workBtn.setBackgroundColor(Color.parseColor("#388E3C"))
                holder._workBtn.visibility = View.VISIBLE
            }
        }


        holder._editBtn.setOnClickListener {
            onItemClickCallback.onEditClicked(listTasks[position])
        }

        holder._deleteBtn.setOnClickListener {
            onItemClickCallback.onDeleteClicked(position)
        }

        holder._workBtn.setOnClickListener {
            onItemClickCallback.onWorkClicked(position)
        }
    }
}