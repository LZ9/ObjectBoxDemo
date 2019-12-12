package com.lodz.android.objectboxdemo.ui.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.corekt.utils.DateUtils
import com.lodz.android.objectboxdemo.R
import com.lodz.android.objectboxdemo.db.table.NoteTableBean
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter

/**
 * 笔记列表适配器
 * @author zhouL
 * @date 2019/12/12
 */
class NoteListAdapter(context: Context) :BaseRecyclerViewAdapter<NoteTableBean>(context){

    private var mUpdateListener: ((bean: NoteTableBean) -> Unit)? = null
    private var mDeleteListener: ((bean: NoteTableBean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DataViewHolder(getLayoutView(parent, R.layout.rv_item_note_list))

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position) ?: return
        showItem(holder as DataViewHolder, bean)
    }

    private fun showItem(dataViewHolder: DataViewHolder, bean: NoteTableBean) {
        dataViewHolder.apply {
            titleTv.text = bean.title
            contentTv.text = bean.content ?: ""
            val date = bean.date
            timeTv.text = if (date != null) DateUtils.getFormatString(DateUtils.TYPE_6, date) else ""
            updateBtn.setOnClickListener {
                mUpdateListener?.invoke(bean)
            }
            deleteBtn.setOnClickListener {
                mDeleteListener?.invoke(bean)
            }
        }
    }

    fun setOnClickUpdateListener(listener: (bean: NoteTableBean) -> Unit) {
        mUpdateListener = listener
    }

    fun setOnClickDeleteListener(listener: (bean: NoteTableBean) -> Unit) {
        mDeleteListener = listener
    }

    private inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** 标题 */
        internal val titleTv by bindView<TextView>(R.id.title_tv)
        /** 内容 */
        internal val contentTv by bindView<TextView>(R.id.content_tv)
        /** 时间 */
        internal val timeTv by bindView<TextView>(R.id.time_tv)
        /** 更新 */
        internal val updateBtn by bindView<Button>(R.id.update_btn)
        /** 删除 */
        internal val deleteBtn by bindView<Button>(R.id.delete_btn)
    }



}