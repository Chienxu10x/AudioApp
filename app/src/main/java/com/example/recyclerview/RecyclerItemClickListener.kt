package com.example.recyclerview

import android.content.Context
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val listener: OnItemClickListener,
    private val longPressTimeout: Long = 3000 // Đặt thời gian giữ mặc định là 1 giây (1000ms)
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector
    private var handler: Handler? = null
    private var isLongPressHandled = false

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                isLongPressHandled = true
                val childView = recyclerView.findChildViewUnder(e.x, e.y)
                if (childView != null) {
                    listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x, e.y)
        if (childView != null) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    startHandler(rv)
                    isLongPressHandled = false
                }
                MotionEvent.ACTION_UP -> {
                    handler?.removeCallbacksAndMessages(null)
                    if (!isLongPressHandled) {
                        listener.onItemClick(childView, rv.getChildAdapterPosition(childView))
                    }
                }
                MotionEvent.ACTION_CANCEL -> {
                    handler?.removeCallbacksAndMessages(null)
                }
            }
            return gestureDetector.onTouchEvent(e)
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    private fun startHandler(rv: RecyclerView) {
        handler = Handler()
        handler?.postDelayed({
            if (!isLongPressHandled) {
                val childView = rv.findChildViewUnder(0F, 0F)
                if (childView != null) {
                    listener.onItemLongClick(childView, rv.getChildAdapterPosition(childView))
                    isLongPressHandled = true
                }
            }
        }, longPressTimeout)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}
