package com.example.android_mvvm_template.common.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.common.ext.exhaustive
import com.example.android_mvvm_template.register.NicknameEnableStatus

@BindingAdapter("nicknameStatusForBackground")
fun View.setNicknameStatusForBackground(status: NicknameEnableStatus?) {
    val backgroundColorRes = when (status) {
        NicknameEnableStatus.NORMAL, null -> R.color.alto
        NicknameEnableStatus.ERROR -> R.color.coral_red_alpha_87
        NicknameEnableStatus.ENABLE -> R.color.blue500
    }.exhaustive
    setBackgroundResource(backgroundColorRes)
}