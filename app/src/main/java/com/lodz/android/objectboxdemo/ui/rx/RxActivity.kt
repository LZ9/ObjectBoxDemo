package com.lodz.android.objectboxdemo.ui.rx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.objectboxdemo.R
import com.lodz.android.objectboxdemo.db.dao.rx.ObjectBoxRxImpl
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.objectboxdemo.ui.list.NoteListAdapter
import com.lodz.android.pandora.base.activity.BaseActivity
import com.lodz.android.pandora.rx.subscribe.observer.BaseObserver
import com.lodz.android.pandora.rx.utils.RxUtils
import io.reactivex.Observable

/**
 * RxJava调用
 * @author zhouL
 * @date 2019/12/11
 */
class RxActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RxActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mContentEdit by bindView<EditText>(R.id.content_edit)
    private val mInsertBtn by bindView<MaterialButton>(R.id.insert_btn)
    private val mRecyclerView by bindView<RecyclerView>(R.id.recycler_view)
    private lateinit var mAdapter: NoteListAdapter

    override fun getLayoutId(): Int = R.layout.activity_db

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().setTitleName(R.string.main_rx)
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

    override fun setListeners() {
        super.setListeners()

        mInsertBtn.setOnClickListener {
            val content = mContentEdit.text.toString()
            if (content.isEmpty()){
                toastShort(R.string.main_input_hint)
                return@setOnClickListener
            }
            mContentEdit.setText("")
            insertData(content)
        }

        mAdapter.setOnClickUpdateListener { bean ->
            updateData(bean.id)
        }

        mAdapter.setOnClickDeleteListener { bean ->
            deleteData(bean)
        }

    }

    /** 插入数据，内容[content] */
    private fun insertData(content: String) {
        Observable.just(content)
            .flatMap {
                ObjectBoxRxImpl.get().addNote(it).rx()
            }
            .flatMap {
                ObjectBoxRxImpl.get().getNoteList().rx()
            }
            .compose(RxUtils.ioToMainObservable())
            .compose(bindDestroyEvent())
            .subscribe(BaseObserver.action({ list ->
                mAdapter.setData(list)
                mAdapter.notifyDataSetChanged()
            }))
    }

    /** 更新数据，数据[id] */
    private fun updateData(id: Long) {
        Observable.just(id)
            .flatMap {
                ObjectBoxRxImpl.get().updateNote(it).rx()
            }
            .flatMap {
                ObjectBoxRxImpl.get().getNoteList().rx()
            }
            .compose(RxUtils.ioToMainObservable())
            .compose(bindDestroyEvent())
            .subscribe(BaseObserver.action({ list ->
                mAdapter.setData(list)
                mAdapter.notifyDataSetChanged()
            }))
    }

    /** 更新数据，数据[bean] */
    private fun deleteData(bean: NoteTableBean) {
        Observable.just(bean)
            .flatMap {
                ObjectBoxRxImpl.get().removeNote(it).rx()
            }
            .flatMap {
                ObjectBoxRxImpl.get().getNoteList().rx()
            }
            .compose(RxUtils.ioToMainObservable())
            .compose(bindDestroyEvent())
            .subscribe(BaseObserver.action({ list ->
                mAdapter.setData(list)
                mAdapter.notifyDataSetChanged()
            }))
    }

    override fun initData() {
        super.initData()
        ObjectBoxRxImpl.get().getNoteList().rx()
            .compose(RxUtils.ioToMainObservable())
            .compose(bindDestroyEvent())
            .subscribe(BaseObserver.action({ list ->
                mAdapter.setData(list)
                mAdapter.notifyDataSetChanged()
                showStatusCompleted()
            }))
    }
}