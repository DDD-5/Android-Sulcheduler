package com.example.android_mvvm_template.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvvm_template.R
import java.time.LocalDate

class DateAdapter : ListAdapter<MonthEntity, MonthViewHolder>(DiffCallBack()) {

    private val selectManager = SelectManager()

    class DiffCallBack : DiffUtil.ItemCallback<MonthEntity>() {
        override fun areItemsTheSame(oldItem: MonthEntity, newItem: MonthEntity): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: MonthEntity, newItem: MonthEntity): Boolean {
            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_month_item, parent, false)
        (view as MonthView).dayClickListener = { date ->
            selectManager.select(date)
            notifyDataSetChanged()
        }
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position), selectManager.startSelectDate, selectManager.endSelectDate)
    }

    fun setSingleMode(singleMode: Boolean) {
        selectManager.setSingleMode(singleMode)
        submitList(currentList)
    }
}

class MonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(entity: MonthEntity, startSelectDate: LocalDate?, endSelectDate: LocalDate?) {
        (itemView as MonthView).bind(entity, startSelectDate, endSelectDate)
    }
}

private class SelectManager {

    private var isSingleMode = true
    var startSelectDate: LocalDate? = null
    var endSelectDate: LocalDate? = null


    fun setSingleMode(singleMode: Boolean) {
        isSingleMode = singleMode
        startSelectDate = null
        endSelectDate = null
    }

    fun select(date: LocalDate) {

        if (isSingleMode) {
            startSelectDate = date
            endSelectDate = date
        } else {
            when {
                // 아무것도 선택 안됐을 떄
                startSelectDate == null -> {
                    startSelectDate = date

                }
                //하나만 선택되어 있을 때
                endSelectDate == null -> {
                    when {
                        startSelectDate == date -> {
                            startSelectDate = null
                        }
                        startSelectDate?.isAfter(date) == true -> {
                            endSelectDate = startSelectDate
                            startSelectDate = date
                        }
                        else -> {
                            endSelectDate = date
                        }
                    }
                }
                //둘 다 선택되어 있을 때
                else -> {
                    when (date) {
                        startSelectDate -> {
                            startSelectDate = endSelectDate
                            endSelectDate = null
                        }
                        endSelectDate -> {
                            endSelectDate = null
                        }
                        else -> {
                            startSelectDate = date
                            endSelectDate = null
                        }
                    }
                }
            }
        }
    }
}