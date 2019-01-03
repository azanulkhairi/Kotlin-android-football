package com.dicoding.azanul.footballapps.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.activity.DetailActivity
import com.dicoding.azanul.footballapps.activity.PlayerDetailActivity
import com.dicoding.azanul.footballapps.model.Event
import com.dicoding.azanul.footballapps.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerAdapter(private val players : List<Player>): RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position])
    }

}

class PlayerViewHolder(view : View) : RecyclerView.ViewHolder(view){

    private val imagePlayer : ImageView = view.find(R.id.image_player)
    private val textviewPlayer : TextView = view.find(R.id.textview_player)
    private val position_player : TextView = view.find(R.id.position_player)

    fun bindItem(player : Player){
        textviewPlayer.text = player.strPlayer
        position_player.text = player.strPosition
        Picasso.get().load(player.strCutout).into(imagePlayer)

        itemView.setOnClickListener {

            itemView.context.startActivity<PlayerDetailActivity>("players" to player)
        }
    }
}

class PlayerUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>):View{
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_VERTICAL

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    imageView{
                        id = R.id.image_player
                    }.lparams{
                        width = dip(80)
                        height =dip(80)
                        gravity = Gravity.START
                    }
                    textView {
                        id = R.id.textview_player
                        padding = dip(16)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textView {
                        id = R.id.position_player
                        gravity = Gravity.END
                    }
                }
            }
        }
    }

}
