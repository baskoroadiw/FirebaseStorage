package com.baskoroadi.firebasestorage

import android.view.View

interface RecyclerViewClickListener {
    fun onItemClicked(view: View,items: Items)
    fun onLongItemClicked(view: View,items: Items)
}