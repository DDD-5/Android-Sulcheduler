package com.example.android_mvvm_template.register

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*

class RegisterViewModel : ViewModel() {
    val nickname = MutableLiveData("")
    val nicknameCountString = nickname.map {
        "${it.count()}/$MAX_LENGTH_OF_USER_NAME"
    }

    val birthYear = MutableLiveData<String>()
    val isBirthYearHasOnlyDigit: Boolean
        get() = birthYear.value?.isDigitsOnly() == true

    private val _isNicknameDuplication = MutableLiveData<Boolean?>()
    val isNicknameDuplication: LiveData<Boolean?> = _isNicknameDuplication

    val nicknameEnableStatus = MediatorLiveData<NicknameEnableStatus>()

    init {
        initMediatorLiveData()
    }

    fun appendTextToBirthYear(text: String) {
        birthYear.value = birthYear.value?.plus(text)
    }

    fun onFocusBirthYear() {
        birthYear.value = birthYear.value?.filter { it.isDigit() }
    }

    fun onClickDuplicationCheckBtn() {
        if (nickname.value.isNullOrEmpty().not()) {
            // TODO API 연동
            _isNicknameDuplication.value = true
        }
    }

    fun onBirthYearChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    private fun initMediatorLiveData() {
        nicknameEnableStatus.run {
            addSource(nickname) {
                _isNicknameDuplication.value = null
                value = if (it.isEmpty()) {
                    NicknameEnableStatus.NORMAL
                } else {
                    NicknameEnableStatus.ENABLE
                }
            }
            addSource(isNicknameDuplication) {
                if (it != null) {
                    value = if (it) NicknameEnableStatus.ERROR else NicknameEnableStatus.ENABLE
                }
            }
        }
    }

    companion object {
        const val MAX_LENGTH_OF_USER_NAME = 12
    }
}