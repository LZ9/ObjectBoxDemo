package com.lodz.android.objectboxdemo.ui.main

import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.objectboxdemo.R
import com.lodz.android.objectboxdemo.ui.normal.NormalActivity
import com.lodz.android.objectboxdemo.ui.rx.RxActivity
import com.lodz.android.objectboxdemo.ui.vm.VmActivity
import com.lodz.android.pandora.base.activity.BaseActivity
import com.lodz.android.pandora.widget.base.TitleBarLayout

/**
 * 主页
 * @author zhouL
 * @date 2019/12/10
 */
class MainActivity : BaseActivity() {

    private val mNormalBtn by bindView<MaterialButton>(R.id.normal_btn)
    private val mRxBtn by bindView<MaterialButton>(R.id.rx_btn)
    private val mJetpackBtn by bindView<MaterialButton>(R.id.jetpack_btn)

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        initTitleBar(getTitleBarLayout())
    }

    private fun initTitleBar(titleBarLayout: TitleBarLayout) {
        titleBarLayout.setTitleName(R.string.app_name)
        titleBarLayout.needBackButton(false)
    }

    override fun setListeners() {
        super.setListeners()
        mNormalBtn.setOnClickListener {
            NormalActivity.start(getContext())
        }

        mRxBtn.setOnClickListener {
            RxActivity.start(getContext())
        }

        mJetpackBtn.setOnClickListener {
            VmActivity.start(getContext())
        }
    }

    override fun initData() {
        super.initData()
        showStatusCompleted()
    }
}
