package paba.coba.android_task_list_apps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    var listTasks = mutableListOf<Task>()

    private lateinit var _rvTask: RecyclerView
    private lateinit var taskAdapter: AdapterRecView

    private val IntentTaskLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val nama = data?.getStringExtra("NAMA")
                val tanggal = data?.getStringExtra("TANGGAL")
                val deskripsi = data?.getStringExtra("DESKRIPSI")

                val isEdit = data?.getBooleanExtra("IS_EDIT", false) ?: false
                val position = data?.getIntExtra("POSITION", -1) ?: -1

                Log.d("MainActivity", "isEdit: $isEdit, position: $position")
                if (isEdit && position != -1) {
                    listTasks[position] = Task(nama!!, tanggal!!, deskripsi!!, false, false)
                    taskAdapter.notifyItemChanged(position)
                } else {
                    val task = Task(nama!!, tanggal!!, deskripsi!!, false, false)
                    listTasks.add(task)
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _rvTask = findViewById(R.id.rvTask)
        _rvTask.layoutManager = LinearLayoutManager(this)
        taskAdapter = AdapterRecView(listTasks)
        _rvTask.adapter = taskAdapter

        var _addTaskBtn = findViewById<Button>(R.id.addTaskBtn)

        _addTaskBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddOrEditTask::class.java)
            IntentTaskLauncher.launch(intent)
        }

        taskAdapter.setOnItemClickCallback(object : AdapterRecView.OnItemClickCallback {
            override fun onEditClicked(data: Task) {
                val intent = Intent(this@MainActivity, AddOrEditTask::class.java).apply {
                    putExtra("DATA", data)
                    putExtra("POSITION", listTasks.indexOf(data))
                }
                IntentTaskLauncher.launch(intent)
            }

            override fun onDeleteClicked(position: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete",
                        { dialog, which ->
                            listTasks.removeAt(position)
                            taskAdapter.notifyItemRemoved(position)
                        })
                    .setNegativeButton("Cancel",
                        { dialog, which ->
                            Toast.makeText(
                                this@MainActivity,
                                "Task not deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ).show()
            }

            override fun onWorkClicked(position: Int) {
                var task = listTasks[position]
                when {
                    task.isStart && !task.isDone -> {
                        task.isDone = true // Mark the task as done
                    }

                    !task.isStart -> {
                        task.isStart = true // Start the task
                    }
                }
                taskAdapter.notifyItemChanged(position) // Refresh the item in RecyclerView
            }
        })
    }
}