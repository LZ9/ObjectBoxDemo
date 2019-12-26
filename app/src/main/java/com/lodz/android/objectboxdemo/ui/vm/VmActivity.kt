package com.lodz.android.objectboxdemo.ui.vm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.objectboxdemo.R
import com.lodz.android.objectboxdemo.ui.list.NoteListAdapter
import com.lodz.android.pandora.mvvm.base.activity.BaseVmActivity

/**
 * Jetpack调用
 * @author zhouL
 * @date 2019/12/11
 */
class VmActivity : BaseVmActivity<VmViewModel>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, VmActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mContentEdit by bindView<EditText>(R.id.content_edit)
    private val mInsertBtn by bindView<MaterialButton>(R.id.insert_btn)
    private val mRecyclerView by bindView<RecyclerView>(R.id.recycler_view)
    private lateinit var mAdapter: NoteListAdapter

    override fun createViewModel(): Class<VmViewModel> = VmViewModel::class.java

    override fun getLayoutId(): Int = R.layout.activity_db

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_normal)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = NoteListAdapter(getContext())
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        mRecyclerView.layoutManager = layoutManager
        mAdapter.onAttachedToRecyclerView(mRecyclerView)// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mAdapter
    }

    override fun onClickBackBtn() {
        super.onClickBackBtn()
        finish()
    }

    override fun onClickReload() {
        super.onClickReload()
        showStatusLoading()
        getViewModel().getNoteList()
    }

    override fun setListeners() {
        super.setListeners()

        mInsertBtn.setOnClickListener {
            val content = mContentEdit.text.toString()
            if (content.isEmpty()){
                toastShort(R.string.main_input_hint)
                return@setOnClickListener
            }
            mContentEdit.setText("")
            getViewModel().insertData(content)
        }

        mAdapter.setOnClickUpdateListener { bean ->
            getViewModel().updateData(bean.id)
        }

        mAdapter.setOnClickDeleteListener { bean ->
            getViewModel().deleteData(bean)
        }

        getViewModel().mList.observe(this, Observer { list ->
            mAdapter.setData(list)
            mAdapter.notifyDataSetChanged()
            showStatusCompleted()
        })
    }

    override fun initData() {
        super.initData()
        getViewModel().getNoteList()
    }
}