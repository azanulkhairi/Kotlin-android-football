package com.dicoding.azanul.footballapps.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.azanul.footballapps.R
import com.dicoding.azanul.footballapps.model.Player
import com.dicoding.azanul.footballapps.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var playerDetail: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        playerDetail= intent.getParcelableExtra("players")

        var playerPict = find<ImageView>(R.id.image_player_detail)
        Picasso.get().load(playerDetail.strFanart1).into(playerPict)

        var weight = find<TextView>(R.id.val_weight)
        var height = find<TextView>(R.id.val_height)
        var position = find<TextView>(R.id.position)
        var desc = find<TextView>(R.id.player_description)

        weight.text = playerDetail.strWeight
        height.text = playerDetail.strHeight
        position.text = playerDetail.strPosition
        desc.text = playerDetail.strDescriptionEN
    }
}
