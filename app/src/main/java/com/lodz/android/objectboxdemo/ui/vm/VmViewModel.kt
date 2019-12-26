package com.lodz.android.objectboxdemo.ui.vm

import androidx.lifecycle.MutableLiveData
import com.lodz.android.corekt.anko.runOnMain
import com.lodz.android.corekt.anko.runOnSuspendIOCatch
import com.lodz.android.objectboxdemo.db.dao.suspend.ObjectBoxSuspendImpl
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.pandora.mvvm.vm.BaseViewModel
import kotlinx.coroutines.GlobalScope

/**
 * @author zhouL
 * @date 2019/12/16
 */
class VmViewModel :BaseViewModel(){

    var mList = MutableLiveData<MutableList<NoteTableBean>>()

    fun insertData(content: String) {
        GlobalScope.runOnSuspendIOCatch({
            ObjectBoxSuspendImpl.get().addNote(content)
            getNoteList()
        }, { e ->
            showStatusError(e)
        })
    }

    fun updateData(id: Long) {
        GlobalScope.runOnSuspendIOCatch({
            ObjectBoxSuspendImpl.get().updateNote(id)
            getNoteList()
        }, { e ->
            showStatusError(e)
        })
    }

    fun deleteData(bean: NoteTableBean) {
        GlobalScope.runOnSuspendIOCatch({
            ObjectBoxSuspendImpl.get().removeNote(bean)
            getNoteList()
        }, { e ->
            showStatusError(e)
        })
    }

    fun getNoteList() {
        GlobalScope.runOnSuspendIOCatch({
            val list = ObjectBoxSuspendImpl.get().getNoteList()
            GlobalScope.runOnMain {
                mList.value = list
            }
        }, { e ->
            showStatusError(e)
        })
    }
}