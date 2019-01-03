package com.dicoding.azanul.footballapps.fragment.mainfragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.util.FavoritesPagerAdapter
import com.dicoding.azanul.footballapps.util.MyPagerAdapter

class FragmentFavorites : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tablayout: TabLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View = inflater.inflate(R.layout.fragment_favorite_layout, container,false)
        viewPager = view.findViewById(R.id.viewpager_match_favorite)
        tablayout = view.findViewById(R.id.tabs_favorite)
        val fragmentAdpter = FavoritesPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdpter
        tablayout.setupWithViewPager(viewPager)
        return view
    }
}