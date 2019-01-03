package com.dicoding.azanul.footballapps.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dicoding.azanul.footballapps.fragment.mainfragment.teamdetailfragment.FragmentOverview
import com.dicoding.azanul.footballapps.fragment.mainfragment.teamdetailfragment.FragmentPlayer

class TeamDetailPagerAdapter (fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    val fragments = ArrayList<Fragment>()
    val titles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }
}