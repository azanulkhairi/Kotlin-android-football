package com.dicoding.azanul.footballapps.database

data class FavoriteMatch(val id: Long?, val eventId: String?, val dateEvent: String?, val strHomeTeam: String?,
val intHomeScore: String?, val strAwayTeam : String?, val intAwayScore : String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val EVENT_HOMETEAM: String = "EVENT_HOMETEAM"
        const val EVENT_HOMESCORE: String = "EVENT_HOMESCORE"
        const val EVENT_AWAYTEAM: String = "EVENT_AWAYTEAM"
        const val EVENT_AWAYSCORE: String = "EVENT_AWAYSCORE"
    }
}