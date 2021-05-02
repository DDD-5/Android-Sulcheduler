package com.example.android_mvvm_template.common.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.common.ext.exhaustive
import com.example.android_mvvm_template.register.NicknameColorStatus

@BindingAdapter("nicknameStatusForTextColor")
fun TextView.setNicknameStatusForTextColor(status: NicknameColorStatus?) {
    val textColorRes = when (status) {
        NicknameColorStatus.NORMAL, null -> R.color.silver
        NicknameColorStatus.ERROR -> R.color.martini
        NicknameColorStatus.ENABLE -> R.color.blue500
    }.exhaustive
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        setTextColor(context.getColor(textColorRes))
    } else {
        setTextColor(context.resources.getColor(textColorRes))
    }
}

@BindingAdapter("isNicknameDuplication")
fun TextView.setIsNicknameDuplication(isDuplicated: Boolean?) {
    text = if (isDuplicated != null) {
        if (isDuplicated) {
            context.getString(R.string.error_nickname_duplication)
        } else {
            context.getString(R.string.success_nickname_duplication_check)
        }
    } else {
        ""
    }
}