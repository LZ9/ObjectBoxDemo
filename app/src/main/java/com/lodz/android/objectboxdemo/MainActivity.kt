package com.lodz.android.objectboxdemo

import android.os.Bundle
import com.lodz.android.pandora.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.app_name)
        getTitleBarLayout().needBackButton(false)
        getTitleBarLayout().setBackgroundResource(R.color.colorPrimary)
    }

    override fun initData() {
        super.initData()
        showStatusCompleted()
    }
}
