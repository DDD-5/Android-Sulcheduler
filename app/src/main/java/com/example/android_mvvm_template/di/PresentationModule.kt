package com.example.android_mvvm_template.di

import com.example.android_mvvm_template.MainViewModel
import com.example.android_mvvm_template.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getPresentationModule() = module {
    viewModel { MainViewModel() }
    viewModel { RegisterViewModel() }
}