package me.jorgecasariego.pororopeliculas.repository.config

import me.jorgecasariego.pororopeliculas.model.UserInformation

interface IConfigRepository {

    fun setUserInfo(userInfo: UserInformation)

    fun getCurrentUserInfo(): UserInformation?

    fun getCurrentUserInfoByEmail(email: String): UserInformation?
}