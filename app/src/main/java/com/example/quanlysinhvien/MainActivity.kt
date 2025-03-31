package com.example.quanlysinhvien

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val studentList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val studentName: EditText = findViewById(R.id.studentName)
        val studentId: EditText = findViewById(R.id.studentID)
        val buttonAddStudent: Button = findViewById(R.id.btnAddStudent)
        val delStudent: EditText = findViewById(R.id.delStudentByID)
        val buttonDelStudent: Button = findViewById(R.id.btnDelStudent)

        listView = findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, studentList)
        listView.adapter = adapter

        buttonAddStudent.setOnClickListener {
            val name = studentName.text.toString().trim()
            val mssv = studentId.text.toString().trim()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                studentList.add(0, "$name - $mssv")
                adapter.notifyDataSetChanged()

                studentName.text.clear()
                studentId.text.clear()
            } else {
                Toast.makeText(this, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelStudent.setOnClickListener {
            val mssvToDelete = delStudent.text.toString().trim()

            if (mssvToDelete.isNotEmpty()) {
                val iterator = studentList.iterator()
                var found = false

                while (iterator.hasNext()) {
                    val student = iterator.next()
                    if(student.endsWith(mssvToDelete)) {
                        iterator.remove()
                        found = true
                        break
                    }
                }
                if (found) {
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Da xoa sinh vien co mssv la $mssvToDelete", Toast.LENGTH_SHORT).show()
                    delStudent.text.clear()
                } else {
                    Toast.makeText(this, "Khong tim thay mssv can xoa!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui long nhap mssv de xoa", Toast.LENGTH_SHORT).show()
            }
        }

    }
}