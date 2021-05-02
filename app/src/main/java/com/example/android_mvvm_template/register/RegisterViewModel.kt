package com.example.android_mvvm_template.register

import androidx.lifecycle.*

class RegisterViewModel : ViewModel() {
    val nickname = MutableLiveData("")
    val nicknameCountString = nickname.map {
        "${it.count()}/$MAX_LENGTH_OF_USER_NAME"
    }
    val nicknameColorStatus = MediatorLiveData<NicknameColorStatus>()

    val isOkButtonActive = MediatorLiveData<Boolean>()

    val birthYear = MutableLiveData<String>()
    private val isBirthYearValid: Boolean
        get() = birthYear.value != null && birthYear.value?.length == BIRTH_YEAR_LENGTH

    private val _isNicknameDuplication = MutableLiveData<Boolean?>()
    val isNicknameDuplication: LiveData<Boolean?> = _isNicknameDuplication

    private val _isFemale = MutableLiveData(true)
    val isFemale: LiveData<Boolean> = _isFemale

    init {
        initMediatorLiveData()
    }

    fun onClickOkBtn() {
        val nickname = nickname.value ?: return
        val birthYear = birthYear.value ?: return
        val isFemale = isFemale.value ?: return
        // TODO API 연동
    }

    fun onClickDuplicationCheckBtn() {
        if (nickname.value.isNullOrEmpty().not()) {
            // TODO API 연동
            _isNicknameDuplication.value = false
        }
    }

    fun onClickGender(isClickFemale: Boolean) {
        _isFemale.value = isClickFemale
    }

    private fun initMediatorLiveData() {
        isOkButtonActive.run {
            addSource(isNicknameDuplication) {
                value = it == false && isBirthYearValid
            }
            addSource(birthYear) {
                value = isBirthYearValid && isNicknameDuplication.value == false
            }
        }

        nicknameColorStatus.run {
            addSource(nickname) {
                _isNicknameDuplication.value = null
                value = if (it.isEmpty()) {
                    NicknameColorStatus.NORMAL
                } else {
                    NicknameColorStatus.ENABLE
                }
            }
            addSource(isNicknameDuplication) {
                if (it != null) {
                    value = if (it) NicknameColorStatus.ERROR else NicknameColorStatus.ENABLE
                }
            }
        }
    }

    companion object {
        const val MAX_LENGTH_OF_USER_NAME = 12
        const val BIRTH_YEAR_LENGTH = 4
    }
}