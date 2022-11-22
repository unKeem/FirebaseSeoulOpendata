package com.example.firebaseseoulopendata

import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseseoulopendata.databinding.ActivityListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.selects.select

class ListActivity : AppCompatActivity() {
    lateinit var binding:ActivityListBinding
    lateinit var dataList: MutableList<SwimLessonDAO>
    lateinit var adapter: LessonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataList = mutableListOf()
        adapter = LessonListAdapter(dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        selectData()


        /*add recyvlerView swipe function*/
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val userDAO = UserDAO()
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val key = userList.get(position).userKey
                        userDAO.deleteUser(key).addOnSuccessListener {
                            Toast.makeText(applicationContext,
                                "delete user success",
                                Toast.LENGTH_SHORT).show()
                            Log.d("firebasecrud", "success deleteUser() @UpdateActivity")
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext,
                                "delete user fail",
                                Toast.LENGTH_SHORT).show()
                            Log.d("firebasecrud", "fail deleteUser() @UpdateActivity")
                        }
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                RecyclerViewSwipeDecorator.Builder(c,recyclerView, viewHolder, dX,dY, actionState,isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_24)
                    .addSwipeLeftLabel("delete")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .create()
                    .decorate()
                super.onChildDraw(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun selectData() {
        val data = FirebaseDataAccess()
        data.selectData()?.addValueEventListener(object : ValueEventListener{
            /*run on success - Receive data one by one in json format and set class type*/
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (lessonData in snapshot.children) {
                    val lesson = lessonData.getValue(SwimLessonDAO::class.java)
                    lesson?.id = lessonData.key.toString()
                    if (lesson != null) {
                        dataList.add(lesson)
                    }
                }//for
                adapter.notifyDataSetChanged()
            }//onDataChange

            /*run on failure*/
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "failed loading data", Toast.LENGTH_SHORT).show()
                Log.d("firebasecrud", "failed selectUser() @ListActivity")
            }
        })
    }
}