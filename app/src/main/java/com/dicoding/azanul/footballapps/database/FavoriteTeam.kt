package com.dicoding.azanul.footballapps.database

data class FavoriteTeam (val id: Long?, val teamId: String?, val strTeamBadge: String?, val strTeamName: String?
                         ,val strTeamYear:String?,val strTeamStadium:String?, val strDesc : String?) {
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val STR_TEAM_BADGE: String = "STR_TEAM_BADGE"
        const val STR_TEAM_NAME: String = "STR_TEAM_NAME"
        const val STR_TEAM_YEAR: String = "STR_TEAM_YEAR"
        const val STR_TEAM_STADIUM: String = "STR_TEAM_STADIUM"
        const val STR_TEAM_DESC: String = "STR_TEAM_DESC"

    }
}