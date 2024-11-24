package paba.coba.android_task_list_apps

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    // Menggunakan lateinit karena akan membuat variabel yang non-null tetapi akan kita deklarasikan di dalam function
    private lateinit var _namaTask : Array<String>
    private lateinit var _tanggalTask : Array<String>
    private lateinit var _deskripsiTask : Array<String>

    private var listTasks = arrayListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var _rvTask = findViewById<RecyclerView>(R.id.rvTask)

        fun loadData() {
            _namaTask = resources.getStringArray(R.array.nama_task)
            _tanggalTask = resources.getStringArray(R.array.tanggal_task)
            _deskripsiTask = resources.getStringArray(R.array.deskripsi_task)
        }

        fun addData() {
            // Dengan menggunakan .indices maka position akan berada pada index yang ada pada data yang sudah siapkan melalui function loadData()
            for (position in _namaTask.indices) {
                val data = Task(
                    _namaTask[position],
                    _tanggalTask[position],
                    _deskripsiTask[position]
                )
                listTasks.add(data)
            }
        }

        fun showData() {
            _rvTask.layoutManager = LinearLayoutManager(this)
            _rvTask.adapter = AdapterRecView(listTasks)
        }

        // Memanggil ketiga fungsi yang sudah dibuat pada onCreate() dengan urutan yang benar.
        loadData()
        addData()
        showData()
    }
}