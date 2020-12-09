package com.hydra.starbucksmock.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hydra.starbucksmock.R
import com.hydra.starbucksmock.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by inject()

    private lateinit var portalAdapter: PortalPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        portalAdapter = PortalPagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }

    private fun initView() {
        portal_tabs.setupWithViewPager(portal_area)

        with(portalAdapter) {
            if (count == 0) {
                addTab(Fragment1())
                addTab(Fragment1())
                addTab(Fragment1())
            }
            portal_area.adapter = this
        }
    }

    inner class PortalPagerAdapter(fm: FragmentManager, behavior: Int) :
        FragmentStatePagerAdapter(fm, behavior) {
        private val tabTitles =
            arrayOf(
                R.string.title_home,
                R.string.title_dashboard,
                R.string.title_notifications
            )
        private val fragments: MutableList<Fragment> = mutableListOf()

        override fun getPageTitle(position: Int): CharSequence? {
            return context?.getString(tabTitles[position])
        }

        fun addTab(fragment: Fragment) {
            fragments.add(fragment)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }

}