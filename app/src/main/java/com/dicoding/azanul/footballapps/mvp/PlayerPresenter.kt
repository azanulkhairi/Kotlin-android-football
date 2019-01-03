package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.EventResponse
import com.dicoding.azanul.footballapps.model.PlayerResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter (private val view : PlayerView,
                       private  val apiRepository: ApiRepository,
                       private val gson: Gson) {
    fun getPlayer(strTeam : String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayer(strTeam)),
                PlayerResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showPlayerList(data.player)
            }
        }
    }
}