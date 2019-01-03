package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.Player
import com.dicoding.azanul.footballapps.model.PlayerResponse
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

class TeamPresenterTest {

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var teamView: TeamView

    @Mock
    private  lateinit var teamPresenter: TeamPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        teamPresenter = TeamPresenter(teamView, apiRepository, gson)
    }

    @Test
    fun getTeams() {
            val teams : MutableList<Team> = mutableListOf()
            val response = TeamResponse(teams)
            GlobalScope.launch {
                Mockito.`when`(
                    gson.fromJson(
                        apiRepository.doRequest(TheSportDBApi.getTeams("English%20Premier%20League")),
                        TeamResponse::class.java
                    )
                ).thenReturn(response)

                teamPresenter.getTeams("English%20Premier%20League")
                Mockito.verify(teamView).showLoading()
                Mockito.verify(teamView).showTeamList(teams)
                Mockito.verify(teamView).hideLoading()
            }
        }


    @Test
    fun getSearchTeams() {
        val teams : MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getSearchTeam("arsenal")),
                    TeamResponse::class.java
                )
            ).thenReturn(response)

            teamPresenter.getSearchTeams("arsenal")
            Mockito.verify(teamView).showLoading()
            Mockito.verify(teamView).showTeamList(teams)
            Mockito.verify(teamView).hideLoading()
        }

    }
}