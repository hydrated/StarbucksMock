package com.hydra.starbucksmock

import com.hydra.starbucksmock.ui.viewmodel.Fragment1ViewModel
import com.hydra.starbucksmock.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { Fragment1ViewModel() }
}