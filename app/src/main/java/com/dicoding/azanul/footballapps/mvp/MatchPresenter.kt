package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.EventResponse
import com.dicoding.azanul.footballapps.model.EventSearchResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter (private val view : MatchView,
                      private  val apiRepository: ApiRepository,
                      private val gson: Gson
) {

    fun getEventLast(idLeague: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventsLast(idLeague)),
                EventResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }

    fun getEventNext(idLeague: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventsNext(idLeague)),
                EventResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamList(data.events)
            }
        }
    }

    fun getEventSearch(strEvent: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchMatch(strEvent)),
                EventSearchResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showTeamList(data.event)
            }
        }
    }



}