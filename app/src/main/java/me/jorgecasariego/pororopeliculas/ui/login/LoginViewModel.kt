package me.jorgecasariego.pororopeliculas.ui.login

import androidx.lifecycle.ViewModel
import me.jorgecasariego.pororopeliculas.model.UserInformation
import me.jorgecasariego.pororopeliculas.repository.config.IConfigRepository

class LoginViewModel(
        private val configRepository: IConfigRepository
): ViewModel() {

    fun setUserInfo(userInfo: UserInformation) {
        configRepository.setUserInfo(userInfo)
    }
}