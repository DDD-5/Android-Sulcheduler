package com.example.android_mvvm_template.register

import androidx.lifecycle.*

class RegisterViewModel : ViewModel() {
    val nickname = MutableLiveData("")

    val nicknameCountString = nickname.map {
        "${it.count()}/$MAX_LENGTH_OF_USER_NAME"
    }

    val nicknameEnableStatus = MediatorLiveData<NicknameEnableStatus>()

    val birthYear = MutableLiveData<String>()

    private val _isNicknameDuplication = MutableLiveData<Boolean?>()
    val isNicknameDuplication: LiveData<Boolean?> = _isNicknameDuplication

    private val _isFemale = MutableLiveData(true)
    val isFemale: LiveData<Boolean> = _isFemale


    init {
        initMediatorLiveData()
    }


    fun onClickDuplicationCheckBtn() {
        if (nickname.value.isNullOrEmpty().not()) {
            // TODO API 연동
            _isNicknameDuplication.value = true
        }
    }

    fun onClickGender(isClickFemale: Boolean) {
        _isFemale.value = isClickFemale
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