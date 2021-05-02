package com.example.android_mvvm_template

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_mvvm_template.databinding.FragmentFirstBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FirstFragment : BaseFragment<FragmentFirstBinding, MainViewModel>(R.layout.fragment_first) {
    override val viewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(binding: FragmentFirstBinding, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(1000L)
            val action = FirstFragmentDirections.actionFirstFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    override fun initBinding(binding: FragmentFirstBinding) {
        binding.run {
        }
    }

    override fun subscribeViewModel(viewModel: MainViewModel) {
    }

}
