package com.dicoding.azanul.footballapps.fragment.mainfragment.teamdetailfragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.adapter.PlayerAdapter
import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.model.Player
import com.dicoding.azanul.footballapps.model.Team
import com.dicoding.azanul.footballapps.mvp.PlayerPresenter
import com.dicoding.azanul.footballapps.mvp.PlayerView
import com.dicoding.azanul.footballapps.util.invisible
import com.dicoding.azanul.footballapps.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private var teams: Team? = null

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPlayer.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentPlayer : Fragment(), PlayerView {
 // TODO: Rename and change types of parameters
    private var teams: Team? = null

    private lateinit var listPlayer: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teams = it.getParcelable("GET_OBJECT_TEAMS")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return UI {
            linearLayout {
                id = R.id.linear_player
                orientation = LinearLayout.VERTICAL

                topPadding = dip(36)
                leftPadding = dip(16)
                rightPadding = dip(16)

                swipeRefresh = swipeRefreshLayout{
                    setColorSchemeResources(android.R.color.background_dark,android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listPlayer = recyclerView {
                            id = R.id.list_player
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
        presenter = PlayerPresenter(this,request,gson)
        val clubname = teams?.teamName
        presenter.getPlayer(clubname)
        adapter = PlayerAdapter(players)
        listPlayer.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
     }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentPlayer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(team: Team) =
            FragmentPlayer().apply {
                arguments = Bundle().apply {
                    putParcelable("GET_OBJECT_TEAMS", team)
                }
            }
    }
}
