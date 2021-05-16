package com.example.android_mvvm_template.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvvm_template.R
import java.time.LocalDate

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var adapter = DateAdapter()
    private var calendar: RecyclerView

    init {
        LayoutInflater.from(context).inflate(
            R.layout.layout_calendar,
            this,
            true
        ).run {
            orientation = VERTICAL
            calendar = findViewById(R.id.calendar)
            calendar.adapter = adapter
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams = layoutParams.also {
            it.width = LayoutParams.MATCH_PARENT
            it.height = LayoutParams.WRAP_CONTENT
        }
    }

    fun setDate(startDate: LocalDate, endDate: LocalDate) {

        var start = startDate
        var end = endDate

        if (startDate.isAfter(endDate)) {
            start = endDate
            end = startDate
        }

        val list = mutableListOf<MonthEntity>()
        var tmpDate = start

        loop@ while (true) {

            val entity = when {
                tmpDate.month == end.month && tmpDate.year == end.year -> {
                    val isLeap = end.isLeapYear
                    val lastDay = end.month.length(isLeap)
                    MonthEntity(
                        startDate = end.withDayOfMonth(1),
                        endDate = end,
                        startDayOfWeek = convertDayOfWeek(end.withDayOfMonth(1).dayOfWeek.value),
                        endDayOfWeek = convertDayOfWeek(end.withDayOfMonth(lastDay).dayOfWeek.value)
                    ).let {
                        list.add(it)
                    }
                    break@loop
                }
                tmpDate.month == start.month && tmpDate.year == start.year -> {
                    val isLeap = start.isLeapYear
                    val lastDay = start.month.length(isLeap)
                    tmpDate = tmpDate.withDayOfMonth(1)
                    MonthEntity(
                        startDate = start,
                        endDate = start.withDayOfMonth(lastDay),
                        startDayOfWeek = convertDayOfWeek(start.withDayOfMonth(1).dayOfWeek.value),
                        endDayOfWeek = convertDayOfWeek(start.withDayOfMonth(lastDay).dayOfWeek.value)
                    )
                }
                else -> {
                    val isLeap = tmpDate.isLeapYear
                    val lastDay = tmpDate.month.length(isLeap)
                    MonthEntity(
                        startDate = tmpDate,
                        endDate = tmpDate.withDayOfMonth(lastDay),
                        startDayOfWeek = convertDayOfWeek(tmpDate.withDayOfMonth(1).dayOfWeek.value),
                        endDayOfWeek = convertDayOfWeek(tmpDate.withDayOfMonth(lastDay).dayOfWeek.value)
                    )
                }
            }
            list.add(entity)
            tmpDate = tmpDate.plusMonths(1L)
        }
        adapter.submitList(list)
    }

    fun setSingleMode(singleMode: Boolean) {
        adapter.setSingleMode(singleMode)
    }

    //기본 LocalDate 월~일(1~7) -> 일~토 변경
    private fun convertDayOfWeek(index: Int): Int {
        return (index + 1).let {
            if (it == 8) {
                1
            } else {
                it
            }
        }
    }
}

data class MonthEntity(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val startDayOfWeek: Int,
    val endDayOfWeek : Int
)