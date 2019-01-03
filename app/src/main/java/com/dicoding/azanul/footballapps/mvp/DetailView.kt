package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.Team

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(event : List<Event>)
    fun showTeamAwayLogo(team : List<Team>)
    fun showTeamHomeLogo(team : List<Team>)
}