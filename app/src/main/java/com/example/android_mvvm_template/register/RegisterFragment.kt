package com.example.android_mvvm_template.register

import com.example.android_mvvm_template.BaseFragment
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register) {
    override val viewModel: RegisterViewModel by viewModel()

    override fun initBinding(binding: FragmentRegisterBinding) {
        with(binding) {
            registerViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun subscribeViewModel(viewModel: RegisterViewModel) {
    }

}