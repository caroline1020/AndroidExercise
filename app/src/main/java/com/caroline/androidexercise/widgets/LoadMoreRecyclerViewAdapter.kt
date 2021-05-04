package com.caroline.androidexercise.widgets

import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Created by nini on 26/04/2018.
 */
abstract class LoadMoreRecyclerViewAdapter<E>(private val loadMoreListener: OnLoadMoreListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var isMoreDataAvailable = true
    private val data: MutableList<E> = ArrayList()
    fun onLoadError() {
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= itemCount - 1 && isMoreDataAvailable && loadMoreListener != null) {
            loadMoreListener.onLoadMore()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= data.size) {
            VIEW_TYPE_LOADMORE
        } else VIEW_TYPE_UNDEFINED
    }

    fun reset() {
        data.clear()
        isMoreDataAvailable = true
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun getData(): List<E> {
        return data
    }

    fun onLoadMoreEnd(
        newData: List<E>,
        hasMoreData: Boolean
    ) {
        isMoreDataAvailable = hasMoreData
        data.addAll(newData)
        notifyItemRangeChanged(
            data.size - newData.size,
            data.size + 1
        )
    }

    companion object {
        const val VIEW_TYPE_LOADMORE = 999
        const val VIEW_TYPE_UNDEFINED = 0
    }

}