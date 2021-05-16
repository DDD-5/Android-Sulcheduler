package com.example.android_mvvm_template.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.util.toPx
import java.time.LocalDate

class MonthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var monthTitle: TextView

    private lateinit var startMonthDate: LocalDate
    private lateinit var endMonthDate: LocalDate
    private val today = LocalDate.now()

    var dayClickListener: ((date: LocalDate) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(
            R.layout.layout_month,
            this,
            true
        ).run {
            monthTitle = this.findViewById(R.id.monthly_title)
            for (i in 1..childCount - 2) {
                getChildAt(i).setOnClickListener {
                    getClickableDayLocalDate(i)?.let { date ->
                        dayClickListener?.invoke(date)
                    }
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.WRAP_CONTENT
        val horizontalPadding = 25.toPx(context)
        setPadding(horizontalPadding, 0, horizontalPadding, 0)
    }


    fun bind(entity: MonthEntity, startSelect: LocalDate?, endSelect: LocalDate?) {

        startMonthDate = entity.startDate.apply {
            setMonthTitle(this)
        }
        endMonthDate = entity.endDate


        val isLeap = startMonthDate.isLeapYear
        val lastDay = startMonthDate.month.length(isLeap)

        val startDayOfWeek = entity.startDayOfWeek
        val endDayOfWeek = entity.endDayOfWeek

        var day = 1

        //해당 달의 1일 전까지 dayView empty bind
        for (i in 1..startDayOfWeek) {
            (getChildAt(i) as DayView).run {
                bindDummyDay(
                    day = EMPTY_DAY,
                    isRange = isRangeDummyView(1, startSelect, endSelect)
                )
            }
        }

        //해당 달의 1일 ~ 마지막 일 까지 bind
        for (i in startDayOfWeek until startDayOfWeek + lastDay) {
            (getChildAt(i) as DayView).run {
                val now = startMonthDate.withDayOfMonth(day)
                bindDay(
                    day = day,
                    dayOfWeek = geyDayOfWeek(i),
                    isSelect = isSelect(day, startSelect, endSelect),
                    isRange = isRange(now, startSelect, endSelect),
                    isValidDay = isValidDay(day++)
                )
            }
        }

        //해당 달의 마지막 일의 다음날 부터 주의 끝까지 empty bind
        for (i in startDayOfWeek + lastDay until startDayOfWeek + lastDay + (7 - endDayOfWeek)) {
            (getChildAt(i) as DayView).run {
                bindDummyDay(
                    day = EMPTY_DAY,
                    isRange = isRangeDummyView(lastDay, startSelect, endSelect)
                )
            }
        }

        //필요 없는 나머지 DayView Gone
        for (i in startDayOfWeek + lastDay + (7 - endDayOfWeek)..42) {
            (getChildAt(i) as DayView).run {
                bindDummyDay(
                    day = HIDE_DAY,
                    isRange = false
                )
            }
        }
    }

    private fun isValidDay(day: Int): Boolean {
        return day in startMonthDate.dayOfMonth..endMonthDate.dayOfMonth
    }

    private fun getClickableDayLocalDate(index: Int): LocalDate? {
        getChildAt(index).let { dayView ->
            if (dayView is TextView) {
                dayView.text.toString().let {
                    if (it.isNotEmpty()) {
                        val day = Integer.valueOf(dayView.text.toString())
                        if (isValidDay(day)) {
                            return LocalDate.of(startMonthDate.year, startMonthDate.monthValue, day)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun setMonthTitle(date: LocalDate) {
        val text = String.format(resources.getString(R.string.month_title), date.year, date.monthValue)
        monthTitle.text = text
    }

    private fun geyDayOfWeek(index: Int) = ((index) % 7).let {
        if (it == 0) 7 else it
    }

    private fun isRange(now: LocalDate, startDate: LocalDate?, endDate: LocalDate?): Boolean {
        if (startDate == null || endDate == null) return false
        return startDate.isBefore(now) && endDate.isAfter(now) || startDate == now || endDate == now
    }

    private fun isRangeDummyView(day: Int, startDate: LocalDate?, endDate: LocalDate?): Boolean {
        if (startDate == null || endDate == null) return false
        val now = this.startMonthDate.withDayOfMonth(day)
        val tmp = if (day == 1) {
            now.minusDays(1)
        } else {
            now.plusDays(1)
        }
        return isRange(now, startDate, endDate) && isRange(tmp, startDate, endDate)
    }

    private fun isSelect(day: Int, startDate: LocalDate?, endDate: LocalDate?): Int {
        if (startDate == null && endDate == null) return NONE_SELECT
        startMonthDate.withDayOfMonth(day).let {
            return when (it) {
                startDate -> {
                    if (endDate != null) {
                        START_SELECT
                    } else {
                        SINGLE_SELECT
                    }
                }
                endDate -> {
                    END_SELECT
                }
                else -> {
                    NONE_SELECT
                }
            }
        }
    }

    companion object {
        const val EMPTY_DAY = -1
        const val HIDE_DAY = -2
        const val SUNDAY = 1
        const val SATURDAY = 7

        const val START_SELECT = 10
        const val END_SELECT = 20
        const val SINGLE_SELECT = 30
        const val NONE_SELECT = 40
    }

}