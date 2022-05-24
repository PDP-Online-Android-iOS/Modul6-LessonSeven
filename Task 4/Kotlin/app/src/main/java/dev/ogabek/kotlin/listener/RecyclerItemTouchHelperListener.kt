package dev.ogabek.kotlin.listener

import androidx.recyclerview.widget.RecyclerView

interface RecyclerItemTouchHelperListener {

    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)

}
