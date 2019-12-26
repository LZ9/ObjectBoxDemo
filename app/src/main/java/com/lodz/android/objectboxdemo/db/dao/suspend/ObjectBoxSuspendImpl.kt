package com.lodz.android.objectboxdemo.db.dao.suspend

import com.lodz.android.corekt.log.PrintLog
import com.lodz.android.corekt.utils.DateUtils
import com.lodz.android.objectboxdemo.db.dao.ObjectBox
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.objectboxdemo.db.table.NoteTableBean_
import io.objectbox.kotlin.query
import io.objectbox.query.OrderFlags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @author zhouL
 * @date 2019/12/16
 */
class ObjectBoxSuspendImpl : DbSuspendRepository {

    companion object {
        fun get(): ObjectBoxSuspendImpl = ObjectBoxSuspendImpl()
    }

    override suspend fun addNote(content: String): Long =
        GlobalScope.await {
            PrintLog.e("testtag", "addNote : ${Thread.currentThread().name}")
            val title = if (content.length > 2) content.subSequence(0, 2).toString() else content
            val date = Date()
            ObjectBox.boxFor(NoteTableBean::class.java)
                .put(NoteTableBean(title = title, content = content + " add on ${DateUtils.getFormatString(DateUtils.TYPE_2, date)}", date = date))
        }

    override suspend fun getNoteList(): MutableList<NoteTableBean> =
        GlobalScope.await {
            PrintLog.e("testtag", "getNoteList : ${Thread.currentThread().name}")
            ObjectBox.boxFor(NoteTableBean::class.java).query().order(NoteTableBean_.date, OrderFlags.DESCENDING).build().find()
        }

    override suspend fun updateNote(id: Long): Long? =
        GlobalScope.awaitOrNull {
            PrintLog.e("testtag", "updateNote : ${Thread.currentThread().name}")
            val box = ObjectBox.boxFor(NoteTableBean::class.java)
            val bean = box.query {
                equal(NoteTableBean_.id, id)
            }.findFirst() ?: return@awaitOrNull null
            bean.title = DateUtils.getCurrentFormatString(DateUtils.TYPE_2)
            return@awaitOrNull box.put(bean)
        }

    override suspend fun removeNote(bean: NoteTableBean): Boolean =
        GlobalScope.await {
            PrintLog.e("testtag", "removeNote : ${Thread.currentThread().name}")
            ObjectBox.boxFor(NoteTableBean::class.java).remove(bean)
        }
}

suspend fun <T> GlobalScope.awaitOrNull(action: () -> T?): T? =
    withContext(Dispatchers.IO) {
        action()
    }

suspend fun <T> GlobalScope.await(action: () -> T): T =
    withContext(Dispatchers.IO) {
        action()
    }