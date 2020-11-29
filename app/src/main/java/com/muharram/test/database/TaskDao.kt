package com.muharram.test.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.muharram.test.Task
import java.util.*

@Dao
interface TaskDao{
    @Query("SELECT * FROM Task")
    fun getTasks(): LiveData<List<Task>>
    @Query("SELECT * FROM Task WHERE id=(:id)")
    fun getTask(id: UUID):LiveData<Task?>

}