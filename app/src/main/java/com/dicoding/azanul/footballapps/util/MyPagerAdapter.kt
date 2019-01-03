package com.dicoding.azanul.footballapps.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dicoding.azanul.footballapps.fragment.mainfragment.matchfragment.FragmentLast
import com.dicoding.azanul.footballapps.fragment.mainfragment.matchfragment.FragmentNext

class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentLast()
            }
            else ->{
                FragmentNext()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Last Match"
            else -> { "Next Match"}
        }
    }
}