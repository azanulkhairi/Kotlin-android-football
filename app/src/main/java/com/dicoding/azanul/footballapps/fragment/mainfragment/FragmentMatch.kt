package com.dicoding.azanul.footballapps.fragment.mainfragment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.adapter.MatchAdapter
import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.mvp.MatchPresenter
import com.dicoding.azanul.footballapps.mvp.MatchView
import com.dicoding.azanul.footballapps.util.MyPagerAdapter
import com.dicoding.azanul.footballapps.util.invisible
import com.dicoding.azanul.footballapps.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FragmentMatch : Fragment(), MatchView {

    private lateinit var viewPager: ViewPager
    private lateinit var tablayout: TabLayout
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_match_layout, container, false)
        setHasOptionsMenu(true)

        swipeRefresh = view.findViewById(R.id.sw_match)
        swipeRefresh.setColorSchemeResources(
            android.R.color.background_dark, android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        listTeam = view.findViewById(R.id.rec_match)
        listTeam.layoutManager = LinearLayoutManager(ctx)
        viewPager = view.findViewById(R.id.viewpager_match)
        tablayout = view.findViewById(R.id.tabs_match)
        val fragmentAdpter = MyPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdpter
        tablayout.setupWithViewPager(viewPager)
         return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search,menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(textSearch: String?): Boolean {
                    if(textSearch!!.isNotEmpty()){
                        val request = ApiRepository()
                        val gson = Gson()
                        val linearsearch = find<LinearLayout>(R.id.linear_search)
                        linearsearch.visible()
                        presenter = MatchPresenter(this@FragmentMatch,request,gson)
                        presenter.getEventSearch(textSearch)
                        adapter = MatchAdapter(events)
                        listTeam.adapter = adapter

                    }else{
                        listTeam.adapter = null
                    }
                    return true
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun showLoading() {
       // progressBar.visible()
    }

    override fun hideLoading() {
       // progressBar.invisible()
    }

    override fun showTeamList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

}