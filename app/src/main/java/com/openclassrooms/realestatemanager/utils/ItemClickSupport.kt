package com.openclassrooms.realestatemanager.utils

import android.support.v7.widget.RecyclerView
import android.view.View

class ItemClickSupport(private val mItemID:Int, private val mRecyclerView: RecyclerView) {

    private lateinit var mOnItemClickListener: OnItemClickListener
    private lateinit var mOnItemLongClickListener: OnItemLongClickListener

    private val mOnClickListener = View.OnClickListener { v ->
        val holder = mRecyclerView.getChildViewHolder(v)
        mOnItemClickListener.onItemClicked(mRecyclerView, holder.adapterPosition, v)
    }
    private val mOnLongClickListener = object : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            val holder = mRecyclerView.getChildViewHolder(v)
            return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.adapterPosition, v)
        }
    }
    private val mAttachListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener(mOnClickListener)
            view.setOnLongClickListener(mOnLongClickListener)
        }

        override fun onChildViewDetachedFromWindow(view: View) {

        }
    }

    init {
        mRecyclerView.setTag(mItemID, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener): ItemClickSupport {
        mOnItemClickListener = listener
        return this
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener): ItemClickSupport {
        mOnItemLongClickListener = listener
        return this
    }

    private fun detach(view: RecyclerView) {
        view.removeOnChildAttachStateChangeListener(mAttachListener)
        view.setTag(mItemID, null)
    }

    interface OnItemClickListener {

        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    interface OnItemLongClickListener {

        fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean
    }

    companion object {

        fun addTo(view: RecyclerView, itemID: Int): ItemClickSupport {
            var support = view.getTag(itemID)
            if (support == null) {
                support = ItemClickSupport( itemID, view)
            }
            return support as ItemClickSupport
        }

        fun removeFrom(view: RecyclerView, itemID: Int): ItemClickSupport {
            val support = view.getTag(itemID) as ItemClickSupport
            support.detach(view)
            return support
        }
    }
}