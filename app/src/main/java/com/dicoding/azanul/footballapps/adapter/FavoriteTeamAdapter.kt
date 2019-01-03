package com.dicoding.azanul.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.activity.TeamDetailActivity
import com.dicoding.azanul.footballapps.database.FavoriteTeam
import com.dicoding.azanul.footballapps.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class FavoriteTeamAdapter (private val favorite: List<FavoriteTeam>)
    : RecyclerView.Adapter<FavoriteTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(FavTeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bindItem(favorite[position])
    }

    override fun getItemCount(): Int = favorite.size

}

class FavTeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.logo_team
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }
}

class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val logoTeam : ImageView = view.find(R.id.logo_team)
    private val teamName : TextView = view.find(R.id.team_name)

    fun bindItem(team : FavoriteTeam){
        Picasso.get().load(team.strTeamBadge).into(logoTeam)
        teamName.text = team.strTeamName

        val teams = Team(team.teamId,team.strTeamName,team.strTeamBadge, team.strTeamYear, team.strTeamStadium, team.strDesc)

        itemView.setOnClickListener {
            itemView.context.startActivity<TeamDetailActivity>("teams" to teams)
        }
    }
}