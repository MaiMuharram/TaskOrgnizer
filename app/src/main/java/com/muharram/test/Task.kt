package com.muharram.test

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(@PrimaryKey val id : UUID = UUID.randomUUID(),
                var title: String = "",
                var endDate: Date = Date(),
                var status: Int=0 ) { } //ststus 0=to do 1=inprogress 2=done
