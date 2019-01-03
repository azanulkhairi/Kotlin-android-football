package com.dicoding.azanul.footballapps.mvp

import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.api.TheSportDBApi
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.EventResponse
import com.dicoding.azanul.footballapps.model.Player
import com.dicoding.azanul.footballapps.model.PlayerResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {
    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var playerView: PlayerView

    @Mock
    private  lateinit var playerPresenter: PlayerPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        playerPresenter = PlayerPresenter(playerView, apiRepository, gson)
    }

    @Test
    fun getPlayer() {
        val player : MutableList<Player> = mutableListOf()
        val response = PlayerResponse(player)
        GlobalScope.launch {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getPlayer("arsenal")),
                    PlayerResponse::class.java
                )
            ).thenReturn(response)

            playerPresenter.getPlayer("arsenal")
            Mockito.verify(playerView).showLoading()
            Mockito.verify(playerView).showPlayerList(player)
            Mockito.verify(playerView).hideLoading()
        }
    }
}