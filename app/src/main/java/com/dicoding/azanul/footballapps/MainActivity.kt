package com.dicoding.azanul.footballapps

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.dicoding.azanul.footballapps.fragment.mainfragment.FragmentFavorites
import com.dicoding.azanul.footballapps.fragment.mainfragment.FragmentMatch
import com.dicoding.azanul.footballapps.fragment.mainfragment.FragmentTeams
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_match -> {
                val fragment = FragmentMatch()
                setFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_teams -> {
                val fragment = FragmentTeams()
                setFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                val fragment = FragmentFavorites()
                setFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = FragmentMatch()
        setFragment(fragment)
    }
}
