package com.forecast.weather.ui.favourite

import android.app.AlertDialog
import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.forecast.weather.R

class SwipeToDelete(var adapter: FavoriteAdapter,var context: Context):ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT  or ItemTouchHelper.RIGHT ) {
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos=viewHolder.adapterPosition
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.delete))
                .setMessage(context.getString(R.string.messageDel))
                .setPositiveButton(context.getString(R.string.no)) { dialog, which -> adapter.notifyDataSetChanged() }
                .setNegativeButton(context.getString(R.string.yes)) { dialog, which ->
                    adapter.deleteItem(pos)
                }
                .setIcon(R.drawable.ic_delete)
                .show()
    }
}