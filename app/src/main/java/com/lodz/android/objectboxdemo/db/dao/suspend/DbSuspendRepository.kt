package com.lodz.android.objectboxdemo.db.dao.suspend

import com.lodz.android.objectboxdemo.db.table.NoteTableBean

/**
 * @author zhouL
 * @date 2019/12/16
 */
interface DbSuspendRepository {

    /** 新增笔记 */
    suspend fun addNote(content: String): Long

    /** 获取笔记列表 */
    suspend fun getNoteList(): MutableList<NoteTableBean>

    /** 更新笔记 */
    suspend fun updateNote(id: Long): Long?

    /** 更新笔记 */
    suspend fun removeNote(bean: NoteTableBean): Boolean
}