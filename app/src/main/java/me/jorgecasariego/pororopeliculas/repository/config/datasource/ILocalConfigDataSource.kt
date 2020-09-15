package me.jorgecasariego.pororopeliculas.repository.config.datasource

import me.jorgecasariego.pororopeliculas.model.UserInformation

interface ILocalConfigDataSource {

    fun setUserInfo(userInfo: UserInformation)

    fun getCurrentUserInfo(): UserInformation?
}