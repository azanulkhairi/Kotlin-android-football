package com.dicoding.azanul.footballapps.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.dicoding.azanul.footballapps.fragment.mainfragment.favoritefragment.FavoriteMatchFragment
import com.dicoding.azanul.footballapps.fragment.mainfragment.favoritefragment.FavoriteTeamFragment

class FavoritesPagerAdapter (fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoriteMatchFragment()

            }
            else ->{
                FavoriteTeamFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Match"
            else -> { "Teams"}
        }
    }
}