package com.lodz.android.objectboxdemo

import com.lodz.android.corekt.network.NetworkManager
import com.lodz.android.objectboxdemo.db.dao.ObjectBox
import com.lodz.android.pandora.base.application.BaseApplication

/**
 * @author zhouL
 * @date 2019/12/10
 */
class App :BaseApplication(){

    companion object {
        fun get(): App = BaseApplication.get() as App
    }

    override fun onStartCreate() {
        NetworkManager.get().init(this)
        ObjectBox.init(this)
        configTitleBarLayout()
    }

    /** 配置标题栏 */
    private fun configTitleBarLayout() {
        getBaseLayoutConfig().getTitleBarLayoutConfig().isNeedBackBtn = true
        getBaseLayoutConfig().getTitleBarLayoutConfig().backgroundColor = R.color.colorPrimary
        getBaseLayoutConfig().getTitleBarLayoutConfig().titleTextColor = R.color.white
    }

    override fun onExit() {

    }
}