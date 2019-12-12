package com.lodz.android.objectboxdemo.db.table

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/** 笔记表，自增[id]，标题[title]，内容[content]，时间[date] */
@Entity
data class NoteTableBean(
    @Id var id: Long = 0,
    var title: String = "",
    var content: String? = null,
    var date: Date? = null
)