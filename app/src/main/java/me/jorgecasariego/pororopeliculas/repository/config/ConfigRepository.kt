package me.jorgecasariego.pororopeliculas.repository.config

import me.jorgecasariego.pororopeliculas.model.UserInformation
import me.jorgecasariego.pororopeliculas.repository.config.datasource.ILocalConfigDataSource
import me.jorgecasariego.pororopeliculas.repository.config.datasource.LocalConfigDataSource

class ConfigRepository(
        private val localConfigDataSource: ILocalConfigDataSource
) : IConfigRepository {

    override fun setUserInfo(userInfo: UserInformation) {
        localConfigDataSource.setUserInfo(userInfo)
    }

    override fun getCurrentUserInfo() =
        localConfigDataSource.getCurrentUserInfo()
}