package com.lodz.android.objectboxdemo.ui.normal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.corekt.utils.DateUtils
import com.lodz.android.objectboxdemo.R
import com.lodz.android.objectboxdemo.db.dao.ObjectBox
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.objectboxdemo.db.table.NoteTableBean_
import com.lodz.android.objectboxdemo.ui.list.NoteListAdapter
import com.lodz.android.pandora.base.activity.BaseActivity
import io.objectbox.kotlin.query
import io.objectbox.query.OrderFlags
import java.util.*

/**
 * 普通调用
 * @author zhouL
 * @date 2019/12/11
 */
class NormalActivity : BaseActivity() {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, NormalActivity::class.java)
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

    override fun setListeners() {
        super.setListeners()

        mInsertBtn.setOnClickListener {
            val content = mContentEdit.text.toString()
            if (content.isEmpty()){
                toastShort(R.string.main_input_hint)
                return@setOnClickListener
            }
            insertData(content)
            getNoteList()
            mContentEdit.setText("")
        }

        mAdapter.setOnClickUpdateListener { bean ->
            updateData(bean.id)
            getNoteList()
        }

        mAdapter.setOnClickDeleteListener { bean ->
            ObjectBox.remove(NoteTableBean::class.java, bean)
            getNoteList()
        }

    }

    /** 插入数据，内容[content] */
    private fun insertData(content: String) {
        val title = if (content.length > 2) content.subSequence(0, 2).toString() else content
        val date = Date()
        ObjectBox.put(
            NoteTableBean::class.java,
            NoteTableBean(
                title = title,
                content = content + " add on ${DateUtils.getFormatString(DateUtils.TYPE_2, date)}",
                date = date
            )
        )
    }

    /** 更新数据，数据[bean] */
    private fun updateData(id: Long) {
        val box = ObjectBox.boxFor(NoteTableBean::class.java)
        val bean = box.query {
            equal(NoteTableBean_.id, id)
        }.findFirst()
        bean?.apply {
            this.title = DateUtils.getCurrentFormatString(DateUtils.TYPE_2)
            box.put(this)
        }
    }

    override fun initData() {
        super.initData()
        getNoteList()
        showStatusCompleted()
    }

    /** 获取笔记列表 */
    private fun getNoteList(){
        val box = ObjectBox.boxFor(NoteTableBean::class.java)
        val list = box.query {
            order(NoteTableBean_.date, OrderFlags.DESCENDING)
        }.find()
        mAdapter.setData(list)
        mAdapter.notifyDataSetChanged()
    }
}