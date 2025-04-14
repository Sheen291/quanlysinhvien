package com.example.quanlysinhvien

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var listView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        listView.layoutManager = LinearLayoutManager(this)

        adapter = StudentAdapter(studentList)
        listView.adapter = adapter

        buttonAddStudent.setOnClickListener {
            val name = studentName.text.toString().trim()
            val mssv = studentId.text.toString().trim()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                studentList.add(0, "$name - $mssv")
                adapter.notifyItemInserted(0)
                listView.scrollToPosition(0)

                studentName.text.clear()
                studentId.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDelStudent.setOnClickListener {
            val mssvToDelete = delStudent.text.toString().trim()

            if (mssvToDelete.isNotEmpty()) {
                val index = studentList.indexOfFirst { it.endsWith(mssvToDelete) }
                if (index != -1) {
                    studentList.removeAt(index)
                    adapter.notifyItemRemoved(index)
                    Toast.makeText(this, "Đã xóa sinh viên có MSSV là $mssvToDelete", Toast.LENGTH_SHORT).show()
                    delStudent.text.clear()
                } else {
                    Toast.makeText(this, "Không tìm thấy MSSV cần xóa!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập MSSV để xóa", Toast.LENGTH_SHORT).show()
            }
        }
    }


    inner class StudentAdapter(private val students: List<String>) :
        RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

        inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtStudent: TextView = itemView.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return StudentViewHolder(view)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.txtStudent.text = students[position]
        }

        override fun getItemCount(): Int {
            return students.size
        }
    }
}
