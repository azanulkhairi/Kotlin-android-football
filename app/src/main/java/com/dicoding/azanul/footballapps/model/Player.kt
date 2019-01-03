package com.dicoding.azanul.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player (

    @SerializedName("idPlayer")
    var idPlayer :String? = null,

    @SerializedName("idTeam")
    var teamId: String? = null,

    @SerializedName("strNationality")
    var strNationality: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null,

    @SerializedName("strHeight")
    var strHeight: String? = null,

    @SerializedName("strWeight")
    var strWeight: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strFanart1")
    var strFanart1: String? = null,

    @SerializedName("strPosition")
    var strPosition: String? = null

):Parcelable