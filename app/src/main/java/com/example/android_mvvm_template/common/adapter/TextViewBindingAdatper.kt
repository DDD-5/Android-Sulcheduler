package com.example.android_mvvm_template.common.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.common.ext.exhaustive
import com.example.android_mvvm_template.register.NicknameEnableStatus

@BindingAdapter("nicknameStatusForTextColor")
fun TextView.setNicknameStatusForTextColor(status: NicknameEnableStatus?) {
    val textColorRes = when (status) {
        NicknameEnableStatus.NORMAL, null -> R.color.silver
        NicknameEnableStatus.ERROR -> R.color.input_error
        NicknameEnableStatus.ENABLE -> R.color.active_blue
    }.exhaustive
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        setTextColor(context.getColor(textColorRes))
    } else {
        setTextColor(context.resources.getColor(textColorRes))
    }
}
