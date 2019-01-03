package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.model.Event

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Event>)
}