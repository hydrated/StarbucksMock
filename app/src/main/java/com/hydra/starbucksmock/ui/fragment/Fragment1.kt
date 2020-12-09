package com.hydra.starbucksmock.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.hydra.starbucksmock.R
import com.hydra.starbucksmock.ui.viewmodel.Fragment1ViewModel
import com.hydra.starbucksmock.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_fragment1.*
import org.koin.android.ext.android.inject

class Fragment1 : Fragment() {

    private val fragment1ViewModel: Fragment1ViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        fragment1ViewModel.detailListModel.observe(viewLifecycleOwner, Observer {
            recyclerView.setModel(it)
        })
    }


}