package com.example.firebaseseoulopendata

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class FirebaseDataAccess {
    var databaseReference: DatabaseReference? = null

    /*get instance realtime database of firebase*/
    init {
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("SwimLessonDAO")
    }

    /*insert into swimlessonTBL @realtime database*/
    fun insertData(swimLessonDAO: SwimLessonDAO?): Task<Void> {
        /*if at MySQL query
        * insert into user(userKey, userName, userAge, userPhone) values('keyValue', 'nameValue'......)*/
        return databaseReference!!.push().setValue(swimLessonDAO)
    }

    /*realtime database swimlessonTBL select*/
    fun selectData(): Query? {
        return databaseReference
    }

    /*realtime database swimlessonTBL update*/
    fun updateData(key:String, hashMap:HashMap<String,Any>): Task<Void>{
        return databaseReference!!.child(key).updateChildren(hashMap)
    }

    /*realtime database swimlessonTBL delete*/
    fun deleteData(key:String):Task<Void>{
        return databaseReference!!.child(key).removeValue()
    }
}