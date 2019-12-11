package com.lodz.android.objectboxdemo.db.table

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/** 笔记表，自增[id]，内容[text]，时间[date] */
@Entity
data class NoteTableBean(
    @Id var id: Long = 0,
    var text: String? = null,
    var date: Date? = null
)