package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.EventResponse
import com.dicoding.azanul.footballapps.model.Team
import com.dicoding.azanul.footballapps.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {
    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var detailView: DetailView

    @Mock
    private  lateinit var detailPresenter: DetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        detailPresenter = DetailPresenter(detailView, apiRepository, gson)
    }

    @Test
    fun getEventDetail() {
        val events : MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getEventDetail("441613")),
                    EventResponse::class.java
                )
            ).thenReturn(response)

            detailPresenter.getEventDetail("441613")
            Mockito.verify(detailView).showLoading()
            Mockito.verify(detailView).showEventDetail(events)
            Mockito.verify(detailView).hideLoading()
        }
    }

    @Test
    fun getTeamLogoAway() {

        val teams : MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133604"
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamDetailById(idTeam)),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            detailPresenter.getTeamLogoAway(idTeam)
            Mockito.verify(detailView).showLoading()
            Mockito.verify(detailView).showTeamAwayLogo(teams)
            Mockito.verify(detailView).hideLoading()
        }
    }

    @Test
    fun getTeamLogoHome() {
        val teams : MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val idTeam = "133604"
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getTeamDetailById(idTeam)),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            detailPresenter.getTeamLogoHome(idTeam)
            Mockito.verify(detailView).showLoading()
            Mockito.verify(detailView).showTeamHomeLogo(teams)
            Mockito.verify(detailView).hideLoading()
        }
    }

}