package com.dicoding.azanul.footballapps.fragment.mainfragment.matchfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.adapter.MatchAdapter
import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.mvp.MatchPresenter
import com.dicoding.azanul.footballapps.mvp.MatchView
import com.dicoding.azanul.footballapps.util.invisible
import com.dicoding.azanul.footballapps.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FragmentNext : Fragment() , MatchView {
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var spinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                id = R.id.linear_last
                orientation = LinearLayout.VERTICAL

                topPadding = dip(36)
                leftPadding = dip(16)
                rightPadding = dip(16)

                spinner = spinner {
                    id = R.id.spinner
                }

                swipeRefresh = swipeRefreshLayout{
                    setColorSchemeResources(android.R.color.background_dark,android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            id = R.id.list_team_last
                            lparams (width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar {
                        }.lparams{
                            centerHorizontally()
                        }
                    }

                }
            }
        }.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this,request,gson)

        val spinnerItems = resources.getStringArray(R.array.league)

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = MatchAdapter(events)
        listTeam.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val getidLeague = spinner.selectedItem.toString()
                val idLeague = getIdLeague(getidLeague)
                presenter.getEventNext(idLeague)
                adapter = MatchAdapter(events)
                listTeam.adapter = adapter
            }

        }
        swipeRefresh.onRefresh {
            val getidLeague = spinner.selectedItem.toString()
            val idLeague = getIdLeague(getidLeague)
            presenter.getEventNext(idLeague)
        }
    }

    fun getIdLeague(spinnerItems : String): String{
        return when(spinnerItems){
            "English Premier League"->  "4328"
            "English League Championship" -> "4329"
            "German Bundesliga" -> "4331"
            "Italian Serie A" -> "4332"
            "French Ligue 1" -> "4334"
            "Spanish La Liga" -> "4335"
            else -> "4328"
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

}