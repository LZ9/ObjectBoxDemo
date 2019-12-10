package com.lodz.android.objectboxdemo.ui

import android.os.Bundle
import com.lodz.android.objectboxdemo.R
import com.lodz.android.pandora.base.activity.BaseActivity
import com.lodz.android.pandora.widget.base.TitleBarLayout

/**
 * @author zhouL
 * @date 2019/12/10
 */
class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        initTitleBar(getTitleBarLayout())
    }

    private fun initTitleBar(titleBarLayout: TitleBarLayout) {
        titleBarLayout.setTitleName(R.string.app_name)
        titleBarLayout.needBackButton(false)
    }

    override fun initData() {
        super.initData()
        showStatusCompleted()
    }
}
