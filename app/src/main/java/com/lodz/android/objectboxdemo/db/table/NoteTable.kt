package com.lodz.android.objectboxdemo.db.table

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class NoteTable(
    @Id var id: Long = 0,
    var text: String? = null,
    var date: Date? = null
)