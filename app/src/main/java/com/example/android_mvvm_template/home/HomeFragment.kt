package com.example.android_mvvm_template.home

import androidx.recyclerview.widget.PagerSnapHelper
import com.example.android_mvvm_template.BaseFragment
import com.example.android_mvvm_template.R
import com.example.android_mvvm_template.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()

    private val adapter by lazy {
        TestAdapter()
    }

    private val snapHelper = PagerSnapHelper().apply {

    }

    override fun initBinding(binding: FragmentHomeBinding) {
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            calendarView.setDate(LocalDate.now(), LocalDate.now().plusYears(1))

        }
    }

    override fun subscribeViewModel(viewModel: HomeViewModel) {
        viewModel.run {
            init()
            list.observe(viewLifecycleOwner) {
//                adapter.submitList(it)
            }
        }
    }
}