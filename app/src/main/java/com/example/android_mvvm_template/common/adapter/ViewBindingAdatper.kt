package com.example.android_mvvm_template.common.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.common.ext.exhaustive
import com.example.android_mvvm_template.register.NicknameColorStatus

@BindingAdapter("nicknameStatusForBackground")
fun View.setNicknameStatusForBackground(status: NicknameColorStatus?) {
    val backgroundColorRes = when (status) {
        NicknameColorStatus.NORMAL, null -> R.color.alto
        NicknameColorStatus.ERROR -> R.color.coral_red_alpha_87
        NicknameColorStatus.ENABLE -> R.color.blue500
    }.exhaustive
    setBackgroundResource(backgroundColorRes)
}