package com.lodz.android.objectboxdemo.utils

import com.lodz.android.objectboxdemo.db.dao.ObjectBox
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import io.objectbox.rx.RxQuery


/**
 * 数据库接口工程
 * @author zhouL
 * @date 2019/12/11
 */
object DbRepositoryFactory {

    fun create(): DbRepository? {
        val query = ObjectBox.boxStore.boxFor(NoteTableBean::class.java).query().build()

        ObjectBox.boxStore.boxFor(NoteTableBean::class.java).put()

        RxQuery.observable<NoteTableBean>(query).subscribe()
        return null
    }
}