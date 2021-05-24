package com.example.android_mvvm_template.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.home.MonthView.Companion.EMPTY_DAY
import com.example.android_mvvm_template.home.MonthView.Companion.HIDE_DAY
import com.example.android_mvvm_template.home.MonthView.Companion.NONE_SELECT
import com.example.android_mvvm_template.home.MonthView.Companion.SATURDAY
import com.example.android_mvvm_template.home.MonthView.Companion.SUNDAY
import com.example.android_mvvm_template.util.toPx

class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle) {

    private val subTitleTextPaint by lazy {
        TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG).apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textSize = 10.toPx(context).toFloat()
            color = Color.parseColor("#0e1012")
        }
    }

    private val selectPaint: Paint by lazy {
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.blue500)
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private val rangePaint: Paint by lazy {
        Paint().apply {
            color = Color.parseColor("#444b52")
            alpha = 26
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private val reservationPaint: Paint by lazy {
        Paint().apply {
            color = ContextCompat.getColor(context, R.color.blue500)
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 1.toPx(context).toFloat()
        }
    }

    private var subTitle: String = ""

    private var isRange: Boolean = false
    private var isSelect: Int = NONE_SELECT
    private var reserved: Boolean = false
    private val rect = Rect()

    init {
        gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val diameter = (width - 12.toPx(context).toFloat())
        val radius = diameter / 2
        val textHeight = subTitleTextPaint.run {
            descent() - ascent()
        }

        setPadding(
            0,
            (radius - textHeight).toInt(),
            0,
            0
        )
    }

    override fun onDraw(canvas: Canvas) {

        if (subTitle.isNotEmpty()) {
            val textWidth = subTitleTextPaint.measureText(subTitle)

            subTitleTextPaint.getTextBounds(subTitle, 0, subTitle.length, rect)
            canvas.drawText(
                subTitle,
                width / 2 - textWidth / 2,
                height.toFloat() - rect.bottom,
                subTitleTextPaint)
        }

        val diameter = (width - 12.toPx(context).toFloat())

        if (isSelect != NONE_SELECT) {
            val radius = diameter / 2
            val cx = (width / 2).toFloat()
            canvas.drawCircle(cx, radius, radius - 1, selectPaint)
        } else if(reserved && text.isNotEmpty()) {
            val radius = diameter / 2
            val cx = (width / 2).toFloat()
            canvas.drawCircle(cx, radius, radius - 1, reservationPaint)
        }

        super.onDraw(canvas)
    }


    fun bindDay(day: Int, dayOfWeek: Int, isSelect: Int, isRange: Boolean, reserved: Boolean, isValidDay: Boolean, subTitle: String = "") {

        visibility = VISIBLE
        this.isSelect = isSelect
        this.isRange = isRange
        this.reserved = reserved
        this.subTitle = subTitle
        text = day.toString()
        setDayTextColor(dayOfWeek, isSelect, isValidDay)
    }

    fun bindDummyDay(day: Int, isRange: Boolean) {
        when (day) {
            EMPTY_DAY -> {
                visibility = VISIBLE
                text = ""
            }
            HIDE_DAY -> {
                visibility = GONE
            }
        }
        this.isRange = isRange
        this.isSelect = NONE_SELECT
        this.subTitle = subTitle
    }

    private fun setDayTextColor(dayOfWeek: Int, isSelect: Int, isValidDay: Boolean) {
        when {
            //TODO 오늘 이전
//            isValidDay.not() -> {
//                setTextColor(ContextCompat.getColor(context, R.color.invalid_day_color))
//            }
            isSelect != NONE_SELECT -> {
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            //TODO 요일색 정해지면 쓰기
//            dayOfWeek == SUNDAY -> {
//                setTextColor(ContextCompat.getColor(context, R.color.red500))
//            }
//            dayOfWeek == SATURDAY -> {
//                setTextColor(ContextCompat.getColor(context, R.color.blue500))
//            }
            else -> {
                setTextColor(ContextCompat.getColor(context, R.color.black_daynight))
            }
        }
    }
}