package com.example.android_mvvm_template.di

import com.example.android_mvvm_template.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getHomeModule() = module {
    viewModel { HomeViewModel(get()) }
}