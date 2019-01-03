package com.dicoding.azanul.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.activity.DetailActivity
import com.dicoding.azanul.footballapps.activity.TeamDetailActivity
import com.dicoding.azanul.footballapps.database.FavoriteMatch
import com.dicoding.azanul.footballapps.model.Event
import org.jetbrains.anko.*

class FavoriteAdapter(private val favorite: List<FavoriteMatch>)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position])
    }

    override fun getItemCount(): Int = favorite.size

}

class FavUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_VERTICAL
                linearLayout {
                    gravity = LinearLayout.TEXT_ALIGNMENT_GRAVITY
                    textView {
                        id = R.id.match_date
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                linearLayout {

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

                        textView {
                            text = "VS"
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
                        gravity = Gravity.CENTER_VERTICAL
                        lparams(width = 0, height = wrapContent, weight = 1f)
                        textView {
                            id = R.id.away_team
                            textSize = 20f
                            padding = dip(10)
                            gravity = Gravity.END
                        }

                    }

                }
            }
        }
        }
    }



class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val matchDate : TextView = view.find(R.id.match_date)
    private val homeTeam : TextView = view.find(R.id.home_team)
    private val homeScore : TextView = view.find(R.id.home_score)
    private val awayTeam : TextView = view.find(R.id.away_team)
    private val awayScore : TextView = view.find(R.id.away_score)

    fun bindItem(event : FavoriteMatch){
        matchDate.text = event.dateEvent
        homeTeam.text = event.strHomeTeam
        homeScore.text = event.intHomeScore
        awayTeam.text = event.strAwayTeam
        awayScore.text = event.intAwayScore
        val events = Event(event.eventId,event.dateEvent,event.strHomeTeam,event.intHomeScore,event.strAwayTeam,event.intAwayScore)



        itemView.setOnClickListener {
            itemView.context.startActivity<DetailActivity>("event" to events)
        }
    }
}