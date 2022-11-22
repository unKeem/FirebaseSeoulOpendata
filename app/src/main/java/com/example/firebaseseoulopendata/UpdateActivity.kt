package com.example.firebaseseoulopendata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseseoulopendata.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("id") && intent.hasExtra("center") && intent.hasExtra("week") && intent.hasExtra(
                "time") && intent.hasExtra("fee")
        ) {
            val key = intent.getStringExtra("id")!!
            binding.edtCenterName.setText(intent.getStringExtra("center"))
            binding.edtLessonWeek.setText(intent.getStringExtra("week"))
            binding.edtLessonTime.setText(intent.getStringExtra("time"))
            binding.edtLessonFee.setText(intent.getStringExtra("fee"))

            binding.btnUpdate.setOnClickListener {
                val firebaseDataAccess = FirebaseDataAccess()
                val center = binding.edtCenterName.text.toString()
                val week = binding.edtLessonWeek.text.toString()
                val time = binding.edtLessonTime.text.toString()
                val fee = binding.edtLessonFee.text.toString()
                val hashMap: HashMap<String, Any> = HashMap()
                hashMap["center"] = center
                hashMap["week"] = week
                hashMap["time"] = time
                hashMap["fee"] = fee
                firebaseDataAccess.updateData(key, hashMap).addOnSuccessListener {
                    Toast.makeText(this, "update data success", Toast.LENGTH_SHORT).show()
                    Log.d("firebasecrud", "success update @UpdateActivity")
                    val intent = Intent(this@UpdateActivity, ListActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "update data fail", Toast.LENGTH_SHORT).show()
                    Log.d("firebasecrud", "failed update @UpdateActivity")
                }
            }
        } else {
            binding.btnUpdate.isEnabled = true
        }
    }
}