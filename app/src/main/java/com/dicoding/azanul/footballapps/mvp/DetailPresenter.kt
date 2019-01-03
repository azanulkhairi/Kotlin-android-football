package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.EventResponse
import com.dicoding.azanul.footballapps.model.TeamResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter (private val view : DetailView,
                       private  val apiRepository: ApiRepository,
                       private val gson: Gson) {

    fun getEventDetail(idEvent : String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventDetail(idEvent)),
                    EventResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showEventDetail(data.events)
            }
        }
    }


    fun getTeamLogoAway(idTeam: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetailById(idTeam)),
                    TeamResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamAwayLogo(data.teams)
            }
        }
    }

    fun getTeamLogoHome(idTeam: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamDetailById(idTeam)),
                    TeamResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamHomeLogo(data.teams)
            }
        }
    }
}

