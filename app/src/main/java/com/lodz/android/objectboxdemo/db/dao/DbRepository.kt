package com.lodz.android.objectboxdemo.db.dao

import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.pandora.rx.utils.RxAgent

/**
 * 数据库接口
 * @author zhouL
 * @date 2019/12/11
 */
interface DbRepository {

    /** 新增笔记 */
    fun addNote(content: String): RxAgent<Long>

    /** 获取笔记列表 */
    fun getNoteList(): RxAgent<MutableList<NoteTableBean>>

    /** 更新笔记 */
    fun updateNote(id: Long): RxAgent<Long>

    /** 更新笔记 */
    fun removeNote(bean: NoteTableBean): RxAgent<Boolean>

}