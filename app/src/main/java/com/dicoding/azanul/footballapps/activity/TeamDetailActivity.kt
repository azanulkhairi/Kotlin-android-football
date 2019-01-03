package com.dicoding.azanul.footballapps.activity

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.database.FavoriteTeam
import com.dicoding.azanul.footballapps.database.database
import com.dicoding.azanul.footballapps.fragment.mainfragment.teamdetailfragment.FragmentOverview
import com.dicoding.azanul.footballapps.fragment.mainfragment.teamdetailfragment.FragmentPlayer
import com.dicoding.azanul.footballapps.model.Team
import com.dicoding.azanul.footballapps.mvp.TeamView
import com.dicoding.azanul.footballapps.util.TeamDetailPagerAdapter
import com.dicoding.azanul.footballapps.util.invisible
import com.dicoding.azanul.footballapps.util.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.find

class TeamDetailActivity: AppCompatActivity(){

    private lateinit var teamDetail: Team
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        swipeRefresh = find(R.id.sw_team_detail)
        teamDetail= intent.getParcelableExtra("teams")
        favoriteState()
        var teamBadge = find<ImageView>(R.id.image_team_detail)
        Picasso.get().load(teamDetail.teamBadge).into(teamBadge)

        var teamName = find<TextView>(R.id.name_team_detail)
        var teamYr = find<TextView>(R.id.year_team_detail)
        var teamStd = find<TextView>(R.id.std_team_detail)

        teamName.text = teamDetail.teamName
        teamYr.text = teamDetail.teamFormedYear
        teamStd.text = teamDetail.teamStadium

        teamDetailViewPager(view_pager_team_detail)
        tabs_detail_team.setupWithViewPager(view_pager_team_detail)
    }
      private fun teamDetailViewPager(viewPager : ViewPager){
        val fragmentPagerAdapter = TeamDetailPagerAdapter(supportFragmentManager)
        val teams = teamDetail
        val overview = FragmentOverview.newInstance(teams)
        val player = FragmentPlayer.newInstance(teams)
        fragmentPagerAdapter.addFragment(overview,"Overview")
        fragmentPagerAdapter.addFragment(player,"Player")
        viewPager.adapter = fragmentPagerAdapter
      }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.detail_menu, menu)
    menuItem = menu
    setFavorite()
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        finish()
        true
      }
      R.id.add_to_favorite -> {
        if (isFavorite) removeFromFavorite() else addToFavorite()
          isFavorite = !isFavorite
        setFavorite()
        true
      }

      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun addToFavorite() {
    try {
      database.use {
        insert(
          FavoriteTeam.TABLE_FAVORITE_TEAM,
          FavoriteTeam.TEAM_ID to teamDetail.teamId,
          FavoriteTeam.STR_TEAM_BADGE to teamDetail.teamBadge,
          FavoriteTeam.STR_TEAM_NAME to teamDetail.teamName,
          FavoriteTeam.STR_TEAM_YEAR to teamDetail.teamFormedYear,
          FavoriteTeam.STR_TEAM_STADIUM to teamDetail.teamStadium,
          FavoriteTeam.STR_TEAM_DESC to teamDetail.teamDescription
        )

      }
      snackbar(swipeRefresh, "Added to favorite").show()
    } catch (e: SQLiteConstraintException) {
      snackbar(swipeRefresh, e.localizedMessage).show()
    }
  }

  private fun removeFromFavorite() {
    try {
      database.use {
        delete(
          FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
          "id" to teamDetail.teamId.toString()
        )
      }
      snackbar(swipeRefresh, "Removed to favorite").show()
    } catch (e: SQLiteConstraintException) {
      snackbar(swipeRefresh, e.localizedMessage).show()
    }
  }

  private fun favoriteState() {
    database.use {
      val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
        .whereArgs(
          "(TEAM_ID = {id})",
          "id" to teamDetail.teamId.toString()
        )
      val favorite = result.parseList(classParser<FavoriteTeam>())
      if (!favorite.isEmpty()) isFavorite = true
    }
  }

  private fun setFavorite() {
    if (isFavorite)
      menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
    else
      menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
  }


}