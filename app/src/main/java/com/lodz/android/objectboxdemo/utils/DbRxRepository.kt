package com.lodz.android.objectboxdemo.utils

import io.reactivex.Observable

/**
 * 数据库接口
 * @author zhouL
 * @date 2019/12/11
 */
interface DbRxRepository {

    fun <T> insertData(cls: Class<T>, data: T?)

    fun <T> updateData(cls: Class<T>, data: T?)

    fun <T> deleteData(cls: Class<T>, data: T?)

    fun <T> queryData(cls: Class<T>, data: T?)


    fun <T> fa(cls: Class<T>, data: T?): Observable<List<T>>
}