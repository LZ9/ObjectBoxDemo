package com.lodz.android.objectboxdemo.ui.vm

import androidx.lifecycle.MutableLiveData
import com.lodz.android.objectboxdemo.db.dao.rx.ObjectBoxRxImpl
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.pandora.mvvm.vm.BaseViewModel

/**
 * @author zhouL
 * @date 2019/12/16
 */
class VmViewModel :BaseViewModel(){

    var list = MutableLiveData<MutableList<NoteTableBean>>()

    fun insertData(content: String) {
        ObjectBoxRxImpl.get().addNote(content).sync()
        getNoteList()
    }

    fun updateData(id: Long) {
        ObjectBoxRxImpl.get().updateNote(id).sync()
        getNoteList()
    }

    fun deleteData(bean: NoteTableBean) {
        ObjectBoxRxImpl.get().removeNote(bean).sync()
        getNoteList()
    }

    fun getNoteList() {
        list.value = ObjectBoxRxImpl.get().getNoteList().sync()
    }
}