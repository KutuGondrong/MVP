package com.kutugondrong.tikettest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class BaseHolder(@LayoutRes layoutId: Int, parent: ViewGroup?)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent?.context).inflate(layoutId, parent, false))
