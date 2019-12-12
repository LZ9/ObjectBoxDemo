package com.lodz.android.objectboxdemo.ui.vm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.objectboxdemo.R
import com.lodz.android.pandora.base.activity.BaseActivity

/**
 * Jetpack调用
 * @author zhouL
 * @date 2019/12/11
 */
class VmActivity : BaseActivity() {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, VmActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mContentEdit by bindView<EditText>(R.id.content_edit)
    private val mInsertBtn by bindView<MaterialButton>(R.id.insert_btn)
    private val mRecyclerView by bindView<RecyclerView>(R.id.recycler_view)

    override fun getLayoutId(): Int = R.layout.activity_db

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_jetpack)
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun initData() {
        super.initData()
        showStatusCompleted()
    }
}