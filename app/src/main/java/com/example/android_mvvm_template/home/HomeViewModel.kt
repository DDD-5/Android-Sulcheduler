package com.example.android_mvvm_template.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_mvvm_template.R

class HomeViewModel(
    private val context: Context
): ViewModel() {

    private val _list = MutableLiveData<List<CalendarEntities>>()
    val list: LiveData<List<CalendarEntities>> get() = _list

    fun init() {
        loadTest()
    }

    private fun loadTest() {
        _list.value = mutableListOf<CalendarEntities>().apply {
            addAll(createDay())
            for(i in 0 until 31) add(CalendarEntities.CalendarDate(i.toString()))
        }
    }

    private fun createDay(): List<CalendarEntities> {
        return listOf(
            CalendarEntities.CalendarDay(context.getString(R.string.home_sunday)),
            CalendarEntities.CalendarDay(context.getString(R.string.home_monday)),
            CalendarEntities.CalendarDay(context.getString(R.string.home_tuesday)),
            CalendarEntities.CalendarDay(context.getString(R.string.home_wednesday)),
            CalendarEntities.CalendarDay(context.getString(R.string.home_thursday)),
            CalendarEntities.CalendarDay(context.getString(R.string.home_friday)),
            CalendarEntities.CalendarDay(context.getString(R.string.home_saturday)),
        )
    }

    sealed class CalendarEntities {
        data class CalendarDate(val date: String): CalendarEntities()
        data class CalendarDay(val day: String): CalendarEntities()
    }
}