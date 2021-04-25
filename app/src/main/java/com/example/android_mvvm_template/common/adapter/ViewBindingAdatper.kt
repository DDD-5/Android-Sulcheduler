package com.example.android_mvvm_template.common.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.common.ext.exhaustive
import com.example.android_mvvm_template.register.NicknameEnableStatus

@BindingAdapter("nicknameStatusForBackground")
fun View.setNicknameStatusForBackground(status: NicknameEnableStatus?) {
    val backgroundColorRes = when (status) {
        NicknameEnableStatus.NORMAL, null -> R.color.un_focused
        NicknameEnableStatus.ERROR -> R.color.input_error
        NicknameEnableStatus.ENABLE -> R.color.active_blue
    }.exhaustive
    setBackgroundResource(backgroundColorRes)
}