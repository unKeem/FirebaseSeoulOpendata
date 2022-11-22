package com.example.firebaseseoulopendata

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseseoulopendata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*insert data @firebase realtime base*/
        binding.btnInsert.setOnClickListener{
            val firebaseDataAccess = FirebaseDataAccess()
            val center = binding.edtCenterName.text.toString()
            val week = binding.edtLessonWeek.text.toString()
            val time = binding.edtLessonTime.text.toString()
            val fee = binding.edtLessonFee.text.toString()
            val swimLessonDAO = SwimLessonDAO("",center, week, time, fee)

            firebaseDataAccess.insertData(swimLessonDAO)?.addOnSuccessListener {
                Toast.makeText(this, "insert data success", Toast.LENGTH_SHORT).show()
                /*after insert, clear edtiTextView*/
                binding.apply {
                    edtCenterName.text.clear()
                    edtLessonWeek.text.clear()
                    edtLessonTime.text.clear()
                    edtLessonFee.text.clear()
                }
            }?.addOnFailureListener {
                Toast.makeText(this, "insert data fail", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnList.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}