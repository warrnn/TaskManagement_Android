package paba.coba.android_task_list_apps

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class AddOrEditTask : AppCompatActivity() {
    private lateinit var _textName: TextView
    private lateinit var _textDate: TextView
    private lateinit var _buttonDate: Button
    private lateinit var _textDescription: TextView
    private lateinit var _submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_or_edit_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _textName = findViewById(R.id.etNamaTask)
        _textDate = findViewById(R.id.tvSelectedDate)
        _buttonDate = findViewById(R.id.datePickerBtn)
        _textDescription = findViewById(R.id.etDeskripsiTask)
        _submitButton = findViewById(R.id.submitBtn)

        val calendarBox = Calendar.getInstance()
        val dateVox =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                calendarBox.set(Calendar.YEAR, year)
                calendarBox.set(Calendar.MONTH, month)
                calendarBox.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateText(calendarBox)
            }

        _buttonDate.setOnClickListener {
            DatePickerDialog(
                this,
                dateVox,
                calendarBox.get(Calendar.YEAR),
                calendarBox.get(Calendar.MONTH),
                calendarBox.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val dataIntent = intent.getParcelableExtra<Task>("DATA", Task::class.java)
        val position = intent.getIntExtra("POSITION", -1)

        if (dataIntent != null) {
            _textName.setText(dataIntent.nama)
            _textDate.setText(dataIntent.tanggal)
            _textDescription.setText(dataIntent.deskripsi)
        }

        _submitButton.setOnClickListener {
            val intent = Intent().apply {
                putExtra("NAMA", _textName.text.toString())
                putExtra("TANGGAL", _textDate.text.toString())
                putExtra("DESKRIPSI", _textDescription.text.toString())
                putExtra("IS_EDIT", if (dataIntent != null) true else false)
                putExtra("POSITION", position)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun updateDateText(calendar: Calendar) {
        val dateFormat = "dd MMMM yyyy"
        val simple = SimpleDateFormat(dateFormat, Locale("id", "ID"))  // Set locale to Indonesian
        _textDate.setTextColor(Color.BLUE)
        _textDate.setText(simple.format(calendar.time))
    }
}