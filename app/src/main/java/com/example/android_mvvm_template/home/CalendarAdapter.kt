package com.example.android_mvvm_template.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvvm_template.databinding.ItemCalendarDateBinding
import com.example.android_mvvm_template.databinding.ItemCalendarDayBinding
import com.example.android_mvvm_template.databinding.ItemCalendarMonthBinding
import com.example.android_mvvm_template.databinding.ItemCalendarRecycleBinding
import java.lang.IllegalArgumentException

class CalendarAdapter: ListAdapter<HomeViewModel.CalendarEntities, CalendarViewHolder>(DIFF) {

    companion object {
        private val DIFF = object: DiffUtil.ItemCallback<HomeViewModel.CalendarEntities>() {
            override fun areItemsTheSame(oldItem: HomeViewModel.CalendarEntities, newItem: HomeViewModel.CalendarEntities): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeViewModel.CalendarEntities, newItem: HomeViewModel.CalendarEntities): Boolean {
                return oldItem == newItem
            }
        }

        private const val DAY = 0x00
        private const val DATE = 0x01
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return when(viewType) {
            DATE -> {
                CalendarDateViewHolder(
                    ItemCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            DAY -> {
                CalendarDayViewHolder(
                    ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> throw IllegalArgumentException("non defined type")
        }
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is HomeViewModel.CalendarEntities.CalendarDate -> DATE
            is HomeViewModel.CalendarEntities.CalendarDay -> DAY
            else -> throw IllegalArgumentException("non defined type")
        }
    }
}

abstract class CalendarViewHolder(view: View): RecyclerView.ViewHolder(view){
    abstract fun bind(item: HomeViewModel.CalendarEntities)
}

class CalendarMonthViewHolder(private val binding: ItemCalendarMonthBinding): CalendarViewHolder(binding.root) {
    override fun bind(item: HomeViewModel.CalendarEntities) {
        TODO("Not yet implemented")
    }
}

class CalendarDayViewHolder(private val binding: ItemCalendarDayBinding): CalendarViewHolder(binding.root) {
    override fun bind(item: HomeViewModel.CalendarEntities) {
        item as HomeViewModel.CalendarEntities.CalendarDay
        binding.run {
            day = item.day
        }
    }
}

class CalendarDateViewHolder(private val binding: ItemCalendarDateBinding): CalendarViewHolder(binding.root) {
    override fun bind(item: HomeViewModel.CalendarEntities) {
        item as HomeViewModel.CalendarEntities.CalendarDate
        binding.run {
            date = item.date
        }
    }
}

class TestAdapter: ListAdapter<HomeViewModel.CalendarEntities, RecyclerView.ViewHolder>(
    object: DiffUtil.ItemCallback<HomeViewModel.CalendarEntities>() {
        override fun areItemsTheSame(oldItem: HomeViewModel.CalendarEntities, newItem: HomeViewModel.CalendarEntities): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeViewModel.CalendarEntities, newItem: HomeViewModel.CalendarEntities): Boolean {
            return oldItem == newItem
        }

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CalendarRvHolder(
            ItemCalendarRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CalendarRvHolder)?.bind(currentList)
    }
}

class CalendarRvHolder(private val binding: ItemCalendarRecycleBinding): RecyclerView.ViewHolder(binding.root) {
    private val adapter = CalendarAdapter()
    fun bind(currentList: MutableList<HomeViewModel.CalendarEntities>) {
        binding.run {
            rvUm.adapter = adapter.apply { submitList(currentList) }
        }
    }
}