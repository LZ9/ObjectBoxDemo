package com.lodz.android.objectboxdemo.db.dao.suspend

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * 协程转换代理
 * @author zhouL
 * @date 2019/12/16
 */
class SuspendAgent<T>(private val action: Deferred<T?>) {

    suspend fun await(): T? = action.await()

    @ExperimentalCoroutinesApi
    fun block(): T? = try {
        action.getCompleted()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}