package com.lodz.android.objectboxdemo.db.dao.rx

import com.lodz.android.corekt.log.PrintLog
import com.lodz.android.corekt.utils.DateUtils
import com.lodz.android.objectboxdemo.db.dao.ObjectBox
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.objectboxdemo.db.table.NoteTableBean_
import com.lodz.android.pandora.rx.utils.RxAgent
import com.lodz.android.pandora.rx.utils.doComplete
import com.lodz.android.pandora.rx.utils.doError
import com.lodz.android.pandora.rx.utils.doNext
import io.objectbox.kotlin.query
import io.objectbox.query.OrderFlags
import io.reactivex.Observable
import java.util.*

/**
 * @author zhouL
 * @date 2019/12/12
 */
class ObjectBoxRxImpl private constructor() :
    DbRepository {

    companion object {
        fun get(): ObjectBoxRxImpl =
            ObjectBoxRxImpl()
    }

    override fun addNote(content: String): RxAgent<Long> =
        RxAgent(Observable.create { emitter ->
            try {
                PrintLog.e("testtag", "addNote : ${Thread.currentThread().name}")
                val title = if (content.length > 2) content.subSequence(0, 2).toString() else content
                val date = Date()
                val id = ObjectBox.boxFor(
                    NoteTableBean::class.java
                )
                    .put(NoteTableBean(title = title, content = content + " add on ${DateUtils.getFormatString(DateUtils.TYPE_2, date)}", date = date))
                emitter.doNext(id)
                emitter.doComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.doError(e)
            }
        })

    override fun getNoteList(): RxAgent<MutableList<NoteTableBean>> =
        RxAgent(Observable.create { emitter ->
            try {
                PrintLog.e("testtag", "getNoteList : ${Thread.currentThread().name}")
                val list = ObjectBox.boxFor(
                    NoteTableBean::class.java
                )
                    .query {
                        order(NoteTableBean_.date, OrderFlags.DESCENDING)
                    }.find()
                emitter.doNext(list)
                emitter.doComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.doError(e)
            }

        })

    override fun updateNote(id: Long): RxAgent<Long> =
        RxAgent(Observable.create { emitter ->
            try {
                PrintLog.e("testtag", "updateNote : ${Thread.currentThread().name}")
                val box = ObjectBox.boxFor(
                    NoteTableBean::class.java
                )
                val bean = box.query {
                    equal(NoteTableBean_.id, id)
                }.findFirst()
                if (bean == null){
                    emitter.doError(NullPointerException("data is null"))
                    return@create
                }
                bean.title = DateUtils.getCurrentFormatString(DateUtils.TYPE_2)
                val resultId = box.put(bean)
                emitter.doNext(resultId)
                emitter.doComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.doError(e)
            }
        })

    override fun removeNote(bean: NoteTableBean): RxAgent<Boolean> =
        RxAgent(Observable.create { emitter ->
            try {
                PrintLog.e("testtag", "removeNote : ${Thread.currentThread().name}")
                val isSuccess = ObjectBox.boxFor(
                    NoteTableBean::class.java
                ).remove(bean)
                emitter.doNext(isSuccess)
                emitter.doComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.doError(e)
            }
        })
}