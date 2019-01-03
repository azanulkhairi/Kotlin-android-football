package com.dicoding.azanul.footballapps.mvp

import android.util.EventLog
import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.EventResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var matchView: MatchView

    @Mock
    private  lateinit var matchPresenter: MatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        matchPresenter = MatchPresenter(matchView, apiRepository, gson)
    }

    @Test
    fun getEventLast() {
        val events : MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getEventsLast("4328")),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            matchPresenter.getEventLast("4328")
            Mockito.verify(matchView).showLoading()
            Mockito.verify(matchView).showTeamList(events)
            Mockito.verify(matchView).hideLoading()
        }
    }

    @Test
    fun getEventNext() {
        val events : MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getEventsNext("4328")),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            matchPresenter.getEventNext("4328")
            Mockito.verify(matchView).showLoading()
            Mockito.verify(matchView).showTeamList(events)
            Mockito.verify(matchView).hideLoading()
        }
    }

    @Test
    fun getEventSearch() {
        val events : MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getSearchMatch("barcelona")),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            matchPresenter.getEventSearch("barcelona")
            Mockito.verify(matchView).showLoading()
            Mockito.verify(matchView).showTeamList(events)
            Mockito.verify(matchView).hideLoading()
        }
    }
}