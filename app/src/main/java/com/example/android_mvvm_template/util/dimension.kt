package com.example.android_mvvm_template.util

import android.content.Context
import android.content.res.Resources

fun Int.toPx(context: Context): Int {
    return this.toFloat().toPx(context)
}

internal fun Int.toPx(resource: Resources): Int {
    return this.toFloat().toPx(resource)
}

internal fun Float.toPx(context: Context): Int {
    return toPx(context.resources)
}

internal fun Float.toPx(resource: Resources): Int {
    return (this * resource.getDensity()).toInt()
}

internal fun Resources.getDensity(): Float {
    return displayMetrics.density
}