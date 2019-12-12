package com.lodz.android.objectboxdemo.db.dao

import android.content.Context
import com.lodz.android.objectboxdemo.BuildConfig
import com.lodz.android.objectboxdemo.db.table.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

/**
 * Singleton to keep BoxStore reference.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(boxStore).start(context.applicationContext)
        }
    }

    fun <T> boxFor(cls: Class<T>): Box<T> = boxStore.boxFor(cls)

    fun <T> put(cls: Class<T>, data: T?): Boolean {
        val t = data ?: return false
        val box = boxFor(cls)
        box.put(t)
        return true
    }

    fun <T> remove(cls: Class<T>, data: T?): Boolean {
        val t = data ?: return false
        val box = boxFor(cls)
        return box.remove(t)
    }

}