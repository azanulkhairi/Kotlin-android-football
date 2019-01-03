package com.dicoding.azanul.footballapps.activity

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.api.ApiRepository
import com.dicoding.azanul.footballapps.database.FavoriteMatch
import com.dicoding.azanul.footballapps.database.database
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.Team
import com.dicoding.azanul.footballapps.mvp.DetailPresenter
import com.dicoding.azanul.footballapps.mvp.DetailView
import com.dicoding.azanul.footballapps.util.invisible
import com.dicoding.azanul.footballapps.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailActivity : AppCompatActivity() , DetailView {

    private lateinit var presenter: DetailPresenter
    private lateinit var progressBar: ProgressBar
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var eventDetail: Event

    private lateinit var swipeRefresh: SwipeRefreshLayout

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.VERTICAL
            gravity = LinearLayout.TEXT_ALIGNMENT_GRAVITY


            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    android.R.color.background_dark,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )
                scrollView {
                    isVerticalScrollBarEnabled = false

                    relativeLayout {
                        lparams(width = matchParent, height = matchParent)
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                        linearLayout {
                            id = R.id.date_event
                            gravity = LinearLayout.TEXT_ALIGNMENT_GRAVITY
                            textView {
                                id = R.id.match_date
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.logo
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                imageView {
                                    id = R.id.home_logo

                                }.lparams {
                                    width = dip(50)
                                    height = dip(50)
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                imageView {
                                    id = R.id.away_logo
                                }.lparams {
                                    width = dip(50)
                                    height = dip(50)
                                }
                            }
                        }.lparams {
                            below(R.id.date_event)
                            width = matchParent
                            height = wrapContent
                        }
                        linearLayout {
                            id = R.id.score
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    padding = dip(10)
                                    id = R.id.home_team
                                    textSize = 20f
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.home_score
                                    textSize = 20f
                                    padding = dip(10)
                                }
                                textView("vs") {
                                    textSize = 15f
                                    padding = dip(10)
                                }
                                textView {
                                    id = R.id.away_score
                                    textSize = 20f
                                    padding = dip(10)
                                }
                            }

                            linearLayout {
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.away_team
                                    textSize = 20f
                                    padding = dip(10)
                                }
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            below(R.id.logo)
                        }

                        linearLayout {
                            id = R.id.goal
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    gravity = Gravity.START
                                    id = R.id.goal_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("goals") { }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.goal_away
                                }

                            }
                        }.lparams {
                            below(R.id.score)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.shot
                            linearLayout {
                                gravity = Gravity.END
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.shot_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("shot") {
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.shot_away
                                }
                            }

                        }.lparams {
                            below(R.id.goal)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.lineup
                            gravity = Gravity.CENTER
                            textView("LINE") {
                                textSize = 18f
                            }
                        }.lparams {
                            below(R.id.shot)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.keeper
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.goalkeeper_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("GoalKeeper") {}
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.goalkeeper_away
                                }
                            }
                        }.lparams {
                            below(R.id.lineup)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.defender
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.defender_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("Defend") {}
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.defender_away
                                }
                            }
                        }.lparams {
                            below(R.id.keeper)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.midfield
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.midfielder_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("Midfield") {}
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.midfielder_away
                                }
                            }
                        }.lparams {
                            below(R.id.defender)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            id = R.id.forward
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.forward_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("Forward") { }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.forward_away
                                }
                            }
                        }.lparams {
                            below(R.id.midfield)
                            width = matchParent
                            height = wrapContent
                        }

                        linearLayout {
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.subs_home
                                }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView("Substitutes") { }
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                lparams(width = 0, height = wrapContent, weight = 1f)
                                textView {
                                    id = R.id.subs_away
                                }
                            }
                        }.lparams {
                            below(R.id.forward)
                            width = matchParent
                            height = wrapContent
                        }

                    }
                }
            }
        }


        eventDetail = intent.getParcelableExtra("event")
        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        presenter.getEventDetail(eventDetail.idEvent)
        presenter.getTeamLogoAway(eventDetail.idAwayTeam)
        presenter.getTeamLogoHome(eventDetail.idHomeTeam)


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
                    FavoriteMatch.TABLE_FAVORITE,
                    FavoriteMatch.EVENT_ID to eventDetail.idEvent,
                    FavoriteMatch.EVENT_HOMETEAM to eventDetail.strHomeTeam,
                    FavoriteMatch.EVENT_HOMESCORE to eventDetail.intHomeScore,
                    FavoriteMatch.EVENT_AWAYTEAM to eventDetail.strAwayTeam,
                    FavoriteMatch.EVENT_AWAYSCORE to eventDetail.intAwayScore
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
                    FavoriteMatch.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to eventDetail.idEvent.toString()
                )
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to eventDetail.idEvent.toString()
                )
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventDetail(event: List<Event>) {

        swipeRefresh.isRefreshing = false
        val matchDate: TextView = find(R.id.match_date)
        val homeTeam: TextView = find(R.id.home_team)
        val homeScore: TextView = find(R.id.home_score)
        val awayTeam: TextView = find(R.id.away_team)
        val awayScore: TextView = find(R.id.away_score)
        val awayGoal: TextView = find(R.id.goal_away)
        val homeGoal: TextView = find(R.id.goal_home)
        val awayShot: TextView = find(R.id.shot_away)
        val homeShot: TextView = find(R.id.shot_home)
        val awayGk: TextView = find(R.id.goalkeeper_away)
        val homeGk: TextView = find(R.id.goalkeeper_home)
        val awayDef: TextView = find(R.id.defender_away)
        val homeDef: TextView = find(R.id.defender_home)
        val awayMid: TextView = find(R.id.midfielder_away)
        val homeMid: TextView = find(R.id.midfielder_home)
        val awayForw: TextView = find(R.id.forward_away)
        val homeForw: TextView = find(R.id.forward_home)
        val awaySub: TextView = find(R.id.subs_away)
        val homeSub: TextView = find(R.id.subs_home)

        matchDate.text = event[0].dateEvent
        homeTeam.text = event[0].strHomeTeam
        homeScore.text = event[0].intHomeScore
        awayTeam.text = event[0].strAwayTeam
        awayScore.text = event[0].intAwayScore
        awayGoal.text = event[0].strAwayGoalDetails
        homeGoal.text = event[0].strHomeGoalDetails
        awayShot.text = event[0].intAwayShots
        homeShot.text = event[0].intHomeShots
        awayGk.text = event[0].strAwayLineupGoalkeeper
        homeGk.text = event[0].strHomeLineupGoalkeeper
        homeDef.text = event[0].strHomeLineupDefense
        awayDef.text = event[0].strAwayLineupDefense
        awayMid.text = event[0].strAwayLineupMidfield
        homeMid.text = event[0].strHomeLineupMidfield
        awayForw.text = event[0].strAwayLineupForward
        homeForw.text = event[0].strHomeLineupForward
        awaySub.text = event[0].strAwayLineupSubstitutes
        homeSub.text = event[0].strHomeLineupSubstitutes
    }


    override fun showTeamAwayLogo(team: List<Team>) {
        var awayLogo = find<ImageView>(R.id.away_logo)
        Picasso.get().load(team[0].teamBadge).into(awayLogo)
    }

    override fun showTeamHomeLogo(team: List<Team>) {

        var homeLogo = find<ImageView>(R.id.home_logo)
        Picasso.get().load(team[0].teamBadge).into(homeLogo)

    }
}


