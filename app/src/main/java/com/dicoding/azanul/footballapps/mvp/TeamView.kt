package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}