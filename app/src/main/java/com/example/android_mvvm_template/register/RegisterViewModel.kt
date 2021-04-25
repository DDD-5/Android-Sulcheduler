package com.example.android_mvvm_template.register

import androidx.lifecycle.*

class RegisterViewModel : ViewModel() {
    val nickname = MutableLiveData("")
    val nicknameCountString = nickname.map {
        "${it.count()}/$MAX_LENGTH_OF_USER_NAME"
    }

    private val _isNicknameDuplication = MutableLiveData<Boolean?>()
    val isNicknameDuplication: LiveData<Boolean?> = _isNicknameDuplication

    val nicknameEnableStatus = MediatorLiveData<NicknameEnableStatus>()

    init {
        initMediatorLiveData()
    }

    fun onClickDuplicationCheckBtn() {
        // TODO API 연동
        _isNicknameDuplication.value = true
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