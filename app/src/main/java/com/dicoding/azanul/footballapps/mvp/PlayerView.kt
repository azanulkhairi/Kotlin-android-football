package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.model.Player
import com.dicoding.azanul.footballapps.model.Team

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}